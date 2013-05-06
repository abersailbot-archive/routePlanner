package gui;

import java.awt.Graphics;

import org.openstreetmap.gui.jmapviewer.JMapViewer;

public class BoatIndicators{
	
	JMapViewer map;
	
	public BoatIndicators(JMapViewer map){
		this.map = map;
	}

	public void draw(){
		Graphics g = map.getGraphics();
		
		g.drawRect(10, 10, 50, 100);
	}
}
