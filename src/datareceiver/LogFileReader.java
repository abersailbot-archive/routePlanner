package datareceiver;

import gui.LogReaderPanel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import data.DataSet;

/**
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 * @version 1.0 (5 May 2013)
 */
public class LogFileReader extends AbstractDataReceiver{

	private File logFile;
	private LinkedList<String> lines;
	private int lineCounter;
	private LogReaderPanel controller;
	private boolean paused = true;
	
	public LogFileReader(DataSet dataSet){
		super(dataSet);
		lines = new LinkedList<String>();
	}
	
	public void setController(LogReaderPanel lrp){
		this.controller = lrp;
	}
	
	public void openFile(File logFile) throws FileNotFoundException, IOException{
		this.logFile = logFile;
		lines.clear();
		lineCounter = 0;
		BufferedReader reader = new BufferedReader(new FileReader(logFile));
		String line;
		while((line = reader.readLine())!=null) lines.add(line);
		System.out.println(lines.size() + " lines read.");
		paused = false;
	}
	
	public int getTotalLines(){
		return lines.size();
	}
	
	public void jumpToLine(int lineNumber){
		lineCounter = lineNumber;
	}

	@Override
	protected String readDataString(){
		if(paused) return null;
		if(lineCounter <=0) lineCounter = 0;
		if(lineCounter >= lines.size()) return null;
		else{
			if(controller!=null) controller.setSliderPosition(lineCounter+1);
			return lines.get(lineCounter++);
		}
	}

	public File getLogFile(){
		return logFile;
	}

	public void setLogFile(File logFile){
		this.logFile = logFile;
	}

	public boolean isPaused(){
		return paused;
	}

	public void setPaused(boolean paused){
		this.paused = paused;
	}

}
