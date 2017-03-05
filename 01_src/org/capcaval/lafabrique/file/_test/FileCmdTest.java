package org.capcaval.lafabrique.file._test;

import org.capcaval.lafabrique.file.command.FileCmd;

public class FileCmdTest {
	
	@org.junit.Test
	public void testCopyFile(){
		FileCmd.makeDir.name("temp").execute();
		FileCmd.copy.from("*.txt").to("temp").execute();
	}

}
