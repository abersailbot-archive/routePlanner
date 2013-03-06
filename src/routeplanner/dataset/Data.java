/**
 * 
 */
package routeplanner.dataset;

/**
 * @author kamil
 *
 */
public class Data {
	private String key;
	private String label;
	private Number value;
	
	public Data(String key, String label){
		this.key = key;
		this.label = label;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
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
