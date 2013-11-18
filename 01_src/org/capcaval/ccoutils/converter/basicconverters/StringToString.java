package org.capcaval.ccoutils.converter.basicconverters;

import org.capcaval.ccoutils.converter.ConverterAbstract;

public class StringToString extends ConverterAbstract<String, String> {
	@Override
	public String convert(String inobj) {
		if(inobj==null){
			inobj="";
		}
		
		return inobj;
	}

}
