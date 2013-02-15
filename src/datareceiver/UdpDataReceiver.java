/**
 * 
 */
package datareceiver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import data.DataSet;

/**
 * Receives data string in UDP packet, broadcast by the remote device on port 4321
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 *
 */
public class UdpDataReceiver extends AbstractDataReceiver {

	DatagramSocket ds;
	
	public UdpDataReceiver(DataSet dataSet) {
		super(dataSet);
		try{
			ds = new DatagramSocket(4321);
	        ds.setBroadcast(true);
		}catch(SocketException ex){
			ex.printStackTrace();
		}
		
	}

	/**
	 * Reads the UDP packet
	 * @see datareceiver.AbstractDataReceiver#readDataString()
	 */
	@Override
	protected String readDataString() {
		
		try{
			
	        byte buffer[] = new byte[4096];
	        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
	        ds.receive(packet);
	        return new String(buffer, 0, buffer.length);
	        
		}catch(IOException ex){
			ex.printStackTrace();
			return null;
		}
				
	}

}
