package org.capcaval.lafabrique.project;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.capcaval.lafabrique.lafab.Config;
import org.capcaval.lafabrique.lafab.JUnit;
import org.capcaval.lafabrique.lafab.Pack;
import org.capcaval.lafabrique.lafab.command.CommandCompile;
import org.capcaval.lafabrique.lafab.command.CommandJar;
import org.capcaval.lafabrique.lafab.command.CommandPack;
import org.capcaval.lafabrique.lafab.command.CommandScript;
import org.capcaval.lafabrique.lang.JVM;

import system.JDKEnum;

/**
 * @author mik
 *
 */
public abstract class Project extends Config{

	public List<Path> excludeSourceList;
	public Compile compile = new Compile();
	public Jar jar = new Jar();
	public Test test = new Test();
	public Pack pack = new Pack();
	public Script script = new Script();
	public JUnit junit = new JUnit();
	
	public String version;
	public String licence;
	public String[] authorList;
	public String copyright;
	
	public String[] libList = new String[]{};
	public JDKEnum jdkVersion;
	public String name = this.getClass().getName();
	public String[] packageNameList = new String[]{""};
	public String url;

	
	
	public Project(){
		// define the default task execution
		//this.taskArray(this.compile, this.junit, this.jar, this.script, this.pack);
		this.taskArray(this.compile, this.jar, this.script, this.pack);
		
		// define the default tasks
		this.compile.task(new CommandCompile());
		this.jar.task(new CommandJar());
		this.pack.task(new CommandPack());
		this.script.task(new CommandScript());
		
		// retrieve the project settings
		this.defineProject();

		//TODO see to remove the below source 
		
		
		// if no specific outputBinPath defined get the one
		if(this.binDirPath == null){
			this.binDirPath = Paths.get(this.productionDirPath.toString() + "/bin"); 
		}
		// if no specific jar bin output defined get the one 
		if(this.jar.outputBinPath == null){
			// by default link the jar output same as the project
			this.jar.outputBinPath = this.binDirPath;
			}
		if(this.jar.outputJar == null){
			// by default link the jar output same as the project
			this.jar.outputJar = this.productionDirPath;
			}
	}

	protected void author(String...authorList){
		this.authorList = authorList;
	}

	protected void copyright(String copyright){
		this.copyright = copyright;
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

	protected void version(int... version){
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

	protected void jdkVersion(JDKEnum version) {
		this.jdkVersion = version;
	}
	protected void name(String name) {
		this.name = name;
	}

	
	public void packageName(String... packageNameList) {
		this.packageNameList = packageNameList;
	}
	
	public void url(String url) {
		this.url = url;
	}
}