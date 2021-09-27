package com.revature.objectmapper;

import java.util.HashMap;
import java.util.HashSet;

import org.apache.log4j.Logger;

public class ObjectCache {
	
	private static Logger log = Logger.getLogger(ObjectCache.class);
	private final static ObjectCache obj_cache = new ObjectCache();
	private final HashMap<Class<?>, HashSet<Object>> cache;
	
	private ObjectCache() {
		super();
		cache = new HashMap<>();
	}
	
	public HashMap<Class<?>, HashSet<Object>> getCache() {
		return cache;
	}
	
	public void putObjectInCache(final Object obj) {
		if (!cache.containsKey(obj.getClass())) {
			cache.put(obj.getClass(), new HashSet<>());
		}
		log.info("Added object to local cache.");
		cache.get(obj.getClass()).add(obj);
		
	}
	
	public boolean cacheContains(final Object obj) {
		
		if (cache.containsKey(obj.getClass())) {
			HashSet<Object> objs = cache.get(obj.getClass());
			
			if (objs.contains(obj)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean removeObjectFromCache(final Object obj) {
		
		if (cache.containsKey(obj.getClass())) {
			HashSet<Object> objs = cache.get(obj.getClass());
			
			if (objs.contains(obj)) {
				cache.get(obj.getClass()).remove(obj);
				log.info("Removed object from the local cache.");
				return true;
			}
		}
		
		log.warn("Could not remove object from the local cache.");
		return false;
	}
	
	public boolean updateObjectInCache(final Object toReplace, final Object toAdd) {
		
		if (removeObjectFromCache(toReplace)) {
			putObjectInCache(toAdd);
			log.info("Succsessfully updated object in the local cache.");
			return true;
		}
		
		log.warn("Could not update object from the local cache.");
		return false;
	}
	
	public static ObjectCache getInstance() {
		return obj_cache;
	}

}
