package com.easy.mysql.fetching.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

import com.easy.mysql.EasyMysql;
import com.easy.mysql.fetching.EasyMysqlFetch;
import com.easy.mysql.streams.impl.EasyMysqlAsynchronous;

/**
 * Created with eclipse 28/01/2015 12:30:13 a. m.
 * 
 * @Author Juan Sebastian Quiceno <Juan.2114@hotmail.com>
 */
public class EasyMysqlAsynchronousFetch implements EasyMysqlFetch {
	
	/**
	 * Represents this instanced class context
	 */
	public static final EasyMysqlAsynchronousFetch CONTEXT = new EasyMysqlAsynchronousFetch();
	
	/**
	 * Attempts to asynchronously fetch the nodes with a callback design
	 * 
	 * @param context	the mysql context
	 */
	public void fetch(final EasyMysql<EasyMysqlAsynchronousFetch> context) {
		final ExecutorService service = context.getExecutor();
		if (Objects.nonNull(service)) {
			service.submit(new Runnable() {

				@Override
				public void run() {
					Statement statement = null;
					try {
						statement = context.getConnection().createStatement();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if (Objects.nonNull(statement)) {
						try {
							final ResultSet resultSet = statement.executeQuery(context.getStream().toString());
							if (context instanceof EasyMysqlAsynchronous)
								context.getHandler().onFetch((EasyMysqlAsynchronous) context, resultSet);
						} catch (SQLException e) {
							if (context instanceof EasyMysqlAsynchronous)
								context.getHandler().onFetchException((EasyMysqlAsynchronous) context, e);
						}
					}
				}

			});
		}
	}
	
	/**
	 * Returns this class context
	 * @return	the context
	 */
	public static final EasyMysqlAsynchronousFetch getContext() {
		return CONTEXT;
	}
}
