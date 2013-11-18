package org.capcaval.ccoutils.commandline;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.capcaval.ccoutils.converter.Converter;
import org.capcaval.ccoutils.converter.ConverterManager;
import org.capcaval.ccoutils.lang.ArrayError;
import org.capcaval.ccoutils.lang.ArrayTools;

public class CommandLineComputer {
	
	protected Map<String, CommandWrapper> commandMap = new HashMap<>(); 
	//protected List<CommandWrapper> commandList = new ArrayList<>();
	protected Map<String, Class<?>>primitiveMap = new HashMap<>();
	protected ConverterManager cm = new ConverterManager();
	protected String prefix;
	protected String consoleName;
	
	public CommandLineComputer(){
		this.init(null, "");
	}
	public CommandLineComputer(String consoleName, String prefix){
		this.init(consoleName, prefix);
	}

	public CommandLineComputer(String prefix){
		this.init(null, prefix);
	}
	
	public void init(String consoleName, String prefix){
		// keep the following name
		this.consoleName = consoleName;
		
		// keep the prefix
		this.prefix = prefix;
		
		// allocate a help command
		HelpCommand helpCommand = new HelpCommand(consoleName, new ArrayList<>(commandMap.values()), prefix);
		
		// add the help command
		this.addCommandInstance(helpCommand);
		
		this.primitiveMap.put("int", Integer.class);
		this.primitiveMap.put("double", Double.class);
		this.primitiveMap.put("float", Float.class);
		this.primitiveMap.put("long", Long.class);
		this.primitiveMap.put("bool", Boolean.class);
		this.primitiveMap.put("char", Character.class);
		this.primitiveMap.put("short", Short.class);
		this.primitiveMap.put("byte", Byte.class);
		
	}

	public class ComputeResult{
		public String returnMessage;
		public boolean isCommandFound;
		
		public ComputeResult(String returnMessage, boolean isCommandFound){
			this.returnMessage = returnMessage;
			this.isCommandFound = isCommandFound;
		}
		
		public String getReturnMessage(){
			return this.returnMessage;
		}
		
		public boolean isCommandFound(){
			return this.isCommandFound;
		}
	}
	
	public ComputeResult computeCommandLine(String... commandArray){
		if((commandArray == null)||(commandArray.length == 0)){
			return new ComputeResult("["+ this.consoleName + "] Error : There is no command or default command." + 
					"\nPlease use help to see the avalaible commands.", false);
		}
		
		// get the command name
		String command = commandArray[0];
		// get the command wrapper
		CommandWrapper w = this.commandMap.get(command);
		
		if(w == null){
			return new ComputeResult("["+ this.consoleName + "] Error : The command \"" + command + "\" can not be found." + 
					"\nPlease use help to see the avalaible commands.", false);
		}
		
		//
		List<String> commandList = ArrayTools.newArrayList(commandArray);
		// remove the first element
		commandList = commandList.subList(1, commandList.size());

		// let's go execute
		ComputeResult result = executeCommand(w, commandList, this.cm);
		
		return result;
	}
	
