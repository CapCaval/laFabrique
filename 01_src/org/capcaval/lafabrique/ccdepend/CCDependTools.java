package org.capcaval.lafabrique.ccdepend;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.capcaval.lafabrique.ccdepend.meta.Clazz;
import org.capcaval.lafabrique.ccdepend.meta.Item;
import org.capcaval.lafabrique.ccdepend.meta.Item.ItemType;
import org.capcaval.lafabrique.ccdepend.meta.Package;
import org.capcaval.lafabrique.file.FileSeekerResult;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.file.SourceInfo;
import org.capcaval.lafabrique.file.SourceTool;

public class CCDependTools {

	public static Item[] analyse(Path pathToAnalyse, String filter) {
		//
		Map<String, Item> map = new HashMap<>();
		
		// create the root
		Package root  = new Package( ".", null, ItemType.internal);
		
		// get all files
		FileSeekerResult result = FileTools.seekFiles(filter, pathToAnalyse);
		
		// analyse all of them
		for(Path path : result.getPathList()){
			// first get the source
			String source = FileTools.readStringfromFile(path);
			
			// create its own packages if needed
			Package[] packageArray = createPackageFromSource(source, map);
			
			SourceInfo info =SourceTool.getSourceInfo(source);

			if(info == null){
				System.out.println(source);
			}
			
			Package parent = null;
			
			// get the last element which is the class package
			if(packageArray.length > 0){
				parent = packageArray[packageArray.length-1];
			}
			
			Item item = new Clazz(info.getName(), parent, ItemType.internal);
			// add it as child 
			if(parent!=null){
				parent.addChildren(item);
			}
			
			// get all dependencies
			String[] importArray = SourceTool.getImportListFromString(source);
			// get package from source
			String pkgName = SourceTool.getPackageName(source);
			
			for(String importPkg : importArray){
				// make sure that all dependencies packages are created
				createPackage(importPkg, ItemType.external, map);
				// inject dependency
				injectDependencies(pkgName, importPkg, map);
			}
		}
		
		List<Item> rootList = new ArrayList<>();
		// get all the root
		for (Item item : map.values()){
			// root do not have point
			if(item.getName().contains(".")==false){
				rootList.add(item);
			}
		}
		
		return rootList.toArray(new Item[0]);
	}

	private static void injectDependencies(String pkgName, String importPkg, Map<String, Item> map) {
		String[] pkgClassParentArray = SourceTool.getAllParentPackage(pkgName);
		String[] pkgImportParentArray = SourceTool.getAllParentPackage(importPkg);
		
		int classNb = pkgClassParentArray.length;
		int importNb = pkgImportParentArray.length;
		
		int iterationNb = classNb;
		
		if(importNb>classNb){
			iterationNb = importNb;
		}
		
		for(int i=0; i<iterationNb; i++){
			// if too high set dependencies on the last elements
			int classIndex = i;
			if( i> pkgClassParentArray.length-1){
				classIndex = pkgClassParentArray.length-1;
			}
			int importIndex = i;
			if( i> pkgImportParentArray.length-1){
				importIndex = pkgImportParentArray.length-1;
			}
			
			// get the items
			Item classItem = map.get(pkgClassParentArray[classIndex]);
			Item importItem = map.get(pkgImportParentArray[importIndex]);
			
			// set the import inside the class branch items
			if(classItem!=importItem){
				classItem.addDependency(importItem);
			}
		}
		
	}

	public static Package[] createPackageFromSource(String source, Map<String, Item> map) {
		String pkgName = SourceTool.getPackageName(source);
		return createPackage(pkgName, ItemType.internal, map); 
	}

	public static Package[] createPackage(String pkgName, ItemType type, Map<String, Item> map) {
		String[] allPackageName = SourceTool.getAllParentPackage(pkgName);
		Item parent =null;
		
		List<Package> packageList = new ArrayList<>();
		
		for(int i = 0; i<allPackageName.length ; i++){
			String pkgStr = allPackageName[i];
			
			if(map.containsKey(pkgStr)==false){
				// check for package parent 
				if(i<=0){
					// from root package no parent
					parent = null;
				}
				// allocate package
				Package pkg = new Package(pkgStr, parent, type);
				
				if(i>0){
					// I am the child of the previous package
					packageList.get(i-1).addChildren(pkg);
					
				}
				// store it
				map.put(pkgStr, pkg);
				// keep it
				packageList.add(pkg);
				// this package become the next package parent
				parent = pkg;
			}else{
				// might be needed for next package creation
				parent = map.get(pkgStr);
				// keep it
				packageList.add((Package)parent);
			}
		}
		return packageList.toArray(new Package[0]);
	}
	
	public static List<DependencyError> checkForDependencyLoop(Item... itemArray){
		List<DependencyError> errorList = new ArrayList<DependencyError>();
		return checkForDependencyLoop(errorList, itemArray);
	}
	
	public static List<DependencyError> checkForDependencyLoop(List<DependencyError> errorList, Item... itemArray){
		for(Item item : itemArray){
			// check that none are dependent to it
			checkForDependencyLoop( errorList, new ArrayList<Item>(), item);
		}
		return errorList;
	}

	public static List<DependencyError> checkForDependencyLoop(List<DependencyError> errorList, List<Item> routeList, Item item){
		// add the item to the 
		routeList.add(item);
		
		// get all item's dependencies
		Item[] dependencyArray = item.getDependecies();
		
		for(Item depItem : dependencyArray){
			// check if it is inside the route
			if(routeList.contains(depItem) == true){
				// create an error 
				DependencyError error = new DependencyError(depItem, new ArrayList<>(routeList));
				errorList.add(error);
			}
		}
		return errorList;
	}

	public static DependencyError[] checkForDependencyLoop(Path pathToAnalyse, String filter) {
		// first analyse
		Item[] itemArray = analyse(pathToAnalyse, filter);
		List<DependencyError> errorList = checkForDependencyLoop(itemArray);
		
		return errorList.toArray(new DependencyError[0]);
	}	
	
}
