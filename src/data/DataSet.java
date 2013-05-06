/**
 * 
 */
package data;

import java.util.ArrayList;

/**
 * @author kamil
 *
 */
public class DataSet {

	private static DataSet instance;
	
	private ArrayList<DataCell> dataSet;
	
	
	private DataSet(){
		dataSet = new ArrayList<DataCell>();
	}
	
	public static DataSet getInstance(){
		if(instance==null) instance = new DataSet();
		return instance;
	}
	
	public DataSet addDataCell(DataCell cell){
		dataSet.add(cell);
		return this;
	}
	
	public DataSet addDataCell(String id, String label){
		DataCell cell = new DataCell(id, label);
		dataSet.add(cell);
		return this;
	}

	public ArrayList<DataCell> getDataSet() {
		return dataSet;
	}

	public void setDataSet(ArrayList<DataCell> dataSet) {
		this.dataSet = dataSet;
	}
	
	public void updateDataCell(String key, Number value){
		for(DataCell item : dataSet){
			if(item.getId().equals(key)){
				item.setValue(value);
				return;
			}
		}
		System.out.println("Cannot update data : dataset does not contain ("+key+")");
	}
	
	public Number getValueByKey(String key){
		for(DataCell item : dataSet){
			if(item.getId().equals(key)){
				return item.getValue();
			}
		}
		System.out.println("Cannot find value : dataset does not contain ("+key+")");
		return 0;
	}
	
	
	
}
