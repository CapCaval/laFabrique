package org.capcaval.lafabrique.net.udp._test.sample;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Arrays;

import junit.framework.Assert;

public class StackOverflowMultiTestMain {

	public static void main(String[] args) {
	    InetAddress addr;
	    try {
	        addr = InetAddress.getByName("224.0.0.0");
	    } catch (UnknownHostException e) {
	        throw new RuntimeException(e);
	    }

	    int port = 5555;
	    byte[] data = "1234567890".getBytes();
	    final DatagramPacket sendPacket = new DatagramPacket(
	            data,
	            data.length,
	            addr,
	            port
	    );

	    // initialize the server.
	    MulticastSocket s;
	    try {
	        s = new MulticastSocket(port);
	        s.joinGroup(addr);
	        s.setLoopbackMode(true);
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }

	    // send every 100 ms.
	    new Thread(new Runnable() {
	        @Override
	        public void run() {
	            for (int i = 0 ; i < 5; ++i) {
	                try {
	                    System.out.println("Sending");
	                    MulticastSocket ms = new MulticastSocket();
	                    ms.joinGroup(addr);
	                    ms.send(sendPacket);
	                } catch (IOException e) {
	                    throw new RuntimeException(e);
	                }

	                try {
	                    Thread.sleep(100);
	                    
	                } catch (InterruptedException e1) {
	                    e1.printStackTrace();
	                }
	            }
	        }
	    }).start();

	    // receive the packet.
	    DatagramPacket packet = new DatagramPacket(new byte[data.length], data.length);
	    try {
	        System.out.println("Listening");
	        s.receive(packet);
	    } catch (IOException e) {
	        s.close();
	        throw new RuntimeException(e);
	    }

	    s.close();

	    System.out.println("DATA: "+ new String(packet.getData()));
	}

}
