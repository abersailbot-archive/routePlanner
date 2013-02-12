/**
 * 
 */
package gui;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.TileSource;
import org.openstreetmap.gui.jmapviewer.tilesources.OfflineOsmTileSource;

import data.DataSet;

/**
 * @author kamil
 *
 */
public class RoutePlannerFrame extends JFrame {

	public RoutePlannerFrame(DataSet dataSet){
		
		this.setSize(700, 500);
		this.setTitle("RoutePlanner v2");
		
		JTabbedPane tabs = new JTabbedPane();
		
		
		JMapViewer map = new JMapViewer();
		TileSource ts = new OfflineOsmTileSource("file:///media/DATA/programowanie_linux/RoutePlanner2/tiles",15,17);
		
		map.setDisplayPositionByLatLon(52.41156, -4.08975, 15);
		map.addMapMarker(new MapMarkerDot(Color.BLUE, 52.41156, -4.08975));
		map.setTileSource(ts);
		
		this.getContentPane().add(tabs);
		tabs.addTab("Map", map);
		
		TelemetryDataPanel tp = new TelemetryDataPanel(6,2, dataSet);
		tabs.addTab("Telemetry", tp);
		this.setVisible(true);
		
		}
}
