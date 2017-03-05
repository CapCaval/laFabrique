package org.capcaval.lafabrique.net.udp._test;

import java.io.InputStream;

import org.capcaval.lafabrique.net.udp.UDPReceiver;
import org.capcaval.lafabrique.net.udp.UDPSender;
import org.junit.Assert;
import org.junit.Test;

public class UDPTest 
{

    @Test
    public void InAndOutTest()
    {
		UDPReceiver udpDataReceiver = new UDPReceiver(1234);
		InputStream is = udpDataReceiver.getInputStream();

		byte[] data = new byte[]{1,2,3,4,5,6,7,8,9,10,11,12};
		byte[] result = new byte[]{2,4,6,8,10,12};
	    byte[] readData = new byte[1];
		
		UDPSender out = new UDPSender("127.0.0.1", 1234);
		
		out.sendData(data);
			
		for(int i = 0; i < data.length/2; i++){
			try {
				// skip all odd values
				is.skip(1);
				is.read(readData);
				// should read only the even values
				Assert.assertEquals(result[i], readData[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		out.close();
		udpDataReceiver.close();
    }
}
