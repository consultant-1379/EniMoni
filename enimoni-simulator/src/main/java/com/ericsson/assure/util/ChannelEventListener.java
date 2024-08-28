package com.ericsson.assure.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;

public class ChannelEventListener implements MessageListener {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private volatile boolean isDestroyed = false;
    private int eventCount = 0;
   
	public void onMessage(final Message msg) {
        if (isDestroyed) {
            log.warn("Listener destroyed but still receiving messages. Will not forward them further!");
            return;
        }
        eventCount++;
       
       log.info("events recived = {}", eventCount);
        
    }

    void destroyListener() {
    	isDestroyed = true;
        log.info("Destroying hazelcast event listener, will not process any more messages!");
    }

    public int getEventCount() {
		return eventCount;
	}


}

