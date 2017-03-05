package org.capcaval.lafabrique.classgen;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.capcaval.lafabrique.classgen.converters.FloatToStringLiteralConverter;
import org.capcaval.lafabrique.classgen.converters.IntegerToStringLiteralConverter;
import org.capcaval.lafabrique.classgen.converters.LongToStringLiteralConverter;
import org.capcaval.lafabrique.classgen.converters.PathToStringLiteralConverter;
import org.capcaval.lafabrique.classgen.converters.StringToStringLiteralConverter;
import org.capcaval.lafabrique.converter.Converter;
import org.capcaval.lafabrique.converter.ConverterManager;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.lang.ArrayTools;
import org.capcaval.lafabrique.lang.ClassTools;
import org.capcaval.lafabrique.lang.ListTools;
import org.capcaval.lafabrique.lang.StringMultiLine;
import org.capcaval.lafabrique.lang.StringTools;
import org.capcaval.lafabrique.pair.Pair;
import org.capcaval.lafabrique.pair.PairImpl;



public class ClassGenTools {

	public static Attribute<?>[] getAttributes(Object object) {
		List<Attribute<?>> list = new ArrayList<>();
		for(Field field : object.getClass().getDeclaredFields()){
			Object value = null;
			try {
				field.setAccessible(true);
				value = field.get(object);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			// allocate a new attribute
			Attribute<?> attribute = new Attribute<>(field.getName(), field.getType(), value);
			// keep the value
			list.add(attribute);
		}
		
		return list.toArray(new Attribute[0]);
	}

	public static void generateClass(Path path, String fullClassName, Object instance, Converter<?, String>... converterArray) {
		
		// first get all the attribute values
		Attribute<?>[] attributeArray = getAttributes(instance);
		
		// get the class name
		String className = ClassTools.getClassNameFromFullName(fullClassName);

		// get the package name
		String packageName = ClassTools.getPackageNameFromFullName(fullClassName);
		
		// parent name
		Class<?> parentType = instance.getClass();
		
		generateClass(path, className, packageName, parentType, attributeArray, converterArray);
	}

	private static void generateClass(Path path, String className, String packageName, Class<?> parentType, Attribute<?>[] attributeArray, Converter<?, String>... converterArray) {
		StringMultiLine str = new StringMultiLine();
		ConverterManager cm = new ConverterManager(
				new FloatToStringLiteralConverter(),
				new IntegerToStringLiteralConverter(),
				new LongToStringLiteralConverter(),
				new StringToStringLiteralConverter());
		
		
		// add extra converters if any
		for(Converter<?, String> converter : converterArray){
			cm.addConverter(converter);
		}
		
		// add the package if any
		if((packageName != null)&&(packageName.length()>0)){
			str.addLine("package " + packageName + ";");}
		
		str.addLine("");
		
		// add all the import
//		for(Attribute<?> att : attributeArray){
//			// add only non primitive types
//			if(att.getType().isPrimitive() == false){
//				str.addLine("import " + att.getType().getName() + ";");}
//		}
		// Add the parent
		str.addLine("import " + parentType.getName() + ";");
		
		str.addLine("");
		
		// create the new class
		str.addLine("public class " + className +" extends " + parentType.getSimpleName() + "{");
		
		// add the constructor
		str.addLine("\tpublic " + className + "(){");
		for(Attribute<?> att : attributeArray){
			if(att.value != null){
				Converter<Object,String> converter = (Converter<Object,String>)cm.getConverter(att.getType(), String.class);
				
				if(converter == null){
					throw new RuntimeException("Does not have a string converter for type : " + att.getType() + " . Please add one inside your ClassGenTools");
				}
				
				String valStr = converter.convert(att.value);
				str.addLine("\t\tthis."+att.name + " = " + valStr + ";");
			}
		}
		str.addLine("\t}");
		
		str.addLine("}");
		
		// build path name
		//String filePath = path.toString() + "/" +
		String fileName = ClassTools.getFileNameFromClassName(packageName + "." +className);
		String fullName = path.toString()+"/"+fileName;
		
		FileTools.writeFile(fullName, str.toString());
	}

	public static void generateEnum(Path path, String name, Object...objectArray ) {
		List<Pair<String, List<Object>>> enumValuesList = new ArrayList<>();
		List<Object> objList = null;
		
		// analyse the objectArray
		for(Object obj : objectArray){
			if(EnumNameValue.class.isAssignableFrom(obj.getClass())){
				EnumNameValue enumName = (EnumNameValue)obj;
				
				//allocate a new list of object
				objList = new ArrayList<>();

				Pair<String, List<Object>> pair = new PairImpl<>(enumName.name, objList);
				enumValuesList.add(pair);
			}else{
				objList.add(obj);}
		}
		
		
		// get the list of parameters
		List<Class<?>> enumParamArray = ClassGenTools.getEnumParamArray(enumValuesList.get(0).secondItem());
		
		ClassGenTools.generateEnum2(path, name, enumParamArray, objectArray);
		
	}
	private static List<Class<?>> getEnumParamArray(List<Object> objectList) {
		List<Class<?>> typeList = new ArrayList<>();
		
		for(Object obj : objectList){
			typeList.add(obj.getClass());
		}
		
		return typeList;
	}

	public static void generateEnum2(Path path, String name, List<Class<?>> enumParamArray, Object...objectArray ) {
		String className = ClassTools.getClassNameFromFullName(name);
		String packageName = ClassTools.getPackageNameFromFullName(name);
		
		ConverterManager cm = new ConverterManager(
				new FloatToStringLiteralConverter(),
				new IntegerToStringLiteralConverter(),
				new LongToStringLiteralConverter(),
				new PathToStringLiteralConverter(),
				new StringToStringLiteralConverter());

		// package
		StringMultiLine str = new StringMultiLine("package " + packageName + ";");
		str.addLine("");
		
		for(int index=0; index<enumParamArray.size(); index++){
			Class<?>type = enumParamArray.get(index);
			if(Path.class.isAssignableFrom(type) == true){
				enumParamArray.set(index, Path.class);
			}
		}
		
		// generate the imports
		for(Class<?> type : enumParamArray){
			// add only non primitive types
			if(type.isPrimitive() == false){
				str.addLine("import " + type.getName() + ";");}
		}
		str.addLine("");
		
		String ctorParam="";
		int i=0;
		// create the enum field
		for(Class<?> enumField : enumParamArray){
			String varName = StringTools.transformCharToLowerCase(enumField.getSimpleName(),0);
			ctorParam = ctorParam + " " + enumField.getSimpleName() + " " + varName + i++ +",";
		}
		ctorParam = StringTools.removeEnd(",", ctorParam);
		
		
		// create the constructor
		str.addLine("");
		str.addLine("public enum " + className +"{");
		str.addLine("");

		int nbOfParam = enumParamArray.size();
		int nbOfEnumValue = objectArray.length/(nbOfParam+1);
		
		StringBuffer enumStr = new StringBuffer();
		int index=0;
		for(int j=0; j<nbOfEnumValue; j++){
			// create the enum value
			StringBuffer enumValue= new StringBuffer();
			
			enumValue.append( StringTools.transformCharToLowerCase(objectArray[index++].toString(),0));
			enumValue.append("(");
			for(Class<?>type : enumParamArray){
				Converter<Object,String> converter = (Converter<Object,String>)cm.getConverter(type, String.class);
				
				String paramStr = "";
				try{
					paramStr = converter.convert(objectArray[index++]);
				}catch(Exception e){
					e.printStackTrace();
				}
				
				enumValue.append(paramStr + ",");
			}
			enumValue = enumValue.deleteCharAt(enumValue.length()-1); //remove the last comma
			enumValue.append("),\n\t");
			
			enumStr.append(enumValue);
		}
		enumStr.delete(enumStr.length()-3, enumStr.length()); //remove the last comma
		enumStr.append(";");
		
		str.addLine("\t"+ enumStr);
		
		str.addLine("\t");
		
		// create the enum field
		i=0;
		for(Class<?> enumField : enumParamArray){
			String varName = StringTools.transformCharToLowerCase(enumField.getSimpleName(),0);
			str.addLine("\tprotected " + enumField.getSimpleName() + " " + varName + i++ +";");
		}
		
		str.addLine("");
		str.addLine("\tprivate " + className+ "(" + ctorParam + "){");
		str.addLine("");
		
		i=0;
		for(Class<?> enumField : enumParamArray){
			String varName = StringTools.transformCharToLowerCase(enumField.getSimpleName(),0);
			str.addLine("\t\tthis." + varName + i +" = " + varName + i++ + ";");
		}
		
		str.addLine("\t}");
		str.addLine("");
		
		// getter
		i=0;
		for(Class<?> enumField : enumParamArray){
			String typeName = enumField.getSimpleName();
			String varName = StringTools.transformCharToLowerCase(typeName,0) + i++;
			str.addLine("\tpublic " + typeName + " get" + typeName + "(){");
			str.addLine("\t\treturn this." + varName + ";");
			str.addLine("\t}");
		}
		
		str.addLine("}");
		Path filePath = Paths.get(ClassTools.getFileNameFromClassName(name));
		path = path.resolve(filePath);
		FileTools.writeFile( path, str.toString());
		
	}
	
}
