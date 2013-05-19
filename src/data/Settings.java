/**
 * 
 */
package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Holds settings for the whole program, each as String.
 * Allows to load/save setting from/to file.
 * 
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 * @version 1.0 (19 May 2013)
 */
public class Settings{
	
	public static final String MAP_LAT = "mapLattitude";
	public static final String MAP_LON = "mapLongitude";
	public static final String MAP_FOLDER = "mapFolder";
	public static final String MAP_MAX_ZOOM = "mapMaxZoomLevel";
	public static final String MAP_MIN_ZOOM = "mapMinZoomLevel";
	public static final String FOLLOW_ROBOT = "followRobot";
	public static final String SERIAL_PORT = "serialPortName";
	
	private static final String SETTINGS_FILE_NAME = "settings.txt";
	private static Properties props = new Properties();	
	
	public static String getString(String key){
		return props.getProperty(key);
	}
	
	public static Double getDouble(String key){
		try{
			String property = props.getProperty(key);
			return property==null ? null : new Double(property);
		}catch(NumberFormatException nfex){
			System.out.println(key + " is not a double value!!!");
			return 0.0;
		}
	}
	
	public static Integer getInteger(String key){
		try{
			String property = props.getProperty(key);
			return property==null ? null : new Integer(property);
		}catch(NumberFormatException nfex){
			System.out.println(key + " is not an integer value!!!");
			return 0;
		}
	}
	
	public static boolean getBoolean(String key){
			String property = props.getProperty(key);
			return property==null ? false : new Boolean(property);
	}
	
	public static void set(String key, Object value){
		props.setProperty(key, value.toString());
	}
	
	public static boolean loadFromFile(){
		try{
			File f = new File(SETTINGS_FILE_NAME);
			if(f.exists()){
				InputStream input = new FileInputStream(f);
				props.load(input);
				input.close();
			}else{
				setDefaults();
			}
		}catch(IOException ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean saveToFile(){
		OutputStream output;
		try{
			File f = new File(SETTINGS_FILE_NAME);
			f.createNewFile();
			output = new FileOutputStream(f);
			props.store(output, "Settings");
			output.close();
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	private static void setDefaults(){
		set(MAP_FOLDER, "map");
		set(MAP_MAX_ZOOM, "18");
		set(MAP_MIN_ZOOM, "15");
		set(MAP_LAT, "52.41156"); //Aberystwyth
		set(MAP_LON, "-4.08975");
		set(FOLLOW_ROBOT, "false");
		set(SERIAL_PORT, "/dev/ttyUSB0"); //default for linux
	}
	
}
