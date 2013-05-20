/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapWaypointMarker;

import data.Waypoints;

/**
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 * @version 1.0 (6 May 2013)
 */
public class WaypointsPanel extends JPanel implements MouseListener, ActionListener{
	
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
	JButton load, save, clear;
	JPanel buttonsPanel;
	Waypoints waypoints;
	boolean editable = false;

	/**
	 * 
	 */
	public WaypointsPanel(Waypoints wps){
		super(new BorderLayout());
		this.waypoints = wps;
		
		wpList = new JList(new WaypointListModel());
		this.add(wpList, BorderLayout.CENTER);
		
		save = new JButton(new ImageIcon("png/Save.png"));
		load = new JButton(new ImageIcon("png/Open.png"));
		clear = new JButton(new ImageIcon("png/Clear.png"));
		
		save.setToolTipText("Save waypoints to file");
		load.setToolTipText("Load waypoints from file");
		clear.setToolTipText("Remove all waypoints");
		
		save.addActionListener(this);
		load.addActionListener(this);
		clear.addActionListener(this);
		
		buttonsPanel = new JPanel(new FlowLayout());
		buttonsPanel.add(load);
		buttonsPanel.add(save);
		buttonsPanel.add(clear);
		
		this.add(buttonsPanel, BorderLayout.NORTH);
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		  .addKeyEventDispatcher(new KeyEventDispatcher() {
		      @Override
		      public boolean dispatchKeyEvent(KeyEvent e) {
		    	  if(e.getID()==KeyEvent.KEY_PRESSED){
		    		  if(e.getKeyCode()==KeyEvent.VK_CONTROL){
		    			  editable = true;
		    		  }
		    	  }else if(e.getID()==KeyEvent.KEY_RELEASED){
		    		  if(e.getKeyCode()==KeyEvent.VK_CONTROL){
		    			  editable = false;
		    		  }
		    	  }
		        return false;
		      }
		});
		
	}
	
	public void refresh(){
		this.remove(wpList);
		wpList = new JList(new WaypointListModel()); //FIXME ugly, ugly way :(
		this.add(wpList, BorderLayout.CENTER);
		refreshMarkers();
		this.validate();
		this.repaint();
	}
	
	public void refreshMarkers(){
		RoutePlannerFrame frame = RoutePlannerFrame.getInstance();
		frame.getMap().removeAllWaypointMarkers();
		for(int i = 0; i < this.waypoints.getPoints().size(); i++){
			Coordinate c = this.waypoints.getPoints().get(i);
			frame.getMap().addMapMarker(new MapWaypointMarker(c, i));
		}
		frame.getMap().validate();
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
		if(arg0.getButton()==MouseEvent.BUTTON1 && this.editable){
			//System.out.println(arg0.getPoint());
			RoutePlannerFrame frame = RoutePlannerFrame.getInstance();
			Coordinate pos = frame.getMap().getPosition(arg0.getPoint());
			//System.out.println(pos);
			this.waypoints.add(pos);
			this.refresh();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0){
		if(arg0.getSource()==this.save){
			JFileChooser fc = new JFileChooser(".");
			fc.setApproveButtonText("Save");
			fc.setDialogTitle("Save waypoints");
			fc.showOpenDialog(this);
			File f = fc.getSelectedFile();
			if(f==null) return;
			this.waypoints.saveToFile(f);
		}else if(arg0.getSource()==this.load){
			JFileChooser fc = new JFileChooser(".");
			fc.setDialogTitle("Open waypoints file");
			fc.showOpenDialog(this);
			File f = fc.getSelectedFile();
			if(f==null) return;
			this.waypoints.readFromFile(f);
			this.refresh();
		}else if(arg0.getSource()==this.clear){
			this.waypoints.clearList();
			this.refresh();
		}
		
	}
	

}
