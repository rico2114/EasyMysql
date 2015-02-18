package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.easy.mysql.EasyMysql;
import com.easy.mysql.streams.EasyMysqlStreamHandler;
import com.easy.mysql.streams.EasyMysqlStreamInterface;
import com.easy.mysql.streams.impl.EasyMysqlAsynchronous;
import com.easy.mysql.streams.impl.EasyMysqlSynchronous;
import com.easy.mysql.streams.property.EasyMysqlProperty;

/**
 * Created with eclipse 28/01/2015 12:20:13 a. m.
 * @Author Juan Sebastian Quiceno <Juan.2114@hotmail.com>
 */
public class Test {
	
	private static final boolean TEST_ASYNCHRONOUS_FETCHING = true;
	
	public static void main(String[] args) {
		// Set our connection
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("url", "username", "password");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// synchronous fetching
		if (!TEST_ASYNCHRONOUS_FETCHING)
			testSync(connection);
		else
		// asynchronous fetching
		testAsync(connection);
	}
	
	public static void testSync(final Connection connection) {
		final EasyMysqlSynchronous sMysql = new EasyMysqlSynchronous(connection);
		insertDataAndPrepareForFetching(sMysql);
		final ResultSet rs = sMysql.getFetching().fetch(sMysql);
		try {
			while (rs.next()) {
				// Grab details here, use rs as favour
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void testAsync(final Connection connection) {
		final EasyMysqlStreamHandler handler = new EasyMysqlStreamHandler() {

			@Override
			public void onPush(EasyMysqlStreamInterface stream) {
				System.out.println("Sucessfully pushed");
			}

			@Override
			public void onFetch(ResultSet resultSet) {
				System.out.println("A synchronous fetching");
			}

			@Override
			public void onPushException(SQLException exception) {
				System.out.println("exception during pushing!");
			}

			@Override
			public void onFetchException(SQLException exception) {
				System.out.println("exception during fetching");
			}
			
		};
		final EasyMysqlAsynchronous asMysql = new EasyMysqlAsynchronous(connection, handler);
		insertDataAndPrepareForFetching(asMysql);
		asMysql.getFetching().fetch(asMysql);
	}
	
	public static void insertDataAndPrepareForFetching(final EasyMysql<?> mysql) {
		mysql.insertInto("Usernames", new EasyMysqlProperty("Name", "Juan"), new EasyMysqlProperty("Name", "Pedro"));
		mysql.push();
		mysql.clear().selectFrom("Usernames").where(new EasyMysqlProperty("Name", "Pedro"));
	}
}
