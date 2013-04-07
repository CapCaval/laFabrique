package org.capcaval.ccoutils.lang;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.capcaval.ccoutils.file.FileSeekerResult;
import org.capcaval.ccoutils.file.FileTool;
import org.capcaval.ccoutils.lang.SystemTools.OSType;
import org.junit.Test;


public class ClassTools {
	public static boolean containNoArgumentConstructor(Class<?> type){
		Constructor<?> c = null;
		try {
			Constructor<?>[] ctorList = type.getConstructors();
			for(Constructor<?>ctor : ctorList){
				System.out.println(Arrays.toString(ctor.getParameterTypes()));
				// 1 means no parameter
				if(ctor.getParameterTypes().length==1){
					c=ctor;
					break;
				}
			}
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return true if a ctor without parameter found
		return c==null?false:true;
	}
	
	public static Class<?>[] getAllType(final Object[] objList){
		// allocate the same size array
		Class<?>[] classTypeList = new Class<?>[objList.length];
		for(int i=0; i< objList.length; i++){
			classTypeList[i]=objList[i].getClass();
		}
		return classTypeList;
	}
	
	public static Class<?> getClassFromShortName(String classShortName) {
		Class<?> returnedClass = null;

		String allPathStr = System.getProperty("java.class.path");
		String pathSeparator = ":";
		char dirSeparator = '/';
		if(SystemTools.getOSType() == OSType.Windows){
			pathSeparator = ";";
			dirSeparator = '\\';
		}
		String[] pathStrList = allPathStr.split(pathSeparator);
		
		// remove all the dirty path added by eclipse
		int index = 0;
		for(String path : pathStrList){
			if(path.contains("org.eclipse")){
				ArrayTools.removeElement(pathStrList, index);
			}
			index++;
		}
		
		// compute the list of path
		// List<Path> pathList = new ArrayList<>();
		for (String pathStr : pathStrList) {
			// pathList.add(Paths.get(pathStr));
			Path path = Paths.get(pathStr);

			FileSeekerResult result = null;
			try {
				result = FileTool.seekFiles(classShortName + ".class", path);
				if (result.getFileList().length == 0) {
					// search
					result = FileTool.seekFiles("*$" + classShortName
							+ ".class", path);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (result != null) {
				Path p = result.getFileList()[0];
				String fileStr = p.toFile().toString();
				// remove the root path
				String str = fileStr.replace(pathStr, "");
				str = str.replace(dirSeparator, '.');
				str = str.substring(1, str.length() - 6);

				ClassLoader cl = ClassTools.class.getClassLoader();
				try {
					returnedClass = cl.loadClass(str);
					break;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

			}
		}
		return returnedClass;
	}
	public static boolean isClassContainsJUnitTest(Class<?> type){
		boolean returnValue = false;
		
		for(Method m : type.getMethods()){
			Test t = m.getAnnotation(Test.class);
			
			if(t!=null){
				returnValue = true;
				break;
			}
		}
		
		return returnValue;
	}
	
}
