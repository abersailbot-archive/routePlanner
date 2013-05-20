/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import datareceiver.LogFileReader;

/**
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 * @version 1.0 (5 May 2013)
 */
public class LogReaderPanel extends JPanel implements ChangeListener, ActionListener{

	JButton playPause, pause, openFileButton;
	JSlider slider;
	JLabel position, fileName;
	JPanel top, centre, bottom;
	
	LogFileReader reader;
	boolean paused = true;
	
	public LogReaderPanel(LogFileReader reader){
		super();
		this.reader = reader;
		centre = new JPanel();
		slider = new JSlider(0, reader.getTotalLines());
		position = new JLabel("0/"+reader.getTotalLines());
		centre.add(slider);
		centre.add(position);
		
		top = new JPanel();
		playPause = new JButton("PLAY");
		playPause.setActionCommand("play_pause");
		playPause.addActionListener(this);
		top.add(playPause);
		
		bottom = new JPanel(new BorderLayout());
		openFileButton = new JButton(new ImageIcon("png/Open.png"));
		openFileButton.setToolTipText("Open log file");
		openFileButton.setActionCommand("open");
		openFileButton.addActionListener(this);
		fileName = new JLabel("File not loaded");
		bottom.add(fileName, BorderLayout.CENTER);
		bottom.add(openFileButton, BorderLayout.EAST);
		
		Border b = new TitledBorder("Log player");
		this.setBorder(b);
		
		this.setLayout(new BorderLayout());
		this.add(centre, BorderLayout.CENTER);
		this.add(top, BorderLayout.NORTH);
		this.add(bottom, BorderLayout.SOUTH);
		this.setVisible(true);
		slider.addChangeListener(this);
	}
	
	public void setSliderPosition(int pos){
		this.slider.setValue(pos);
	}

	@Override
	public void stateChanged(ChangeEvent arg0){
		    if(!this.slider.getValueIsAdjusting()) {
		    	this.reader.jumpToLine(this.slider.getValue()-1);
		    	this.position.setText(this.slider.getValue()-1+"/"+reader.getTotalLines());
		    }
	}

	@Override
	public void actionPerformed(ActionEvent arg0){
		String message = arg0.getActionCommand();
		if(message.equals("open")){
			JFileChooser fc = new JFileChooser(".");
			fc.setDialogTitle("Open log file");
			fc.showOpenDialog(this);
			File f = fc.getSelectedFile();
			if(f==null) return;
			try{
				this.reader.openFile(f);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			this.playPause.setText("PAUSE");
			this.paused = false;
			this.reader.setPaused(this.paused);
			this.refresh();
		}else if(message.equals("play_pause")){
			if(paused){
				this.playPause.setText("PAUSE");
				paused = false;
				this.reader.setPaused(this.paused);
			}else{
				this.playPause.setText("PLAY");
				paused = true;
				this.reader.setPaused(this.paused);
			}
		}
	}
	
	public void refresh(){
		this.slider.setValue(1);
		this.slider.setMinimum(1);
		this.slider.setMaximum(reader.getTotalLines());
		this.position.setText("0/" + reader.getTotalLines());
		File f = reader.getLogFile();
		if(f==null) this.fileName.setText("File not loaded.");
		else this.fileName.setText("File : " + f.getName());
	}

	public LogFileReader getReader(){
		return reader;
	}

	public void setReader(LogFileReader reader){
		this.reader = reader;
	}
	
}
