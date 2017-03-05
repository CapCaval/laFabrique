package org.capcaval.lafabrique.file;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.capcaval.lafabrique.file.SourceInfo.SourceTypeEnum;
import org.capcaval.lafabrique.lang.StringTools;

public class SourceTool {

	public static String getHeaderComment(String fileStr) {

		return getHeaderComment(Paths.get(fileStr));
	}

	enum ParsingStateEnum { insideText, insideCommentC, insideCommentCPlusPlus}
	
	public static String getHeaderComment(Path path) {

		// get the string
		String str = FileTools.readStringfromFile(path);
		
		return getNextComment(str);
	}

	public static String getNextComment(String str) {
		
		ParsingStateEnum state = ParsingStateEnum.insideText;
		
		StringBuilder strb = new StringBuilder();
		
		for(int i=0; i<str.length(); i++){
			char c = str.charAt(i);
			
			ParsingStateEnum newState = getStatus(state, str, i);
			
			// checkout if the state has changed
			if(state!=newState){
				if(newState == ParsingStateEnum.insideText){
					strb.append(str.charAt(i));
					// copy other 2 extra for c comment
					if(state == ParsingStateEnum.insideCommentC){
						
						strb.append(str.charAt(i+1));}
					
					// comment is over, bye bye!
					return strb.toString();
				}
				// new state becomes current state
				state = newState;
			}
			
			if((newState == ParsingStateEnum.insideCommentCPlusPlus)||(newState ==ParsingStateEnum.insideCommentC)){
				strb.append(c);
			}
		}
		
		return strb.toString();
	}
	
	private static ParsingStateEnum getStatus(ParsingStateEnum state, String str, int i) {
		// check out that it is not the last
		if( i< str.length()-1){
			
			if(state == ParsingStateEnum.insideText){
				state = getStatusInsideText( str, i);
			}else if(state == ParsingStateEnum.insideCommentC){
				state = getStatusInsideCommentC( str, i);
			}else if(state == ParsingStateEnum.insideCommentCPlusPlus){
				state = getStatusInsideCommentCplusPlus( str, i);
			}
		}
		
		return state;
	}

	private static ParsingStateEnum getStatusInsideCommentCplusPlus(String str, int i) {
		ParsingStateEnum state = ParsingStateEnum.insideCommentCPlusPlus;

		// if \n// we still inside comment \n with something else where are not anymore 
		if(str.charAt(i)=='\n'){
			if((str.length()>i+2)&&(str.charAt(i+1)=='/')&&(str.charAt(i+2)=='/')){
				state = ParsingStateEnum.insideCommentCPlusPlus;
			}else{
				state = ParsingStateEnum.insideText;	
			}
		}
		// else stay inside c++ comment
		
		return state;
	}

	private static ParsingStateEnum getStatusInsideCommentC(String str, int i) {
		ParsingStateEnum state = ParsingStateEnum.insideCommentC;

		if((str.charAt(i)=='*')&&(str.charAt(i+1)=='/')){
			state = ParsingStateEnum.insideText;
		}
		// else stay inside c comment
		
		return state;
	}

	private static ParsingStateEnum getStatusInsideText(String str, int i) {
		ParsingStateEnum state = ParsingStateEnum.insideText;
		if((str.charAt(i)=='/')&&(str.charAt(i+1)=='/')){
			state = ParsingStateEnum.insideCommentCPlusPlus;
		}else if((str.charAt(i)=='/')&&(str.charAt(i+1)=='*')){
			state = ParsingStateEnum.insideCommentC;	
		}
		// else stay inside text
		
		return state;
	}

	public static String removeAllComment(String str){
		String comment = null;
		boolean isOver = false;
		do{
			comment = getNextComment(str);
			if(comment.equals("")){
				// no comment found bye bye!
				isOver=true;
			}else{
				str = StringTools.replaceString(str, comment, "");}
		}while(isOver==false);
		
		return str;
	}
	
	public static String[] getImportListFromString(String source) {
		// first remove all comments
		source = removeAllComment(source);
		
		// get file line per line
		String[] lineArray = StringTools.getLineArray(source);
		
		List<String> resultList = new ArrayList<>();
		
		for(String line : lineArray){
			line = StringTools.removeFirstCharactersOnString(line, '\t', ' ');
			
			// line shall start with import
			if(line.startsWith("import")){
				line = StringTools.removeStart("import", line);
				line = StringTools.removeChar(line, ' ', ';');
				resultList.add(StringTools.removeStart("import", line));
			}else if((line.length()>0)&&(line.startsWith("package")==false)){
				// go out the import part is over
				break;
			}
		}
		
		return resultList.toArray(new String[0]);
	}

	public static String getPackageName(String source) {
		// first remove all comments
		source = removeAllComment(source);
		
		// get file line per line
		String[] lineArray = StringTools.getLineArray(source);

		String result=null;
		
		for(String line : lineArray){
			result = StringTools.removeFirstCharactersOnString(line, '\t', ' ');
			
			if(result.startsWith("package")){
				result = StringTools.removeStart("package", result);
				result = StringTools.removeChar(result, ' ', ';');
				// it is found bye bye!
				break;
			}
		}
		
		return result;
	}

	public static String[] getAllParentPackage(String pkgName) {
		String[] array = pkgName.split("[.]"); // with regex . means any char. Use of bracket is mandatory 
		
		String[] resultArray = new String[array.length];
		
		for(int i=0; i< array.length; i++){
			String parentStr = "";
			
			// test if a parent exits
			if(i>0){
				// if so concat parent and current names. Parent is the previous.
				parentStr = resultArray[i-1];
			}

			String separator = ".";
			if(parentStr == ""){
				separator = "";
			}
			resultArray[i] = parentStr + separator + array[i];
		}
		
		return resultArray;
	}

	public static SourceInfo getSourceInfo(String source) {
		// first remove all comments
		source = removeAllComment(source);
		
		// get file line per line
		String[] lineArray = StringTools.getLineArray(source);

		String packageName = getPackageName(source);
		
		SourceInfo result = null;
		
		for(String line : lineArray){
			line = StringTools.removeFirstCharactersOnString(line, '\t', ' ');
			
			// replace tab by space
			line= line.replace('\t', ' ');
			
			// remove all space sequence
			line = StringTools.removeCharSequence(' ', line);
			
			// split with space
			String[] array = line.split(" ");
			
//			line = line.replaceAll("{", "");
//			line = line.replaceAll("\n", "");
			
			int i=0;
			for(; i< array.length; i++){
				if(array[i].equals("enum")){
					result = new SourceInfo(array[i+1], packageName, SourceTypeEnum.enumType);
					break;
				}else if(array[i].equals("class")){
					result = new SourceInfo(array[i+1], packageName, SourceTypeEnum.classType);
					break;
				}else if(array[i].equals("interface")){
					result = new SourceInfo(array[i+1], packageName, SourceTypeEnum.interfaceType);
					break;
				}else if(array[i].equals("@interface")){
					result = new SourceInfo(array[i+1], packageName, SourceTypeEnum.annotationType);
					break;
				}
			}
		}
		
		return result;
	}
}
