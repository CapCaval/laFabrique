package org.capcaval.ccoutils.converter;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;

import org.capcaval.ccoutils.lang.ReflectionTools;

public abstract class ConverterAbstract<I, O> implements Converter<I, O> {

	@Override
	public Class<I> getInputType() {
		Method method = ReflectionTools.getMethodFromGenericInheritance("convert", this.getClass());
		// ReflectionTools.
		Class<?> type = method.getParameterTypes()[0];

		return (Class<I>)type;
	}

	@Override
	public Class<O> getOutputType() {
		Method method = ReflectionTools.getMethodFromGenericInheritance("convert", this.getClass());
		// ReflectionTools.
		Class<?> type = method.getReturnType();

		return (Class<O>)type;
	}
}
