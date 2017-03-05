package org.capcaval.lafabrique.compiler._impl;

import org.capcaval.lafabrique.lang.StringTools;

public class CompileResult {
	
	public enum CompilationResultEnum{noErrorNoWarning, warningNoError, error};
	
	protected int errorNumber = 0;
	protected int warningNumber = 0;
	
	protected String compilationLog;
	protected CompilationResultEnum compilationResultEnum = CompilationResultEnum.noErrorNoWarning;

	public CompileResult(String compilationLog) {
		// keep the compilation log
		this.compilationLog = compilationLog;
		
		// get the last line
		String resultStr = StringTools.getLastLine(compilationLog);
		
		// check out for errors
		String[] strArray = resultStr.split(" ");

		// check if no message => exit
		if(strArray.length<=1){
			return;
		}
		
		if(strArray[1].equals("warning")){
			this.warningNumber = Integer.parseInt(strArray[0]);
			
			if(this.warningNumber>0){
				compilationResultEnum = CompilationResultEnum.warningNoError;
			}
		}else if(strArray[1].contains("error")){
			this.errorNumber = Integer.parseInt(strArray[0]);
			
			if(this.errorNumber>0){
				compilationResultEnum = CompilationResultEnum.error;
			}
		}
	}
	
	public int getErrorNumber() {
		return errorNumber;
	}

	public int getWarningNumber() {
		return warningNumber;
	}

	public String getCompilationLog() {
		return compilationLog;
	}

	public CompilationResultEnum getCompilationResultEnum() {
		return compilationResultEnum;
	}
}
