package gui;

import data.DataSet;

public class RunRoutePlanner {

	public static void main(String args[]){
		DataSet set = new DataSet();
		set.addDataCell("lat", "Lattitude")
		.addDataCell("lon", "Longitiude")
		.addDataCell("spd", "Speed")
		.addDataCell("bhead", "Boat heading")
		.addDataCell("thead", "Waypoint heading")
		.addDataCell("wind", "Wind direction")
		.addDataCell("spos", "Sail position")
		.addDataCell("rpos", "Rudder position")
		.addDataCell("nwn", "Next waypoint number")
		.addDataCell("nwlat", "Next waypoint lattitude")
		.addDataCell("nwlong", "Next waypoint longitiude")
		.addDataCell("time", "Timestamp");
		
		
		RoutePlannerFrame frame = new RoutePlannerFrame(set);
	}
	
}
