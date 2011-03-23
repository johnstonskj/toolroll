package org.johnstonshome.utils.fun;

import static org.johnstonshome.utils.lang.Validate.*;

/**
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 * @param <V1> the type of the return value from the binary function
 * @param <V2> the type if the first parameter to the binary function
 * @param <V3> the type if the curried second parameter to the binary function
 */
public class PartialBinaryFunction<V1, V2, V3> implements UnaryFunction<V2, V1> {

	private BinaryFunction<V1,V2,V3> operation;
	private V3 rhs;
	
	/**
	 * Create a curried function, this binds the second argument to a fixed
	 * value and provides a new single-valued (unary) function.
	 * 
	 * @param operation the binary function to curry
	 * @param rhs the new unary function 
	 */
	public PartialBinaryFunction(BinaryFunction<V1,V2,V3> operation, V3 rhs) {
		isNotNull("operation", operation);
		isNotNull("rhs", rhs);
		this.operation = operation;
		this.rhs = rhs;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.johnstonshome.utils.fun.UnaryFunction#call(java.lang.Object)
	 */
	@Override
	public V1 call(V2 lhs) {
		return this.operation.call(lhs, this.rhs);
	}

}
