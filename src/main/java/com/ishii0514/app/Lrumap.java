package com.ishii0514.app;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Lrumap<K,V> {

private int size;
private long interval;
private Map<K,V> datamap;
private List<KeyContainer<K>>  lrulist;

public Lrumap(int in_size)
{
	if (in_size < 1)
		throw new IllegalArgumentException("illegal size.[" + in_size + "]");

	size = in_size;
	interval = 3000;
	datamap = new HashMap<K, V>();
	lrulist = new LinkedList<KeyContainer<K>>();
}
public Lrumap(int in_size,long in_interval)
{
	if (in_size < 1)
		throw new IllegalArgumentException("illegal size.[" + in_size + "]");
	if (in_interval < 1)
		throw new IllegalArgumentException("illegal interval.[" + in_interval + "]");

	size = in_size;
	interval = in_interval;
	datamap = new HashMap<K, V>();
	lrulist = new LinkedList<KeyContainer<K>>();
}

int maxSize()
{
	return size;
}

public void put(K key, V value) {
	//古いデータの削除
	removeOldData(System.currentTimeMillis());
	//重複を防ぐため削除しておく
	remove(key);

	datamap.put(key, value);
	lrulist.add(new KeyContainer<K>(key));
	checkSize();
}

public V get(K key) {
	//古いデータの削除
	removeOldData(System.currentTimeMillis());
	
	if(lrulist.remove(new KeyContainer<K>(key)))
	{
		lrulist.add(new KeyContainer<K>(key));
	}
	return datamap.get(key);
}

public void ModifySize(int in_size) {
	if (in_size <= 0)
		throw new IllegalArgumentException("illegal size.[" + in_size + "]");
	size=in_size;
	checkSize();
}

//指定したキーのデータを削除
private void remove(K key){
		datamap.remove(key);
		lrulist.remove(new KeyContainer<K>(key));
}
//sizeと同じになるまで要素を削除
private void checkSize()
{
	if (lrulist.size() > size)
	{
		remove(lrulist.get(0).getKey());
		checkSize();
	}
}
//古いデータを削除
private void removeOldData(long now){
	if(lrulist.size() == 0)
		return;
	if (lrulist.get(0).IsOld(now, interval))
	{
		remove(lrulist.get(0).getKey());
		removeOldData(now);
	}
}

}