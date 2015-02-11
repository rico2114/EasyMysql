package com.easy.mysql.streams;


/**
 * Created with eclipse 27/01/2015 11:40:50 p. m.
 * @Author Juan Sebastian Quiceno <Juan.2114@hotmail.com>
 */
public interface EasyMysqlStreamInterface {
	
	/**
	 * Called to push the used stream
	 */
	void push();
	
	/**
	 * Represents the handler for the streams
	 * @return	the handler
	 */
	EasyMysqlStreamHandler getHandler();
}
