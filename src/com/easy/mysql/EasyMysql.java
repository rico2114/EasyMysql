package com.easy.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.easy.mysql.fetching.EasyMysqlFetch;
import com.easy.mysql.streams.EasyMysqlFetchableStream;
import com.easy.mysql.streams.EasyMysqlStreamHandler;
import com.easy.mysql.streams.impl.EasyMysqlAsynchronous;

/**
 * Created with eclipse 27/01/2015 11:48:33 p. m.
 * @Author Juan Sebastian Quiceno <Juan.2114@hotmail.com>
 */
public class EasyMysql<T extends EasyMysqlFetch> extends EasyMysqlFetchableStream<T> {

	/**
	 * Static initializer for the driver
	 */
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * Represents the instanced connection
	 */
	private final Connection connection;
	
	/**
	 * Represents the executor used for asynchronous works
	 */
	private ExecutorService executor;
	
	/**
	 * Represents the handler that we are using for this stream
	 */
	private final EasyMysqlStreamHandler handler;
	
	/**
	 * Constructs the easy mysql abstract stream with a given handler
	 * @param handler	the handler for the operations
	 * @param fetch	the fetching process
	 */
	public EasyMysql(final Connection connection, final EasyMysqlStreamHandler handler, final T fetch) {
		super(fetch);
		this.connection = connection;
		this.handler = handler;
	}
	
	/**
	 * Attempts to statically create the connection
	 * @param host	the host ip
	 * @param databaseName	the database name
	 * @param username	the username
	 * @param password	the password
	 * @return	the connection if it was established successfully, null if it wasn't
	 */
	public static final Connection open(final String host, final String databaseName, final String username, final String password) {
		try {
			return DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + databaseName, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void push() {
		final EasyMysqlStreamHandler handler = getHandler();
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (Objects.nonNull(statement)) {
				try {
					statement.executeUpdate(getStream().toString());
					if (Objects.nonNull(handler)) {
						if (this instanceof EasyMysqlAsynchronous)
							handler.onPush((EasyMysqlAsynchronous) this, this);
					}
				} catch (SQLException e) {
					if (Objects.nonNull(handler))
						if (this instanceof EasyMysqlAsynchronous)
							handler.onPushException((EasyMysqlAsynchronous) this, e);
						else
							e.printStackTrace();
				}
			}
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sets the desired number of threads
	 * @return	this instance
	 */
	public EasyMysql<?> setExecutor() {
		this.executor = Executors.newSingleThreadExecutor();
		return this;
	}
	
	/**
	 * Returns the connection
	 * @return	the mysql connection
	 */
	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * Represents the executor
	 * @return	the executor
	 */
	public ExecutorService getExecutor() {
		return executor;
	}

	@Override
	public EasyMysqlStreamHandler getHandler() {
		return handler;
	}
}
