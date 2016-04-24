package org.capcaval.ermine.coves;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import javafx.scene.control.Labeled;

import org.capcaval.ermine.coves.analysis.CovesGadget;
import org.capcaval.ermine.coves.analysis.CovesPanel;
import org.capcaval.ermine.jfx.JfxApplicationTools;
import org.capcaval.lafabrique.application.annotations.AppProperty;
import org.capcaval.lafabrique.commandline.Command;
import org.capcaval.lafabrique.file.FileTools;
import org.capcaval.lafabrique.lang.StringMultiLine;

public abstract class AbstractCovesApplication {

	@AppProperty
	public String languageFile = "language.properties";
	
	@Command
	public void generateTranslationFile() {
		Class<?> appType = this.getClass();

		Class<? extends Controller<?>>[] controllerArray = JfxApplicationTools.getAllAppController(appType);
		CovesPanel[] covesPanelArray = new CovesPanel[controllerArray.length];

		int i = 0;
		for (Class<? extends Controller<?>> ctrlType : controllerArray) {
			covesPanelArray[i++] = CovesTools.analyseHmiTree(ctrlType);
		}

		StringMultiLine str = new StringMultiLine();

		try {
			for (CovesPanel panel : covesPanelArray) {
				String panelName = panel.getPanelName();
				str.addLine("#" + panelName);

				// allocate the panel to get the default value
				Object panelInstance = panel.getType().newInstance();
				
				for (CovesGadget gadget : panel.getGadgetArray()) {
					String name = panelName + "." + gadget.getName();
					// get the gadget instance
					Object gadgetInstance = gadget.getField().get(panelInstance);
					
					if(Labeled.class.isAssignableFrom(gadgetInstance.getClass())){
						Labeled label = (Labeled) gadgetInstance;
						// add to the file
						str.addLine(name + "=" + label.getText());
					}
				}
			}

			FileTools.writeFile( this.languageFile, str.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public void applyLanguage(){
//		// get all the controller
//		Field[] fieldArray = this.getClass().getFields();
//		List<Controller<?>> list = new ArrayList<>();
//		for(Field field : fieldArray){
//			if(field.getAnnotation(AppController.class)!=null){
//				list.add(e)
//			}
//		}
//	}
	
	
	public boolean applyLanguage(Controller<?> controller){
        CovesPanel analyse = CovesTools.analyseHmiTree(controller);
        Map<String, CovesGadget> map = CovesTools.getGadgetMap(analyse);
        
        // get the property file if any
        Properties properties = new Properties();
        
        if(FileTools.isFileExist(this.languageFile) == false){
        	// if does not exist bye bye
        	return false;
        }
        
        try {
        	FileInputStream inputStream = new FileInputStream(this.languageFile); 
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        for(Enumeration<?> e = properties.keys(); e.hasMoreElements();) {
        	Object key = e.nextElement();
        	String value = (String)properties.get(key);
        	CovesGadget gadget = map.get(key);
        	
        	gadget.getInstance().setText(value);
        }
        
        // inform that language properties has been applyed
        return true;
	}
}
