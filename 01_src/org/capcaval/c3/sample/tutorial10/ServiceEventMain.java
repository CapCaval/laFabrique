package org.capcaval.c3.sample.tutorial10;

import org.capcaval.c3.componentmanager.ComponentManager;
import org.capcaval.c3.sample.tutorial10.comsummer.ConsummerService;
import org.capcaval.c3.sample.tutorial10.comsummer._impl.ConsummerImpl;
import org.capcaval.c3.sample.tutorial10.producer._impl.ProducerImpl;


public class ServiceEventMain {

	public static void main(String[] args) {
		String appDescription = ComponentManager.componentManager.startApplication(
				ProducerImpl.class,
				ConsummerImpl.class);
		
		ConsummerService cs = ComponentManager.componentManager.getComponentService(ConsummerService.class);
		//cs.start();
		
		System.out.println(appDescription);
		
	}

}
