package org.capcaval.lafabrique.converter.basicconverters.string;

import org.capcaval.lafabrique.converter.ConverterAbstract;
import org.capcaval.lafabrique.lang.ClassTools;

public class StringToEnum extends ConverterAbstract<String, Enum> {

	@Override
	public Enum convert(String inobj) {
		// get the enum type name. Note that . is a special character
		String[] strList = inobj.split("\\.");
		//strList[0];
		//from the classpath get the enum
		Class<?> c = ClassTools.getClassFromShortName(strList[0]);
		Enum<?> e = (Enum<?>)Enum.valueOf((Class<Enum>)c, strList[1]);
		
		return e;
	}

}
