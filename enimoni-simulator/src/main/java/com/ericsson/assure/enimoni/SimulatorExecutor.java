package com.ericsson.assure.enimoni;
import com.ericsson.assure.model.SimulationConfiguration;
import com.ericsson.assure.publisher.Publisher;
import com.ericsson.assure.util.Populator;
import com.ericsson.assure.util.SimulationConfigurationLoader;
import com.ericsson.xstream.apeventbeans.celltrace.INTERNAL_PER_RADIO_CELL_MEASUREMENT;
import com.ericsson.xstream.base.apeventbeans.ApEventBean;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimulatorExecutor implements Runnable {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	private static Publisher publisher = new Publisher();
	private SimulationConfiguration[] simulationConfigurations = null;
	private boolean loadingStatus = true;
	private boolean eventsNotPublishedYet = true;

	public static void main(String[] args) {
		
		SimulatorExecutor simulator = new SimulatorExecutor();
		simulator.startSimulator();
	}
	
	private void setupEventsToPublish(SimulationConfiguration simulationConfiguration) {
		
		int numberOfEventsToPublish = simulationConfiguration.getEventCount();
		HazelcastInstance hazelcastInstance = simulationConfiguration.getHazelcastInstance();
				
		
		ITopic topic = null;
		String[] channelNames = simulationConfiguration.getTopicNames();
		ApEventBean[] events = simulationConfiguration.getEventArray();
		
		for (int i = 0; i < numberOfEventsToPublish; i ++) {
			
			for (int j = 0; j < channelNames.length; j++){
				ApEventBean event =  populateEvent(events[i], j);
				//log.debug("topic/channel name: {}",  channelNames[j]);
				topic = hazelcastInstance.getTopic(channelNames[j]);
				publisher.publish(event, topic);
			}
		}
		
		eventsNotPublishedYet = false;
		
	}
	
	private ApEventBean populateEvent(ApEventBean event, int channelNumber) {
		Populator populate = new Populator();
		
		if (event instanceof INTERNAL_PER_RADIO_CELL_MEASUREMENT){
			populate.intarnalePerRadioCellMeasurement(event, channelNumber);
		}
		if (event instanceof com.ericsson.xstream.apeventbeans.celltrace.INTERNAL_PROC_UE_CTXT_RELEASE){
			populate.internalProcUeCtxtRelease(event, channelNumber);
		}
		if (event instanceof com.ericsson.xstream.apeventbeans.ctum.CTUM){
			populate.ctum(event, channelNumber);
		}
		return event;
	}

	public void run() {
		runSimulator();
	}
	
	public void startSimulator() {
		runSimulator();
	}
	
	private void runSimulator(){
		int nuberOfconfigs = 0;
		SimulationConfigurationLoader simConfigLoader = new SimulationConfigurationLoader();
		simulationConfigurations = simConfigLoader.load();
		
		nuberOfconfigs = simulationConfigurations.length;
		log.info("Loading complete starting event generation");
		loadingStatus = false;
		while(true){
			
			for(int i = 0; i < nuberOfconfigs; i++){
				setupEventsToPublish(simulationConfigurations[i]);
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public SimulationConfiguration[] getSimulationConfigurations() {
		return simulationConfigurations;
	}

	public boolean getStatus() {
		return this.loadingStatus;
	}
	public boolean isEventsNotPublishedYet() {
		return eventsNotPublishedYet;
	}

}
