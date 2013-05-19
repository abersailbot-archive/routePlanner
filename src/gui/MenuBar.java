/**
 * 
 */
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToggleButton;

import data.Settings;

/**
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 * @version 1.0 (5 May 2013)
 */
public class MenuBar extends JMenuBar implements ActionListener{
	
	private JCheckBoxMenuItem followBoat;

	public MenuBar(){
		super();
		JMenu dataSourceMenu = new JMenu("Data source");
		
		ButtonGroup radio = new ButtonGroup();
		JRadioButtonMenuItem none = new JRadioButtonMenuItem("None");
		none.setActionCommand("none");
		JRadioButtonMenuItem mock = new JRadioButtonMenuItem("Mock");
		mock.setActionCommand("mock");
		JRadioButtonMenuItem serial = new JRadioButtonMenuItem("Serial");
		serial.setActionCommand("serial");
		JRadioButtonMenuItem log = new JRadioButtonMenuItem("Log file");
		log.setActionCommand("logfile");
		radio.add(none);
		radio.add(mock);
		radio.add(serial);
		radio.add(log);
		dataSourceMenu.add(none);
		dataSourceMenu.add(mock);
		dataSourceMenu.add(serial);
		dataSourceMenu.add(log);
		
		none.setSelected(true);
		
		mock.addActionListener(this);
		serial.addActionListener(this);
		log.addActionListener(this);
		
		followBoat = new JCheckBoxMenuItem("Follow boat");
		followBoat.setSelected(Settings.getBoolean(Settings.FOLLOW_ROBOT));
		followBoat.addActionListener(this);
		
		this.add(dataSourceMenu);
		this.add(followBoat);
		this.setVisible(true);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0){
		String message = arg0.getActionCommand();
		switch(message){
		case "none":
		case "mock":
		case "serial":
		case "logfile":
			RoutePlannerFrame.getInstance().setDataSource(message);
			break;
		case "Follow boat":
			RoutePlannerFrame.getInstance().setFollowBoat(followBoat.isSelected());
			Settings.set(Settings.FOLLOW_ROBOT, followBoat.isSelected());
		}
	}
}
