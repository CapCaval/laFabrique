package org.capcaval.ccoutils.application;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.capcaval.ccoutils.application.annotations.AggregatedCommand;
import org.capcaval.ccoutils.commandline.CommandLineComputer;
import org.capcaval.ccoutils.commandline.CommandWrapper;
import org.capcaval.ccoutils.converter.ConverterManager;

public class ApplicationDescription {

	protected ApplicationEvent applicationEvent;
	protected ApplicationProperties applicationProperties;
	protected CommandWrapper defaultCommand;
	protected ApplicationDetails appDetails;
	protected CommandLineComputer commandLineComputer;


	public ApplicationDescription(Object appInstance, Class<?> applicationType, ConverterManager converterManager) {
		// retrieve all details from annotations
		this.appDetails = ApplicationSubTool.newApplicationDetails(appInstance, applicationType);
		
		
		// retrieve all properties
		this.applicationProperties = ApplicationSubTool
				.newApplicationProperties(
						appInstance, 
						applicationType, 
						this.appDetails.getApplicationPropertyFile(), 
						converterManager);
		
		// Get aggregated command if any
		Field[] fieldArray = applicationType.getDeclaredFields();
		List<Object> aggregatedCommandList = new ArrayList<>();
		// retrieve field with AggregatedCommand annotation
		for(Field field : fieldArray){
			field.setAccessible(true);
			if(field.getAnnotation(AggregatedCommand.class)!=null){
				try{
					// add it to the application command
					aggregatedCommandList.add(field.get(appInstance));}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		
		// Get all the available command
		this.commandLineComputer = new CommandLineComputer(this.appDetails.applicationName, "");
		this.commandLineComputer.addCommandInstance(appInstance);
		this.commandLineComputer.addCommandInstance(aggregatedCommandList);
		this.commandLineComputer.addCommandInstance(new HelpCommand(this));

		// Checkout for any default command
		this.defaultCommand = ApplicationSubTool.getDefaultCommand(this.commandLineComputer);
		
		// retrieve all events implemented by the application		
		if(ApplicationEvent.class.isAssignableFrom(applicationType)){
			this.applicationEvent = ApplicationEvent.class.cast(appInstance);
		}
		
		// this is it : )
	}
}
