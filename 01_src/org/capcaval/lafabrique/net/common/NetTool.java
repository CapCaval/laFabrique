package org.capcaval.lafabrique.net.common;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class NetTool {

	public static String getMacAddress() {
		String macStr = null;
		
		try {
			byte[] mac = NetworkInterface.getNetworkInterfaces().nextElement().getHardwareAddress();
			
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));
			}
			macStr = sb.toString();
		} catch (Exception e) {
			// re throw
			throw new RuntimeException("Error : unable to get MAC address", e);
		}

		return macStr;
	}

	public static String getIpAddress() {
		String ipAddressStr = null;

		try {
			InetAddress ip = getInetAddress();
			ipAddressStr = ip.getHostAddress();
		} catch (Exception e) {
			// re throw as runtime
			throw new RuntimeException("Error to get IP address", e);
		}
		return ipAddressStr;
	}

	public static InetAddress getInetAddress() {
		InetAddress inetAddress = null;

		try {
			inetAddress = InetAddress.getLocalHost();
		} catch (Exception e) {
			// re throw as runtime
			throw new RuntimeException("Unable to get IP address", e);
		}
		return inetAddress;
	}

}
