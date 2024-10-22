package com.peppercoin.common.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CompoundObject {
	private Collection objects = new ArrayList();

	public CompoundObject(Object[] objs) {
		for (int i = 0; i < objs.length; ++i) {
			this.objects.add(objs[i]);
		}

	}

	public void add(Object o) {
		this.objects.add(o);
	}

	public Collection getObjects() {
		return this.objects;
	}

	public ArrayList getObjectList() {
		return (ArrayList) this.objects;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		Iterator elems = this.objects.iterator();

		while (elems.hasNext()) {
			buf.append(elems.next());
			buf.append(" ");
		}

		return buf.toString();
	}
}