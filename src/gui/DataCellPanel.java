/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;

import data.DataCell;

/**
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 *
 */
public class DataCellPanel extends JPanel {

	JLabel label;
	JLabel value;
	String id;
	
	/**
	 * 
	 */
	public DataCellPanel(DataCell src) {

		label = new JLabel(src.getLabel());
		value = new JLabel(src.getValue().toString());
		id = src.getId();
		this.setLayout(new BorderLayout(10, 10));
		this.add(label, BorderLayout.CENTER);
		this.add(value, BorderLayout.EAST);
//		this.setMaximumSize(new Dimension(40,200));
		this.setVisible(true);
	}
	public void changeValue(Number n){
		this.value.setText(n.toString());
	}
	public JLabel getLabel() {
		return label;
	}
	public void setLabel(JLabel label) {
		this.label = label;
	}
	public JLabel getValue() {
		return value;
	}
	public void setValue(JLabel value) {
		this.value = value;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	

	

}
