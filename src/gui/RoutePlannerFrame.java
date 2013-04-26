/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.TileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OfflineOsmTileSource;

import data.DataSet;
import datareceiver.AbstractDataReceiver;
import datareceiver.MockDataReceiver;
import datareceiver.SerialDataReceiver;

/**
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 *
 */
public class RoutePlannerFrame extends JFrame {

	JMapViewer map;
	TelemetryDataPanel tp;
	DataSet dataSet;
	AbstractDataReceiver dr;
	MapMarkerDot boatMarker;
	
	
public RoutePlannerFrame(DataSet dataSet){
		
		this.setSize(700, 500);
		this.setTitle("RoutePlanner v2");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.dataSet = dataSet;
		this.dr = new MockDataReceiver(dataSet);
		SerialDataReceiver test = new SerialDataReceiver(dataSet, "/dev/ttyUSB0");
		this.dr= test;
		
		map = new JMapViewer();
		File mapFolder = new File("./llyn-yr-oerfa");
		//File mapFolder = new File("./tiles");
		TileSource ts = new OfflineOsmTileSource("file://"+mapFolder.getAbsolutePath(),15,17);
		
		//map.setDisplayPositionByLatLon(52.41156, -4.08975, 15); // Aber
		map.setDisplayPositionByLatLon(52.4008, -3.8713, 15); //llyn-yr-oerfa
		boatMarker = new MapMarkerDot(Color.RED, 52.4008, -3.8713);
		map.addMapMarker(boatMarker);
		map.setTileSource(ts);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new EmptyBorder(5,5,5,5));
		
		
		String[] ignoredData = {"time"};
		tp = new TelemetryDataPanel(6,2, dataSet, Arrays.asList(ignoredData));
		leftPanel.add(tp);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(leftPanel, BorderLayout.WEST);
		this.getContentPane().add(map, BorderLayout.CENTER);

		
		this.setVisible(true);
		
		}
	
	public void run(){
		try{
		
			
		while(true){
			dr.updateDataSet();
			tp.updatePanel();
			this.updateBoatPosition();
			this.invalidate();
			Thread.sleep(1000);
		}
		
		
		}catch(InterruptedException ex){
			ex.printStackTrace();
		}
	}
	
	private void updateBoatPosition(){
		try{
		Double lat = (Double) dataSet.getValueByKey("lat");
		Double lon = (Double) dataSet.getValueByKey("lon");
		//TODO Find a better way of doing that
		map.removeMapMarker(boatMarker);
		boatMarker = new MapMarkerDot(Color.RED, lat, lon);
		map.addMapMarker(boatMarker);
		map.setDisplayPositionByLatLon(lat, lon, map.getZoom()); // Aber
		}catch(Exception ex){
			//ex.printStackTrace();
		}
	}
}
