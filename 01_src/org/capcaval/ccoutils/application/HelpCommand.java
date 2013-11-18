package org.capcaval.ccoutils.application;

import org.capcaval.ccoutils.commandline.Command;
import org.capcaval.ccoutils.commandline.CommandParamDesc;
import org.capcaval.ccoutils.commandline.CommandWrapper;
import org.capcaval.ccoutils.formattedtext.Chap;
import org.capcaval.ccoutils.formattedtext.FormatText;
import org.capcaval.ccoutils.jbitascii.BitmapAsciiTools;
import org.capcaval.ccoutils.lang.StringTools;

public class HelpCommand {
	protected ApplicationDescription description;
	
	public HelpCommand(ApplicationDescription description){
		this.description = description;
	}
	
	@Command (defaultRun = true, desc = "Displays information about this application.It provides all application properties and commands.")
	public String help(){
		String logo = null;
		if(this.description.appDetails.helpDisplayDetails != null){
			logo = BitmapAsciiTools.convertStringToAscii(
					this.description.appDetails.applicationName,
					this.description.appDetails.helpDisplayDetails.asciiLogoGradient,
					this.description.appDetails.helpDisplayDetails.helpDisplayidthInChar);
			}
		else{
			logo = this.description.appDetails.applicationName;
		}
		
		int displayWidth = this.description.appDetails.helpDisplayDetails.helpDisplayidthInChar;
		
		// make a delimiter
		String delimiter = StringTools.repeatString("-", displayWidth);
		
		String author = "";
		if(this.description.appDetails.author != null){
			author = "by " + this.description.appDetails.author;
		}
		
		FormatText ft = new FormatText();
		ft.setColumnWidthInChar(80);
		String str = ft.format(
				delimiter,
				logo,
				delimiter,
				Chap.level1 + "Version : " + this.description.appDetails.applicationVersion + "      " + author,
				Chap.level1 + "About:",
				Chap.level1 + StringTools.multiLineString(this.description.appDetails.aboutArray),
				Chap.none + delimiter,
				
				" ");
		
		// TODO formattedString
		
		
/*		StringBuffer buf = new StringBuffer();
		buf.append( delimiter + "\n");
		buf.append( logo + "\n");
	
		// ------------------------------------------------------------------------------------------
		buf.append("\t Version : " + this.description.appDetails.applicationVersion + "      " + author + "\n");
		buf.append( delimiter + "\n");
		// ------------------------------------------------------------------------------------------
		buf.append("About :\n");
		buf.append(StringTools.formatMultiLineToCharWidth(this.description.appDetails.aboutArray, displayWidth));
		buf.append( delimiter + "\n");
		// ------------------------------------------------------------------------------------------
		buf.append("\t Properties : \n");
		
		for( AppPropertyInfo info : this.description.applicationProperties.getAppPropertyInfoList()){
			buf.append("\t\t " + info.type.getSimpleName() + " " + info.propertyName + " : " + info.comment + "\n");
		}
		
		buf.append("\t Command : \n");
		for( CommandWrapper command : this.description.commandLineComputer.getCommandWrapperList()){
			buf.append("\t\t " + command.commandStr + " : " + command.descriptionStr + " \n");
			for(CommandParamDesc paramDesc : command.paramList){
				buf.append("\t\t\t " + paramDesc.type.getSimpleName() + " " + paramDesc.name + " : " + paramDesc.description + " \n");
			}
		}
		
		buf.append("--------------------------------------------------------------------------------\n");
	*/	
		return str;
	}
	
	
	@Command (defaultRun = true, desc = "Displays information about this application.It provides all application properties and commands.")
	public String help2(){
		String logo = null;
		if(this.description.appDetails.helpDisplayDetails != null){
			logo = BitmapAsciiTools.convertStringToAscii(
					this.description.appDetails.applicationName,
					this.description.appDetails.helpDisplayDetails.asciiLogoGradient,
					this.description.appDetails.helpDisplayDetails.helpDisplayidthInChar);
			}
		else{
			logo = this.description.appDetails.applicationName;
		}
		
		int displayWidth = this.description.appDetails.helpDisplayDetails.helpDisplayidthInChar;
		
		// make a delimiter
		String delimiter = StringTools.repeatString("-", displayWidth);
		
		StringBuffer buf = new StringBuffer();
		buf.append( delimiter + "\n");
		buf.append( logo + "\n");
		String author = "";
		if(this.description.appDetails.author != null){
			author = "by " + this.description.appDetails.author;
		}
		// ------------------------------------------------------------------------------------------
		buf.append("\t Version : " + this.description.appDetails.applicationVersion + "      " + author + "\n");
		buf.append( delimiter + "\n");
		// ------------------------------------------------------------------------------------------
		buf.append("About :\n");
		buf.append(StringTools.formatMultiLineToCharWidth(this.description.appDetails.aboutArray, displayWidth));
		buf.append( delimiter + "\n");
		// ------------------------------------------------------------------------------------------
		buf.append("\t Properties : \n");
		
		for( AppPropertyInfo info : this.description.applicationProperties.getAppPropertyInfoList()){
			buf.append("\t\t " + info.type.getSimpleName() + " " + info.propertyName + " : " + info.comment + "\n");
		}
		
		buf.append("\t Command : \n");
		for( CommandWrapper command : this.description.commandLineComputer.getCommandWrapperList()){
			buf.append("\t\t " + command.commandStr + " : " + command.descriptionStr + " \n");
			for(CommandParamDesc paramDesc : command.paramList){
				buf.append("\t\t\t " + paramDesc.type.getSimpleName() + " " + paramDesc.name + " : " + paramDesc.description + " \n");
			}
		}
		
		buf.append("--------------------------------------------------------------------------------\n");
		
		return buf.toString();
	}

}
