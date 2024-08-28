package com.ericsson.assure.enimoni.model;

import com.ericsson.assure.enimoni.core.model.EnimoniCellObject;
import com.ericsson.xstream.apeventbeans.correlation.CELL_LOG;

public class LTE_EUTRAN_CELL_LOG extends CELL_LOG implements Cloneable, EnimoniCellObject {

	private static final long serialVersionUID = 4958774859519368075L;
	
	@Override
	public Object clone() {
		try {
			final LTE_EUTRAN_CELL_LOG cloned = (LTE_EUTRAN_CELL_LOG) super.clone();
			return cloned;
		} catch (final CloneNotSupportedException e) {
			e.printStackTrace();
		} catch (final Throwable t) {
			t.printStackTrace();
		}
		return null;
	}

	public LTE_EUTRAN_CELL_LOG cloneMe() {
		final LTE_EUTRAN_CELL_LOG source = (LTE_EUTRAN_CELL_LOG) this.clone();
		return source;
	}
}
