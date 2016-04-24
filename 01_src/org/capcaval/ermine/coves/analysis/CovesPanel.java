package org.capcaval.ermine.coves.analysis;

import java.util.Arrays;

import org.capcaval.lafabrique.lang.StringMultiLine;

public class CovesPanel {
	
	protected String panelName;

	protected Class<?> type;
	protected CovesGadget[] gadgetArray;
	protected CovesPanel[] subPanelArray;

	public CovesPanel(String panelName, Class<?>type, CovesGadget[] gadgetArray, CovesPanel[] subPanelArray){
		this.panelName = panelName;
		this.type = type;
		this.gadgetArray = gadgetArray;
		this.subPanelArray = subPanelArray;
	}

	public String getPanelName() {
		return panelName;
	}

	public Class<?> getType() {
		return type;
	}

	public CovesGadget[] getGadgetArray() {
		return gadgetArray;
	}

	public CovesPanel[] getSubPanelArray() {
		return subPanelArray;
	}
	
	@Override
	public String toString(){
		StringMultiLine sml = new StringMultiLine(
				"Name : " + this.panelName,
				"GadgetList : " + Arrays.toString(this.gadgetArray)
				);
		
		return sml.toString();
	}
}
