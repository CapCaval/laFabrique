package org.capcaval.cctools.recycling;

import java.util.ArrayList;
import java.util.List;

public class ObjectPool <T>{
	List<T> freeInstanceList = new ArrayList<T>(); 
	List<T> usedInstanceList = new ArrayList<T>();
	boolean isFlushType;
	protected Class<T> poolType;
	Flush<T> flushInstance=null;
	
	public ObjectPool(Class<T> poolType, int size, Flush<T> flushInstance){
		this.init(poolType, size, flushInstance);
	}
	
	public ObjectPool(Class<T> poolType, int size){
		this.init(poolType, size, null);
	}
	
	public void init(Class<T> poolType, int size, Flush<T> flushInstance){
		// check that the type has a no parameter constructor
		
		
		// keep a reference of the object type
		this.poolType = poolType;
		// keep a ref on flushInatnce
		this.flushInstance= flushInstance;
		
		// allocate the requested number
		for(int i=0; i<size; i++){
			newInstance(poolType);
		}
	}
	
	public T newInstance(Class<T> poolType){
		T obj = null;
		try {
			obj = poolType.newInstance();
			this.freeInstanceList.add(obj);
		} catch (Exception e) {
			throw new RuntimeException("ccTools Error : The class " + poolType + " can not instanciate. Check that it has a default constructor without paramaeter.", e);
		}
		return obj;
	}
	
	public T getNewFreeInstance(){
		// get a free instance
		int lastIndex = this.freeInstanceList.size();
		
		if(lastIndex == 0){
			// add a new instance
			T newInstance = this.newInstance(this.poolType);
			this.freeInstanceList.add(newInstance);
			
			// update the new size
			lastIndex = this.freeInstanceList.size();
		}
		
		T instance = this.freeInstanceList.get(lastIndex-1);
		// remove it as free
		this.freeInstanceList.remove(instance);
		// set it as used
		this.usedInstanceList.add(instance);
		
		return instance;
	}
	
	public void releaseInstance(T instance){
		// remove it as used
		this.usedInstanceList.remove(instance);
		// set it as free
		this.freeInstanceList.add(instance);
		// flush it if needed
		if(this.flushInstance != null){
			this.flushInstance.flushInstance(instance);
		}
		
	}

	public int getUsedInstanceNumber() {
		return this.usedInstanceList.size();
	}

	public Object getFreeInstanceNumber() {
		return this.freeInstanceList.size();
	}

}
