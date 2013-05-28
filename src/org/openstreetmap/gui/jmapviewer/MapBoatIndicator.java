/**
 * 
 */
package org.openstreetmap.gui.jmapviewer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

import data.DataSet;

/**
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 * @version 1.0 (4 May 2013)
 */
public class MapBoatIndicator implements MapMarker{

	double lat, lon;
	int heading;
	int wind;
	
//	BufferedImage arrow;
	
	public MapBoatIndicator(double lat, double lon){
		this.lat = lat;
		this.lon = lon;
//		try{
//			arrow = ImageIO.read(new File("png/arrow.png"));
//		}catch(IOException ex){
//			ex.printStackTrace();
//			System.exit(0);
//		}
	}
	
	@Override
	public double getLat(){
		return lat;
	}

	@Override
	public double getLon(){
		return lon;
	}

	@Override
	public void paint(Graphics g, Point position){
		int size_h = 5;
		double c = 30; //length of heading arrow
        int size = size_h * 2;
       
        double b = c*Math.sin(Math.toRadians(heading));
        double a = c*Math.cos(Math.toRadians(heading));
        int diffX = (int) Math.round(b);
        int diffY = (int) Math.round(a);    
        g.drawLine(position.x, position.y, position.x+diffX, position.y-diffY);
        
        g.setColor(Color.YELLOW);
        g.fillOval(position.x - size_h, position.y - size_h, size, size);
        g.setColor(Color.BLACK);
        g.drawOval(position.x - size_h, position.y - size_h, size, size);
        
        drawArrow((Graphics2D) g, position.x, position.y, position.x+diffX, position.y-diffY);
        /*
        int n = 100;
        if(wind<=90) drawWindArrow(g, new Point(position.x-n, position.y+n));
        else if(wind<=180) drawWindArrow(g, new Point(position.x-n, position.y-n));
        else if(wind<=270) drawWindArrow(g, new Point(position.x+n, position.y-n));
        else drawWindArrow(g, new Point(position.x+n, position.y+n));
		*/
	}
	/*
	public void drawWindArrow(Graphics g, Point position){
		
		Graphics2D g2d = (Graphics2D) g;
		//Graphics2D part = (Graphics2D) g2d.create(position.x, position.y, 300, 300);
		Graphics2D part = (Graphics2D) g2d.create(0, 0, 300, 300);
		//AffineTransform t = g2d.getTransform();
		//t.rotate(0.3);
		//g2d.setTransform(t);
		part.rotate(Math.toRadians(wind), 40, 40);
		part.drawImage(arrow, 0, 0, null);
		//g2d.setTransform(t);
		
//		double c = 50; //length of heading arrow
//		double b = c*Math.sin(Math.toRadians(wind));
//        double a = c*Math.cos(Math.toRadians(wind));
//        int diffX = (int) Math.round(b);
//        int diffY = (int) Math.round(a);    
//        g.drawLine(position.x, position.y, position.x+diffX, position.y-diffY);
//        drawArrow((Graphics2D) g, position.x, position.y, position.x+diffX, position.y-diffY);
	}
	*/
	
	
	/**
	   * Draws an arrow on the given Graphics2D context
	   * @param g The Graphics2D context to draw on
	   * @param x The x location of the "tail" of the arrow
	   * @param y The y location of the "tail" of the arrow
	   * @param xx The x location of the "head" of the arrow
	   * @param yy The y location of the "head" of the arrow
	   */
	  private void drawArrow( Graphics2D g, int x, int y, int xx, int yy )
	  {
	    float arrowWidth = 7.0f ;
	    float theta = 0.623f ;
	    int[] xPoints = new int[ 3 ] ;
	    int[] yPoints = new int[ 3 ] ;
	    float[] vecLine = new float[ 2 ] ;
	    float[] vecLeft = new float[ 2 ] ;
	    float fLength;
	    float th;
	    float ta;
	    float baseX, baseY ;

	    xPoints[ 0 ] = xx ;
	    yPoints[ 0 ] = yy ;

	    // build the line vector
	    vecLine[ 0 ] = (float)xPoints[ 0 ] - x ;
	    vecLine[ 1 ] = (float)yPoints[ 0 ] - y ;

	    // build the arrow base vector - normal to the line
	    vecLeft[ 0 ] = -vecLine[ 1 ] ;
	    vecLeft[ 1 ] = vecLine[ 0 ] ;

	    // setup length parameters
	    fLength = (float)Math.sqrt( vecLine[0] * vecLine[0] + vecLine[1] * vecLine[1] ) ;
	    th = arrowWidth / ( 2.0f * fLength ) ;
	    ta = arrowWidth / ( 2.0f * ( (float)Math.tan( theta ) / 2.0f ) * fLength ) ;

	    // find the base of the arrow
	    baseX = ( (float)xPoints[ 0 ] - ta * vecLine[0]);
	    baseY = ( (float)yPoints[ 0 ] - ta * vecLine[1]);

	    // build the points on the sides of the arrow
	    xPoints[ 1 ] = (int)( baseX + th * vecLeft[0] );
	    yPoints[ 1 ] = (int)( baseY + th * vecLeft[1] );
	    xPoints[ 2 ] = (int)( baseX - th * vecLeft[0] );
	    yPoints[ 2 ] = (int)( baseY - th * vecLeft[1] );

	    g.drawLine( x, y, (int)baseX, (int)baseY ) ;
	    g.fillPolygon( xPoints, yPoints, 3 ) ;
	  }
	
	public void update(){
		DataSet ds = DataSet.getInstance();
		lat = (Double) ds.getValueByKey("lat");
		lon = (Double) ds.getValueByKey("lon");
		//TODO investigate why it doesnt work normally
		heading = Integer.parseInt(ds.getValueByKey("bhead").toString());
		wind = Integer.parseInt(ds.getValueByKey("wind").toString());
	}

	

}
