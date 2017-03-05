package org.capcaval.lafabrique.converter.basicconverters.string;

import org.capcaval.lafabrique.converter.ConverterAbstract;

public class StringToString extends ConverterAbstract<String, String> {
	@Override
	public String convert(String inobj) {
		if(inobj==null){
			inobj="";
		}
		
		return inobj;
	}

}
