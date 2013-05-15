package org.capcaval.ccoutils.data._impl;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.capcaval.ccoutils.data.Data;
import org.capcaval.ccoutils.data.DataList;
import org.capcaval.ccoutils.data.DataListEvent;
import org.capcaval.ccoutils.lang.ReflectionTools;

public class DataListImpl<T> implements DataList<T> {
	List<Data<T>> objectList = new ArrayList<>();
	List<DataListEvent<T>> observerList = new ArrayList<>();
	List<T> list = new ArrayList<>();
	
	public DataListImpl(){
	}
	
	@Override
	public void addObject(int index, T object) {
		// allocate a new data
		Data<T> data = new DataImpl<T>();
		// set the value
		data.setValue(object);
		// keep it
		this.objectList.add(index, data);
		// notify all the observers
		this.notifyAddAllObservers(object);
	}

	@Override
	public void updateObject(int index, T object) {
		// first, retrieve the data
		Data<T> data = this.objectList.get(index);
		// update the value
		data.setValue(object);
	}

	@Override
	public void removeData(Data<T>... dataList) {
		for(Data<T> data: dataList){
			// remove the data instance
			this.objectList.remove(data);}
	}

	@Override
	public T getObject(int index) {
		// remove the data instance
		Data<T> data = this.objectList.get(index);
		return data.getValue();
	}

	@Override
	public Data<T> getDataObject(int index) {
		return this.objectList.get(index);
	}

	@Override
	public void subscribeToData(DataListEvent<T> dataListObserver) {
		this.observerList.add(dataListObserver);
	}
	@SuppressWarnings("unchecked")
	public void notifyAddAllObservers(T... listOfObject){
		// hum!! var args is type as Object[] and not T[]
		T[] array = (T[])Array.newInstance(listOfObject[0].getClass(), listOfObject.length);
		int i=0;
		for(Object o : listOfObject){
			array[i++]=(T)o;
		}
		for(DataListEvent<T>obs : this.observerList){
			obs.notifyObjectCreated(array);
		}
	}
	@SuppressWarnings("unchecked")
	public void notifyUpdateAllObservers(T... listOfObject){
		for(DataListEvent<T>obs : this.observerList){
			obs.notifyObjectUpdated(listOfObject);
		}
	}
	@SuppressWarnings("unchecked")
	public void notifyDeleteAllObservers(T listOfObject){
		for(DataListEvent<T>obs : this.observerList){
			obs.notifyObjectDeleted(listOfObject);
		}
	}

	@Override
	public void addObject(T... objectList) {
		for(T obj : objectList){
			Data<T> data = new DataImpl<T>();
			data.setValue(obj);
			this.objectList.add(data);
		}
		// notify all the observers
		this.notifyAddAllObservers(objectList);
	}

	@Override
	public void addData(Data<T>... dataList) {
		for(Data<T> data : dataList){
			this.objectList.add(data);
		}
	}

	@Override
	public void updateData(Data<T>... dataList) {
		for(Data<T> data : dataList){
			int index = this.objectList.indexOf(data);
			this.objectList.set(index, data);
		}
	}

	@Override
	public void removeObject(int... index) {
		this.objectList.remove(index);
	}

	@Override
	public T[] getAllObject() {
		// get this current method
//		Method m=null;
//		try {
//			m= DataListImpl.class.getMethod("getAllObject");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		// get its generic type
		// MLB Class<?> c = ReflectionTools.getGenericReturnedType(m);
		
		// get the first element of the list
		Class<?> type = Object.class;
		if(this.objectList.size()>=1){
			this.objectList.get(0).getValue().getClass();}
		
		T[] array = (T[])Array.newInstance(type, this.objectList.size());
		int index = 0;
		for (Data<T> data :this.objectList){
			array[index++] = data.getValue();
		} 
		return array;
	}

	@Override
	public Data<T>[] getAllDataObject() {
		return null;
	}
}
