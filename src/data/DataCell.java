package data;
/**
 * 
 */

/**
 * @author kamil
 *
 */
public class DataCell {

	private String id;
	private String label;
	private Number value;
	
	
	/**
	 * 
	 */
	public DataCell(String id, String label) {
		this.id = id;
		this.label = label;
		this.value = 0;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public Number getValue() {
		return value;
	}


	public void setValue(Number value) {
		this.value = value;
	}

	
}
