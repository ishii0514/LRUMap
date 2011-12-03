package com.ishii0514.app;


public class KeyContainer<K> {

	private K key;
	private long time;
	
	public KeyContainer(K key)
	{
		this.key = key;
		this.time = System.currentTimeMillis();
	}
	
	@SuppressWarnings("unchecked")
	public boolean equals(Object o)
	{
		return (o instanceof KeyContainer) && key.equals(((KeyContainer<K>)o).getKey());
	}
	public K getKey(){
		return key;
	}
	
	//指定時刻より古い
	public boolean IsOld(long now,long interval){
		return (now -interval) > time;
	}
}
