package org.capcaval.lafabrique.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSender 
{
    protected DatagramSocket senderSocket;
    protected DatagramPacket sendPacket;


    public UDPSender(
        final String ipAddress,
        final int portNumber)
    {
    	this.init(ipAddress, portNumber, 64000); // 64 k is the maximum UDP size 
    }

    public void init(
        final String ipAddress,
        final int portNumber,
        final int bufferSize)
    {
		byte[] data = new byte[bufferSize];
		try {		
			this.senderSocket= new DatagramSocket();
			this.sendPacket = new DatagramPacket(data , data.length, InetAddress.getByName(ipAddress), portNumber);
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
