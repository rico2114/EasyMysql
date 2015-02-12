package com.easy.mysql.streams;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with eclipse 27/01/2015 11:43:10 p. m.
 * @Author Juan Sebastian Quiceno <Juan.2114@hotmail.com>
 */
public interface EasyMysqlStreamHandler {

	/**
	 * Attempts to push a piece of stream to the database
	 * @param stream	the stream that we are pushing
	 */
	void onPush(final EasyMysqlStreamInterface stream);
	
	/**
	 * Attempts to fetch the current result set for user handled actions
	 * @param resultSet	the result set
	 */
	void onFetch(final ResultSet resultSet);
	
	/**
	 * Called when an exception gets thrown during pushing
	 * @param exception	the exception
	 */
	default void onPushException(final SQLException exception) {
		// Empty by default, override if needed
	}
	
	/**
	 * Called when an exception gets thrown during fetching
	 * @param exception	the exception
	 */
	default void onFetchException(final SQLException exception) {
		// Empty by default, override if needed
	}
}
