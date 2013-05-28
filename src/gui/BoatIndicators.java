package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BoatIndicators{
	
	private static int wind;
	private static int heading;
	private static int wpHeading;
	private static int rudder = 180;
	
	public static final int SIZE = 300;
	
	private static BufferedImage windInd, boatInd, wpHeadingInd, rudderInd;
	
	public static void update(Number w, Number h, Number wph, Number r){
		wind = Integer.parseInt(w.toString());
		heading = Integer.parseInt(h.toString());
		wpHeading = Integer.parseInt(wph.toString());
		rudder = Integer.parseInt(r.toString());
	}
	
	public static void loadImages() throws IOException{
		windInd = ImageIO.read(new File("png/wind.png"));
		boatInd = ImageIO.read(new File("png/boat.png"));
		rudderInd = ImageIO.read(new File("png/rudder.png"));
	}
	
	public static void draw(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		
		Graphics2D windGraphics = (Graphics2D) g.create();
		windGraphics.rotate(Math.toRadians(wind), SIZE/2, SIZE/2);
		windGraphics.drawImage(windInd, 0, 0, null);
		
		Graphics2D boatGraphics = (Graphics2D) g.create();
		boatGraphics.rotate(Math.toRadians(heading), SIZE/2, SIZE/2);

		Graphics2D rudderGraphics = (Graphics2D) boatGraphics.create(135, 215, 50, 50);
		
		rudderGraphics.rotate(Math.toRadians(rudder - 180), 15, 3);
		rudderGraphics.drawImage(rudderInd, 0, 0, null);
		
		
		boatGraphics.drawImage(boatInd, 0, 0, null);
		
	}
}
