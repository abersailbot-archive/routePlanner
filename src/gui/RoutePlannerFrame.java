/**
 * 
 */
package gui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.TileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OfflineOsmTileSource;

import data.DataSet;
import datareceiver.AbstractDataReceiver;
import datareceiver.UdpDataReceiver;

/**
 * @author kamil
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
		this.dataSet = dataSet;
		this.dr = new UdpDataReceiver(dataSet);
		
		JTabbedPane tabs = new JTabbedPane();
		
		
		map = new JMapViewer();
		TileSource ts = new OfflineOsmTileSource("file:///media/DATA/programowanie_linux/RoutePlanner2/tiles",15,17);
		
		map.setDisplayPositionByLatLon(52.41156, -4.08975, 15);
		boatMarker = new MapMarkerDot(Color.RED, 52.41156, -4.08975);
		map.addMapMarker(boatMarker);
		map.setTileSource(ts);
		
		this.getContentPane().add(tabs);
		tabs.addTab("Map", map);
		
		tp = new TelemetryDataPanel(6,2, dataSet);
		tabs.addTab("Telemetry", tp);
		this.setVisible(true);
		
		}
	
	public void run(){
		try{
		
			
		while(true){
			dr.updateDataSet();
			tp.updateCells();
			this.updateBoatPosition();
			this.invalidate();
			Thread.sleep(1000);
		}
		
		
		}catch(InterruptedException ex){
			ex.printStackTrace();
		}
	}
	
	private void updateBoatPosition(){
		Double lat = (Double) dataSet.getValueByKey("lat");
		Double lon = (Double) dataSet.getValueByKey("lon");
		//TODO Find a better way of doing that
		map.removeMapMarker(boatMarker);
		boatMarker = new MapMarkerDot(Color.RED, lat, lon);
		map.addMapMarker(boatMarker);
	}
}
