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
package org.capcaval.lafabrique.application;

public class ApplicationDetails {

	public static class HelpDisplayDetails{
		protected String asciiLogoGradient;
		protected int helpDisplayidthInChar;

		public HelpDisplayDetails(String asciiLogoGradient, int asciiLogoWidthInChar) {
			this.asciiLogoGradient = asciiLogoGradient;
			this.helpDisplayidthInChar = asciiLogoWidthInChar;
		}
	}
	
	protected String applicationName = "";
	protected String applicationVersion = "";
	protected String applicationInformation = "";
	protected String applicationPropertyFile = "";
	protected String author;
	protected String[] aboutArray;
	protected HelpDisplayDetails helpDisplayDetails = null;
	
	public ApplicationDetails(String applicationName, String applicationVersion, String applicationInformation,
			String applicationPropertyFile, String author, String[] aboutArray,HelpDisplayDetails asciiLogoDetails) {
		
		this.applicationName = applicationName;
		this.applicationVersion = applicationVersion;
		this.applicationInformation = applicationInformation;
		this.applicationPropertyFile = applicationPropertyFile;
		this.helpDisplayDetails = asciiLogoDetails;
		this.author =author;
		this.aboutArray = aboutArray;
	}
	
	public String getApplicationName() {
		return applicationName;
	}

	public String getApplicationVersion() {
		return applicationVersion;
	}

	public String getApplicationInformation() {
		return applicationInformation;
	}

	public String getApplicationPropertyFile() {
		return applicationPropertyFile;
	}
	public HelpDisplayDetails getAsciiLogoDetails() {
		return this.helpDisplayDetails;
	}
	public String getAuthor() {
		return author;
	}

	public String[] getAboutArray() {
		return aboutArray;
	}

}
