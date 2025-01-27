package com.peppercoin.common;

import com.peppercoin.common.platform.PpcnSession;
import java.io.Serializable;

public abstract class PersistentObject implements Serializable {
	protected final void save() {
		PpcnSession.getDb().save(this);
	}

	public final void update() {
		PpcnSession.getDb().saveOrUpdate(this);
	}
}