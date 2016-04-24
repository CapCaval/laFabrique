package system;

import java.nio.file.Path;


public enum JDKEnum{
	jdk1_8_0_25(java.nio.file.Paths.get("/home/mik/prg/java/jdk1.8.0_25")),
	jdk1_7_0_02(java.nio.file.Paths.get("/home/mik/prg/java/jdk1.7.0_02")),
	jre1_7_0_51(java.nio.file.Paths.get("/home/mik/prg/java/jre1.7.0_51")),
	jdk1_7_0_51(java.nio.file.Paths.get("/home/mik/prg/java/jdk1.7.0_51")),
	jdk1_7_0_09(java.nio.file.Paths.get("/home/mik/prg/java/jdk1.7.0_09")),
	jre1_7_0_40(java.nio.file.Paths.get("/home/mik/prg/java/jre1.7.0_40")),
	jdk1_8_0(java.nio.file.Paths.get("/home/mik/prg/java/jdk1.8.0")),
	jdk1_7_0(java.nio.file.Paths.get("/home/mik/prg/java/jdk1.7.0"));
	
	protected Path path0;

	private JDKEnum( Path path0){

		this.path0 = path0;
	}

	public Path getPath(){
		return this.path0;
	}
}
