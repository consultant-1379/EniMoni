package com.ericsson.assure.config;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

 
public class Configurator {
	
	
	private static Configurator defaultInstance;
	private Properties props = null;

	public static Configurator getInstance() {
		File f = new File("enimoni-simulator.properties");
		if(!f.exists()){
			System.out.println("NO config file Found expected enimoni-simulator.properties");
		}
		if (defaultInstance == null) {
			defaultInstance = new Configurator();
			defaultInstance.props = loadProperties("enimoni-simulator.properties");
		}

		return defaultInstance;
	}
	

	public Properties getProperties() {
		return props;
	}

	private static Properties loadProperties(String fileName) {
		Properties props = null;
		InputStream is = null;
		try {
			is = Configurator.class.getClassLoader().getResourceAsStream(fileName);
			props = new Properties();
			props.load(is);
		} catch (Exception e) {
            //log
		} finally{
			if(is != null){
				try {
					is.close();
				} catch (Exception ignore) {
				}
			}
		}
		return props;
	}
}
