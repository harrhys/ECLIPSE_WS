package com.peppercoin.common.persistence;

import java.sql.Connection;

public interface DbAction {
	void exec(Connection var1);
}