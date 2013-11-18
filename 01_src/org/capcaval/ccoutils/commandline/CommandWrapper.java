package org.capcaval.ccoutils.commandline;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class CommandWrapper {
	public String commandStr;
	public String descriptionStr;
	public Object instance;
	public Method method;
	public boolean isContaingList;
	public CommandParamDesc[] paramList;

	public CommandWrapper(String name, Method method, Object instance) {
		this.commandStr = name;
		this.instance = instance;
		this.method = method;
		// default value is false
		this.isContaingList = false;
		
		this.descriptionStr = method.getAnnotation(Command.class).desc();
		
		Annotation[][] annotationParamList = method.getParameterAnnotations();
		Class<?>[] paramTypeList = method.getParameterTypes();
		
		this.paramList = new CommandParamDesc[paramTypeList.length];
		
		for(int i = 0; i< this.paramList.length; i++){
			Annotation[] annotationList = annotationParamList[i];
			String paramName = "--";
			String description = "";
			// get annotation description if any
			for(Annotation annotation : annotationList){
				if(annotation.annotationType() == CommandParam.class){
					CommandParam commandParam = (CommandParam)annotation;
					description = commandParam.desc();
					paramName = commandParam.name();
				}
			}
			
			// create the new description
			this.paramList[i] = new CommandParamDesc(paramName, paramTypeList[i], description);
		}
	}
	@Override
	public String toString(){
		return this.commandStr;
	}
}
