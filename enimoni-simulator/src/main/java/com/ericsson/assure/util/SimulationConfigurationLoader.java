package com.ericsson.assure.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.assure.config.Configurator;
import com.ericsson.assure.model.SimulationConfiguration;

public class SimulationConfigurationLoader {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	private static List<String> headerList = new ArrayList<String>();
	private static List<SimulationConfiguration> simulationConfigurationList = new ArrayList<SimulationConfiguration>();
	private static final String HEADER_IDENTIFIER		= "topic.HEADER.";
	private static final String EVENTS_IDENTIFIER		= ".events";
	private static final String BUS_IDENTIFIER			= ".bus";
	private static final String TOPIC_COUNT_IDENTIFIER 	= ".topic.count";
	private static final String TOPIC_IDENTIFIER 	    = "topic.";
	
	public SimulationConfiguration[] load(){
		loadHeaderList();
		loadSimulationConfigurationList();
		return simulationConfigurationList.toArray(new SimulationConfiguration[0]);
	}
	private void loadSimulationConfigurationList() {
		String busConfigurationFile = null;
		String topicCount = null;
		String events = null;
		for(String headerName : headerList){
			events = Configurator.getInstance().getProperties().getProperty(TOPIC_IDENTIFIER + headerName + EVENTS_IDENTIFIER);
			busConfigurationFile = Configurator.getInstance().getProperties().getProperty(TOPIC_IDENTIFIER + headerName + BUS_IDENTIFIER);
			topicCount = Configurator.getInstance().getProperties().getProperty(TOPIC_IDENTIFIER + headerName + TOPIC_COUNT_IDENTIFIER);
			log.info("Loading header {}, with bus configuration {}", headerName,busConfigurationFile);
			simulationConfigurationList.add(new SimulationConfiguration(headerName,	topicCount, events, busConfigurationFile));
		}
	}
	private static void loadHeaderList() {
		Set<Map.Entry<Object,Object>> propertyList = Configurator.getInstance().getProperties().entrySet();
		for (Entry<Object, Object> entry : propertyList) {
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			if(key.contains(HEADER_IDENTIFIER)){
				headerList.add(value);
			}
		}
	}

}
