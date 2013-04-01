package org.capcaval.ccproject;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mik
 *
 */
public abstract class AbstractProject implements Project{

	public Path[] sourceList;
	public List<Path> excludeSourceList;
	public Jar jar = new Jar();
	public Test test;
	public String version;
	public String[] libPathList;
	public String licence;
	public String[] authorList;
	public String copyright;
	public Path outputPathBin;
	
	public AbstractProject(){
		this.defineProject();
	}

	

	protected void source(String...stringPathList){
		List<Path> pathList = new ArrayList<>();
		for(String str : stringPathList){
			pathList.add(Paths.get(str));
		}
		
		this.sourceList = pathList.toArray(new Path[0]);
	}

	protected void author(String...authorList){
		this.authorList = authorList;
	}

	protected void copyright(String copyright){
		this.copyright = copyright;
	}

	public void outputBinPath(String pathStr){
		this.outputPathBin = Paths.get(pathStr);
	}
	
	protected void exclude(String...stringPathList){
		List<Path> pathList = new ArrayList<>();
		for(String str : stringPathList){
			pathList.add(Paths.get(str));
		}
		this.excludeSourceList = pathList;
	}

	protected void version(String version){
		this.version= version;
	}

	protected void librairiePath(String... libPathList){
		this.libPathList =libPathList;
	}

	
	protected void version(int... version){
		
	}

	protected void librairiesForCompiling(String...stringList){
	}

	protected void outputClass(String stringList){
	}
	protected void excludeDirectoryShortname(String stringList){
	}
	
	public void licence(String licence) {
		this.licence= licence;
	}


}