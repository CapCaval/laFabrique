package org.capcaval.ermine.coves;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Labeled;

import org.capcaval.c3.componentmanager.ComponentManager;
import org.capcaval.ermine.coves.analysis.CovesGadget;
import org.capcaval.ermine.coves.analysis.CovesPanel;

public class CovesTools {

	static public CovesPanel analyseHmiTree( Class<? extends Controller<?>> rootCtrlType) {
		Controller<?> instance = null;
		try {
			instance = rootCtrlType.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return analyseHmiTree(instance);
	}
	
	static public CovesPanel analyseHmiTree( Controller<?> controller) {
		Class<?>rootCtrlType = controller.getClass();
		
		// get the returning view method
		Method method = null;
		try {
			method = rootCtrlType.getMethod("getView");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// get the view type
		Class<?> viewType = method.getReturnType();
		List<CovesGadget> gadgetList = new ArrayList<>();
		
		try {
			Object viewInstance = controller.getView();
			
			// Analyze all members
			for (Field field : viewType.getDeclaredFields()) {
				if (Labeled.class.isAssignableFrom(field.getType())) {
					field.setAccessible(true);
					// get the labeled instance
					Labeled labeled = (Labeled) field.get(viewInstance);
					CovesGadget gadget =new CovesGadget(field.getName(), labeled.getClass(), labeled.getText(), field, labeled);
					gadgetList.add(gadget);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		CovesPanel analysis = new CovesPanel(
				viewType.getSimpleName(), 
				viewType , 
				gadgetList.toArray(new CovesGadget[0]), null);

		return analysis;
	}
	
	public static Map<String, CovesGadget> getGadgetMap(CovesPanel rootPanel){
		return getGadgetMap(rootPanel, null);
	}
	
	public static Map<String, CovesGadget> getGadgetMap(CovesPanel rootPanel, Map<String, CovesGadget> map){
		if(map == null){
			map = new HashMap<>();}
		
		String panelName = rootPanel.getPanelName();
		
		for(CovesGadget gadget : rootPanel.getGadgetArray()){
			map.put( panelName+ "." + gadget.getName(), gadget);
		}
		
		return map;
	}
	
	
    public static void injectC3ServicesAndEvents(final Controller<?> controller) {
    	ComponentManager cm = ComponentManager.componentManager;
    	
    	cm.injectServicesAndEvents(controller);
    	
        // get the view if any
        Object view = controller.getView();
        
        // navigate inside the controller tree
        // get the view type
        Class<?>viewType = view.getClass();
        
        // for all members with annotation
        for(Field field : viewType.getDeclaredFields()){
            if(Controller.class.isAssignableFrom(field.getType())){
                Controller<?> subController = null;
                try {
                    field.setAccessible(true);
                    subController = (Controller<?>) field.get(view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                // recursive call
                injectC3ServicesAndEvents(subController);
            }
        }
        // service and event have been set so init method
        // can be called for instance to subscribe to event
        controller.init();
    }
}
