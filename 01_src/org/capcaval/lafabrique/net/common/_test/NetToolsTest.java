package org.capcaval.lafabrique.net.common._test;

import junit.framework.Assert;

import org.capcaval.lafabrique.lang.StringTools;
import org.capcaval.lafabrique.net.common.NetTool;
import org.junit.Test;

public class NetToolsTest {

	@Test
	public void getMacAdressTest(){
		String macAdress = NetTool.getMacAddress();
		
		boolean isMacAddressFormat = StringTools.isExclusiveMadeOf(macAdress, "0123456789ABCDEF:");
		Assert.assertTrue(isMacAddressFormat);
	}

	@Test
	public void getIpAdressTest(){
		String ipAdress = NetTool.getIpAddress();
		
		boolean isIpAddressFormat = StringTools.isExclusiveMadeOf(ipAdress, "0123456789.");
		Assert.assertTrue(isIpAddressFormat);
	}
	
}
