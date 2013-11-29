package org.capcaval.ccoutils.lafabrique;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.capcaval.ccoutils.lang.ArrayTools;

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
	public Path outputBinPath;
	public Path productionDirPath = Paths.get("20_prod");
	public Path rootProjectDirPath = Paths.get("00_prj");
	public String[] libList = new String[0];
	public String jdkVersion;
	public String name;
	public Path projectDir = Paths.get("00_prj");
	public String[] packageNameList = new String[]{""};
	public String url;
	
	public List<Path> libDirList = ArrayTools.newArrayList(Paths.get("02_lib"));

	
	
	public AbstractProject(){
		// retrieve the project settings
		this.defineProject();

		// if no specific outputBinPath defined get the one
		if(this.outputBinPath == null){
			this.outputBinPath = Paths.get(this.productionDirPath.toString() + "/bin"); 
		}
		// if no specific jar bin output defined get the one 
		if(this.jar.outputBinPath == null){
			// by default link the jar output same as the project
			this.jar.outputBinPath = this.outputBinPath;
			}
		if(this.jar.outputJar == null){
			// by default link the jar output same as the project
			this.jar.outputJar = this.productionDirPath;
			}

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
		this.outputBinPath = Paths.get(pathStr);
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
	public void lib(String... libList) {
		this.libList = libList;
	}
	public void prodDirPath(String pathStr) {
		this.productionDirPath = Paths.get(pathStr);
	}
	protected void jdkVersion(String version) {
		this.jdkVersion = version;
	}
	protected void name(String name) {
		this.name = name;
	}
	protected void projectDir(String projectDir) {
		this.projectDir = Paths.get(projectDir);
	}
	
	public void libDir(String... libDirStrList) {
		for(String dirStr : libDirStrList){
			this.libDirList.add(Paths.get(dirStr));
		}
		
	}
	
	public void packageName(String... packageNameList) {
		this.packageNameList = packageNameList;
	}
	
	public void url(String url) {
		this.url = url;
	}


}