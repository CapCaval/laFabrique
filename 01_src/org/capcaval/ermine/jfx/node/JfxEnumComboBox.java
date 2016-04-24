package org.capcaval.ermine.jfx.node;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class JfxEnumComboBox <T extends Enum<?>> extends ComboBox<T> {

	public static JfxEnumComboBox2Factory factory = new JfxEnumComboBox2FactoryImpl(); 

	public static interface JfxEnumComboBox2Factory{
		<T extends Enum<?>> JfxEnumComboBox<T> newInstance(Class<T> type); 
	}
	
	public static class JfxEnumComboBox2FactoryImpl implements JfxEnumComboBox2Factory{
		@Override
		public <T extends Enum<?>> JfxEnumComboBox<T> newInstance(Class<T> type) {
			return new JfxEnumComboBox<>(type);
		}
	}
	
	private Class<T> type;
	
	public JfxEnumComboBox(Class<T> type) {
		// keep a ref on the type
		this.type = type;
		
		// get the current item list
		ObservableList<T> list = this.getItems();
		// clean if needed
		list.clear();

		// get the new values
		T[] constantArray = (T[])type.getEnumConstants();
		// add them
		list.addAll(constantArray);
	}

	protected Class<T> getType(){
		return this.type;
	}
}
