package com.ericsson.assure.enimoni.model;

import java.util.Map;

import com.ericsson.assure.enimoni.core.model.EnimoniUeObject;
import com.ericsson.oss.itpf.wwecds.ECDSRadioEvent;
import com.ericsson.oss.itpf.wwecds.SessionStatus;
import com.ericsson.xstream.apeventbeans.correlation.CALL_DROP;

public class LTE_EUTRAN_CALL_DROP extends CALL_DROP implements Cloneable, EnimoniUeObject,ECDSRadioEvent {

	private static final long serialVersionUID = 4958774859519368075L;
	
	@Override
	public Object clone() {
		try {
			final LTE_EUTRAN_CALL_DROP cloned = (LTE_EUTRAN_CALL_DROP) super.clone();
			return cloned;
		} catch (final CloneNotSupportedException e) {
			e.printStackTrace();
		} catch (final Throwable t) {
			t.printStackTrace();
		}
		return null;
	}

	public LTE_EUTRAN_CALL_DROP cloneMe() {
		final LTE_EUTRAN_CALL_DROP source = (LTE_EUTRAN_CALL_DROP) this.clone();
		return source;
	}

	public void fillMapWithCommonInfo(Map arg0) {
		// TODO Auto-generated method stub
		
	}

	public SessionStatus getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setStatus(SessionStatus arg0) {
		// TODO Auto-generated method stub
		
	}

}
