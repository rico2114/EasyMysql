package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	public static void main(String[] args) {
		// Set our connection
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("url", "username", "password");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// synchronous fetching
		testSync(connection);
		// asynchronous fetching
		testAsync(connection);
	}
	
	public static void testSync(final Connection connection) {
		final EasyMysqlSynchronous sMysql = new EasyMysqlSynchronous(connection);
		sMysql.insertInto("Usernames", new EasyMysqlProperty("Name", "Juan"), new EasyMysqlProperty("Name", "Pedro"));
		sMysql.push();
		
		sMysql.clear().selectFrom("Usernames").where(new EasyMysqlProperty("Name", "Pedro"));
		final ResultSet rs = sMysql.getFetching().fetch(sMysql);
		try {
			while (rs.next()) {
				
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
			
		};
		final EasyMysqlAsynchronous asMysql = new EasyMysqlAsynchronous(connection, handler);
		asMysql.insertInto("Usernames", new EasyMysqlProperty("Name", "Juan"), new EasyMysqlProperty("Name", "Pedro"));
		asMysql.push();
		asMysql.clear().selectFrom("Usernames").where(new EasyMysqlProperty("Name", "Pedro"));
		asMysql.getFetching().fetch(asMysql);
	}
}
