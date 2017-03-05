package org.capcaval.lafabrique.lang;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.capcaval.lafabrique.file.FileSeekerResult;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.lang.SystemTools.OSType;

public class ClassTools {
	public static boolean isConstructorWithoutArgument(Class<?> type) {

		int paramNumber = 1; // number of param for inner class

		// for inner class non static
		boolean isClassStatic = (type.getModifiers() & Modifier.STATIC) == Modifier.STATIC;

		if ((type.getEnclosingClass() == null) || (isClassStatic == true)) {
			// number of param for standard class
			paramNumber = 0;
		}

		Constructor<?> c = null;
		try {
			Constructor<?>[] ctorList = type.getConstructors();
			for (Constructor<?> ctor : ctorList) {
				// 1 means no parameter
				if (ctor.getParameterTypes().length == paramNumber) {
					c = ctor;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return true if a ctor without parameter found
		return c == null ? false : true;
	}

	public static Class<?>[] getAllType(final Object[] objList) {
		// allocate the same size array
		Class<?>[] classTypeList = new Class<?>[objList.length];
		for (int i = 0; i < objList.length; i++) {
			classTypeList[i] = objList[i].getClass();
		}
		return classTypeList;
	}

	public static Class<?> getClassFromShortName(String classShortName) {
		Class<?> returnedClass = null;

		String allPathStr = System.getProperty("java.class.path");
		String pathSeparator = ":";
		char dirSeparator = '/';
		if (SystemTools.getOSType() == OSType.Windows) {
			pathSeparator = ";";
			dirSeparator = '\\';
		}
		String[] pathStrList = allPathStr.split(pathSeparator);

		// remove all the dirty path added by eclipse
		int index = 0;
		for (String path : pathStrList) {
			if (path.contains("org.eclipse")) {
				ArrayTools.removeElementAt(pathStrList, index);
			}
			index++;
		}

		// compute the list of path
		// List<Path> pathList = new ArrayList<>();
		for (String pathStr : pathStrList) {
			// pathList.add(Paths.get(pathStr));
			Path path = Paths.get(pathStr);

			FileSeekerResult result = FileTools.seekFiles(classShortName + ".class", path);
			if (result.getFileList().length == 0) {
				// search
				result = FileTools.seekFiles("*$" + classShortName + ".class", path);
			}

			if (result != null) {
				Path p = result.getPathList()[0];
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

	public static boolean doesClassContainJUnitTest(Class<?> type) {
		boolean returnValue = false;

		for (Method m : type.getMethods()) {
			Annotation[] array = m.getAnnotations();

			for (Annotation annotation : array) {
				Class<?> annoType = annotation.annotationType();
				if (annoType.getName().equals("org.junit.Test")) {
					return true;
				}
			}
		}

		return returnValue;
	}

	public static Path getBinaryPath(Class<?> type) {
		// convert the class name with package to file path
		String fileName = type.getName().replace('.', '/') + ".class";

		// get the url
		URL url = type.getClassLoader().getResource(fileName);

		// get the path
		Path path = null;
		try {
			path = Paths.get(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return path;
	}

	public static String getFileNameFromType(Class<?> type) {
		// get the full name with package
		String className = type.getCanonicalName();

		return getFileNameFromClassName(className);
	}

	public static String getFileNameFromClassName(String className) {
		String filename = className.replace(".", "/") + ".java";

		return filename;
	}

	public static String getClassNameFromFullName(String fullName) {
		String[] packageArray = fullName.split("\\.");

		// this is the default value if there is no package
		String className = fullName;

		// get last if packages
		if (packageArray.length > 1) {
			className = packageArray[packageArray.length - 1];
		}

		return className;
	}

	public static String getPackageNameFromFullName(String fullName) {
		String[] packageArray = fullName.split("\\.");

		// this is the default value if there is no package
		String packageName = "";

		// get last if packages
		if (packageArray.length > 1) {
			String className = packageArray[packageArray.length - 1];
			packageName = fullName.substring(0, fullName.length() - className.length() - 1);
		}

		return packageName;
	}

	public static <T> T getFileNewInstance(String fileName, String... binDirArray) {
		T instance = null;
		Class<?> c = null;

		SystemClassLoader scm = new SystemClassLoader();

		// add all the binary directory
		for (String binDir : binDirArray) {
			try {
				scm.addURL(binDir);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String name = fileName.replace('/', '.').replace(".java", "");

		try {
			c = scm.loadClass(name);
			instance = (T) c.newInstance();

		} catch (Exception e) {
			// MLB
			System.out.println("----------------------------------------------");
			System.out.println("----------------------------------------------");
			e.printStackTrace();
			System.out.println("----------------------------------------------");
			// throw new
			// RuntimeException("[ccOutils] ERROR : ClassTools.getFileNewInstance can not load and instanciate the class "
			// + name +
			// "\nWith the following path : " + scm.toString(), e);
			// not found return null
			instance = null;

		} finally {
			try {
				scm.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return instance;

	}

	public static boolean doesClassContainMain(Class<?> type) {
		Method m = null;

		try {
			m = type.getMethod("main", String[].class);
		} catch (Exception e) {
			// this is the extremely rare exception where I do nothing
			// within a catch case
		}

		// if null return false else true
		return m == null ? false : true;
	}

	public static Version getClassVersion(String pathStr) {
		byte[] byteArray = FileTools.readByteArrayfromFile(pathStr);
		
		byte[] header = ArrayTools.getSubArray( byteArray, 0, 3);
		// check that it is a Java file
		ByteBuffer wrapped = ByteBuffer.wrap(header);
		int headerInt = wrapped.getInt();
		System.out.println(ArrayTools.byteArrayToHexaString(header));
		System.out.println(Integer.toHexString((int)headerInt));
		
		if(headerInt!=0xCAFEBABE){
			throw new RuntimeException("[laFabrique] Error : the file " + pathStr + " is not a java class.");
		}
		
		// get major version
		byte[] minorArray = ArrayTools.getSubArray( byteArray, 4, 5);
		int minor=ByteBuffer.wrap(minorArray).getShort();
		
		//get minor version
		byte[] majorArray = ArrayTools.getSubArray( byteArray, 6, 7);
		int major=ByteBuffer.wrap(majorArray).getShort();
		
		Map<Integer, Version> map = new HashMap<>();
		map.put(0x2D, Version.factory.newVersion(1,1));
		map.put(0x2E, Version.factory.newVersion(1,2));
		map.put(0x2F, Version.factory.newVersion(1,3));
		map.put(0x30, Version.factory.newVersion(1,4));
		map.put(0x31, Version.factory.newVersion(1,5));
		map.put(0x32, Version.factory.newVersion(1,6));
		map.put(0x33, Version.factory.newVersion(1,7));
		map.put(0x34, Version.factory.newVersion(1,8));
		
		Version version = map.get(major);
		version = Version.factory.newVersion( version.getVersionIntArray()[0], version.getVersionIntArray()[1], minor);
		return version;
	}

}
