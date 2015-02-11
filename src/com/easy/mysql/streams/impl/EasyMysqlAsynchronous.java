package com.easy.mysql.streams.impl;

import java.sql.Connection;

import com.easy.mysql.EasyMysql;
import com.easy.mysql.fetching.impl.EasyMysqlAsynchronousFetch;
import com.easy.mysql.streams.EasyMysqlStreamHandler;

/**
 * Created with eclipse 29/01/2015 6:29:10 p. m.
 * @Author Juan Sebastian Quiceno <Juan.2114@hotmail.com>
 */
public class EasyMysqlAsynchronous extends EasyMysql<EasyMysqlAsynchronousFetch>{

	/**
	 * Constructs a synchronous mysql fetch automatically setting the executor (preparing them)
	 * @param connection	the connection to schedule the fetching and pushing
	 * @param handler	the handler used for asynchronous callbacks
	 * @param fetch	the fetching method
	 */
	public EasyMysqlAsynchronous(Connection connection, final EasyMysqlStreamHandler handler, EasyMysqlAsynchronousFetch fetch) {
		super(connection, handler, fetch);
		setExecutor();
	}
	
	/**
	 * Represents the default constructor
	 * @param connection	the connection
	 * @param handler	the handler used for asynchronous callbacks
	 */
	public EasyMysqlAsynchronous(Connection connection, final EasyMysqlStreamHandler handler) {
		this(connection, handler, EasyMysqlAsynchronousFetch.getContext());
	}

}
