package com.easy.mysql.streams;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.easy.mysql.streams.impl.EasyMysqlAsynchronous;

/**
 * Created with eclipse 27/01/2015 11:43:10 p. m.
 * @Author Juan Sebastian Quiceno <Juan.2114@hotmail.com>
 */
public interface EasyMysqlStreamHandler {

	/**
	 * Attempts to push a piece of stream to the database
	 * @param context	the mysql instance used for operations
	 * @param stream	the stream that we are pushing
	 */
	void onPush(final EasyMysqlAsynchronous context, final EasyMysqlStreamInterface stream);
	
	/**
	 * Attempts to fetch the current result set for user handled actions
	 * @param mysql	the mysql instance used for operations
	 * @param resultSet	the result set
	 */
	void onFetch(final EasyMysqlAsynchronous context, final ResultSet resultSet);
	
	/**
	 * Called when an exception gets thrown during pushing
	 * @param mysql	the mysql instance used for operations
	 * @param exception	the exception
	 */
	default void onPushException(final EasyMysqlAsynchronous context, final SQLException exception) {
		// Print stack trace by default
		exception.printStackTrace();
	}
	
	/**
	 * Called when an exception gets thrown during fetching
	 * @param mysql	the mysql instance used for operations
	 * @param exception	the exception
	 */
	default void onFetchException(final EasyMysqlAsynchronous context, final SQLException exception) {
		// Print stack trace by default
		exception.printStackTrace();
	}
}
