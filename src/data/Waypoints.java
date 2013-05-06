/**
 * 
 */
package data;

import java.awt.Point;
import java.util.LinkedList;

/**
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 * @version 1.0 (6 May 2013)
 */
public class Waypoints{
	
	LinkedList<Point> points;
	
	public Waypoints(){
		points = new LinkedList<Point>();
	}

	public void add(Point waypoint){
		this.points.add(waypoint);
	}

	public LinkedList<Point> getPoints(){
		return points;
	}

	public void setPoints(LinkedList<Point> points){
		this.points = points;
	}
	
	
}
