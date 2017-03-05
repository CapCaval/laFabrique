package org.capcaval.lafabrique.net.udp._test.sample;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BroadCastMain {

	public static void main(String[] args) {

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					DatagramSocket socket;
					socket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
					socket.setBroadcast(true);
					
					while (true) {
						System.out.println(getClass().getName() + ">>>Ready to receive broadcast packets!");
						// Receive a packet
						byte[] recvBuf = new byte[15];
						DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
						socket.receive(packet);

						// Packet received
						System.out.println(getClass().getName() + ">>>Discovery packet received from: "
								+ packet.getAddress().getHostAddress());
						System.out.println(getClass().getName() + ">>>Packet received; data: "
								+ new String(packet.getData()));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		t.start();

		try {
			Thread.currentThread().sleep(3000);
			
			
			// Open a random port to send the package
			DatagramSocket c = new DatagramSocket();
			c.setBroadcast(true);

			byte[] sendData = "DISCOVER_FUIFSERVER_REQUEST".getBytes();
			// Try the 255.255.255.255 first

			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
					InetAddress.getByName("255.255.255.255"), 8888);
			c.send(sendPacket);

			System.out.println(">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
