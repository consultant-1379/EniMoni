package com.ericsson.assure.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.xstream.base.apeventbeans.ApEventBean;
import com.hazelcast.core.ITopic;

public class Publisher {
	
	private long publishedEventCount = 0;

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	public void publish(ApEventBean apEventBean,  ITopic topic){
		topic.publish(apEventBean);
		//log.debug("publish topic: {}", apEventBean.getCSVString() );
		publishedEventCount++;
		
		if (publishedEventCount % 100 == 0){
			log.info("The number of events published so far: {}", publishedEventCount);
		}
	}
}
