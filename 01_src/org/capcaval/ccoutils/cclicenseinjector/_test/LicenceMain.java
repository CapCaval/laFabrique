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
package org.capcaval.ccoutils.cclicenseinjector._test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.capcaval.ccoutils.file.FileSeekerResult;
import org.capcaval.ccoutils.file.FileTools;
import org.capcaval.ccoutils.lang.ArrayTools;

public class LicenceMain {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//create from the template
		Path templateFile = Paths.get("01_src/org/capcaval/app/cclicenseinjector/templates/MIT.txt");
		Path outputfile = Paths.get("licence.txt");
		
		Map<String, String> map = ArrayTools.newMap(
				"YEAR","2012",
				"COPYRIGHT_HOLDERS","CapCaval.org");
		FileTools.replaceTokenInFile(templateFile, outputfile, map, '<', '>');
		String licence = FileTools.readStringfromFile(outputfile) + "\n";
		
		FileSeekerResult result = FileTools.seekFiles("*.java", Paths.get("01_src"));
		
//		for(Path path : result.getFileList()){
//			FileTool.insertStringInFile(path, licence, FilePosition.head);
//		}

	}

}
