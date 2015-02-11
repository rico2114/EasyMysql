package com.easy.mysql.streams.property;


/**
 * Created with eclipse 28/01/2015 12:04:10 a. m.
 * @Author Juan Sebastian Quiceno <Juan.2114@hotmail.com>
 */
public class EasyMysqlProperty {

	/**
	 * Represents the property key
	 */
	private final String key;
	
	/**
	 * Represents the property value
	 */
	private final Object value;
	
	/**
	 * Constructs a mysql properties
	 * @param key	the key of the property
	 * @param value	the value of the property
	 */
	public EasyMysqlProperty(final String key, final Object value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Gets the property key
	 * @return	the key 
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * Gets the property value
	 * @return	the value
	 */
	public Object getValue() {
		return value;
	}
}
