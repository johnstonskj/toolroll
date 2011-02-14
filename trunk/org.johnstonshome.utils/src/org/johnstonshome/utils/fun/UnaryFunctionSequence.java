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
 * Models a function that maps from an input element to an output element.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 * @param <V1> the type of the element to be passed into the function.
 * @param <V2> the type of the value returned from the function.
 */
public class UnaryFunctionSequence<V1, V2> implements UnaryFunction<V1, V2> {

	private UnaryFunction<V1, ?> handler;
	private UnaryProcedure<Throwable> errorHandler;
	private UnaryFunctionSequence<V2, ?> next;

	@SuppressWarnings("unchecked")
	public <V3> UnaryFunctionSequence<V3,V2> then(UnaryFunction<V1, V3> handler) {
		Validate.isNotNull("handler", handler);
		this.handler = handler;
		this.next = new UnaryFunctionSequence<V2,V3>();
		return (UnaryFunctionSequence<V3,V2>) this.next;
	}
	
	@SuppressWarnings("unchecked")
	public <V3> UnaryFunctionSequence<V3,V2> then(UnaryFunction<V1, V3> handler, UnaryProcedure<Throwable> errorHandler) {
		Validate.isNotNull("handler", handler);
		Validate.isNotNull("errorHandler", errorHandler);
		this.handler = handler;
		this.errorHandler = errorHandler;
		this.next = new UnaryFunctionSequence<V2,V3>();
		return (UnaryFunctionSequence<V3,V2>) this.next;
	}

	@Override
	public V2 call(V1 value) {
		V2 response = null;
		try {
			response = this._call(value);
		} catch (Throwable t) {
			if (this.errorHandler != null) {
				this.errorHandler.call(t);
			}
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	private V2 _call(Object value) {
		/*
		 * This method was required to get around compiler errors because of 
		 * Java's generic type erasure. 
		 */
		if (this.next == null || this.next.handler == null) {
			if (this.handler != null) {
				return (V2)this.handler.call((V1)value);
			}
		} else {
			return (V2)this.next._call(this.handler.call((V1)value));
		}
		return null;
	}
}
