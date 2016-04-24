package ccoutils.data;

import java.awt.Color;

import org.capcaval.ccoutils.commandline.Command;
import org.capcaval.ccoutils.common.TextFileDisplayFrame;
import org.capcaval.ccoutils.converter.ConverterAbstract;
import org.capcaval.ccoutils.data.Data;
import org.capcaval.ccoutils.data.DataEvent;
import org.capcaval.ccoutils.lang.SystemTools;

import ccoutils.SampleCommons;

public class DataSample {

	@Command(desc = "show how to use a data wrapper. This provides observer/observable pattern and data transformation feature.")
	public void data(){
		System.out.println( SampleCommons.SAMPLE_SOURCE_CODE_MESSAGE 
				+ SystemTools.getCurrentFullMethodName());
		
		TextFileDisplayFrame.
			factory.
			newTextFileDisplayFrame( this.getClass()).
			display(800, 0, 600, 800);
		
		Data<Long> data = Data.factory.newData(-1L);
		
		// observer pattern
		System.out.println("At subscription the current value is notified.");
		data.subscribeToData(new DataEvent<Long>() {
			
			@Override
			public void notifyDataUpdated(Long data) {
				System.out.println("Data has been updated. The new value is : " + data);
			}
		});
		
		System.out.println();
		
		// modify with the setter
		System.out.println("modify the long with the type safe method \"setValue\".");
		data.setValue(114L);
		
		System.out.println();
		
		// modify with the feeder by default data can transform String->Long as all others
		// basic types
		System.out.println("modify the long by a string value.");
		data.feed("115");

		System.out.println();
		
		data.addDataConverter(new ConverterAbstract<Color, Long>() {
			@Override
			public Long convert(Color color) {
				return new Long(color.getRGB());
			}
		});
		// modify with a custom type and use the feeder 
		System.out.println("Use a custom converter. Modify the long by a Color value.");
		data.feed(Color.CYAN);
		
		SystemTools.readConsoleInput("\nPlease, press return key to quit.");
		System.exit(0);
		
		return;
	}
}
