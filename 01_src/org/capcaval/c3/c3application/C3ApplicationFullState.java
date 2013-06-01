/*
Copyright (C) 2012 by CapCaval.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
package org.capcaval.c3.c3application;

import org.capcaval.c3.c3application.objectfactory.ObjectFactoryFromStringValue;
import org.capcaval.c3.component.Component;
import org.capcaval.c3.componentmanager.ComponentManager;
import org.capcaval.c3.componentmanager._impl.ComponentManagerImpl;

public abstract class C3ApplicationFullState implements ApplicationState{
	
	private C3ApplicationTool applicationTool;

	public String launchApplication(String[] args) {
		Class<? extends Component>[] noComponentList = new Class[0];
		return launchApplication(args, noComponentList);
	}
	
	public String launchApplication(String[] args, Class<? extends Component>... componentTypeList) {
		//clean up component manager
		ComponentManager.componentManager.reset();
		
		// create the application tool
		this.applicationTool = new C3ApplicationTool(this, args);
		
		// create applicationDescription 
		ApplicationDescription appDesc = this.applicationTool.newApplicationDescription(this);
		
		// retrieve all the parameters and set them
		this.applicationTool = new C3ApplicationTool(this, args);
		appDesc = this.applicationTool.seekAndSetProperties(this, appDesc);
		String appDescription = "";
		
		if( this.isHelpRequested(args) == true){
			this.applicationTool.displayHelpToSysOut(appDesc);
		}else if(this.isGHelpRequested(args) == true){
			this.applicationTool.displayGHelpToSysOut(appDesc);
		}else{
			appDescription = this.applicationTool.launchApplication(this, appDesc, componentTypeList);
		}
		// get all the used services and consumed events
		ApplicationDescription ad = this.applicationTool.seekAllUsedServicesAndConsummedEvents(this);
		// set them all
		this.applicationTool.setAllUsedServciesAndConsumedEvents(ad, this, ComponentManager.componentManager);
		
		return appDescription;
	}
	
	protected boolean isHelpRequested(String[] args) {
		boolean isHelpRequested = false;
		
		// check out if help is requested
		if ((args != null) && (args.length > 0)) {
			String param = args[0];
			if (	param.equals("-help") || 
					param.equals("--help")||
					param.equals("-h") ||
					param.equals("--h")) {
						// ok help is asked
						isHelpRequested = true;
					}
				}
		return isHelpRequested;
	}

	protected boolean isGHelpRequested(String[] args) {
		boolean isGHelpRequested = false;
		
		// check out if graphical help is requested
		if ((args != null) && (args.length > 0)) {
			String param = args[0];
			if (	param.equals("-ghelp") || 
					param.equals("--ghelp")||
					param.equals("-gh") ||
					param.equals("--gh")) {
						// ok help is asked
				isGHelpRequested = true;
					}
				}
		return isGHelpRequested;
	}

	
	public void addNewObjectFactory(Class<?> objectFactoryType, ObjectFactoryFromStringValue<?> factory ){
		// add a new factory
		this.applicationTool.addNewObjectFactory( objectFactoryType, factory);
	}
	

}
