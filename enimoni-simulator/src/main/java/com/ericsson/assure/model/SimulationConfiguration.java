package com.ericsson.assure.model;

import com.ericsson.assure.util.HazelcastAdapterUtil;
import com.ericsson.xstream.base.apeventbeans.ApEventBean;
import com.hazelcast.core.HazelcastInstance;

public class SimulationConfiguration {

	private int topicCount=0;
	private int eventCount=0;
	private String hazelcastConfigurationFile=null;
	private ApEventBean[] eventArray=null;
	private String[] topicNames = null;
	private String[] events = null;
	private HazelcastInstance hazelcastInstance = null;
	
	public SimulationConfiguration(String headerName, String topicCount, String events, String busConfigurationFile)  {
		
		this.hazelcastConfigurationFile = busConfigurationFile;
		
		setHazelcastInstance(HazelcastAdapterUtil.getOrCreateHazelcastInstance(hazelcastConfigurationFile));
		
		try {
			this.topicCount = Integer.parseInt(topicCount);
		}catch(NumberFormatException e){
			System.out.println("Failed to parse the number of topics for the header: " + headerName +", the value which was collected was " + topicCount + ", check the configuration file and ensure the value is an interger");
			System.exit(0);
		}
		
		topicNames = new String[this.topicCount];
		for(int i = 0; i < this.topicCount; i++){
			topicNames[i] = ""+ headerName  + "_" + i;
		}
		
		this.events = events.split(",");
		this.eventCount = this.events.length;
		
		this.eventArray = new ApEventBean[eventCount];
		for (int i = 0; i < eventCount; i++){
			Class<?> eventClass = null;
			try {
				eventClass = Class.forName(this.events[i]);
				ApEventBean tempEventBean;
				tempEventBean = (ApEventBean) eventClass.newInstance();
				eventArray[i] = tempEventBean;	
			} catch (ClassNotFoundException e) {
				e.printStackTrace();			
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}	
		}
	}
	
	public int getTopicCount() {
		return topicCount;
	}
	public void setTopicCount(int topicCount) {
		this.topicCount = topicCount;
	}
	public String getHazelcastConfigurationFile() {
		return hazelcastConfigurationFile;
	}
	public void setHazelcastConfigurationFile(String hazelcastConfigurationFile) {
		this.hazelcastConfigurationFile = hazelcastConfigurationFile;
	}
	public ApEventBean[] getEventArray() {
		return eventArray;
	}
	public void setEventArray(ApEventBean[] eventArray) {
		this.eventArray = eventArray;
	}
	public String[] getTopicNames() {
		return topicNames;
	}
	public void setTopicNames(String[] topicNames) {
		this.topicNames = topicNames;
	}
	public int getEventCount() {
		return eventCount;
	}
	public void setEventCount(int eventCount) {
		this.eventCount = eventCount;
	}
	public String[] getEvents() {
		return events;
	}
	public void setEvents(String[] events) {
		this.events = events;
	}

	public HazelcastInstance getHazelcastInstance() {
		return hazelcastInstance;
	}

	public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
		this.hazelcastInstance = hazelcastInstance;
	}
	
	
}
