package com.easy.mysql.streams;

import com.easy.mysql.builder.EasyMysqlStreamBuilder;
import com.easy.mysql.fetching.EasyMysqlFetch;

/**
 * Created with eclipse 27/01/2015 11:55:21 p. m.
 * @Author Juan Sebastian Quiceno <Juan.2114@hotmail.com>
 */
public abstract class EasyMysqlFetchableStream <F extends EasyMysqlFetch> extends EasyMysqlStreamBuilder {
	
	/**
	 * Represents the fetching type
	 */
	private F fetch;
	
	/**
	 * Constructs the easy mysql extend stream
	 * @param fetch	the fetch type
	 */
	public EasyMysqlFetchableStream(final F fetch) {
		this.fetch = fetch;
	}
	
	/**
	 * Gets to the fetching process
	 * @return	the fetching process
	 */
	public F getFetching() {
		return fetch;
	}
}
