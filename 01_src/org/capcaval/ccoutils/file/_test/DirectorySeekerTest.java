package org.capcaval.ccoutils.file._test;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.capcaval.ccoutils.file.DirectorySeeker;
import org.capcaval.ccoutils.file.FileSeekerResult;
import org.junit.Assert;
import org.junit.Test;

public class DirectorySeekerTest {

	@Test
	public void DirectorySeekerTest() {
		FileSeekerResult result = DirectorySeeker.seekDirectory(".", "2_sr");
		Assert.assertArrayEquals(result.getPathList(), new Path[]{Paths.get("02_src")});
		
	}

}
