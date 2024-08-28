package com.ericsson.assure.enimoni.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.xstream.base.apeventbeans.ApEventBean;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.UpdateListener;

public class EsperHandler {
	//                                        /enimoni-correlation/
	public static final String RELEATIVE_PATH_TO_CONFIGURATION = "../enimoni-solution-set/src/main/resources/esper-config/";
	public static final String ESPER_CONFIGURATION_FILE_NAME = "enimoni-esper-configuration.xml";
	private static final Logger logger = LoggerFactory.getLogger(EsperHandler.class);
	private EPServiceProvider serviceProvider = null;
	private EPRuntime epRuntime = null;
	private String epl= null;
	private Configuration configuration = null;
	
	
	public EsperHandler(String ruleFileName){
		this.epl = ruleFileName;
	}
	
	public void configureHandler(UpdateListener listener, String[] statements) throws Exception {
		configuration = new Configuration();
		configuration.configure(new File(RELEATIVE_PATH_TO_CONFIGURATION + ESPER_CONFIGURATION_FILE_NAME));
		serviceProvider = EPServiceProviderManager.getProvider(this.epl, configuration); 
		logger.info("Deploying epl : " + epl + " with steps , initialization, epl file deployment, statement attachment");
		deployCEP(listener,statements);
		logger.info("epl : " + epl + " is successfully deployed.");
	}
	
	public void sendEvent(ApEventBean event){
		epRuntime.sendEvent(event);
	}
	
	public EPRuntime getRuntime(){
		return this.epRuntime;
	}
	
	private void deployCEP(UpdateListener listener, String[] statements) throws Exception{
		serviceProvider.initialize();
		InputStream inputFile = getInputStream(epl);
		serviceProvider.getEPAdministrator().getDeploymentAdmin().readDeploy(inputFile, null, null, null);
		if(listener != null && statements != null)
			listenToStatements(listener, statements);
		epRuntime = serviceProvider.getEPRuntime();
	}

	private void listenToStatements(UpdateListener listener,String[] statements) {
		for (String statement : statements) {
			addStatementAndListener(statement, listener);
		}
	}
	private static InputStream getInputStream(String fileName) throws FileNotFoundException {
		InputStream inputStream = ResourceLoadingUtilities.getInputStreamForResourceOnClassPath(fileName);
		if (inputStream == null) {
			throw new FileNotFoundException("Could not find file " + fileName + " on the classpath");
		}
		return inputStream;
	}

	private void addStatementAndListener(String statement, UpdateListener updateListener) {
		EPAdministrator epAdministrator = serviceProvider.getEPAdministrator();
		EPStatement epStatement = epAdministrator.getStatement(statement);
		if (epStatement == null) {
			throw new RuntimeException("Could not find epl statement " + statement);
		}
		logger.info("Adding listener on statement " + statement);
		epStatement.addListener(updateListener);
	}
}
