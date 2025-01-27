package com.peppercoin.common;

import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

public class JMXHTMLTable {
	private Vector _tableRows = new Vector();
	private StringBuffer _renderBuf = new StringBuffer("</PRE><TABLE BORDER=\"1\">");

	public JMXHTMLTable(String[] headers) {
		this._renderBuf.append(this.renderRow(headers, true));
	}

	public void addRow(String[] columns) {
		this._tableRows.add(this.renderRow(columns, false));
	}

	private String renderRow(String[] columns, boolean bold) {
		StringBuffer buf = new StringBuffer("<TR>");

		for (int i = 0; i < columns.length; ++i) {
			buf.append("<TD>");
			if (bold) {
				buf.append("<B>");
			}

			buf.append(columns[i]);
			if (bold) {
				buf.append("</B>");
			}

			buf.append("</TD>");
		}

		buf.append("</TR>");
		return buf.toString();
	}

	public String render() {
		return this.render(true);
	}

	public String render(boolean sort) {
		if (sort) {
			Collections.sort(this._tableRows);
		}

		Iterator iter = this._tableRows.iterator();

		while (iter.hasNext()) {
			this._renderBuf.append((String) iter.next());
		}

		this._renderBuf.append("</TABLE><PRE>");
		return this._renderBuf.toString();
	}
}