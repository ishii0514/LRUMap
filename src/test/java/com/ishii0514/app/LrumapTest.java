package com.ishii0514.app;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNull;
import org.testng.annotations.Test;
/**
* Unit test for simple App.
*/
public class LrumapTest

{

@Test
    public void testLRUMap()
    {
     Lrumap<String, String> lru = new Lrumap<String,String>(3);
     assertEquals(3, lru.maxSize());
     Lrumap<Object,Object> lru1 = new Lrumap<Object, Object>(4);
     assertEquals(4, lru1.maxSize());
    }

@Test
    public void testPutGet()
    {
     Lrumap<String, String> lru= new Lrumap<String, String>(2);
     lru.put("A","testA");
     assertEquals("testA",lru.get("A"));
     lru.put("B","testB");
     assertEquals("testB",lru.get("B"));
    }

@Test
    public void testPut3Elements()
    {
     Lrumap<String, String> lru = new Lrumap<String, String>(1);
     lru.put("A", "testA");
     lru.put("B", "testB");
     assertNull(lru.get("A"));

     Lrumap<String, String> lru2 = new Lrumap<String, String>(1);
     lru2.put("B", "testB");
     lru2.put("A", "testA");
     assertNull(lru2.get("B"));

     Lrumap<String, String> lru3 = new Lrumap<String, String>(1);
     lru3.put("A", "testA");
     lru3.put("B", "testB");
     lru3.put("C", "testC");
     assertNull(lru3.get("B"));
    }

@Test
    public void testTouch()
    {
     Lrumap<String, String> lru = new Lrumap<String, String>(2);
     lru.put("1","dragon");
     lru.put("2", "liger");
     lru.get("1");
     lru.put("3", "poseidon");
     assertNull(lru.get("2"));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSize()
    {
     new Lrumap<String, String>(0);
    }

    @Test
    public void testDuplicate()
    {
     Lrumap<String, String> lru = new Lrumap<String, String>(2);
     lru.put("1","dragon");
     lru.put("2", "liger");
     lru.put("1","dog");
     lru.put("3","giraff");

     assertEquals("dog",lru.get("1"));
    }

    @Test
    public void testDynamicSize()
    {
     Lrumap<String, String> lru = new Lrumap<String, String>(2);
     lru.put("A","iii");
     lru.put("B", "kkk");

     lru.ModifySize(1);
     assertNull(lru.get("A"));

    }
    
    @Test
    public void testDynamicSize2()
    {
     Lrumap<String, String> lru = new Lrumap<String, String>(4);
     lru.put("A","i");
     lru.put("B", "j");
     lru.put("C", "k");
     lru.put("D", "l");

     lru.ModifySize(1);
     assertNull(lru.get("A"));
     assertNull(lru.get("B"));
     assertNull(lru.get("C"));
     assertEquals("l",lru.get("D"));

    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testDynamicSize1()
    {
     Lrumap<String, String> lru = new Lrumap<String, String>(2);
     lru.put("A","iii");
     lru.put("B", "kkk");

     lru.ModifySize(-1);
    }
    
    @Test
    public void testDeleteOldData()
    {
     Lrumap<String, String> lru = new Lrumap<String, String>(2);
     lru.put("A","iii");
     lru.put("B", "kkk");

     try {
		Thread.sleep(4000);
	} catch (InterruptedException e) {
		// TODO 自動生成された catch ブロック
		e.printStackTrace();
	}
     assertNull(lru.get("B"));
    }

}