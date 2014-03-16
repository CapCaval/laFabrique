package ccoutils.data;

import org.capcaval.ccoutils.commandline.Command;
import org.capcaval.ccoutils.data._impl.DataImpl;
import org.capcaval.ccoutils.data.Data;
import org.capcaval.ccoutils.data.DataEvent;

public class DataSample {

	@Command(desc = "show how to use a data wrapper.")
	public void Data(){
		Data<Integer> data = Data.factory.newData(-1);
		
		// observer pattern
		data.subscribeToData(new DataEvent<Integer>() {
			
			@Override
			public void notifyDataUpdated(Integer data) {
				System.out.println("Data has been updated. The new value is : " + data);
			}
		});
		// modify with the setter
		data.setValue(114);
		// modify with the feeder
		data.feed("115");
	}
}
