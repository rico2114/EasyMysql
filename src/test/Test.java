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
	
	/**
	 * Do we want to test asynchronous fetching or not
	 */
	private static final boolean TEST_ASYNCHRONOUS_FETCHING = false;
	
	public static void main(String[] args) {
		// Set our connection
		Connection connection = null;
		try {
			// Connect to a desired location
			connection = DriverManager.getConnection("jdbc:mysql://**.***.**.*:3306/databasename", "name", "password");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// synchronous fetching if configured
		if (!TEST_ASYNCHRONOUS_FETCHING)
			testSync(connection);
		else
			// asynchronous fetching if configured
			testAsync(connection);
	}
	
	public static void testSync(final Connection connection) {
		// Create our easy to use mySQL synchronous wrapper
		final EasyMysqlSynchronous mysql = new EasyMysqlSynchronous(connection);
		// Insert the data
		insertDataAndPrepareForFetching(mysql);
		// Fetch the data
		final ResultSet resultSet = mysql.getFetching().fetch(mysql);
		try {
			if (resultSet.next()) {
				System.out.println("[Test#testSync] " + resultSet.getString("Name") + " has an age of " + resultSet.getInt("Age") + ".");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void testAsync(final Connection connection) {
		// Create our handler <tt> Note that handlers are only used for a-synchronous operations </tt>
		final EasyMysqlStreamHandler handler = new EasyMysqlStreamHandler() {

			@Override
			public void onPush(final EasyMysqlAsynchronous context, EasyMysqlStreamInterface stream) {
				System.out.println("[Test#testAsync] Sucessfully pushed.");
			}

			@Override
			public void onFetch(final EasyMysqlAsynchronous context, ResultSet resultSet) {
				try {
					if (resultSet.next()) {
						System.out.println("[Test#testAsync] " + resultSet.getString("Name") + " has an age of " + resultSet.getInt("Age") + ".");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				// shut down the worker thread
				context.shutdown();
			}			
		};
		// Create our easy to use mySQL a-synchronous wrapper
		final EasyMysqlAsynchronous mysql = new EasyMysqlAsynchronous(connection, handler);
		// Insert the data
		insertDataAndPrepareForFetching(mysql);
		// Fetch the data
		mysql.getFetching().fetch(mysql);
	}
	
	public static void insertDataAndPrepareForFetching(final EasyMysql<?> mysql) {
		// INSERT JUAN
		mysql.insertInto("Usernames", new EasyMysqlProperty("Name", "Juan"), new EasyMysqlProperty("Age", 17));
		mysql.push();
		// CLEAR THE STREAM
		mysql.clear();
		// INSERT PEDRO
		mysql.insertInto("Usernames", new EasyMysqlProperty("Name", "Pedro"), new EasyMysqlProperty("Age", 35));
		mysql.push();
		// CLEAR THE STREAM
		mysql.clear();
		// PREPARE FETCHING
		mysql.selectFrom("Usernames").where(new EasyMysqlProperty("Name", "Pedro"));
	}
}
