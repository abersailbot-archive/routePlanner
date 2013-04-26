package datareceiver;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import data.DataSet;

/**
 * Receives data string through serial port,
 * using java-simple-serial-connector (https://code.google.com/p/java-simple-serial-connector/).
 * 
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 *
 */
public class SerialDataReceiver extends AbstractDataReceiver implements SerialPortEventListener{
	
	SerialPort serialPort;
	String latestData, buffer;
	
	public SerialDataReceiver(DataSet dataSet, String port){
		super(dataSet);
		
//		  String[] portNames = SerialPortList.getPortNames();
//	        for(int i = 0; i < portNames.length; i++){
//	            System.out.println(portNames[i]);
//	        }
		
		serialPort = new SerialPort(port);
		
        try {
            System.out.println("Port opened: " + serialPort.openPort());
            System.out.println("Params set: " + serialPort.setParams(115200, 8, 1, 0));
            //int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR + SerialPort.MASK_BREAK;
            //serialPort.setEventsMask(mask);
            serialPort.addEventListener(this);
            //System.out.println("Port closed: " + serialPort.closePort());
        }
        catch (SerialPortException ex){
            System.out.println(ex);
        }
	}

	@Override
	public String readDataString() {
//		String dataString = "";
//		  try {
//			    byte buffer[] = serialPort.readBytes();
//			    String s = serialPort.readString();
//			    //String s = buffer!=null ? buffer.toString() : "No data";
//			    System.out.println(s);
//			    return s;
//	        }
//	        catch (SerialPortException ex){
//	            System.out.println(ex);
//	            return "";
//	        }
		return latestData;
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		
		if(event.isRXCHAR()){
			try {
				String newData = serialPort.readString();
				//System.out.println(newData);
				System.out.println("New data : " + newData);
				buffer += newData;
				
				buffer = buffer.replace("\n", "");
				buffer = buffer.replace("\r", "");
				if(buffer.contains("$")){
					System.out.println("Buffer complete!");
					latestData = buffer;
					buffer = "";
				}
				System.out.println("Buffer data : " + buffer);
				System.out.println("Latest data : " + latestData);
			} catch (SerialPortException e) {
				e.printStackTrace();
			}
		}
	}

}
