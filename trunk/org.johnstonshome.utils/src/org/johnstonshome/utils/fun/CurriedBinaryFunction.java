package org.johnstonshome.utils.fun;

import static org.johnstonshome.utils.lang.Validate.*;

public class CurriedBinaryFunction<V1, V2, V3> implements UnaryFunction<V2, V1> {

	private BinaryFunction<V1,V2,V3> operation;
	private V3 rhs;
	
	public CurriedBinaryFunction(BinaryFunction<V1,V2,V3> operation, V3 rhs) {
		isNotNull("operation", operation);
		isNotNull("rhs", rhs);
		this.operation = operation;
		this.rhs = rhs;
	}
	
	@Override
	public V1 call(V2 lhs) {
		return this.operation.call(lhs, this.rhs);
	}

}
