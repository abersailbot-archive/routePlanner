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

	private ArrayList<DataCell> dataSet;
	
	
	public DataSet(){
		dataSet = new ArrayList<DataCell>();
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
		System.out.println("Cannot update data : key does not exist ("+key+")");
	}
	
	
	
}
