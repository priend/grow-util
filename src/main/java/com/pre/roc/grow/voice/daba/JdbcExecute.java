package com.pre.roc.grow.voice.daba;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作
 * 
 * @file DBUtil.java
 * @dateTime 2017年7月19日 下午5:36:52
 */
public class JdbcExecute {

	private static String BLOB = "BLOB";

	/**
	 * @return
	 * @throws SQLException
	 */
	private static Connection getConn() throws SQLException {

		return ConnectionDispersed.getInstance().getConn();
	}

	/**
	 * 
	 * @param con
	 * @param st
	 * @param rs
	 * @throws SQLException
	 */
	public static void close(Statement statement, ResultSet resultSet) throws SQLException {

		if (resultSet != null)
		{
			try
			{
				resultSet.close();
			}
			catch (SQLException e)
			{
				throw e;
			}
		}
		if (statement != null)
		{
			try
			{
				statement.close();
			}
			catch (SQLException e)
			{
				throw e;
			}
		}
	}

	/**
	 * @param strSQL
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static int doUpdate(String strSQL, Object... params) throws SQLException {

		return doUpdate(strSQL, Arrays.asList(params));
	}

	/**
	 * 执行insert, update, delete
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static int doUpdate(String sql, List<Object> params) throws SQLException {

		int result = -1;
		PreparedStatement preparedStatement = null;

		try
		{
			preparedStatement = getConn().prepareStatement(sql);
			JdbcExecute.setParams(preparedStatement, params);
			result = preparedStatement.executeUpdate();
		}
		finally
		{
			JdbcExecute.close(preparedStatement, null);
		}
		return result;
	}

	/**
	 * 执行带事务的insert, update, delete
	 * 
	 * @param sqls
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static int doUpdate(List<String> sqls, List<List<Object>> params) throws SQLException {

		int rows = -1;
		Connection connection = getConn();
		PreparedStatement preparedStatement = null;
		try
		{
			connection.setAutoCommit(false);
			for (int i = 0; i < sqls.size(); i++)
			{
				preparedStatement = connection.prepareStatement(sqls.get(i));
				setParams(preparedStatement, params.get(i));
				rows += preparedStatement.executeUpdate();
			}
			connection.commit();
		}
		catch (SQLException e)
		{
			connection.rollback();
			rows = 0;
			throw e;
		}
		finally
		{
			JdbcExecute.close(preparedStatement, null);
		}
		return rows;
	}

	/**
	 * 针对一条数据的查询 聚合查询
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public static Map<String, Object> queryOne(String sql, List<Object> params) throws SQLException, IOException {

		Map<String, Object> results = new HashMap<String, Object>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try
		{
			preparedStatement = getConn().prepareStatement(sql);
			setParams(preparedStatement, params);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
				for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
				{
					if (BLOB.equals(resultSetMetaData.getColumnTypeName(i)))
					{
						results.put(resultSetMetaData.getColumnName(i), JdbcExecute.getBlobBytes(resultSet.getBlob(i)));
					}
					else
					{
						results.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
				}
			}
		}
		catch (SQLException e1)
		{
			throw e1;
		}
		catch (IOException e)
		{
			throw e;
		}
		finally
		{
			JdbcExecute.close(preparedStatement, resultSet);
		}
		return results;
	}

	/**
	 * 针对多条数据的查询
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public static List<Map<String, Object>> queryList(String sql, List<Object> params) throws SQLException, IOException {

		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try
		{
			preparedStatement = getConn().prepareStatement(sql);
			setParams(preparedStatement, params);
			resultSet = preparedStatement.executeQuery();
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			while (resultSet.next())
			{
				Map<String, Object> result = new HashMap<String, Object>();
				for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++)
				{
					if (BLOB.equals(resultSetMetaData.getColumnTypeName(i)))
					{
						result.put(resultSetMetaData.getColumnName(i), JdbcExecute.getBlobBytes(resultSet.getBlob(i)));
					}
					else
					{
						result.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
					}
				}
				results.add(result);
			}
		}
		catch (SQLException e1)
		{
			throw e1;
		}
		catch (IOException e)
		{
			throw e;
		}
		finally
		{
			JdbcExecute.close(preparedStatement, resultSet);
		}
		return results;
	}

	/**
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public static List<Map<String, Object>> queryList(String sql, Object... params) throws SQLException, IOException {

		return JdbcExecute.queryList(sql, Arrays.asList(params));
	}

	/**
	 * 给预处理sql语句中占位符赋值
	 * 
	 * @param ps
	 * @param params
	 * @throws SQLException
	 */
	public static void setParams(PreparedStatement ps, List<Object> params) throws SQLException {
		if (params != null)
		{
			for (int i = 0; i < params.size(); i++)
			{
				Object obj = params.get(i);
				try
				{
					if (obj instanceof Integer)
					{
						ps.setInt(i + 1, Integer.parseInt(String.valueOf(obj)));
					}
					else if (obj instanceof Double)
					{
						ps.setDouble(i + 1, Double.parseDouble(String.valueOf(obj)));
					}
					else if (obj instanceof String)
					{
						ps.setString(i + 1, (String) obj);
					}
					else if (obj instanceof Date)
					{
						ps.setTimestamp(i + 1, new Timestamp(((Date) obj).getTime()));
					}
					else if (obj instanceof InputStream)
					{
						ps.setBlob(i + 1, (InputStream) obj);
					}
					else
					{
						ps.setObject(i + 1, obj);
					}
				}
				catch (SQLException e)
				{
					throw e;
				}
			}
		}
	}

	/**
	 * @param blob
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	private static byte[] getBlobBytes(Blob blob) throws SQLException, IOException {
		if (blob != null)
		{
			BufferedInputStream bufferedInputStream = new BufferedInputStream(blob.getBinaryStream());
			byte[] blobBytes = new byte[(int) blob.length()];
			bufferedInputStream.read(blobBytes);
			return blobBytes;
		}
		else
		{
			return null;
		}
	}
}
