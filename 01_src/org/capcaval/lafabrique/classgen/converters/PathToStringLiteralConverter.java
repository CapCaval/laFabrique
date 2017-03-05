package org.capcaval.lafabrique.classgen.converters;

import java.nio.file.Path;

import org.capcaval.lafabrique.converter.ConverterAbstract;

public class PathToStringLiteralConverter extends ConverterAbstract<Path, String> {

	@Override
	public String convert(Path inobj) {
		return "java.nio.file.Paths.get(\"" + inobj.toString() + "\")";
	}
}
