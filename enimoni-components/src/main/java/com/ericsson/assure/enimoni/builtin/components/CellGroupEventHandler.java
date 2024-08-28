package com.ericsson.assure.enimoni.builtin.components;

import com.ericsson.assure.enimoni.core.constants.Constants;
import com.ericsson.assure.enimoni.core.model.EnimoniCellObject;
import com.ericsson.oss.itpf.common.event.handler.AbstractEventHandler;
import com.ericsson.oss.itpf.common.event.handler.EventInputHandler;

public class CellGroupEventHandler extends AbstractEventHandler implements EventInputHandler, Constants {

	public void onEvent(Object inputEvent) {
		if (inputEvent instanceof EnimoniCellObject) {
			//EnimoniCellObject cellObject  = (EnimoniCellObject)inputEvent;
			log.warn("Cell Event received . ");	
		}
	}

	@Override
	protected void doInit() {
		// TODO Auto-generated method stub
		
	}
	

}
