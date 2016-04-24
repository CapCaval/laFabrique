package org.capcaval.ermine.coves.statemachine._test.j2d;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SubView 
    extends JPanel
{
    protected JButton plusBtn = new JButton("+");

    /**
     * Get accessor for plusBtn
     */
    public JButton getPlusBtn () {
        return this.plusBtn;
    }

    /**
     * Card accessor for plusBtn
     */
    public int cardPlusBtn () {
        if ( this.plusBtn == null ) return 0;
        else return 1;
    }

    protected JButton minusBtn = new JButton("+");

    /**
     * Get accessor for minusBtn
     */
    public JButton getMinusBtn () {
        return this.minusBtn;
    }

    /**
     * Card accessor for minusBtn
     */
    public int cardMinusBtn () {
        if ( this.minusBtn == null ) return 0;
        else return 1;
    }


    public SubView()
    {
    	this.plusBtn = new JButton("+");
    	this.minusBtn = new JButton("-");

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		this.add(this.plusBtn);
		this.add(this.minusBtn);
    }
}
