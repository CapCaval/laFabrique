package org.capcaval.ermine.coves;

public interface Controller <T>{
	T getView();
	
	void init();
}