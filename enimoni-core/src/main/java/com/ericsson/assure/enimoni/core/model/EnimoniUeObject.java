package com.ericsson.assure.enimoni.core.model;

public interface EnimoniUeObject {
	 public long getIMSI();
	 public long getIMEISV();
	 public long getENB_ID();
	 public long getGLOBAL_CELL_ID();
	 public long getMME_S1_AP_ID();
	 public long getENB_S1_AP_ID();
	 public byte[] getGUMMEI();
	 public long getRAC_UE_REF();
}
