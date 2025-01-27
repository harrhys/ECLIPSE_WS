package com.peppercoin.common.persistence;

import com.peppercoin.common.config.Config;
import com.peppercoin.common.exception.PpcnError;
import com.peppercoin.common.platform.PpcnSession;
import com.peppercoin.common.util.PpcnLogger;
import com.peppercoin.common.util.PpcnLogger.Level;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DbUtil {
	private static final PpcnLogger logger;
	private static DataSource pool;

	public static PpcnTransaction beginTransaction() {
		logger.log("beginTransaction", "", Level.DEBUG);
		PpcnTransaction tx = new PpcnTransaction();
		PpcnSession.push();
		return tx;
	}

	public static void commit(PpcnTransaction tx) {
		PpcnSession.pop();
		tx.commit();
		logger.log("commitTransaction", "", Level.DEBUG);
	}

	public static void close(PpcnStatement st) {
		if (st != null) {
			st.close();
		}

	}

	public static void close(PpcnTransaction tx) {
		if (tx != null && tx.isOpen()) {
			logger.log("close", "Rolling back uncommitted transaction.", Level.WARN);
			PpcnSession.pop();
			tx.rollback();
		}

	}

	public static Connection getConnection() {
		try {
			if (pool == null) {
				makePool();
			}
		} catch (Exception var2) {
			throw new PpcnError("Can't create Connection pool", var2);
		}

		try {
			return pool.getConnection();
		} catch (SQLException var1) {
			throw new PpcnError("Can't get Database connection from pool", var1);
		}
	}

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException var2) {
				logger.log("close", "Caught an SQLException closing a connection: " + var2, Level.ERROR);
			}
		}

	}

	public static void exec(DbAction action) {
		PpcnTransaction tx = null;
		Connection conn = null;

		try {
			tx = beginTransaction();
			conn = getConnection();
			action.exec(conn);
			commit(tx);
		} finally {
			close(conn);
			close(tx);
		}

	}

	public static Object exec(DbObjectAction action) {
		PpcnTransaction tx = null;
		Object obj = null;
		Connection conn = null;

		try {
			tx = beginTransaction();
			conn = getConnection();
			obj = action.exec(conn);
			commit(tx);
		} finally {
			close(conn);
			close(tx);
		}

		return obj;
	}

	private static DataSource getPool() {
		return pool;
	}

	private static void makePool() {
		try {
			Context env = (Context) (new InitialContext()).lookup("java:");
			String datasource = Config.get("datasource");
			pool = (DataSource) env.lookup("/" + datasource);
			if (pool == null) {
				throw new PpcnError("DBUtil.makePool '" + datasource + "' is an unknown DataSource",
						new RuntimeException());
			}
		} catch (NamingException var2) {
			throw new PpcnError("DBUtil.makePool", var2);
		}
	}

	static {
		logger = new PpcnLogger(com.peppercoin.common.persistence.DbUtil.class.getName());
		pool = null;
		makePool();
	}
}