package org.capcaval.lafabrique.net.udp._test;

import java.io.InputStream;

import org.capcaval.lafabrique.net.udp.UDPMultiCastReceiver;
import org.capcaval.lafabrique.net.udp.UDPMultiCastSender;
import org.junit.Assert;
import org.junit.Test;

public class UDPMultiCastTest 
{

    @Test
    public void InAndOutTest()
    {
    	String groupAddress = "224.0.0.3";
    	int port = 8888;
    	
    	UDPMultiCastReceiver udpDataReceiver = new UDPMultiCastReceiver(groupAddress, port);
		InputStream is = udpDataReceiver.getInputStream();

		byte[] data = new String("1234567980").getBytes();
	    byte[] readData = new byte[1];
		
	    UDPMultiCastSender sender = new UDPMultiCastSender("224.0.0.4", port);
		sender.sendData(data);
			
		for(int i = 0; i < data.length; i++){
			try {
				is.read(readData);
				// should read only the even values
				Assert.assertEquals(data[i], readData[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		sender.close();
		udpDataReceiver.close();
    }
}
