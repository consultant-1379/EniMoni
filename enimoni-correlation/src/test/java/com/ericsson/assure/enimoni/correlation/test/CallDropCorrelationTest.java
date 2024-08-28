package com.ericsson.assure.enimoni.correlation.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.assure.enimoni.handler.EsperHandler;
import com.ericsson.assure.enimoni.model.LTE_EUTRAN_CALL_DROP;
import com.ericsson.xstream.apeventbeans.celltrace.INTERNAL_PROC_UE_CTXT_RELEASE;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import static org.junit.Assert.*;

public class CallDropCorrelationTest {
	
	private EsperHandler cepHandler = null;
	private static String eplName = "LTE_EUTRAN_UE_CALL_DROP.epl";
	private static String[] statementArray = { "CALL_DROP" };
	private final InstantListener sessionListener = new InstantListener();
	private static final Logger logger = LoggerFactory.getLogger(CallDropCorrelationTest.class);
	public static List<LTE_EUTRAN_CALL_DROP> callDropList = new ArrayList<LTE_EUTRAN_CALL_DROP>();
	
	public class InstantListener implements UpdateListener {
		public void update(final EventBean[] newEvents, final EventBean[] oldEvents) {
			if (newEvents != null) {
				logger.info("# of correlated events received : " + newEvents.length);
				int callDropEventCount = 0;
				for (final EventBean eventBean : newEvents) {
					if (eventBean.getUnderlying() instanceof LTE_EUTRAN_CALL_DROP) {
						final LTE_EUTRAN_CALL_DROP callClosureEvent = (LTE_EUTRAN_CALL_DROP) eventBean.getUnderlying();
						callDropList.add(callClosureEvent);
						callDropEventCount++;
					}
				}
				logger.info("# of CALL DROP events received : " + callDropEventCount + " [Total: " + callDropList.size() + "]");
			}
		}
	}
	
	@Before
	public void setUp() throws Exception {
		cepHandler = new EsperHandler(eplName);
		cepHandler.configureHandler(sessionListener, statementArray);
		callDropList.clear();
	}
	
	@Test
	public void shouldTransforUERelease2CallDrop(){
		INTERNAL_PROC_UE_CTXT_RELEASE release = new INTERNAL_PROC_UE_CTXT_RELEASE();
		cepHandler.sendEvent(release);
		assertEquals(1,callDropList.size());
	}

}
