package com.peppercoin.common.persistence;

import com.peppercoin.common.config.Config;
import com.peppercoin.common.exception.PpcnError;
import com.peppercoin.common.query.ScrollableQueryResults;
import com.peppercoin.common.util.Money;
import com.peppercoin.common.util.MoneyBit;
import com.peppercoin.common.util.Percentage;
import com.peppercoin.common.util.PpcnCurrency;
import com.peppercoin.common.util.PpcnDate;
import com.peppercoin.common.util.PpcnLogger;
import com.peppercoin.common.util.PpcnTime;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;

public class PpcnResultSet implements ScrollableQueryResults {
	private ResultSet rs;
	private ArrayList rows;
	private int size = -1;
	private int rowNum = 0;
	private ArrayList row;
	private boolean wasClipped;
	protected PpcnLogger logger;
	private int type;
	private static final int DEFAULT_PRECISION = 3;

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public ResultSetMetaData getMetaData() {
		try {
			return this.rs.getMetaData();
		} catch (SQLException var2) {
			throw new PpcnError("PpcnResultSet.next", var2);
		}
	}

	public PpcnResultSet(ResultSet rs) {
		this.logger = new PpcnLogger(com.peppercoin.common.persistence.PpcnResultSet.class.getName());
		this.type = 0;
		this.rs = rs;
	}

	public boolean next() {
		return this.scroll(1);
	}

	public void close() {
		try {
			this.rs.close();
		} catch (SQLException var2) {
			throw new PpcnError("PpcnResultSet.close", var2);
		}
	}

	public int getInt(int column) {
		try {
			return this.rs.getInt(column);
		} catch (SQLException var3) {
			throw new PpcnError("PpcnResultSet.get", var3);
		}
	}

	public double getDouble(String column) {
		return this.getDouble(column, 3);
	}

	public double getDouble(int column) {
		return this.getDouble(column, 3);
	}

	public double getDouble(String column, int precision) {
		try {
			NumberFormat formatter = NumberFormat.getInstance();
			formatter.setMinimumFractionDigits(precision);
			formatter.setMaximumFractionDigits(precision);
			return new Double(formatter.format(this.rs.getDouble(column)));
		} catch (SQLException var4) {
			throw new PpcnError("PpcnResultSet.get", var4);
		}
	}

	public double getDouble(int column, int precision) {
		try {
			NumberFormat formatter = NumberFormat.getInstance();
			formatter.setMinimumFractionDigits(precision);
			formatter.setMaximumFractionDigits(precision);
			return new Double(formatter.format(this.rs.getDouble(column)));
		} catch (SQLException var4) {
			throw new PpcnError("PpcnResultSet.get", var4);
		}
	}

	public Percentage getPercentage(int column, int precision) {
		return new Percentage(new Double(this.getDouble(column, precision)), precision);
	}

	public Percentage getPercentage(int column) {
		return new Percentage(new Double(this.getDouble(column, 3)), 3);
	}

	public int getInt(String column) {
		try {
			return this.rs.getInt(column);
		} catch (SQLException var3) {
			throw new PpcnError("PpcnResultSet.get", var3);
		}
	}

	public String getString(int column) {
		try {
			return this.rs.getString(column);
		} catch (SQLException var3) {
			throw new PpcnError("PpcnResultSet.get", var3);
		}
	}

	public String getString(String column) {
		try {
			return this.rs.getString(column);
		} catch (SQLException var3) {
			throw new PpcnError("PpcnResultSet.get", var3);
		}
	}

	public PpcnTime getTime(int column) {
		try {
			Timestamp t = this.rs.getTimestamp(column);
			return t == null ? null : new PpcnTime(t);
		} catch (SQLException var3) {
			throw new PpcnError("PpcnResultSet.get", var3);
		}
	}

	public PpcnTime getTime(String column) {
		try {
			Timestamp t = this.rs.getTimestamp(column);
			return t == null ? null : new PpcnTime(t);
		} catch (SQLException var3) {
			throw new PpcnError("PpcnResultSet.get", var3);
		}
	}

	public PpcnDate getDate(int column) {
		try {
			Date d = this.rs.getDate(column);
			return d == null ? null : new PpcnDate(d);
		} catch (SQLException var3) {
			throw new PpcnError("PpcnResultSet.get", var3);
		}
	}

	public PpcnDate getDate(String column) {
		try {
			Date d = this.rs.getDate(column);
			return d == null ? null : new PpcnDate(d);
		} catch (SQLException var3) {
			throw new PpcnError("PpcnResultSet.get", var3);
		}
	}

	public Money getMoney(int column) {
		try {
			PpcnCurrency c = this.getCurrency("currency_code");
			BigDecimal d = this.rs.getBigDecimal(column);
			return c != null && d != null ? new Money(c, d) : null;
		} catch (SQLException var4) {
			throw new PpcnError("PpcnResultSet.get", var4);
		}
	}

	public Money getMoney(String column) {
		try {
			PpcnCurrency c = this.getCurrency("currency_code");
			BigDecimal d = this.rs.getBigDecimal(column);
			return c != null && d != null ? new Money(c, d) : null;
		} catch (SQLException var4) {
			throw new PpcnError("PpcnResultSet.get", var4);
		}
	}

