/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.fun;

import org.johnstonshome.utils.lang.Validate;

/**
 * Models a typical key/value pair and used to expand elements in Map-like
 * structures into lists in the {@link Maps} functions.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 * @param <K> the type for the key in the pair
 * @param <V> the type for the value in the pair
 */
public class Pair<K, V> {

	private K key;
	private V value;
	
	/**
	 * Construct a new key/value pair.
	 * 
	 * @param key key (may not be <code>null</code>)
	 * @param value the value, this may be null
	 */
	public Pair(final K key, final V value) {
		Validate.isNotNull("key", key);
		this.setKey(key);
		this.setValue(value);
	}

	/**
	 * Set a new value for the key in this pair
	 * @param key new value for the key
	 */
	public void setKey(final K key) {
		Validate.isNotNull("key", key);
		this.key = key;
	}

	/**
	 * Return the current value for the key in this pair
	 * @return the current value of the key
	 */
	public K getKey() {
		return this.key;
	}

	/**
	 * Set a new value for the value in this pair
	 * @param value new value for the value
	 */
	public void setValue(final V value) {
		this.value = value;
	}

	/**
	 * Return the current value for the value in this pair
	 * @return the current value of the value
	 */
	public V getValue() {
		return this.value;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash = 31 * getKey().hashCode();
		hash = 31 * hash + (getValue() == null ? 0 : getValue().hashCode());
		return hash;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if ((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		Pair<K,V> other = (Pair<K, V>) obj;
		if ((this.getValue() == null || other.getValue() == null) &&
			this.getValue() != other.getValue()) {
			return false;
		}
		return (this.getKey().equals(other.getKey()) &&
				((this.getValue() == null && other.getValue() == null) || 
				 this.getValue().equals(other.getValue()))
				);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format(
				"{%s: %s}", 
				getKey().toString(), 
				getValue().toString());
	}
}
