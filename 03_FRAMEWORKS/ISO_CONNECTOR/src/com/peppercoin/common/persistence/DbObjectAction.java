package com.peppercoin.common.persistence;

import java.sql.Connection;

public interface DbObjectAction {
	Object exec(Connection var1);
}