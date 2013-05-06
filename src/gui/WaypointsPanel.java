/**
 * 
 */
package gui;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import data.Waypoints;

/**
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 * @version 1.0 (6 May 2013)
 */
public class WaypointsPanel extends JPanel implements MouseListener, KeyListener{
	
	JTextArea text = new JTextArea();
	Waypoints waypoints;
	boolean editable = false;

	/**
	 * 
	 */
	public WaypointsPanel(Waypoints wps){
		super();
		this.waypoints = wps;
		
	}
	
	public void refresh(){
		for(Point wp : waypoints.getPoints()){
			text.setText(text.getText()+ wp.getX() + ", " + wp.getY() + "/n");
		}
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0){
		if(arg0.getButton()==MouseEvent.BUTTON1){
			System.out.println("Key event");
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
	

}
