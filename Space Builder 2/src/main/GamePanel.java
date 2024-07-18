package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import block.Block;
import projectile.BasicBulletProjectile;
import projectile.Projectile;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 8944822019376595677L;
	private static final double INTEGRATED_SCALE_FACTOR = 0.03;
	private static final double ASPECT_RATIO = 4.0 / 3.0;
	private Vector2 mousePosition = new Vector2();
	private Vector2 lastMousePosition = new Vector2();
	private final Game game;
	private double zoomFactor = 1;
	
    @Override
    public Dimension getPreferredSize() {
        if (getParent() != null) {
        	Dimension parentSize = getParent().getSize();
        	
        	if(parentSize.width <= parentSize.height * ASPECT_RATIO) {
            	int width = parentSize.width;
            	int height = (int)(parentSize.width / ASPECT_RATIO);

            	return new Dimension(width, height);
        	}else {
            	int height = parentSize.height;
            	int width = (int)(parentSize.height * ASPECT_RATIO);

            	return new Dimension(width, height);
        	}
        } else {
            return super.getPreferredSize();
        }
    }
	
	GamePanel(Game game){
		this.game = game;
		this.setDoubleBuffered(true);
		this.setEnabled(true);
		this.setBackground(Color.BLACK);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		//setup
		Graphics2D g2 = (Graphics2D) g;
						
		//add rendering stuff here 
		
		//all this stuff is temporary until i make graphics and stuff
		//draw the blocks
		Block[] blocks = this.game.getAllBlocks();
		
		for(Block b : blocks) {			
			AffineTransform old = g2.getTransform();
			AffineTransform transform = g2.getTransform();

			transform.translate(convertToScreenX(b.getWorldPosition().x), convertToScreenY(b.getWorldPosition().y));
			transform.rotate(-b.getRotationAngle());
			
			g2.setTransform(transform);
			
			g2.setColor(Color.WHITE);
			
			g2.fillRect(
					-scaleToScreen(b.getBlockSize()/2.0), 
					-scaleToScreen(b.getBlockSize()/2.0), 
					scaleToScreen(b.getBlockSize()), 
					scaleToScreen(b.getBlockSize()));
			g2.setColor(Color.BLACK);
			g2.drawRect(
					-scaleToScreen(b.getBlockSize()/2.0), 
					-scaleToScreen(b.getBlockSize()/2.0), 
					scaleToScreen(b.getBlockSize()), 
					scaleToScreen(b.getBlockSize()));
			

			g2.fillRect(
					-scaleToScreen(b.getBlockSize()/2.0), 
					-scaleToScreen(b.getBlockSize()/10.0), 
					scaleToScreen(b.getBlockSize()), 
					scaleToScreen(b.getBlockSize()/5.0));
			g2.fillRect(
					-scaleToScreen(b.getBlockSize()/10.0+1.0/10), 
					-scaleToScreen(b.getBlockSize()/4.0), 
					scaleToScreen(b.getBlockSize()/5.0),
					scaleToScreen(b.getBlockSize()/2.0));

			g2.setTransform(old);
		}
		
		Projectile[] projectiles = game.getAllProjectiles();
		
		g2.setColor(Color.WHITE);
		for(Projectile p : projectiles) {
			if(p instanceof BasicBulletProjectile) {
			g2.fillOval(
					convertToScreenX(((BasicBulletProjectile)p).getWorldPosition().x) - scaleToScreen(((BasicBulletProjectile)p).getRadius()), 
					convertToScreenY(((BasicBulletProjectile)p).getWorldPosition().y) - scaleToScreen(((BasicBulletProjectile)p).getRadius()), scaleToScreen(((BasicBulletProjectile)p).getRadius()) * 2, scaleToScreen(((BasicBulletProjectile)p).getRadius()) * 2);
			
			}
		}
	}
	
	private int scaleToScreen(double x) {		
    	Dimension parentSize = getParent().getSize();

    	if(parentSize.width * ASPECT_RATIO >= parentSize.height) {
    		return (int)Math.round(x * INTEGRATED_SCALE_FACTOR * getWidth() * zoomFactor);
    	}else {
    		return (int)Math.round(x * INTEGRATED_SCALE_FACTOR * getHeight() * zoomFactor);
    	}
		
	}
	private double scaleFromScreen(int x) {        
	    Dimension parentSize = getParent().getSize();

    	if(parentSize.width * ASPECT_RATIO >= parentSize.height) {
	        return x/(INTEGRATED_SCALE_FACTOR * getWidth() * zoomFactor);
	    } else {
	        return x/(INTEGRATED_SCALE_FACTOR * getHeight() * zoomFactor);
	    }
	}
	
	public Vector2 getMouseGamePosition() {
	    Point mousePosition = this.getMousePosition();
		
		if(mousePosition == null) {
			return lastMousePosition;
		}
		
		this.mousePosition.x = convertToGameX(mousePosition.x);
		this.mousePosition.y = convertToGameY(mousePosition.y);
		
		lastMousePosition = new Vector2(this.mousePosition);
		return this.mousePosition;

	}
	private int convertToScreenX(double x) {
		return (int)Math.round(this.getWidth()/2.0 + scaleToScreen(x-game.getPlayerCenter().x));
	}
	private int convertToScreenY(double y) {
		return (int)Math.round(this.getHeight()/2.0 - scaleToScreen(y-game.getPlayerCenter().y));
	}
	public double convertToGameX(int x) {
		return scaleFromScreen(x-getWidth()/2) + game.getPlayerCenter().x;
	}
	public double convertToGameY(int y) {
		return scaleFromScreen(-y + this.getHeight()/2) + game.getPlayerCenter().y;
	}
}
