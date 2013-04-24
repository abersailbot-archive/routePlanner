package datareceiver;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;
import data.DataSet;

/**
 * Receives data string through serial port,
 * using java-simple-serial-connector (https://code.google.com/p/java-simple-serial-connector/).
 * 
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 *
 */
public class SerialDataReceiver extends AbstractDataReceiver{
	
	SerialPort serialPort;
	
	public SerialDataReceiver(DataSet dataSet, String port){
		super(dataSet);
		
		  String[] portNames = SerialPortList.getPortNames();
	        for(int i = 0; i < portNames.length; i++){
	            System.out.println(portNames[i]);
	        }
		
		serialPort = new SerialPort(port);
		
        try {
            System.out.println("Port opened: " + serialPort.openPort());
            System.out.println("Params setted: " + serialPort.setParams(9600, 8, 1, 0));
            //System.out.println("Port closed: " + serialPort.closePort());
        }
        catch (SerialPortException ex){
            System.out.println(ex);
        }
	}

	@Override
	public String readDataString() {
		String dataString = "";
		  try {
			    byte buffer[] = serialPort.readBytes(serialPort.getInputBufferBytesCount());
			    String s = buffer.toString();
			    System.out.println(s);
			    return s;
	        }
	        catch (SerialPortException ex){
	            System.out.println(ex);
	            return "";
	        }
	}

}
