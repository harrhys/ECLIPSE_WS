package com.peppercoin.common.persistence;

import com.peppercoin.common.exception.PpcnError;
import com.peppercoin.common.util.Money;
import com.peppercoin.common.util.PpcnCurrency;
import com.peppercoin.common.util.PpcnDate;
import com.peppercoin.common.util.PpcnLogger;
import com.peppercoin.common.util.PpcnTime;
import com.peppercoin.common.util.PpcnLogger.Level;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PpcnStatement {
	private static final PpcnLogger logger;
	private PreparedStatement statement;
	private int column = 0;

	public PpcnStatement(PreparedStatement st) {
		this.statement = st;
	}

	public PpcnStatement(Connection c, String query) {
		try {
			logger.log("SQL", query, Level.DEBUG);
			this.statement = c.prepareStatement(query);
		} catch (SQLException var4) {
			throw new PpcnError("PpcnStatement.init(" + query + ")", var4);
		}
	}

	public PpcnResultSet executeQuery() {
		try {
			return new PpcnResultSet(this.statement.executeQuery());
		} catch (SQLException var2) {
			throw new PpcnError("PpcnStatement.executeQuery", var2);
		}
	}

	public void close() {
		try {
			this.statement.close();
		} catch (SQLException var2) {
			throw new PpcnError("PpcnStatement.close", var2);
		}
	}

	public int executeUpdate() {
		try {
			return this.statement.executeUpdate();
		} catch (SQLException var2) {
			throw new PpcnError("PpcnStatement.executeUpdate", var2);
		}
	}

	public void set(int v) {
		this.set(++this.column, v);
	}

	public void set(int col, int v) {
		try {
			this.statement.setInt(col, v);
			this.column = col;
		} catch (SQLException var4) {
			throw new PpcnError("PpcnStatement.set", var4);
		}
	}

	public void set(String v) {
		this.set(++this.column, v);
	}

	public void set(int col, String v) {
		try {
			this.statement.setString(col, v);
			this.column = col;
		} catch (SQLException var4) {
			throw new PpcnError("PpcnStatement.set", var4);
		}
	}

	public void set(Integer v) {
		this.set(++this.column, v);
	}

	public void set(int col, Integer v) {
		try {
			this.statement.setInt(col, v);
			this.column = col;
		} catch (SQLException var4) {
			throw new PpcnError("PpcnStatement.set", var4);
		}
	}

	public void set(Money v) {
		this.set(++this.column, v);
	}

	public void set(int col, Money v) {
		try {
			this.statement.setBigDecimal(col, v == null ? null : v.getAmount());
			this.column = col;
		} catch (SQLException var4) {
			throw new PpcnError("PpcnStatement.set", var4);
		}
	}

	public void set(PpcnCurrency v) {
		this.set(++this.column, v);
	}

	public void set(int col, PpcnCurrency v) {
		try {
			this.statement.setString(col, v == null ? null : v.getCurrencyCode());
			this.column = col;
		} catch (SQLException var4) {
			throw new PpcnError("PpcnStatement.set", var4);
		}
	}

	public void set(PpcnTime v) {
		this.set(++this.column, v);
	}

	public void set(int col, PpcnTime v) {
		try {
			this.statement.setString(col, v == null ? null : v.toDbString());
			this.column = col;
		} catch (SQLException var4) {
			throw new PpcnError("PpcnStatement.set", var4);
		}
	}

	public void set(PpcnDate v) {
		this.set(++this.column, v);
	}

	public void set(int col, PpcnDate v) {
		try {
			this.statement.setString(col, v == null ? null : v.toDbString());
		} catch (SQLException var4) {
			throw new PpcnError("PpcnStatement.set", var4);
		}
	}

	public void setDate(PpcnDate date) {
		this.setDate(++this.column, date);
	}

	public void setDate(int col, PpcnDate date) {
		try {
			Date sqlDate = new Date(date.getGregorianCalendar().getTimeInMillis());
			this.statement.setDate(col, sqlDate);
		} catch (SQLException var4) {
			throw new PpcnError("PpcnStatement.setDate", var4);
		}
	}

	public void setTime(PpcnTime date) {
		this.setTime(++this.column, date);
	}

	public void setTime(int col, PpcnTime date) {
		try {
			Date sqlDate = new Date(date.getGregorianCalendar().getTimeInMillis());
			logger.log("setDate", "setting bound variable at index " + col + " to " + sqlDate.toString(), Level.INFO);
			this.statement.setDate(col, sqlDate);
		} catch (SQLException var4) {
			throw new PpcnError("PpcnStatement.setDate", var4);
		}
	}

	static {
		logger = new PpcnLogger(com.peppercoin.common.persistence.PpcnStatement.class.getName());
	}
}