/**
 * 
 */
package org.openstreetmap.gui.jmapviewer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

/**
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 * @version 1.0 (9 May 2013)
 */
public class MapWaypointMarker implements MapMarker {

    double lat;
    double lon;
    int number;

   public MapWaypointMarker(Coordinate c, int number) {
        super();
        this.lat = c.getLat();
        this.lon = c.getLon();
        this.number = number;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void paint(Graphics g, Point position) {
        int size_h = 10;
        int size = size_h * 2;
        g.setColor(Color.WHITE);
        g.fillOval(position.x - size_h, position.y - size_h, size, size);
        g.setColor(Color.BLACK);
        g.drawOval(position.x - size_h, position.y - size_h, size, size);
        if(number < 10)
        	g.drawString(number+"", position.x - 3, position.y + 4);
        else
        	g.drawString(number+"", position.x - 6, position.y + 4);
    }

    @Override
    public String toString() {
        return "MapMarker at " + lat + " " + lon;
    }

}