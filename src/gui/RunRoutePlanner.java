package gui;

import data.DataSet;

public class RunRoutePlanner {

	public static void main(String args[]){
		
		DataSet set = DataSet.getInstance();
		
		set.addDataCell("lat", "Lattitude")
		.addDataCell("lon", "Longitiude")
		.addDataCell("spd", "Speed")
		.addDataCell("bhead", "Boat heading")
		.addDataCell("thead", "Target heading")
		.addDataCell("whead", "Waypoint heading")
		.addDataCell("wind", "Wind direction")
		.addDataCell("spos", "Sail position")
		.addDataCell("rpos", "Rudder position")
		.addDataCell("nwn", "Next waypoint number")
		.addDataCell("nwlat", "Next WP lattitude")
		.addDataCell("nwlon", "Next WP longitiude")
		.addDataCell("time", "Timestamp");
		
		
		RoutePlannerFrame frame = RoutePlannerFrame.getInstance();
		frame.run();
	}
	
}