	public MoneyBit getMoneyBit(int column) {
		try {
			PpcnCurrency c = this.getCurrency("currency_code");
			BigDecimal d = this.rs.getBigDecimal(column);
			return c != null && d != null ? new MoneyBit(c, d) : null;
		} catch (SQLException var4) {
			throw new PpcnError("PpcnResultSet.get", var4);
		}
	}

	public MoneyBit getMoneyBit(String column) {
		try {
			PpcnCurrency c = this.getCurrency("currency_code");
			BigDecimal d = this.rs.getBigDecimal(column);
			return c != null && d != null ? new MoneyBit(c, d) : null;
		} catch (SQLException var4) {
			throw new PpcnError("PpcnResultSet.get", var4);
		}
	}

	public PpcnCurrency getCurrency(int column) {
		try {
			String c = this.rs.getString(column);
			return c == null ? null : PpcnCurrency.getInstance(c);
		} catch (SQLException var3) {
			throw new PpcnError("PpcnResultSet.get", var3);
		}
	}

	public PpcnCurrency getCurrency(String column) {
		try {
			String c = this.rs.getString(column);
			return c == null ? null : PpcnCurrency.getInstance(c);
		} catch (SQLException var3) {
			throw new PpcnError("PpcnResultSet.get", var3);
		}
	}

	public boolean hasMore() {
		try {
			return !this.rs.isLast();
		} catch (SQLException var2) {
			throw new PpcnError("PpcnResultSet.hasMore", var2);
		}
	}

	public Object get() {
		throw new PpcnError("get is unsupported by PppcnResultSet");
	}

	public int size() {
		if (this.size == -1) {
			int curRow = this.rowNum;
			this.last();
			this.size = this.rowNum;
			this.setRowNumber(curRow);
		}

		if (this.size == -1) {
			this.size = 0;
		}

		return this.size;
	}

	public boolean isScrollable() {
		return true;
	}

	public boolean scroll(int i) {
		if (i <= 0 && this.rowNum + i <= 0) {
			this.beforeFirst();
			return true;
		} else {
			try {
				boolean success = this.rs.absolute(this.rowNum + i);
				if (success) {
					this.rowNum += i;
				}

				return success;
			} catch (SQLException var3) {
				throw new PpcnError("PpcnResultSet.scroll", var3);
			}
		}
	}

	public int getRowNum() {
		return this.rowNum;
	}

	public void add(ScrollableQueryResults r) {
		throw new PpcnError("add is unsupported by PppcnResultSet");
	}

	public boolean isFirst() {
		try {
			return this.rs.isFirst();
		} catch (SQLException var2) {
			throw new PpcnError("PpcnResultSet.isLast", var2);
		}
	}

	public boolean isLast() {
		try {
			return this.rs.isLast();
		} catch (SQLException var2) {
			throw new PpcnError("PpcnResultSet.isLast", var2);
		}
	}

	public Object get(int i) {
		try {
			return this.rs.getObject(i);
		} catch (SQLException var3) {
			throw new PpcnError("PpcnResultSet.get(i)", var3);
		}
	}

	public boolean last() {
		try {
			int curRow;
			for (curRow = this.rowNum; this.rs.next(); ++this.rowNum) {
				;
			}

			return curRow != this.rowNum;
		} catch (SQLException var2) {
			throw new PpcnError("PpcnResultSet.last", var2);
		}
	}

	private void load() {
		try {
			this.rs.beforeFirst();
			this.rows = new ArrayList();
			this.rowNum = 0;
			int rowLimit = Config.getInt("max-rows-returned", 999) + 1;

			int rowCount;
			for (rowCount = 0; this.rs.next() && rowCount < rowLimit; ++this.rowNum) {
				ArrayList li = new ArrayList();
				int cols = this.rs.getMetaData().getColumnCount();

				for (int j = 1; j <= cols; ++j) {
					li.add(this.rs.getObject(j));
				}

				li.trimToSize();
				this.rows.add(li);
				++rowCount;
			}

			this.wasClipped = rowCount == rowLimit;
			this.rows.trimToSize();
			this.size = rowCount;
		} catch (SQLException var6) {
			throw new PpcnError("PpcnResultSet.load", var6);
		}
	}

	public boolean previous() {
		try {
			boolean success = this.rs.absolute(this.rowNum - 1);
			--this.rowNum;
			return success;
		} catch (SQLException var2) {
			throw new PpcnError("PpcnResultSet.previous", var2);
		}
	}

	public boolean wasClipped() {
		return this.wasClipped;
	}

	public Collection getRows() {
		this.load();
		return this.rows;
	}

	public void setRows(Collection rows) {
		this.rows = new ArrayList(rows);
	}

	public void first() {
		try {
			this.rs.absolute(1);
			this.rowNum = 1;
		} catch (SQLException var2) {
			throw new PpcnError("PpcnResultSet.first", var2);
		}
	}

	public void beforeFirst() {
		try {
			this.rs.beforeFirst();
			this.rowNum = 0;
		} catch (SQLException var2) {
			throw new PpcnError("PpcnResultSet.beforeFirst", var2);
		}
	}

	public boolean setRowNumber(int i) {
		try {
			boolean success = this.rs.absolute(i);
			this.rowNum = i;
			return success;
		} catch (SQLException var3) {
			throw new PpcnError("PpcnResultSet.setRowNumber", var3);
		}
	}
}