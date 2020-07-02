package com.farbig.cart.services.gateway.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GatewayMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HashMap data = null;

    /**
    * Constructs an empty Message with default initial capacity(16) and default
    * initial load factor (0.75)
    */
   public GatewayMessage()
   {
       data = new HashMap();
   }

   /**
    * Constructs an empty Message with given initial capacity and default
    * initial load factor (0.75)
    * 
    * @param initialCapacity
    * @throws IllegalArgumentException -
    *             if the initial capacity is negative
    */
   public GatewayMessage(int initialCapacity) throws IllegalArgumentException
   {
       data = new HashMap(initialCapacity);
   }

   /**
    * Constructs an empty Message with given initial capacity and given initial
    * load factor
    * 
    * @param initialCapacity
    * @param loadFactor
    * @throws IllegalArgumentException -
    *             if the initial capacity is negative or the load factor is
    *             non-positive
    */
   public GatewayMessage(int initialCapacity, float loadFactor)
           throws IllegalArgumentException
   {
       data = new HashMap(initialCapacity);
   }

   /**
    * returns the number of entries in the message, 0 if it is empty
    */
   public int size()
   {
       return data.size();
   }

   /**
    * returns the value associated with this name in this message
    * 
    * @param name
    * @return the object assoicated with this name, or null if the name is not
    *         found
    */
   public Object get(String name)
   {
       return data.get(name);
   }

   /**
    * Puts an Object into the internal Hashmap with key=name, replace the old
    * value if the key exists already.
    * 
    * @param name
    * @param value
    * @return previous value associated with name, null if there was no mapping
    */
   public Object put(String name, Object value)
   {
       return data.put(name, value);
   }

   /**
    * Copies the content of given map to the message, all existing mappings
    * will be replaced
    * 
    * @param m
    * @throws NullPointerException -
    *             if m is null
    */
   public void putAll(Map m) throws NullPointerException
   {
       data.putAll(m);
   }

   /**
    * removes all mappings in this message
    */
   public void clear()
   {
       data.clear();
   }

   /**
    * removes an association based on the name if present
    * 
    * @param name
    * @return previous value associated with name, null if there was no mapping
    */
   public Object remove(String name)
   {
       return data.remove(name);
   }

   /**
    * @param name -
    *            name of the key whose presence in the message is to be tested
    * @return true if the name is presents, false otherwise
    */
   public boolean containsKey(String name)
   {
       return data.containsKey(name);
   }
   
   /**
    */
   public Set keySet()
   {
       return data.keySet();
   }

	

}
