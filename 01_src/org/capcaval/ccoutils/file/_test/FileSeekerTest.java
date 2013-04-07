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
package org.capcaval.ccoutils.file._test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import junit.framework.Assert;

import org.capcaval.ccoutils.file.FileSeekerResult;
import org.capcaval.ccoutils.file.FileTool;

public class FileSeekerTest {
	@org.junit.Test
	public void testFileSeeker() throws IOException {
		FileSeekerResult result = FileTool.seekFiles("*.java", Paths.get("01_src"));
		Assert.assertTrue(result.getFileList().length > 1);
		
		System.out.println(Arrays.toString(result.getFileList()));
	}

	@org.junit.Test
	public void testFileSeekerinJar() throws IOException {
		FileSeekerResult result = FileTool.seekFiles("*.class", Paths.get("02_lib"));
		Assert.assertTrue(result.getFileList().length > 1);
		
		System.out.println(Arrays.toString(result.getFileList()));
	}
	
	@org.junit.Test
	public void testFileInnerSeekerinJar() throws IOException {
		FileSeekerResult result = FileTool.seekFiles("*$InnerTest.class", Paths.get("10_bin"));
		Assert.assertTrue(result.getFileList().length == 1);
		
		System.out.println(Arrays.toString(result.getFileList()));
	}

}
