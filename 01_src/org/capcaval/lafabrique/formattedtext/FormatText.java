package org.capcaval.lafabrique.formattedtext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.capcaval.lafabrique.formattedtext.formatter.DefaultTextFormatter;
import org.capcaval.lafabrique.formattedtext.formatter.Level1Formatter;
import org.capcaval.lafabrique.formattedtext.formatter.Level2Formatter;
import org.capcaval.lafabrique.formattedtext.formatter.Level3Formatter;

public class FormatText {

	
	protected  HashMap<String, TextFormatter> FormatterMap;
	protected TextFormatter defaultTextFormatter = new DefaultTextFormatter();
	private int columnWidthInChar = 80;

	public FormatText(){
		this.FormatterMap = new HashMap<>();
		
		this.FormatterMap.put(Chap.level1.toString(), new Level1Formatter());
		this.FormatterMap.put(Chap.level2.toString(), new Level2Formatter());
		this.FormatterMap.put(Chap.level3.toString(), new Level3Formatter());
	} 
	
		
	public String format(Object... list) {
		String command = null;

		StringBuilder returnedString = new StringBuilder();
		TextFormatter tf = this.defaultTextFormatter;
		
		// add line when contains carrier return
		List<String> strList = new ArrayList<>();
		for(Object object:list){
			String str = object.toString();
			// check if CR inside
			if(str.contains("\n")){
				// if so split string
				String[] splitStr = str.split("\n");
				for(String strToAdd : splitStr){
					strList.add(strToAdd);
				}
			}
			else{
				strList.add(str);
			}
		}
		
		// now compute all lines
		for(String text:strList){
			if(text.charAt(0) == Type.start){
				String[] strArray = text.split(String.valueOf(Type.stop));
				if(strArray.length>1){
					text = strArray[1];
				}else{
					text="";
				}

				// add the cut character
				command = strArray[0]+ Type.stop;
				// get it
				tf = this.getFormatter(command);
			}
			// format it
			text = tf.format(this.columnWidthInChar-1, text); // -1 for \n
			returnedString.append(text + "\n");
		}
		return returnedString.toString();
	}

	protected TextFormatter getFormatter(String command) {
		// get the formatter corresponding to the command
		TextFormatter ft = this.FormatterMap.get(command);
		// no formatter found
		if(ft == null){
			ft = this.defaultTextFormatter;
		}
		// return it
		return ft;
	}

	public void setColumnWidthInChar(int columnWidthInChar) {
		this.columnWidthInChar = columnWidthInChar;
	}
}
