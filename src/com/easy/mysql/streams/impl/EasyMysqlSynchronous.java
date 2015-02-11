package com.easy.mysql.streams.impl;

import java.sql.Connection;

import com.easy.mysql.EasyMysql;
import com.easy.mysql.fetching.impl.EasyMysqlSynchronousFetch;

/**
 * Created with eclipse 29/01/2015 6:29:10 p. m.
 * @Author Juan Sebastian Quiceno <Juan.2114@hotmail.com>
 */
public class EasyMysqlSynchronous extends EasyMysql<EasyMysqlSynchronousFetch>{

	/**
	 * Constructs a synchronous mysql fetch
	 * @param connection	the connection to schedule the fetching and pushing
	 * @param fetch	the fetching method
	 */
	public EasyMysqlSynchronous(Connection connection, EasyMysqlSynchronousFetch fetch) {
		super(connection, null, fetch);
	}
	
	/**
	 * Represents the default constructor
	 * @param connection	the connection
	 */
	public EasyMysqlSynchronous(Connection connection) {
		this(connection, EasyMysqlSynchronousFetch.getContext());
	}

}
