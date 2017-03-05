package org.capcaval.lafabrique.commandline;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.capcaval.lafabrique.commandline._test.CommandHandler;
import org.capcaval.lafabrique.common.CommandResult;
import org.capcaval.lafabrique.common.CommandResult.Type;
import org.capcaval.lafabrique.converter.Converter;
import org.capcaval.lafabrique.converter.ConverterManager;
import org.capcaval.lafabrique.lang.ArrayTools;
import org.capcaval.lafabrique.lang.ParamError;

public class CommandLineComputer {
	
	protected Map<String, CommandWrapper> commandMap = new HashMap<>(); 
	protected Map<String, CommandWrapper> commandNoParamMap = new HashMap<>();
	protected Map<String, Class<?>>primitiveMap = new HashMap<>();
	protected ConverterManager cm = new ConverterManager();
	protected String prefix;
	private List<Object> commandInstanceList = new ArrayList<>();
	private Map<Class<? extends Annotation>, CommandHandler> commandHandlerMap = new HashMap<>();
	
	//protected String consoleName;
	
	public CommandLineComputer(){
		this.init(null, "", Command.class);
	}

	public CommandLineComputer(String prefix){
		this.init(null, prefix, Command.class);
	}

	public CommandLineComputer(String consoleName, String prefix){
		this.init(consoleName, prefix, Command.class);
	}

	public CommandLineComputer(String consoleName, String prefix, Class<?> annotationType){
		this.init(consoleName, prefix, Command.class);
	}
	
