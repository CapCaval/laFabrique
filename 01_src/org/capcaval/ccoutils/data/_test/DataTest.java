package org.capcaval.ccoutils.data._test;

import org.capcaval.ccoutils.converter.ConverterAbstract;
import org.capcaval.ccoutils.data.Data;
import org.capcaval.ccoutils.data.DataEvent;
import org.capcaval.ccoutils.data.DataReadOnly;
import org.capcaval.ccoutils.data._impl.DataImpl;

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
				return Integer.getInteger(inobj);
			}
		});
		d.feed("12");
		d.feed(20.0);
	}
		
	
}
