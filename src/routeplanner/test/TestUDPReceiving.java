/**
 * 
 */
package routeplanner.test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Tries to receive UDP package and prints it.
 * 
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 *
 */
public class TestUDPReceiving {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			for(;;){
				DatagramSocket ds = new DatagramSocket(4321);
		        ds.setBroadcast(true);
		        byte buffer[] = new byte[4096];
		        DatagramPacket p = new DatagramPacket(buffer,buffer.length);
		        ds.receive(p);
		        System.out.println(buffer);
		        Thread.sleep(100);
			}
		}catch(Exception ex){
			System.out.println(ex);
		}
	}

}
