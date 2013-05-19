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
 * Every string sent by remote device needs to be ended with a '$' sign
 * (therefore '$' must not be contained inside the string).
 * 
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 *
 */
public class SerialDataReceiver extends AbstractDataReceiver implements SerialPortEventListener{
	
	SerialPort serialPort;
	String latestData, buffer;
	
	public SerialDataReceiver(DataSet dataSet, String port){
		super(dataSet);
		
		serialPort = new SerialPort(port);
		
        try {
            serialPort.openPort();
            serialPort.setParams(115200, 8, 1, 0);
            serialPort.addEventListener(this);
        }
        catch (SerialPortException ex){
            System.out.println(ex);
        }
	}

	@Override
	public String readDataString() {
		return latestData;
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		
		if(event.isRXCHAR()){
			try {
				String newData = serialPort.readString();
				buffer += newData;
				
				buffer = buffer.replace("\n", "");
				buffer = buffer.replace("\r", "");
				if(buffer.contains("$")){
					latestData = buffer;
					buffer = "";
				}
			} catch (SerialPortException e) {
				e.printStackTrace();
			}
		}
	}

}
