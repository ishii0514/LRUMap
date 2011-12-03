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

public Lrumap(int size)
{
	if (size < 1)
		throw new IllegalArgumentException("illegal size.[" + size + "]");

	this.size = size;
	this.interval = 3000;
	this.datamap = new HashMap<K, V>();
	this.lrulist = new LinkedList<KeyContainer<K>>();
}
public Lrumap(int size,long interval)
{
	this(size);
	if (interval < 1)
		throw new IllegalArgumentException("illegal interval.[" + interval + "]");
	this.interval = interval;
}

int maxSize()
{
	return size;
}

public void put(K key, V value) {
	//古いデータの削除
	removeOldData(System.currentTimeMillis());
	//重複を防ぐため削除しておく
	removeData(key);

	addData(key,value);
	checkSize();
}

public V get(K key) {
	//古いデータの削除
	removeOldData(System.currentTimeMillis());
	//リスト付け替え
	replaceLRU(key);
	return datamap.get(key);
}

public void ModifySize(int size) {
	if (size <= 0)
		throw new IllegalArgumentException("illegal size.[" + size + "]");
	this.size=size;
	checkSize();
}

//指定したキーのデータを削除
private void removeData(K key){
		datamap.remove(key);
		lrulist.remove(new KeyContainer<K>(key));
}
//データ登録
private void addData(K key,V value){
	datamap.put(key, value);
	lrulist.add(new KeyContainer<K>(key));
}
//リストの付け替え
private void replaceLRU(K key){
	if(lrulist.remove(new KeyContainer<K>(key)))
	{
		lrulist.add(new KeyContainer<K>(key));
	}
}

//sizeと同じになるまで要素を削除
private void checkSize()
{
	if (lrulist.size() > size)
	{
		removeData(lrulist.get(0).getKey());
		checkSize();
	}
}
//古いデータを削除
private void removeOldData(long now){
	if(lrulist.size() == 0)
		return;
	if (lrulist.get(0).IsOld(now, interval))
	{
		removeData(lrulist.get(0).getKey());
		removeOldData(now);
	}
}

}