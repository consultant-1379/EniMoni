package com.ericsson.assure.enimoni.correlation.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.assure.enimoni.handler.EsperHandler;
import com.ericsson.assure.enimoni.model.LTE_EUTRAN_CELL_LOG;
import com.ericsson.xstream.apeventbeans.celltrace.INTERNAL_PER_RADIO_CELL_MEASUREMENT;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class CellMeasurementTest {
	private EsperHandler cepHandler = null;
	private static String eplName = "LTE_EUTRAN_CELL_MEASUREMENT_GROUP.epl";
	private static String[] statementArray = { "CELL_LOG" };
	private final InstantListener sessionListener = new InstantListener();
	private static final Logger logger = LoggerFactory.getLogger(CallDropCorrelationTest.class);
	public static List<LTE_EUTRAN_CELL_LOG> cellLogList = new ArrayList<LTE_EUTRAN_CELL_LOG>();
	
	public class InstantListener implements UpdateListener {
		public void update(final EventBean[] newEvents, final EventBean[] oldEvents) {
			if (newEvents != null) {
				logger.info("# of correlated events received : " + newEvents.length);
				int cellLogEventCount = 0;
				for (final EventBean eventBean : newEvents) {
					if (eventBean.getUnderlying() instanceof LTE_EUTRAN_CELL_LOG) {
						final LTE_EUTRAN_CELL_LOG cellLogEvent = (LTE_EUTRAN_CELL_LOG) eventBean.getUnderlying();
						cellLogList.add(cellLogEvent);
						cellLogEventCount++;
					}
				}
				logger.info("# of CALL Log events received : " + cellLogEventCount + " [Total: " + cellLogList.size() + "]");
			}
		}
	}
	
	@Before
	public void setUp() throws Exception {
		cepHandler = new EsperHandler(eplName);
		cepHandler.configureHandler(sessionListener, statementArray);
		cellLogList.clear();
	}
	
	@Test
	public void shouldTransforUERelease2CallDrop(){
		INTERNAL_PER_RADIO_CELL_MEASUREMENT cell_measurement = new INTERNAL_PER_RADIO_CELL_MEASUREMENT();
		cell_measurement.setGLOBAL_CELL_ID(1234);
		cell_measurement.setNOISEINTERF_MEAS_0(2);
		cell_measurement.setNOISEINTERF_MEAS_3(3);
		cell_measurement.setNOISEINTERF_MEAS_7(4);
		cell_measurement.setNOISEINTERF_MEAS_PUCCH_4(5);
		cell_measurement.setNOISEINTERF_MEAS_PUCCH_14(6);
		cell_measurement.setNOISEINTERF_MEAS_PUCCH_15(7);
		cell_measurement.setTimestamp(System.currentTimeMillis());
		 
		cepHandler.sendEvent(cell_measurement);
		cepHandler.sendEvent(cell_measurement);
		cell_measurement.setTimestamp(System.currentTimeMillis() +
				(Integer.parseInt(cepHandler.getRuntime().getVariableValue("CELL_GROUPING_PERIOD_IN_MINUTES").toString())+1) * 60 * 1000); //add six minutes, groups are set to 5 minutes
		cepHandler.sendEvent(cell_measurement);
		LTE_EUTRAN_CELL_LOG cellLog = cellLogList.get(0);
		System.out.println("cell log event " + cellLog.getCSVString());
		assertEquals(1,cellLogList.size());
	}
}
