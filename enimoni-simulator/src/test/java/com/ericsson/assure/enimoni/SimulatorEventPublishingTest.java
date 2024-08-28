package com.ericsson.assure.enimoni;

import java.util.ArrayList;

import com.ericsson.assure.model.SimulationConfiguration;
import com.ericsson.assure.util.ChannelEventListener;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimulatorEventPublishingTest extends TestCase {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	private ArrayList<ChannelEventListener> channelListernerList = new ArrayList<ChannelEventListener>();
	private SimulatorExecutor simulator = null;
	@Before
	public void setUp(){
		simulator = new SimulatorExecutor();
		Thread simulatorThread = new Thread(simulator);
		simulatorThread.start();
	}
	
	@Test
	public void testEventsAreCollectedByListener(){
		int numberOfEventsSeenByListerner = 0;
		boolean pass = false;
		boolean notReady = true;
		boolean eventsNotPublishedYet = true; 
		notReady = simulator.getStatus();
		while(notReady){
			try {
				Thread.sleep(1000);
				notReady = simulator.getStatus();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		registerListerners(simulator.getSimulationConfigurations(), simulator.getSimulationConfigurations().length);
		while(eventsNotPublishedYet){
			try {
				Thread.sleep(1000);
				eventsNotPublishedYet = simulator.isEventsNotPublishedYet();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(ChannelEventListener listener :channelListernerList){
			numberOfEventsSeenByListerner = listener.getEventCount();
			log.info("Number of events is: " + numberOfEventsSeenByListerner);
			if (numberOfEventsSeenByListerner > 0) {
				pass = true;
			}else if (numberOfEventsSeenByListerner == 0){
				pass = false;
			}
		}
		
		assertTrue(pass);
	}
	
	private void registerListerners(SimulationConfiguration[] simulationConfigurations, int nuberOfconfigs) {
		ITopic topic = null;
		for(int i = 0; i < nuberOfconfigs; i++){
			
			int channelCount = simulationConfigurations[i].getTopicCount();
			String[] channelNames = new String[channelCount];
			channelNames = simulationConfigurations[i].getTopicNames();
			HazelcastInstance hazelcastInstance = simulationConfigurations[i].getHazelcastInstance();
			
			for (int j = 0; j < channelNames.length; j++){
				topic = hazelcastInstance.getTopic(channelNames[j]);
				ChannelEventListener channelListener = new ChannelEventListener();
				channelListernerList.add(channelListener);
				topic.addMessageListener(channelListener);
				log.info("listerner added to topic:" + channelNames[j]);
			}
		}
	}

}
