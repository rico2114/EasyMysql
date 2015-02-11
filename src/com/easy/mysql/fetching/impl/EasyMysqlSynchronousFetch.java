package com.easy.mysql.fetching.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import com.easy.mysql.EasyMysql;
import com.easy.mysql.fetching.EasyMysqlFetch;

/**
 * Created with eclipse 28/01/2015 12:28:56 a. m.
 * @Author Juan Sebastian Quiceno <Juan.2114@hotmail.com>
 */
public class EasyMysqlSynchronousFetch implements EasyMysqlFetch {

	/**
	 * Represents this instanced class context
	 */
	private static final EasyMysqlSynchronousFetch CONTEXT = new EasyMysqlSynchronousFetch();
	
	/**
	 * Represents a synchronous fetch action
	 * @param context	the context, implicitly explicit the synchronous fetch to avoid wrong misconceptions
	 * @return	the result set
	 */
	public ResultSet fetch(final EasyMysql<EasyMysqlSynchronousFetch> context) {
		Statement statement = null;
		try {
			statement = context.getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		if (Objects.nonNull(statement)) {
			try {
				return statement.executeQuery(context.getStream().toString());
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	};
	
	/**
	 * Returns this class context
	 * @return	the context
	 */
	public static final EasyMysqlSynchronousFetch getContext() {
		return CONTEXT;
	}
}
