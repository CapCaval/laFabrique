package org.capcaval.ermine.jfx.panes;

import javafx.scene.Node;

import org.capcaval.ermine.jfx.layout.JfxRefPointLayout;
import org.capcaval.ermine.jfx.node.JfxEnumComboBox;
import org.capcaval.ermine.layout.RefPointAlignEnum;
import org.capcaval.ermine.layout.RefPointEnum;


public class JfxRefPointPane extends JfxPane{

	protected JfxRefPointLayout layout;

	public JfxRefPointPane(){
		// create a layout and keep a ref on it
		this.layout = new JfxRefPointLayout(this);
	}
	
	public void addNode(Node node, RefPointEnum refpoint){
		this.layout.addNode(node, refpoint);
	}

	public void addNode(Node node, RefPointEnum refpoint, int deltax, int deltay){
		this.layout.addNode(node, refpoint, deltax, deltay);
	}

	public void addResizableNode(Node node, int tlDeltaX, int tlDeltaY, int brDeltaX, int brDeltaY){
		this.layout.addResizableNode(node, tlDeltaX, tlDeltaY, brDeltaX, brDeltaY);
	}
	
	public void addResizableNode(Node node, RefPointEnum tlRefpoint, int tlDeltaX, int tlDeltaY, RefPointEnum brRefpoint, int brDeltaX, int brDeltaY){
		this.layout.addResizableNode(node, tlRefpoint, tlDeltaX, tlDeltaY, brRefpoint, brDeltaX, brDeltaY);
	}

	public void addNode(Node node, RefPointEnum refPointEnum, RefPointAlignEnum refPointAlignEnum) {
		this.layout.addNode(node, refPointEnum, refPointAlignEnum);
		
	}
	
}
