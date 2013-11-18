package org.capcaval.ccoutils.formattedtext.formatter;

import org.capcaval.ccoutils.formattedtext.TextFormatter;
import org.capcaval.ccoutils.lang.StringTools;

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
		// format with the correct column width 
		String result = StringTools.formatToCharWidth(string,columnWidthInChar-this.level,"\n" + shift);
		
		// concat the shift and the string
		return shift+result;
	}
}
