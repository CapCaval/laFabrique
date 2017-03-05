package org.capcaval.lafabrique.data._test;

import org.capcaval.lafabrique.converter.ConverterAbstract;
import org.capcaval.lafabrique.data.Data;
import org.capcaval.lafabrique.data.DataEvent;
import org.capcaval.lafabrique.data.DataReadOnly;
import org.capcaval.lafabrique.data._impl.DataImpl;

public class DataTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Data<Integer> d = new DataImpl<Integer>();
		DataReadOnly<Integer> dro = d;
		
		d.subscribeToData(new DataEvent<Integer>() {
			@Override
			public void notifyDataUpdated(Integer data) {
				System.out.println(data);
			}
		});
		d.setValue(5);
		
		d.addDataConverter(new ConverterAbstract<String, Integer>(){
			@Override
			public Integer convert(String inobj) {
				return Integer.valueOf(inobj);
			}
		});
		d.feed("12");
		//d.feed(20.0);
	}
		
	
}
