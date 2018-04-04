package com.pre.roc.grow.voice.daba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDispersed {

	private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>();
	private static final ConnectionDispersed INSTANCE = new ConnectionDispersed();

	private static JdbcDynamic JDBC_CONFIG = JdbcDynamic.getInstance();

	static
	{
		try
		{
			Class.forName(JDBC_CONFIG.getDriver());
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	public static ConnectionDispersed getInstance() {
		return INSTANCE;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {

		return DriverManager.getConnection(JDBC_CONFIG.getUrl(), JDBC_CONFIG.getUsername(), JDBC_CONFIG.getPassword());
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public boolean isConnection() throws SQLException {

		if (null == CONNECTION_HOLDER.get() || CONNECTION_HOLDER.get().isClosed())
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public Connection getConn() throws SQLException {

		if (!isConnection())
		{
			CONNECTION_HOLDER.set(this.getConnection());
		}
		return CONNECTION_HOLDER.get();
	}

	/**
	 * @throws SQLException
	 */
	public void close() throws SQLException {

		if (!isConnection())
		{
			return;
		}
		CONNECTION_HOLDER.get().close();
		CONNECTION_HOLDER.remove();
	}

	/**
	 * @throws SQLException
	 */
	public void commit() throws SQLException {

		if (!isConnection())
		{
			return;
		}

		CONNECTION_HOLDER.get().commit();
	}

	/**
	 * @throws SQLException
	 */
	public void rollback() throws SQLException {

		if (!isConnection())
		{
			return;
		}

		CONNECTION_HOLDER.get().rollback();
	}

}