	public void init(String consoleName, String prefix, Class<?> annotationType){
		// test if it is an annotation
		if(annotationType.isAnnotation() == false){
			throw new RuntimeException("annotationType is not annotation : " + annotationType);
		}
		
		// keep the following name
		CommandResult.applicationName.set(consoleName);
		//this.consoleName = consoleName;
		
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
	
	public CommandResult computeCommandLine(String... commandArray){
		if((commandArray == null)||(commandArray.length == 0)){
			return new CommandResult(Type.COMMAND_NOT_FOUND,  "There is no command or default command." + 
					"\nPlease use help to see the available commands.");
		}
		
		// get the command name
		String command = commandArray[0];
		
		CommandWrapper w = null;
		if(commandArray.length == 1){
			w = this.commandNoParamMap.get(command);
		}
		else{
			// get the command wrapper
			w = this.commandMap.get(command);
		}
		
		if(w == null){
			return new CommandResult( Type.COMMAND_NOT_FOUND, "The command \"" + command + "\" can not be found." + 
					"\n   Please use help to see the avalaible commands.");
		}
		
		//
		List<String> commandList = ArrayTools.newArrayList(commandArray);
		// remove the first element
		commandList = commandList.subList(1, commandList.size());

		// let's go execute
		CommandResult result = executeCommand(w, commandList, this.cm);
		
		return result;
	}
	
	protected CommandResult executeCommand(CommandWrapper w, List<String> commandList, ConverterManager cm) {
		ParamError paramError = null;
		Object returnedObject = null;
		
		CommandResult cr = new CommandResult();
		
		try {
			// convert all the parameters types
			Class<?>[] paramTypeList = w.method.getParameterTypes();
			
			// check the number of parameter and if it is not containing a list
			if((w.isContaingList == false)&&(commandList.size() != paramTypeList.length)){
				cr.addErrorMessage("The command " + w.commandStr + 
						" needs " + paramTypeList.length + " parameters and you have entered " + commandList.size() + " parameter(s)" +
						"\n  the command signature is " + w.commandStr + Arrays.toString(paramTypeList));
				return cr;
			}
			
			List<Object> paramObjList = new ArrayList<>();
			
			int i = 0;
			for(Class<?> type :paramTypeList){
				paramError = this.computeParam(
						type, 
						paramObjList,
						i,
						this.computeCommandForList(i++, commandList),
						cm);
				
				if(paramError != null){
					// go out
					break;
				}
			}
			if(paramError == null){
				List<CommandHandler> list = new ArrayList<>();
				
				// Check all annotation
				for(Annotation annotation : w.method.getAnnotations()){
					CommandHandler handler = this.commandHandlerMap.get(annotation.annotationType());
					
					// if any handler execute it
					if(handler != null){
						list.add(handler);
					}
				}
				
				if(list.size()>0){
					// get the handle method
					//Method method = CommandHandler.class.getMethod("handle", Object.class, Method.class, Object[].class);
					
					// add the original command
					
					
					// execute in all except the original command
					for(int j=0; j<list.size(); j++){ 
						// get the handler
						CommandHandler handler = list.get(j);
						// get the next
						//CommandHandler nextHandler = list.get(j+1);
						// run it with the next
						Object[] retArray = handler.handle( w.instance, w.method, paramObjList.toArray(new Object[0]));
						// only one parameter to be returned
						returnedObject = retArray[0];
						
					}
				}else{
					// no handler has been found so just execute the command
					try{
						returnedObject = w.method.invoke(w.instance, paramObjList.toArray(new Object[0]));
					}catch(Exception e){
						System.out.println("+++++++++++++++++++++++++++++++++++++++");
						System.out.println("ERROR  " + w.commandStr + Arrays.toString(paramObjList.toArray(new Object[0])));
						System.out.println(w.method.getName());
						e.printStackTrace();
					}
				}
				
				
				// If annotation is registered call the pre method
				// Object[] paramArray = preCommand.run(paramObjList.toArray(new Object[0]))
				
				
			}
			else{
				cr.addErrorMessage("Cannot convert parameter at index " + paramError.getIndex() + " with the value \"" + paramError.getErrorSourceObject() +
						"\"\n the raised exception is : \n" + paramError.getException().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// convert the return value
		if(returnedObject != null){
			Class<?> retType = returnedObject.getClass();
			
			if(retType.isEnum() == true){
				retType = Enum.class;
			}
		
			// get the converter
			Converter<Object,String> c = (Converter<Object,String>)this.cm.getConverter(retType, String.class);
			String messageBack;
			if(c==null){
				messageBack=returnedObject.toString();
			}else{
				messageBack=c.convert(returnedObject);
			}
			
			// add  the value to the return value
			cr.addMessage(messageBack);
		}
		return cr;
	}
	
	private ParamError computeParam(Class<?> type, List<Object> paramObjList, int index, List<String> paramList, ConverterManager cm) {
		ParamError paramError = null;
		
		// check if it is an enum
		if(type.isEnum() == true){
			type = Enum.class;
		}
		Object o = null;
		if(type.isArray()||List.class.isAssignableFrom(type)){
			paramError = this.computeArrayParameter(type, index, paramList, paramObjList, cm);
		}
		else{
			String srcStr = paramList.get(0);
			o = this.convertStringParameter(srcStr, type, cm, index);
			
			if( o == null){
				paramError = new ParamError(0, null, srcStr);
			}else if(o.getClass().equals(ParamError.class)){
				paramError = (ParamError)o;
			}
			
			
			
			paramObjList.add(o);
		}
		
		return paramError;
	}
	private ParamError computeArrayParameter(Class<?> arrayType, int index, List<String> paramList, List<Object> objList, ConverterManager cm) {
		ParamError paramError = null;
		// create a temporary list
		List<Object> tmpObjList = new ArrayList<>();
		
		// get the object type
		Class<?> objectType = arrayType.getComponentType();
		
		// first convert all string
		for(String paramStr : paramList){
			Object obj = this.convertStringParameter(paramStr, objectType, cm, index++);
			
			if(obj.getClass() == ParamError.class){
				return (ParamError)obj;
			}
			
			tmpObjList.add(obj);
		}
		
		// check if it is a List or an array []
		if(arrayType.isArray() == true){
			Object obj = ArrayTools.convertToArray(tmpObjList, objectType);
			if(obj.getClass() == ParamError.class){
				paramError = (ParamError)obj;
			}
			
			objList.add(obj);
		}else{
			// else it is a list keep it as it is
			objList.add(paramList);
			}
		
		return paramError;
	}
	private Object convertStringParameter(String paramStr, Class<?> type, ConverterManager cm, int index) {
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
			ParamError error = new ParamError(index, e, paramStr);
			return error;
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
		// keep a ref on the new instance
		this.commandInstanceList.add(instance);
		
		// compute what is inside the instance
		this.computeCommandInstance(instance, Command.class);
	}
	public void computeCommandInstance(Object instance, Class<? extends Annotation> annotationType){	
		// get all the method
		Method[] methodList = instance.getClass().getMethods();
		for(Method method : methodList){
			if(method.isAnnotationPresent(annotationType) == true){
				//allocate wrapper
				CommandWrapper cw = new CommandWrapper(method.getName(), method, instance);
				
				// check if the command contains a list correctly and set the result
				cw.isContaingList = this.checkIfCommandContainsList(method);
				
				if(method.getParameterTypes().length ==0){
					// add the command inside the map
					this.commandNoParamMap.put(method.getName(), cw);
				}else{
					// avoid duplication remove old command if any
					if(this.commandMap.containsKey(method.getName())){
						this.commandMap.remove(method.getName());
					}
				
					// add the command inside the map
					this.commandMap.put(method.getName(), cw);
				}
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

	public void addCommandHandler(Class<? extends Annotation> annotationType, CommandHandler preCommand) {
		this.commandHandlerMap.put(annotationType, preCommand);;
	}
	
	
}
