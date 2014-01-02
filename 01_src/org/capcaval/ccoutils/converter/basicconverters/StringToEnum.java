package org.capcaval.ccoutils.converter.basicconverters;

import org.capcaval.ccoutils.converter.Converter;
import org.capcaval.ccoutils.converter.ConverterAbstract;
import org.capcaval.ccoutils.file.FileTools;
import org.capcaval.ccoutils.lang.ClassTools;

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
