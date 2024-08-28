package com.ericsson.assure.enimoni.builtin.components;

import com.ericsson.assure.enimoni.core.constants.Constants;
import com.ericsson.assure.enimoni.core.model.EnimoniUeObject;
import com.ericsson.oss.itpf.common.event.handler.AbstractEventHandler;
import com.ericsson.oss.itpf.common.event.handler.EventInputHandler;

public class UeEventLogHandler extends AbstractEventHandler implements EventInputHandler, Constants {

	public void onEvent(Object inputEvent) {
		if (inputEvent instanceof EnimoniUeObject) {
			EnimoniUeObject ueObject  = (EnimoniUeObject)inputEvent;
			if(log.isDebugEnabled())
				log.debug("ueObjectReceived IMSI={}, IMEISV={} " , ueObject.getIMSI() , ueObject.getIMEISV());	
		}
	}

	@Override
	protected void doInit() {
		// TODO Auto-generated method stub
		
	}
	

}
