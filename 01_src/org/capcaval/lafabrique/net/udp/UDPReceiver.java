package org.capcaval.lafabrique.net.udp;

import java.net.SocketException;
import java.net.DatagramPacket;
import java.io.InputStream;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.io.PipedInputStream;
import java.net.DatagramSocket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.lang.Thread;

public class UDPReceiver 
{
    protected PipedInputStream reader;

    protected PipedInputStream writer;

    protected DatagramSocket serverSocket;

    protected AtomicBoolean isOver;

    protected byte[] readData = null;

    /**
     * Get accessor for readData
     * @return  value of readData
     */
    public byte[] getReadData () {
        return this.readData;
    }

    protected DatagramPacket receivedPacket;


    public UDPReceiver(
        final int portNumber)
    {
    	// allocate the state boolean
    	this.isOver = new AtomicBoolean(false);
    	
    	// allocate the receiver buffer
    	this.readData = new byte[4096];
    	
		// Initialize the two streams
		this.reader = new PipedInputStream();
		
		// create the socket
		try {
			this.serverSocket = new DatagramSocket(portNumber);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		this.receivedPacket = new DatagramPacket(this.readData, this.readData.length);
		
		// allocate 
		Thread t = this.createUdpThread(this.serverSocket,
				this.readData,
				this.isOver,
				"UDP reader");
		// run it
		t.start();
    }

    public InputStream getInputStream()
    {
    	return this.reader;
    }

    public void close()
    {
		// first change the timeout for a quicker exception
		this.isOver.set(true);
    	this.serverSocket.close();
    }

    public Thread createUdpThread(
        final DatagramSocket serverSocket,
        final byte[] receiverBuff,
        final AtomicBoolean isOver,
        final String name)
    {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				PipedOutputStream writer = null;
				try {
					writer = new PipedOutputStream(UDPReceiver.this.reader);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				// Run until it is over
				while(isOver.get() == false){
					try {
						// get the upd's data
						serverSocket.receive(receivedPacket);
						byte[] data = receivedPacket.getData();
						
						int length = receivedPacket.getLength();
						
						// write them inside the stream
						writer.write(data,0,length);

						// give the hand to the OS if needed
						Thread.yield();
					} catch (Exception e) {
						if(isOver.get() == false){
							e.printStackTrace();}
						// else that means that the socket is closing
						// therefore the exception is normal and do not display it
					}
				}
			}
		}, name);
		return t;
    }
}
