package org.capcaval.lafabrique.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;

public class UDPMultiCastSender 
{
    protected MulticastSocket senderSocket;
    protected DatagramPacket sendPacket;


    public UDPMultiCastSender(
        final String groupAdress,
        final int portNumber)
    {
    	this.init(groupAdress, portNumber, 64000); // 64 k is the maximum UDP size 
    }

    public void init(
        final String groupAdress,
        final int portNumber,
        final int bufferSize)
    {
    	
    	
    	
		byte[] data = new byte[bufferSize];
		try {		
			InetAddress add = InetAddress.getByName(groupAdress);
			
			if(add.isMulticastAddress()==false){
				System.err.println("Error is not valid multicast " + add);
			}
			
			this.senderSocket= new MulticastSocket();
			this.senderSocket.joinGroup(add);
			
			NetworkInterface networkInterface = NetworkInterface.getByName("192.168.0.50");
			SocketAddress socketAddress = new InetSocketAddress(groupAdress, portNumber);
			this.senderSocket.joinGroup(socketAddress, networkInterface);

			this.sendPacket = new DatagramPacket(data , data.length, InetAddress.getByName(groupAdress), portNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void sendData(
        final byte[] dataArray)
    {
		this.sendPacket.setData(dataArray, 0, dataArray.length);
		try {
			this.senderSocket.send(this.sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void close()
    {
    	this.senderSocket.close();
    }
}
