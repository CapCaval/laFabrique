package org.capcaval.ermine.coves.statemachine;

import org.capcaval.ermine.coves.statemachine._impl.StateManagerFactoryImpl;


public interface StateManager 
{
    public static final StateManagerFactory factory = new StateManagerFactoryImpl();


     void add(
        final GadgetStateHandler<?> gsh);

     <T extends Enum<T>>void update(
        final T state);
}