	protected ComputeResult executeCommand(CommandWrapper w, List<String> commandList, ConverterManager cm) {
		ArrayError arrayError = null;
		Object returnedObject = null;
		
		try {
			// convert all the parameters types
			Class<?>[] paramTypeList = w.method.getParameterTypes();
			
			// check the number of parameter and if it is not containing a list
			if((w.isContaingList == false)&&(commandList.size() != paramTypeList.length)){
				return new ComputeResult("["+ this.consoleName + "] Error : The command " + w.commandStr + 
						" needs " + paramTypeList.length + " parameters and you have entered " + commandList.size() + " parameter(s)" +
						"\n  the command signature is " + w.commandStr + Arrays.toString(paramTypeList), false); 
			}
			
			List<Object> paramObjList = new ArrayList<>();
			
			int i = 0;
			for(Class<?> type :paramTypeList){
				this.computeParam(
						type, 
						paramObjList, 
						arrayError, 
						this.computeCommandForList(i, commandList),
						cm);
			}
			if(arrayError == null){
				returnedObject = w.method.invoke(w.instance, paramObjList.toArray(new Object[0]));}
			else{
				returnedObject = new String("["+ this.consoleName+ "] ERROR : parameter at index " + arrayError.getIndex() + " with the value\"" + arrayError.getErrorSourceObject() +
						"\"\n the raised exception is : \n" + arrayError.getException());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String str = null;
		if(returnedObject != null){
			Class<?> retType = returnedObject.getClass();
			
			if(retType.isEnum() == true){
				retType = Enum.class;
			}
		
			Converter<Object,String> c = (Converter<Object,String>)this.cm.getConverter(retType, String.class);
			str = c.convert(returnedObject);
			}
		return new ComputeResult(str, true);
	}
	private void computeParam(Class<?> type, List<Object> paramObjList, ArrayError arrayError, List<String> paramList, ConverterManager cm) {
		// check if it is an enum
		if(type.isEnum() == true){
			type = Enum.class;
		}
		Object o = null;
		if(type.isArray()||List.class.isAssignableFrom(type)){

			
			this.computeArrayParameter(type, paramList, paramObjList, cm);
			
		}
		else{
			o = this.convertStringParameter(paramList.get(0), type, cm);
			paramObjList.add(o);
		}
	}
	private void computeArrayParameter(Class<?> arrayType, List<String> paramList, List<Object> objList, ConverterManager cm) {
		// create a temporary list
		List<Object> tmpObjList = new ArrayList<>();
		
		// get the object type
		Class<?> objectType = arrayType.getComponentType();
		
		// first convert all string
		for(String paramStr : paramList){
			Object obj = this.convertStringParameter(paramStr, objectType, cm);
			tmpObjList.add(obj);
		}
		
		ArrayError arrayError=null;
		
		// check if it is a List or an array []
		if(arrayType.isArray() == true){
			Object obj = ArrayTools.convertToArray(tmpObjList, objectType);
			if(obj.getClass() == ArrayError.class){
				arrayError = (ArrayError)obj;
			}
			
			objList.add(obj);
		}else{
			// else it is a list keep it as it is
			objList.add(paramList);
			}
	}
	private Object convertStringParameter(String paramStr, Class<?> type, ConverterManager cm) {
		// convert primitive to Class for e.g : int => Integer
		if(type.isPrimitive()){
			// modify the primitive to a class
			type = this.primitiveMap.get(type.getName());
		}

		// get converter
		Converter<String, Object> c = (Converter<String, Object>)cm.getConverter(String.class, type);
		Object obj = null;
		try{
			obj = c.convert(paramStr);
		} catch (Exception e) {
			return "["+ this.consoleName + "] Error : The argument with the value \""+ paramStr + "\" can not be transformed in " + type.getSimpleName() + " type";
		}
		
		return obj;
	}
	protected List<String> computeCommandForList(int i, List<String> commandArray) {
		List<String> commandList = commandArray.subList(i, commandArray.size());
		return commandList;
	}
	public void addCommandClass(Class<?> commandType){
		// allocate a new instance
		Object instance = null;
		try {
			instance = commandType.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.addCommandInstance(instance);
	}

	public void addCommandInstance(List<Object> instanceList){
		addCommandInstance(instanceList.toArray());
	}
	
	public void addCommandInstance(Object... instanceList){
		for(Object o : instanceList){
			addCommandInstance(o);
		}
	}
	
	public void addCommandInstance(Object instance){
		// get all the method
		Method[] methodList = instance.getClass().getMethods();
		for(Method method : methodList){
			if(method.isAnnotationPresent(Command.class) == true){
				//allocate wrapper
				CommandWrapper cw = new CommandWrapper(method.getName(), method, instance);
				
				// check if the command contains a list correctly and set the result
				cw.isContaingList = this.checkIfCommandContainsList(method);
				
				// avoid duplication remove old command if any
				if(this.commandMap.containsKey(method.getName())){
					this.commandMap.remove(method.getName());
				}
				
				// add the command inside the map
				this.commandMap.put(method.getName(), cw);
			}
		}
	}
	private boolean checkIfCommandContainsList(Method method) {
		Class<?>[] paramTypeList = method.getParameterTypes();
		
		boolean returnValue = false;
		int i=1;
		for(Class<?> clazz : paramTypeList){
			if((List.class.isAssignableFrom(clazz))||
					(clazz.isArray() == true)){
				returnValue = true;
				
				//check that is the last element
				if(i++<paramTypeList.length){
					throw new RuntimeException("[ccTools ERROR] : Method " + method + "contains a List or an array which is not the last parameters. Command line is not able to handle it.\n" +
							"You have to put your list or array as the last parameter.");
				}
				
				break;
			}
		}
		return returnValue;
	}
	
	public CommandWrapper[] getCommandWrapperList(){
		return this.commandMap.values().toArray(new CommandWrapper[0]);
	}

	
}
