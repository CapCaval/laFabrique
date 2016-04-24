package org.capcaval.ermine.coves.analysis;

import java.lang.reflect.Field;

import javafx.scene.control.Labeled;

import org.capcaval.lafabrique.lang.StringMultiLine;

public class CovesGadget {
	protected String name;
	protected String label;
	protected Class<?> type;
	protected Field field;
	protected Labeled instance;

	public CovesGadget(String name, Class<?> type, String label, Field field, Labeled instance){
		this.name = name;
		this.type = type;
		this.label = label;
		this.field = field;
		this.instance = instance;
	}

	public String getName() {
		return this.name;
	}

	public Field getField() {
		return this.field;
	}
	
	public Class<?> getType() {
		return this.type;
	}
	
	public Labeled getInstance(){
		return this.instance;
	}
	
	@Override
	public String toString(){
		StringMultiLine sml = new StringMultiLine(
				"Name : " + this.name +
				"Type : " + this.type +
				"Label : " + this.label
				);
		
		return sml.toString();
	}
}
