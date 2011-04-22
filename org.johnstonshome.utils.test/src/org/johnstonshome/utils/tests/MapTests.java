/*
 * Licensed Materials - Property of Simon Johnston (simon@johnstonshome.org)
 * (c) Copyright Simon Johnston 2009. All rights reserved.
 * 
 * For full license details, see the file LICENSE included in the
 * distribution of this code.
 * 
 */
package org.johnstonshome.utils.tests;

import static org.junit.Assert.*;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.johnstonshome.utils.fun.Maps;
import org.johnstonshome.utils.lang.Pair;
import org.junit.Test;

/**
 * Test suite for the {@link Maps} class.
 * 
 * @author Simon Johnston (simon@johnstonshome.org)
 *
 */
public class MapTests {
    
    public static class Person {
        private String name;
        private String title;
        private String address;
        private String phone;
        private int    age;
        
        public Person() {
            // required bean constructor
        }
            
        public String getName() {
            return this.name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getTitle() {
            return this.title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public String getAddress() {
            return this.address;
        }
        public void setAddress(String address) {
            this.address = address;
        }
        public String getPhone() {
            return this.phone;
        }
        public void setPhone(String phone) {
            this.phone = phone;
        }
        public int getAge() {
            return this.age;
        }
        public void setAge(int age) {
            this.age = age;
        }
        public int getAgeForward(int years) {
            return this.age + years;
        }
    }

    @Test
    public void testMapItemsNull() {
        assertNull(Maps.items((Map<String, String>)null));
    }
    
	@Test
	public void testMapItems() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("one", "ONE");
		map.put("two", "TWO");
		map.put("three", "THREE");
		
		List<Pair<String, String>> pairs = Maps.items(map);
		assertEquals(3, pairs.size());
	}

    @Test
    public void testMapCombineNull() {
        assertNull(Maps.combine(null));
    }
    
	@Test
	public void testMapCombine() {
		List<Pair<String,String>> list = new LinkedList<Pair<String,String>>();
		list.add(new Pair<String, String>("one", "ONE"));
		list.add(new Pair<String, String>("two", "TWO"));
		list.add(new Pair<String, String>("three", "THREE"));
		
		Map<String, String> map = Maps.combine(list);
		assertEquals(3, map.size());
		assertTrue(map.containsKey("one"));
		assertEquals("TWO", map.get("two"));
	}

    @Test
    public void testDictionaryItemsNull() {
        assertNull(Maps.items((Dictionary<String, String>)null));
    }
    
	@Test
	public void testDictionaryItems() {
		Dictionary<String, String> map = new Hashtable<String, String>();
		map.put("one", "ONE");
		map.put("two", "TWO");
		map.put("three", "THREE");
		
		List<Pair<String, String>> pairs = Maps.items(map);
		assertEquals(3, pairs.size());
	}

    @Test
    public void testPropertiesItemsNull() {
        assertNull(Maps.items((Properties)null));
    }
    
	@Test
	public void testPropertiesItems() {		
		final Properties map = new Properties();
		map.put("one", "ONE");
		map.put("two", "TWO");
		map.put("three", "THREE");
		
		final List<Pair<String, String>> pairs = Maps.items(map);
		assertEquals(3, pairs.size());
	}

    @Test
    public void testBeanItemListNull() {
        assertNull(Maps.beanItems(null));
    }
    
	@Test
	public void testBeanItemList() {
	    Person person = makePerson();
	    final List<Pair<String, Object>> pairs = Maps.beanItems(person);
	    assertEquals(5, pairs.size());
	}
	
    @Test
    public void testBeanItemMapNull() {
        assertNull(Maps.beanMap(null));
    }
    
    @Test
    public void testBeanItemMap() {
        Person person = makePerson();
        final Map<String, Object> map = Maps.beanMap(person);
        assertEquals(5, map.size());
        assertEquals("simonjo", map.get("name"));
        assertEquals("author", map.get("title"));
        assertEquals("here", map.get("address"));
        assertEquals("555-555-5555", map.get("phone"));
        assertEquals(Integer.valueOf(99), map.get("age"));
//        assertEquals(Person.class, map.get("class"));
    }
    
    @Test
    public void testBeanCombineListNull() {
        assertNull(Maps.combineBean((List<Pair<String, Object>>)null, Person.class));
        final List<Pair<String, Object>> list = new LinkedList<Pair<String,Object>>();
        assertNull(Maps.combineBean(list, null));
    }
    
    @Test
    public void testBeanCombineList() {
        final List<Pair<String, Object>> list = new LinkedList<Pair<String,Object>>();
        list.add(new Pair<String, Object>("name", "simonjo"));
        list.add(new Pair<String, Object>("title", "author"));
        list.add(new Pair<String, Object>("address", "here"));
        list.add(new Pair<String, Object>("phone", "555-555-5555"));
        list.add(new Pair<String, Object>("age", Integer.valueOf(99)));
        final Person person = Maps.combineBean(list, Person.class);
        assertEquals("simonjo", person.getName());
        assertEquals("author", person.getTitle());
        assertEquals("here", person.getAddress());
        assertEquals("555-555-5555", person.getPhone());
        assertEquals(99, person.getAge());
    }
    
    @Test
    public void testBeanCombineMapNull() {
        assertNull(Maps.combineBean((Map<String, Object>)null, Person.class));
        final Map<String, Object> map= new HashMap<String, Object>();
        assertNull(Maps.combineBean(map, null));
    }
    
    @Test
    public void testBeanCombineMap() {
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "simonjo");
        map.put("title", "author");
        map.put("address", "here");
        map.put("phone", "555-555-5555");
        map.put("age", Integer.valueOf(99));
        final Person person = Maps.combineBean(map, Person.class);
        assertEquals("simonjo", person.getName());
        assertEquals("author", person.getTitle());
        assertEquals("here", person.getAddress());
        assertEquals("555-555-5555", person.getPhone());
        assertEquals(99, person.getAge());
    }
    
	private Person makePerson() {
        final Person person = new Person();
        person.setName("simonjo");
        person.setTitle("author");
        person.setAddress("here");
        person.setPhone("555-555-5555");
        person.setAge(99);
        return person;
	}
}
