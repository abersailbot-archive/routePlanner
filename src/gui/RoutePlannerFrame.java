/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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

import sun.awt.WindowClosingListener;
import sun.security.action.GetIntegerAction;

import data.DataSet;
import data.Settings;
import data.Waypoints;
import datareceiver.AbstractDataReceiver;
import datareceiver.LogFileReader;
import datareceiver.MockDataReceiver;
import datareceiver.SerialDataReceiver;

/**
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 *
 */
public class RoutePlannerFrame extends JFrame implements WindowListener {

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
	
	AbstractDataReceiver activeDataReceiver;
	LogFileReader logFileReader;
	

	public static RoutePlannerFrame getInstance(){
		if(instance==null) instance = new RoutePlannerFrame();
		return instance;
	}
	
	private RoutePlannerFrame(){
		
		Settings.loadFromFile();
		
		this.setSize(700, 500);
		this.setTitle("RoutePlanner v2");
		MenuBar mb = new MenuBar();
		this.setJMenuBar(mb);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.dataSet = DataSet.getInstance();
		logFileReader = new LogFileReader(dataSet);
		
		map = new JMapViewer();
		String mapFolderName = Settings.getString(Settings.MAP_FOLDER);
		int minZoom = Settings.getInteger(Settings.MAP_MIN_ZOOM);
		int maxZoom = Settings.getInteger(Settings.MAP_MAX_ZOOM);
		File mapFolder = new File(mapFolderName);
		map.setTileSource(new OfflineOsmTileSource("file://"
				+ mapFolder.getAbsolutePath(), minZoom, maxZoom));
		
		//map.setDisplayPositionByLatLon(52.41156, -4.08975, 15); // Aber
		//map.setDisplayPositionByLatLon(52.4008, -3.8713, 15); //llyn-yr-oerfa
		double lat = Settings.getDouble(Settings.MAP_LAT);
		double lon = Settings.getDouble(Settings.MAP_LON);
		map.setDisplayPositionByLatLon(lat, lon, minZoom);
		
		boatMarker = new MapBoatIndicator(lat, lon);
		map.addMapMarker(boatMarker);
		followBoat = Settings.getBoolean(Settings.FOLLOW_ROBOT);
		
		
		String[] ignoredData = {"time"};
		tp = new TelemetryDataPanel(6,2, Arrays.asList(ignoredData));
		
		tabbedPanel = new JTabbedPane();
		tabbedPanel.addTab("Telemetry", tp);
		tabbedPanel.addTab("Waypoints", wpPanel);
		
		sidePanel = new JPanel(new BorderLayout());
		sidePanel.add(tabbedPanel, BorderLayout.CENTER);
		
		controller = new LogReaderPanel(logFileReader);
		sidePanel.add(controller, BorderLayout.SOUTH);
		controller.setVisible(false);
		logFileReader.setController(controller);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(sidePanel, BorderLayout.WEST);
		this.getContentPane().add(map, BorderLayout.CENTER);
		
		map.addMouseListener(this.wpPanel);
		this.setVisible(true);
		this.addWindowListener(this);
		
		}
	
	public void run(){
		try{
		
			
		while(true){
			if(this.activeDataReceiver!=null){
				activeDataReceiver.updateDataSet();
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
		switch(dataSource){
		case "none":
			this.activeDataReceiver = null;
			this.controller.setVisible(false);
			break;
		case "mock":
			this.activeDataReceiver = new MockDataReceiver(dataSet);
			this.controller.setVisible(false);
			break;
		case "serial":
			String portName = Settings.getString(Settings.SERIAL_PORT);
			this.activeDataReceiver = new SerialDataReceiver(dataSet, portName);
			this.controller.setVisible(false);
			break;
		case "logfile":
			this.activeDataReceiver = this.logFileReader;
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

	@Override
	public void windowActivated(WindowEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0){
		Settings.saveToFile();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0){
		// TODO Auto-generated method stub
		
	}

}
