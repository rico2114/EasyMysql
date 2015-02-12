package com.easy.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.easy.mysql.fetching.EasyMysqlFetch;
import com.easy.mysql.streams.EasyMysqlFetchableStream;
import com.easy.mysql.streams.EasyMysqlStreamHandler;

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
	 * Represents the executors used for a synchronous works
	 */
	private ExecutorService executors;
	
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
	
	@Override
	public void push() {
		final EasyMysqlStreamHandler handler = getHandler();
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (Objects.nonNull(statement)) {
			try {
				statement.executeUpdate(getStream().toString());
				if (Objects.nonNull(handler)) {
					handler.onPush(this);
				}
			} catch (SQLException e) {
				if (Objects.nonNull(handler))
					handler.onPushException(e);
				else
					e.printStackTrace();
			}
		}
	}
	
	/**
	 * Sets the desired number of threads
	 * @return	this instance
	 */
	public EasyMysql<?> setExecutor() {
		this.executors = Executors.newSingleThreadExecutor();
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
	 * Represents the executors
	 * @return	the executors
	 */
	public ExecutorService getExecutors() {
		return executors;
	}

	@Override
	public EasyMysqlStreamHandler getHandler() {
		return handler;
	}
}
