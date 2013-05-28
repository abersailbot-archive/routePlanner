/**
 * 
 */
package datareceiver;

import java.util.Date;
import java.util.Random;

import data.DataSet;

/**
 * Imitates receiving data strings, for testing purposes,
 * to check if program reacts correctly to incoming data.
 * 
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 *
 */
public class MockDataReceiver extends AbstractDataReceiver {

	double fakeLat = 52.41156;
	double fakeLon = -4.08975;
	int fakeWind = 0;
	int fakeHeading = 0;
	
	/**
	 * @param dataSet
	 */
	public MockDataReceiver(DataSet dataSet) {
		super(dataSet);
	}

	/**
	 * @see datareceiver.AbstractDataReceiver#readDataString()
	 */
	@Override
	protected String readDataString() {
		Random gen = new Random();
		Date d = new Date();
		
		fakeLat+=0.00005;
		fakeLon-=0.00005;
		
		StringBuilder sb = new StringBuilder();
		sb.append("spd=").append(gen.nextInt(100)+1)
		.append(" bhead=").append(fakeHeading)
		.append(" thead=").append(gen.nextInt(100)+1)
		.append(" wind=").append(fakeWind)
		.append(" spos=").append(gen.nextInt(100)+1)
		.append(" rpos=").append(gen.nextInt(100)+1)
		.append(" time=").append(d.getTime())
		.append(" lat=").append(fakeLat)
		.append(" lon=").append(fakeLon);
		
		fakeWind += 10;
		fakeHeading += 5;
		
		return sb.toString();
	}

}
