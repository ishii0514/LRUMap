package com.ishii0514.app;


public class KeyContainer<K> {

	private K key;
	private long time;
	public KeyContainer(K in_key)
	{
		key = in_key;
		time = System.currentTimeMillis();
	}
	
	public void setTime(){
		time = System.currentTimeMillis();
	}
}
