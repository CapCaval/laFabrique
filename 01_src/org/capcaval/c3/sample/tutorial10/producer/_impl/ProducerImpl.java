package org.capcaval.c3.sample.tutorial10.producer._impl;

import org.capcaval.c3.component.annotation.OwnThread;
import org.capcaval.c3.component.annotation.ProducedEvent;
import org.capcaval.c3.component.annotation.Task;
import org.capcaval.c3.sample.tutorial10.producer.Producer;
import org.capcaval.c3.sample.tutorial10.producer.ProducerEvent;

@OwnThread
public class ProducerImpl implements Producer{
	@ProducedEvent
	ProducerEvent produceProducerEvent;
	
	@Task(each=1000)
	public void execute(){
		System.out.println("producer produces !");
	}
	
}
