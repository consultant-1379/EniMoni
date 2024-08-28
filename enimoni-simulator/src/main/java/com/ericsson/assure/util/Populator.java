package com.ericsson.assure.util;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.ericsson.xstream.apeventbeans.celltrace.INTERNAL_PER_RADIO_CELL_MEASUREMENT;
import com.ericsson.xstream.base.apeventbeans.ApEventBean;

public class Populator {

	
	public void intarnalePerRadioCellMeasurement(ApEventBean event, int channelNumber) {
		setEventDate(event);
		INTERNAL_PER_RADIO_CELL_MEASUREMENT cellMeasurement = (INTERNAL_PER_RADIO_CELL_MEASUREMENT) event;
		//TODO populate the event.
		
	}

	//TODO use the channel number as a method of using the same values for the gummie and ____ for the correlations.
	public void internalProcUeCtxtRelease(ApEventBean event, int channelNumber) {
		setEventDate(event);
		//TODO populate the event.
		
	}

	public void ctum(ApEventBean event, int channelNumber) {
		setEventDate(event);
		//TODO populate the event.
		
	}
	
	private void setEventDate(ApEventBean event) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        event.setTimestamp(calendar.getTimeInMillis());
	}
	
}
