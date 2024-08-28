package com.ericsson.assure.util;

import java.io.FileNotFoundException;
import java.util.concurrent.ConcurrentHashMap;

import com.hazelcast.config.Config;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class HazelcastAdapterUtil {
	
	/*
     * Here we cache already created hazelcast instances - mapped to their configuration file, so that we reuse them per JVM
     */
    private static final ConcurrentHashMap<String, HazelcastInstance> CREATED_HAZELCAST_INSTANCES = new ConcurrentHashMap<String, HazelcastInstance>();
	
    public synchronized static HazelcastInstance getOrCreateHazelcastInstance(String config) {
        if (config == null) {
            throw new IllegalArgumentException("config must not be null");
        }
        
        
        Config cfg = null;
            try {
				cfg = new XmlConfigBuilder(config).build();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
        HazelcastInstance hzInstance = CREATED_HAZELCAST_INSTANCES.get(config);
        if (hzInstance == null){
        	hzInstance = Hazelcast.newHazelcastInstance(cfg);
        	CREATED_HAZELCAST_INSTANCES.put(config, hzInstance);
        }
        return hzInstance;
    }
    

    
    
}
