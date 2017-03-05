package org.capcaval.lafabrique.data;

public interface DataMapReadWrite <T, K> extends DataMapReadOnly<T, K>{
	void addObjectFromKey(K key, T object);
	void updateObjectFromKey(K key, T object);
	void deleteObjectFromKey(K key);
}
