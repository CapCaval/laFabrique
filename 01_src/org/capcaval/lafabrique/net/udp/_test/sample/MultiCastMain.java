package org.capcaval.lafabrique.net.udp._test.sample;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.util.Enumeration;

public class MultiCastMain {

	public static void main(String[] args) {
//	    sudo ifconfig lo multicast
//	    sudo route add -net 224.0.0.0 netmask 240.0.0.0 dev lo
		
		
		// Which port should we listen to
		int port = 4000;
		// Which address
		String group = "225.0.0.0";
		String dataStr = "Hello!!!!";
		
		Thread tb = new Thread(()->{
			try{
			
			NetworkInterface networkInterface = NetworkInterface.getByName("192.168.0.50");
			SocketAddress socketAddress = new InetSocketAddress(group, port);
			
			final MulticastSocket s = new MulticastSocket(port);
			s.joinGroup(socketAddress, networkInterface);
			s.setLoopbackMode(true);
			
			byte[] buf = new byte[512];
			DatagramPacket pack = new DatagramPacket(buf, buf.length);
			
			while (true) {
				System.out.println(">>>Ready to receive multicast packets on " + group + ":"+port);
				// Receive a packet
				s.receive(pack);

				// Packet received
				System.out.println( ">>>Discovery packet received from: "
						+ pack.getAddress().getHostAddress());
				System.out.println( ">>>Packet received; data: "
						+ new String(pack.getData()));
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		});
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					


//				    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
//				    while (interfaces.hasMoreElements()) {
//				        NetworkInterface iface = interfaces.nextElement();
//				        if (iface.isLoopback() || !iface.isUp())
//				            continue;
//
//				        Enumeration<InetAddress> addresses = iface.getInetAddresses();
//				        while(addresses.hasMoreElements()) {
//				            InetAddress addr = addresses.nextElement();
//				            multicastSocket.setInterface(addr);
//				            multicastSocket.send(datagramPacket);
//				        }
//				    }
					
					
					byte[] buf = new byte[512];
					DatagramPacket pack = new DatagramPacket(buf, buf.length);
					
					
					
					while (true) {
						System.out.println(">>>Ready to receive multicast packets on " + group + ":"+port);
						// Receive a packet
						//s.receive(pack);

						// Packet received
						System.out.println(getClass().getName() + ">>>Discovery packet received from: "
								+ pack.getAddress().getHostAddress());
						System.out.println(getClass().getName() + ">>>Packet received; data: "
								+ new String(pack.getData()));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		t.start();

		try {
			Thread.sleep(3000);
			
			MulticastSocket s = new MulticastSocket();
			s.setLoopbackMode(true);
			s.joinGroup(InetAddress.getByName(group));
			
			// Open a random port to send the package

			byte[] sendData = dataStr.getBytes();

			DatagramPacket sendPacket = new DatagramPacket(
					sendData, sendData.length,
					InetAddress.getByName(group), port);
			
		    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		    while (interfaces.hasMoreElements()) {
		        NetworkInterface iface = interfaces.nextElement();
		        if (iface.isLoopback() || !iface.isUp())
		            continue;

		        Enumeration<InetAddress> addresses = iface.getInetAddresses();
		        while(addresses.hasMoreElements()) {
		            InetAddress addr = addresses.nextElement();
		            s.setInterface(addr);
		            s.send(sendPacket);
		            
		            System.out.println(">>> Request packet sent to: " + group + ":" + port + "   from " + addr);
		        }
		    }
			
			
			

//			s.send(sendPacket);

			
			
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
