/**
 * 
 */
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import data.Waypoints;

/**
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 * @version 1.0 (6 May 2013)
 */
public class WaypointsPanel extends JPanel implements MouseListener, KeyListener, ActionListener{
	
	private class WaypointListModel implements ListModel{

		
		public WaypointListModel(){
		}
		
		@Override
		public void addListDataListener(ListDataListener arg0){
			// TODO Auto-generated method stub
			
		}

		@Override
		public Object getElementAt(int arg0){
			return waypoints.getStringRepresentation(arg0);
		}

		@Override
		public int getSize(){
			return waypoints.getPoints().size();
		}

		@Override
		public void removeListDataListener(ListDataListener arg0){
			// TODO Auto-generated method stub
			
		}
		
	}
	
	JList wpList = new JList();
	JButton load, save;
	Waypoints waypoints;
	boolean editable = false;

	/**
	 * 
	 */
	public WaypointsPanel(Waypoints wps){
		super();
		this.waypoints = wps;
		
		wpList = new JList(new WaypointListModel());
		this.add(wpList);
		
		save = new JButton("Save to file");
		load = new JButton("Load from file");
		
		save.addActionListener(this);
		load.addActionListener(this);
		
		this.add(save);
		this.add(load);
		
		
	}
	
	public void refresh(){
		this.remove(wpList);
		wpList = new JList(new WaypointListModel());
		this.add(wpList);
		//this.invalidate();
		this.validate();
		//this.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0){
	}

	@Override
	public void mouseReleased(MouseEvent arg0){
		if(!this.isVisible()) return;
			
		if(arg0.getButton()==MouseEvent.BUTTON1){
			System.out.println("Key event");
			System.out.println(arg0.getPoint());
			RoutePlannerFrame frame = RoutePlannerFrame.getInstance();
			Coordinate pos = frame.getMap().getPosition(arg0.getPoint());
			System.out.println(pos);
			this.waypoints.add(pos);
			this.refresh();
		}
		
	}

	@Override
	public void keyPressed(KeyEvent arg0){
		System.out.println("KEY");
		if(arg0.getKeyCode()==KeyEvent.VK_CONTROL){
			this.editable = true;
			System.out.println("CTRL PRESSED");
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0){
		System.out.println("KEY");
		if(arg0.getKeyCode()==KeyEvent.VK_CONTROL){
			this.editable = false;
			System.out.println("CTRL released");
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0){
		if(arg0.getSource()==this.save){
			this.waypoints.saveToFile();
		}else if(arg0.getSource()==this.load){
			this.waypoints.readFromFile(new File("waypoints.txt"));
			this.refresh();
		}
		
	}
	

}
