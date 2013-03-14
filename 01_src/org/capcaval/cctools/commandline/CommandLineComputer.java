package org.capcaval.cctools.commandline;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.capcaval.cctools.converter.Converter;
import org.capcaval.cctools.converter.ConverterManager;

public class CommandLineComputer {
	
	Map<String, CommandWrapper> commandMap = new HashMap<>(); 
	Map<String, Class<?>>primitiveMap = new HashMap<>();
	ConverterManager cm = new ConverterManager();
	
	public CommandLineComputer(){
		this.primitiveMap.put("int", Integer.class);
		this.primitiveMap.put("double", Double.class);
	}
	
	public String computeCommandLine(String... commandList){
		Object returnedObject = null;
		
		// get the command name
		String command = commandList[0];
		// get the command wrapper
		CommandWrapper w = this.commandMap.get(command);
		//
		List<String> commandArray = new ArrayList<>();
		for(int i=1 ; i<commandList.length; i++){
			commandArray.add(commandList[i]);
		}
		// 
		try {
			// convert all the parameters types
			Class<?>[] paramTypeList = w.method.getParameterTypes();
			List<Object> paramObjList = new ArrayList<>();
			
			int i = 0;
			for(Class<?> type :paramTypeList){
				//TODO convert primitive to Class for e.g : int => Integer
				if(type.isPrimitive()){
					// modify the primitive to a class
					type = this.primitiveMap.get(type.getName());
				}
				
				Converter<String, Object> c = (Converter<String, Object>)this.cm.getConverter(String.class, type);
				Object o = c.convert(commandArray.get(i++));
				paramObjList.add(o);
			}
			
			returnedObject = w.method.invoke(w.instance, paramObjList.toArray(new Object[0]));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String str = null;
		if(returnedObject != null){
			Class<?> retType = returnedObject.getClass();
		
			Converter<Object,String> c = (Converter<Object,String>)this.cm.getConverter(retType, String.class);
			str = c.convert(returnedObject);
			}
		
		return str;
	}
	
	public void addCommandClass(Class<?> commandType){
		// allocate a new instance
		Object instance = null;
		try {
			instance = commandType.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Method[] methodList = commandType.getMethods();
		for(Method method : methodList){
			if(method.isAnnotationPresent(Command.class) == true){
				//allocate wrapper
				CommandWrapper cw = new CommandWrapper(method.getName(), method, instance);
				this.commandMap.put(method.getName(), cw);
			}
		}
	}

}
