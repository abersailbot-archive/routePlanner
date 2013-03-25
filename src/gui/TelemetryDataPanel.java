/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data.DataCell;
import data.DataSet;

/**
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 *
 */
public class TelemetryDataPanel extends JPanel {

	//Pointer to dataset that will always contain fresh data
	DataSet dataSet;
	
	JPanel dataPanel, bottomPanel;
	JLabel lastUpdate;
	
	ArrayList<DataCellPanel> cells = new ArrayList<DataCellPanel>();
	
	
	/**
	 * Constructor for the TelemetryDataPanel. 
	 * As telemetry data is displayed here in a grid layout, 
	 * it takes numbers of rows and column we expect for this layout.
	 * Also allows to ignore some data (e. g. when we will display it in a non-standard way).
	 * @param rows number of rows
	 * @param cols number of columns 
	 * @param dataSet source DataSet for the panel data
	 * @param ignored keys to be omitted
	 */
	public TelemetryDataPanel(int rows, int cols, DataSet dataSet, Collection<String> ignored) {
		super(new BorderLayout());
		this.dataSet = dataSet;
		dataPanel = new JPanel();
		bottomPanel = new JPanel();
		lastUpdate = new JLabel("Panel created");
		dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
	
		for(DataCell dataCell : dataSet.getDataSet()){
			if(!ignored.contains(dataCell.getId()))
			cells.add(new DataCellPanel(dataCell));
		}
		
		for(DataCellPanel cell : cells){
			dataPanel.add(cell);
		}
		
		this.add(dataPanel, BorderLayout.NORTH);
		
		bottomPanel.add(lastUpdate);
		this.add(bottomPanel);
		
		this.setVisible(true);
		
	}
	
	
	public ArrayList<DataCellPanel> getCells() {
		return cells;
	}
	public void setCells(ArrayList<DataCellPanel> cells) {
		this.cells = cells;
	}

	public DataCellPanel getCellPanelById(String id){
		for(DataCellPanel cp : cells){
			if(cp.getId().equals(id)) return cp;
		}
		return null;
	}
	
	/**
	 * Updates all data labels with values from dataSet
	 */
	public void updateCells(){
		for(DataCellPanel cell : cells){
			/*
			 * That really is awful, but basically it gets value from the dataSet
			 * (by the id of the DataCellPanel) and changes the label of the cell.
			 */
			cell.changeValue(dataSet.getValueByKey(cell.getId()));
		}
		this.invalidate();
		this.repaint();
	}

	public void updatePanel(){
		this.updateCells();
		
		Number timestamp = dataSet.getValueByKey("time");
		
		Date now = new Date();
		double secDiff = (now.getTime() - (Long) timestamp ) * 0.001;
		
		lastUpdate.setText(secDiff < 1 ? "Updated less than a second ago." : "Updated " + secDiff + " seconds ago.");
		
	}
}
