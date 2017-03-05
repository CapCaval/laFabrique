package org.capcaval.lafabrique.compiler._test;

import org.capcaval.lafabrique.compiler._impl.CompileResult;

public class ObjectAndResult<T> {
	protected T object;
	protected CompileResult result;

	public ObjectAndResult(T object, CompileResult result) {
		this.object = object;
		this.result = result;
	}

	public CompileResult getResult() {
		return result;
	}

	public T getObject() {
		return object;
	}

	
}
