package org.capcaval.ccoutils.factory;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.capcaval.ccoutils.factory._impl.FactoryInvocationHandler;
import org.capcaval.ccoutils.factory._impl.GenericFactoryImpl;
import org.capcaval.ccoutils.factory._sample.generic.GreeterImpl;

/**
 * @author mik
 *
 */
public class FactoryTools {
	
	protected static Map<Class<?>, FactoryInvocationHandler<?>> factoryMap = new HashMap<Class<?>, FactoryInvocationHandler<?>>();
	
	/**
	 * @param objType
	 * @param objTypeImpl
	 */
	public static <T> void setObjectImplementationType(Class<T> objType, Class<? extends T> objTypeImpl){
		// seek for the factory fields
		
		// trows an exception if no factory found
		
		// if any configure the factory with the new type
	}

	/**
	 * @param objType
	 * @return
	 */
	public static <T> Class<? extends T> getObjectImplementationType(Class<T> objType){
		// seek for the factory fields
		
		// throw an exception if no factory found
		
		// if any configure return thye object implementation type
		return null;
	}

	/**
	 * @param objType
	 * @param objTypeImpl
	 */
	public static <T> void setFactoryImplementationType(Class<T> objType, Class<? extends T> objTypeImpl){
		// seek for the factory fields
		
		// throws an exception if no factory found
		
		// if any configure the factory with the new type
	}

	/**
	 * Change a factory implementation type. Raise a RuntimeException 
	 * if the factory type cannot be found.
	 * 
	 * @param objType	Factory class type 
	 * @param factoryInstance New factory instance
	 */
	public static <T> void setFactoryImplementationInstance(Class<T> objType,  T factoryInstance){
		// seek for the factory fields
		FactoryInvocationHandler<T> fih = (FactoryInvocationHandler<T>)FactoryTools.factoryMap.get(objType);
		
		// throw an exception if no factory found
		if(fih == null){
			throw new RuntimeException("[ccOutils Factory] ERROR : The factory type : " + objType.getName() + " can not be found");
		}
		else{
			// if any configure return the object implementation type
			fih.SetFactoryImpl(factoryInstance);
		}

		
	}

	/**
	 * Create a new factory by defining its interface and also a default implementation type
	 * The implementation class has to have a constructor without parameter. 
	 * @param factoryType Interface of the factory 
	 * @param factoryImplType Default implementation class of the factory
	 * @return the factory instance
	 */
	public static <T, I extends T> T newFactory(Class<T>factoryType, Class<I> factoryImplType){
		// allocate the factory
		T factoryImplInstance=null;
		try {
			factoryImplInstance = factoryImplType.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("[ccOutils Factory] ERROR : can not allocate the factory implementation type " + factoryImplType + " because it does not have a constructor without parameters", e);
		}
		T proxy = FactoryTools.newFactory(factoryType, factoryImplInstance);
		
		return proxy;
	}


	/**
	 * Create a new factory by defining a concrete implementation without 
	 * The implementation class has to have a constructor without parameter. 
	 * @param factoryType class type of the factory 
	 * @return the instance
	 */
	public static <T> T newImmutableFactory(Class<T>factoryType){
		if(factoryType.isInterface() == true){
			throw new RuntimeException("[ccOutils Factory] ERROR : newImmutableFactory method can not" + factoryType + " type because it is an interface" );
		}
		// allocate the factory
		T factoryImplInstance=null;
		try {
			factoryImplInstance = factoryType.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("[ccOutils Factory] ERROR : can not allocate the factory implementation type " + factoryType + " because it does not have a constructor without parameters", e);
		}
		
		return factoryImplInstance;
	}

	
	/**
	 * Create a new instance of a factory. It is mutable which means that the factory 
	 * implementation can be changed with another one as long as they implement the 
	 * factory interface 
	 * @param factoryType	Interface factory type. Define the contract to create objects. 
	 * @param factoryImplInstance Default implementation instance of the factory.
	 * @return Mutable factory instance.
	 */
	public static <T> T newFactory(Class<T>factoryType, T factoryImplInstance){
		// allocate the invocation handler
		FactoryInvocationHandler<T> fih = new FactoryInvocationHandler<T>(factoryImplInstance);
		// keep a reference on it
		FactoryTools.factoryMap.put(factoryType, fih);
		
		T proxy = (T) Proxy.newProxyInstance(FactoryInvocationHandler.class.getClassLoader(),
				new Class[]{factoryType}, 
				fih);
		
		return proxy;
	}

	/**
	 * @param factoryType Factory class type
	 * @param factoryImplInstance Default factory Instance
	 * @param numberOfInstance 
	 * @return Factory instance
	 */
	public static <T> T newFactory(Class<T>factoryType, T factoryImplInstance, int numberOfInstance){
		// allocate the invocation handler
		FactoryInvocationHandler<T> fih = new FactoryInvocationHandler<T>(factoryImplInstance);
		// keep a reference on it
		FactoryTools.factoryMap.put(factoryType, fih);
		
		T proxy = (T) Proxy.newProxyInstance(FactoryInvocationHandler.class.getClassLoader(),
				new Class[]{factoryType}, 
				fih);
		
		return proxy;
	}

	public static <T, I extends T> GenericFactory<T> newGenericFactory(Class<I> FactoredType) {
		return new GenericFactoryImpl<T>(FactoredType);
	}
}
