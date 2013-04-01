package org.capcaval.ccoutils.unittest;



import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

/**
  * Class to help in unit test.
  */
public class TestTools 
{

    /**
      * bBlocking methods in order to wait for instance and event from another thread. The Atomic boolean can be set to false until an event is raised.
      * 
      * 
      * @param timeOut time in millisec for the time out
      * @param bool The value to check out  when its value is equal to true
      * 
      * @return  return true if the value has been set to true
      * return false if the time out has been raised
      */
    public static boolean waitUntilTrue(
        final int timeOut,
        final AtomicBoolean bool)
    {
// objecteering-start..........................................T/2KI3/S0UV7M:POW
    	Date startTime = new Date();
    	
    	boolean over = false;
    	boolean returnBool = false;
    	
    	// wait until the value is true or that the time out is over
    	while( over == false){
    		if( bool.get() == true){
    			over =true;
    			returnBool = true;
    		}
    		else{
    			// check out if the time out is done
    			Date nowTime = new Date();
    			long timeEllapse = nowTime.getTime() - startTime.getTime();
    		
    			if(timeEllapse >= timeOut)
    				over = true;}
    	}
// objecteering-end............................................E/2KI3/S0UV7M:POW
// objecteering-start..........................................T/4KI3/S0UV7M:0PW
    	return returnBool;
// objecteering-end............................................E/4KI3/S0UV7M:0PW
    }
}
