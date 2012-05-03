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
package org.capcaval.app.cclicenseinjector;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Calendar;

import org.capcaval.c3.application.Application;
import org.capcaval.c3.application.annotations.AppInformation;
import org.capcaval.c3.application.annotations.AppProperty;

enum LicenseType {
	MIT, BSD
}

@AppInformation(
		version = "0.0.1", 
		about = "This application is made for inserting license inside source file.")
public class CCLicenseInjector extends Application {

	@AppProperty(comment = "Type of the license")
	LicenseType licenseType = LicenseType.MIT;

	@AppProperty(comment = "root path directory of the source to be injected.")
	Path rootDirectory = FileSystems.getDefault().getPath(
			"org.capcaval.app.cclicenseinjector.templates");

	@AppProperty(comment = "Year to be written inside the license.")
	String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

	@AppProperty(comment = "Copy right holders to be written inside the license.")
	String copyRightHolders = "Unknown";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty(
				"org.capcaval.app.cclicenseinjector.CCLicenseInjector.copyRightHolders",
				"capcaval.org");
		System.setProperty("copyRightHolders", "capcaval.org_bis");

		new CCLicenseInjector().launchApplication(args);
	}

	@Override
	public void notifyApplicationToBeRun(String propertiesDescrition, String componentsDescription) {
		System.out.println("***** Application run");
		System.out.println(this.copyRightHolders);
		System.out.println(this.year);
		System.out.println(this.rootDirectory);
		System.out.println(this.licenseType);
	}

	@Override
	public void notifyApplicationToBeClosed() {
		System.out.println("***** Application Closed");
	}

}
