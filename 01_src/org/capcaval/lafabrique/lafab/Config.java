package org.capcaval.lafabrique.lafab;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.capcaval.lafabrique.lang.ArrayTools;
import org.capcaval.lafabrique.project.Project;
import org.capcaval.lafabrique.task.TaskManager;

public abstract class Config extends TaskManager<Project>{
	public Path projectDir = Paths.get("00_prj");
	public Path[] sourceDirList = new Path[]{Paths.get("01_src")};
	public List<Path> libDirList = ArrayTools.newArrayList(Paths.get("02_lib"));
	public Path binDirPath= Paths.get("10_bin");
	public Path binIdeDirPath= Paths.get("11_idebin");
	public Path productionDirPath = Paths.get("20_prod");

	public void prjDir(String projectDir) {
		this.projectDir = Paths.get(projectDir);
	}
	
	protected void srcDir(String...stringPathList){
		List<Path> pathList = new ArrayList<>();
		for(String str : stringPathList){
			pathList.add(Paths.get(str));
		}
		
		this.sourceDirList = pathList.toArray(new Path[0]);
	}

	public void libDir(String... libDirStrList) {
		for(String dirStr : libDirStrList){
			this.libDirList.add(Paths.get(dirStr));
		}
	}
	
	public void binDir(String pathStr){
		this.binDirPath = Paths.get(pathStr);
	}
	
	public void binIdeDir(String pathStr){
		this.binIdeDirPath = Paths.get(pathStr);
	}

	public void prodDir(String pathStr) {
		this.productionDirPath = Paths.get(pathStr);
	}
	
	public abstract void defineProject();
}
