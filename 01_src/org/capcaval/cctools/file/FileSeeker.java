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
package org.capcaval.cctools.file;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileSeeker extends SimpleFileVisitor<Path> {
	private PathMatcher matcher;
	int numMatches;
	public List<Path> fileList = new ArrayList<Path>();
	
	
	
	
	public FileSeeker(String pattern) throws IOException{
		this.matcher = FileSystems.getDefault()
                    .getPathMatcher("glob:" + pattern);
		
	}
	
	@Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        find(file);
        return FileVisitResult.CONTINUE;
    }
	
	void find(Path file) {
        Path name = file.getFileName();
        if (name != null && matcher.matches(name)) {
            this.fileList.add(file);
			numMatches++;
            }
    }
	
	public class FileSeekerResult{
		private Path[] fileList = null;
		
		public Path[] getFileList(){
			return this.fileList;
		}
		
		public FileSeekerResult(Path[] fileList){
			this.fileList = fileList;
		}
	}

	public FileSeekerResult seek(Path startingDir) throws IOException {
		Files.walkFileTree(startingDir, this);
		
		FileSeekerResult result = new FileSeekerResult(this.fileList.toArray(new Path[0]));
		
		return result;
	}

}
