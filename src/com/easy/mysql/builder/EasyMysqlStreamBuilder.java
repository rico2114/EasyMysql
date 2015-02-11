package com.easy.mysql.builder;

import java.util.Objects;

import com.easy.mysql.streams.EasyMysqlStreamInterface;
import com.easy.mysql.streams.property.EasyMysqlProperty;

/**
 * Created with eclipse 28/01/2015 12:18:18 a. m.
 * @Author Juan Sebastian Quiceno <Juan.2114@hotmail.com>
 */
public abstract class EasyMysqlStreamBuilder implements EasyMysqlStreamInterface {

	/**
	 * Represents the string buffer stream
	 */
	private final StringBuilder stream;
	
	/**
	 * Constructs the easy mysql stream and initializes work
	 */
	public EasyMysqlStreamBuilder() {
		this.stream = new StringBuilder();
	}
	
	/**
	 * Inserts into the stream the properties
	 * @param tableName	the table name we are modifying
	 * @param properties	the properties (row, columns)
	 * @return	the stream builder for chaining
	 */
	public EasyMysqlStreamBuilder insertInto(final String tableName, final EasyMysqlProperty ... properties) {
		stream.append("INSERT INTO " + tableName + "");
		for (EasyMysqlProperty property : properties) {
			if (Objects.nonNull(property)) {
				stream.append(property.getKey() + ", ");
			}
		}
		stream.setLength(stream.lastIndexOf(", "));
		stream.append(") VALUES (");
		for (EasyMysqlProperty property : properties) {
			if (Objects.nonNull(property)) {
				stream.append("'" + property.getValue() + "', ");
			}
		}
		stream.setLength(stream.lastIndexOf(", "));
		stream.append(");");
		return this;
	}
	
	/**
	 * Updates the current table and property with a new value
	 * @param tableName	the table name
	 * @param property	the property 
	 * @return	the stream builder for chaining
	 */
	public EasyMysqlStreamBuilder update(final String tableName, final EasyMysqlProperty property) {
		stream.append("UPDATE " + tableName + " SET " + property.getKey() + " = '" + property.getValue() + "'");
		return this;
	}
	
	/**
	 * Appends a select from operation
	 * @param tableName	the table name
	 * @return	the stream builder for chaining
	 */
	public EasyMysqlStreamBuilder selectFrom(final String tableName) {
		stream.append("SELECT * FROM " + tableName + " ");
		return this;
	}
	
	/**
	 * Appends a where operation
	 * @param properties	the properties to perform a where <tt> note that if properties length is higher than 1
	 * we will automatically perform an {@link EasyMysqlStreamBuilder#and(EasyMysqlProperty...)}
	 * @return	the stream builder for chaining
	 */
	public EasyMysqlStreamBuilder where(final EasyMysqlProperty ... properties) {
		final EasyMysqlProperty first = properties[0];
		if (Objects.nonNull(first))
			stream.append("WHERE " + first.getKey() + " = '" + first.getValue() + "' ");
		
		for (int i = 1; i < properties.length; i++) {
			final EasyMysqlProperty property = properties[i];
				if (Objects.nonNull(property)) {
					and(property);
				}
		}
		return this;
	}
	
	/**
	 * Appends an and operation
	 * @param properties	the properties to and
	 * @return	the stream builder for chaining
	 */
	public EasyMysqlStreamBuilder and(final EasyMysqlProperty ... properties) {
		for (EasyMysqlProperty property : properties) {
			stream.append("AND " + property.getKey() + " = '" + property.getValue() + "'");
		}
		return this;
	}
	
	/**
	 * Clears the current stream
	 * @return	the stream builder for chaining
	 */
	public EasyMysqlStreamBuilder clear() {
		stream.setLength(0);
		return this;
	}
	
	/**
	 * Returns the stream builder
	 * @return	the stream builder
	 */
	public StringBuilder getStream() {
		return stream;
	}
}