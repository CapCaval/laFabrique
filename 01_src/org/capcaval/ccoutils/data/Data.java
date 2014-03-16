package org.capcaval.ccoutils.data;


public interface Data<T> extends DataReadWrite<T>, DataFeeder<T> {
	public static DataFactory factory = new DataFactoryImpl();
	
	public interface DataFactory{
		<T> Data<T> newData();
		<T> Data<T> newData(T data);
		<T> DataReadOnly<T> newReadOnlyData(T data);
	}
}
