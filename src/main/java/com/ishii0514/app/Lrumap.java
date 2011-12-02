package com.ishii0514.app;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Lrumap<K,V> {

private int size;
private Map<K,V> datamap;
private List<K> history;

public Lrumap(int in_size)
{
	if (in_size < 1)
		throw new IllegalArgumentException();

	size = in_size;
	datamap = new HashMap<K, V>();
	history = new LinkedList<K>();
}

int maxSize()
{
	return size;
}

public void put(K key, V value) {
	//重複を防ぐため削除しておく
	remove(key);
	
	datamap.put(key, value);
	history.add(key);
	checkSize();
}

public V get(K key) {
	if (history.remove(key))
	{
		history.add(key);
	}
	return datamap.get(key);
}

public void ModifySize(int in_size) {
	if (in_size <= 0)
		throw new IllegalArgumentException();
	
	size=in_size;
	while(checkSize());
}

//サイズが大きかったら、１つ削除
private boolean checkSize()
{
	if (history.size() > size)
	{
		remove(history.get(0));
		return true;
	}
	return false;
}

//指定したキーのデータを削除
private void remove(K key){
		datamap.remove(key);
		history.remove(key);
}

}