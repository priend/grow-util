package com.pre.roc.grow.voice.daba;

import java.sql.SQLException;

/**
 *
 * @file ExecuteTransaction.java
 * @dateTime 2017年8月1日 下午4:31:47
 */
public class TransactionExecute {

	public int TRANSACTION_READ_UNCOMMITTED = 1;
	public int TRANSACTION_READ_COMMITTED = 2;
	public int TRANSACTION_REPEATABLE_READ = 4;
	public int TRANSACTION_SERIALIZABLE = 8;

	private ConnectionDispersed connectionDispersed = ConnectionDispersed.getInstance();

	/**
	 * @throws Exception
	 */
	public void open() throws SQLException {

		this.connectionDispersed.getConn().setAutoCommit(false);
	}

	/**
	 * 开启事务
	 * 
	 * @param level
	 * @throws SQLException
	 */
	public void open(Integer level) throws SQLException {

		this.open();

		if (null != level)
		{
			this.connectionDispersed.getConn().setTransactionIsolation(level);
		}
	}

	/**
	 * 
	 * @throws SQLException
	 */
	public void commit() throws SQLException {

		this.connectionDispersed.commit();
	}

	/**
	 * 
	 * @throws SQLException
	 */
	public void rollback() throws SQLException {

		this.connectionDispersed.rollback();
	}

	/**
	 * @throws SQLException
	 */
	public void close() throws SQLException {

		this.connectionDispersed.close();
	}
}
