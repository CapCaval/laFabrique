package org.capcaval.lafabrique.formattedtext.formatter;

import org.capcaval.lafabrique.formattedtext.TextFormatter;
import org.capcaval.lafabrique.lang.StringTools;

public abstract class LevelFormatter implements TextFormatter{

	String tab = "   ";
	int level = 0;
	
	@Override
	public String format(int columnWidthInChar, String string) {
		String shift = "";
		// construct the tab
		for(int i=0; i<this.level; i++){
			shift = shift + tab;
		}
		// add the spaces
		String result = StringTools.formatToCharWidth(string,columnWidthInChar-shift.length(), shift);
		// format with the correct column width 
		result = StringTools.formatToCharWidth(shift + result,columnWidthInChar,"\n");
		
		// concat the shift and the string
		return result;
	}
}
