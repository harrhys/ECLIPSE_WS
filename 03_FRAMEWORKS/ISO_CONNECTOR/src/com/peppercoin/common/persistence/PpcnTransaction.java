package com.peppercoin.common.persistence;

import com.peppercoin.common.exception.PpcnError;
import com.peppercoin.common.platform.EjbUtil;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

public class PpcnTransaction {
	private UserTransaction tx = (UserTransaction) EjbUtil.lookup("UserTransaction");
	private boolean isOpen;
	private boolean didOpen;

	public PpcnTransaction() {
		Object ex = null;

		try {
			if (this.tx.getStatus() == 6) {
				this.didOpen = true;
				this.tx.begin();
			} else {
				this.didOpen = false;
			}
		} catch (SystemException var3) {
			ex = var3;
		} catch (NotSupportedException var4) {
			ex = var4;
		}

		if (ex != null) {
			throw new PpcnError("PpcnTransaction.begin", (Throwable) ex);
		} else {
			this.isOpen = true;
		}
	}

	public static boolean inTransaction() {
		UserTransaction tx = (UserTransaction) EjbUtil.lookup("UserTransaction");

		try {
			return tx.getStatus() != 6;
		} catch (SystemException var2) {
			throw new PpcnError("PpcnTransaction.inTransaction", var2);
		}
	}

	public boolean isOpen() {
		return this.isOpen;
	}

	public void commit() {
		this.checkOpen("commit");
		this.isOpen = false;
		if (this.didOpen) {
			Object ex = null;

			try {
				this.tx.commit();
			} catch (RollbackException var3) {
				ex = var3;
			} catch (HeuristicMixedException var4) {
				ex = var4;
			} catch (HeuristicRollbackException var5) {
				ex = var5;
			} catch (SystemException var6) {
				ex = var6;
			}

			if (ex != null) {
				throw new PpcnError("PpcnTransaction.commit", (Throwable) ex);
			}
		}
	}

	public void rollback() {
		this.checkOpen("rollback");
		this.isOpen = false;
		if (this.didOpen) {
			SystemException ex = null;

			try {
				this.tx.rollback();
			} catch (SystemException var3) {
				ex = var3;
			}

			if (ex != null) {
				throw new PpcnError("PpcnTransaction.commit", ex);
			}
		}
	}

	private void checkOpen(String where) {
		if (!this.isOpen) {
			throw new PpcnError("PpcnTransaction." + where + ": transaction is already closed.");
		}
	}
}