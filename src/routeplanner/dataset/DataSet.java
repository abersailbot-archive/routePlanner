package routeplanner.dataset;

import java.util.ArrayList;

public class DataSet {

	private ArrayList<Data> dataSet;
	
	public DataSet addData(String key, String label){
		dataSet.add(new Data(key,label));
		return this;
	}
}
