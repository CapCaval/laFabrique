package org.capcaval.lafabrique.lang;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionTools {

	public static String stackTraceToString(Throwable throwable) {
		// create a writer
		StringWriter stringWriter = new StringWriter();
		// set it to write in it
		PrintWriter printWriter = new PrintWriter(stringWriter, true);
		// write the exception
		throwable.printStackTrace(printWriter);
		// get back the String
		return stringWriter.getBuffer().toString();
	}
}
