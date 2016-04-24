package org.capcaval.ermine.coves.statemachine._test.j2d;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.capcaval.ermine.coves.statemachine.StateManager;

public class MainView 
    extends JPanel
{
    protected SubCtrl subCtrl;

    /**
     * Get accessor for subCtrl
     */
    public SubCtrl getSubCtrl () {
        return this.subCtrl;
    }

    /**
     * Card accessor for subCtrl
     */
    public int cardSubCtrl () {
        if ( this.subCtrl == null ) return 0;
        else return 1;
    }

    protected JButton button1 = new JButton("btn 1");

    /**
     * Get accessor for button1
     */
    public JButton getButton1 () {
        return this.button1;
    }

    /**
     * Card accessor for button1
     */
    public int cardButton1 () {
        if ( this.button1 == null ) return 0;
        else return 1;
    }

    protected JButton button2 = new JButton("btn 2");

    /**
     * Get accessor for button2
     */
    public JButton getButton2 () {
        return this.button2;
    }

    /**
     * Card accessor for button2
     */
    public int cardButton2 () {
        if ( this.button2 == null ) return 0;
        else return 1;
    }

    protected JComboBox stateCombo;

    /**
     * Get accessor for stateCombo
     */
    public JComboBox getStateCombo () {
        return this.stateCombo;
    }

    /**
     * Card accessor for stateCombo
     */
    public int cardStateCombo () {
        if ( this.stateCombo == null ) return 0;
        else return 1;
    }


    public MainView(
        final StateManager stateManager)
    {
    	// create the subCtrl
    	this.subCtrl = new SubCtrl(stateManager);
    	
    	this.button1 = new JButton("Button1");
    	this.button2 = new JButton("Button2");
    	this.stateCombo = new JComboBox();
    	//this.stateCombo.setPreferredSize(new Dimension(200, 80));
    	this.stateCombo.setMaximumSize(new Dimension(150, 40));

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.add(this.button1);
		this.add(this.button2);
		this.add(this.stateCombo);
		this.add(this.subCtrl.getView());
    	
    }
}
