/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Point;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapBoatIndicator;
import org.openstreetmap.gui.jmapviewer.interfaces.TileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OfflineOsmTileSource;

import data.DataSet;
import data.Waypoints;
import datareceiver.AbstractDataReceiver;
import datareceiver.LogFileReader;
import datareceiver.MockDataReceiver;
import datareceiver.SerialDataReceiver;

/**
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 *
 */
public class RoutePlannerFrame extends JFrame {

	private static RoutePlannerFrame instance;
	
	JMapViewer map;
	TelemetryDataPanel tp;
	JTabbedPane tabbedPanel;
	JPanel sidePanel;
	LogReaderPanel controller;
	DataSet dataSet;
	
	MapBoatIndicator boatMarker;
	Waypoints waypoints = new Waypoints();
	WaypointsPanel wpPanel = new WaypointsPanel(waypoints);
	
	boolean followBoat;
	
	AbstractDataReceiver dr;
	LogFileReader lfr;
	

	public static RoutePlannerFrame getInstance(){
		if(instance==null) instance = new RoutePlannerFrame();
		return instance;
	}
	
	private RoutePlannerFrame(){
		
		this.setSize(700, 500);
		this.setTitle("RoutePlanner v2");
		MenuBar mb = new MenuBar();
		this.setJMenuBar(mb);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.dataSet = DataSet.getInstance();
		//this.dr = new MockDataReceiver(dataSet);
		//SerialDataReceiver test = new SerialDataReceiver(dataSet, "/dev/ttyUSB0");
		lfr = new LogFileReader(dataSet);
		//this.dr= lfr;
		
		map = new JMapViewer();
		File mapFolder = new File("./llyn-yr-oerfa");
		//File mapFolder = new File("./tiles");
		TileSource ts = new OfflineOsmTileSource("file://"+mapFolder.getAbsolutePath(),15,17);
		
		//map.setDisplayPositionByLatLon(52.41156, -4.08975, 15); // Aber
		map.setDisplayPositionByLatLon(52.4008, -3.8713, 15); //llyn-yr-oerfa
		boatMarker = new MapBoatIndicator(52.4008, -3.8713);
		map.addMapMarker(boatMarker);
		map.setTileSource(ts);
		
		String[] ignoredData = {"time"};
		tp = new TelemetryDataPanel(6,2, Arrays.asList(ignoredData));
		
		tabbedPanel = new JTabbedPane();
		tabbedPanel.addTab("Telemetry", tp);
		tabbedPanel.addTab("Waypoints", wpPanel);
		
		sidePanel = new JPanel(new BorderLayout());
		sidePanel.add(tabbedPanel, BorderLayout.CENTER);
		
		controller = new LogReaderPanel(lfr);
		sidePanel.add(controller, BorderLayout.SOUTH);
		controller.setVisible(false);
		lfr.setController(controller);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(sidePanel, BorderLayout.WEST);
		this.getContentPane().add(map, BorderLayout.CENTER);
		
		map.addMouseListener(this.wpPanel);
		map.addKeyListener(wpPanel);
		this.addKeyListener(wpPanel);
		sidePanel.addKeyListener(wpPanel);
		this.setVisible(true);
		
		}
	
	public void run(){
		try{
		
			
		while(true){
			if(this.dr!=null){
				dr.updateDataSet();
				tp.updatePanel();
				this.updateBoatPosition();
				this.invalidate();
			}
			Thread.sleep(1000);			
		}
		
		
		}catch(InterruptedException ex){
			ex.printStackTrace();
		}
	}
	
	public void setDataSource(String dataSource){
		if(dataSource.equals("mock")){
			this.dr = new MockDataReceiver(dataSet);
			this.controller.setVisible(false);
		}else if(dataSource.equals("serial")){
			this.dr = new SerialDataReceiver(dataSet, "/dev/ttyUSB0");
			this.controller.setVisible(false);
		}else if(dataSource.equals("logfile")){
			this.dr = this.lfr;
			//lfr.setController(this.controller);
			//this.controller.setReader(lfr);
			this.controller.refresh();
			this.controller.setVisible(true);
		}
	}
	
	private void updateBoatPosition(){
		try{
			boatMarker.update();
			if(followBoat) map.setDisplayPositionByLatLon(
					boatMarker.getLat(), boatMarker.getLon(), map.getZoom());
			map.repaint();
		}catch(Exception ex){
			//ex.printStackTrace();
		}
	}

	public boolean isFollowBoat(){
		return followBoat;
	}

	public void setFollowBoat(boolean followBoat){
		this.followBoat = followBoat;
	}

	public JMapViewer getMap(){
		return map;
	}
}
