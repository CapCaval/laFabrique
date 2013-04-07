package org.capcaval.ccoutils.data;

@SuppressWarnings("unchecked")
public interface DataListReadWrite <T> extends DataListReadOnly<T>{
	void addObject(T... object);
	void addObject(int index, T object);
	void addData(Data<T>... dataList);
	void updateObject(int index, T object);
	void updateData(Data<T>... dataList);
	void removeObject(int... index);
	void removeData(Data<T>... dataList);
}
