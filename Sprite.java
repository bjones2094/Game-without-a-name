/*
 * Sprite class provides a basic outline for anything that will be drawn to the screen
 * Mostly used as a basis for more specific classes
 */

import java.awt.Image;
import java.awt.Graphics;

public class Sprite {
	public int x_pos;
	public int y_pos;
	
	public int width;
	public int height;
	
	public Image currentImage;

	public Sprite() {
		this.x_pos = 0;
		this.y_pos = 0;
		
		this.width = 50;
		this.height = 50;
		
		this.currentImage = null;
	}
	
	public Sprite(int x, int y, Image i) {
		this.x_pos = x;
		this.y_pos = y;
		
		this.width = 50;
		this.height = 50;
		
		this.currentImage = i;
	}
	
	public void draw(Graphics g) {
		g.drawImage(this.currentImage, this.x_pos, this.y_pos, null);
	}
	
	public void setPos(int x, int y) {
		this.x_pos = x;
		this.y_pos = y;
	}
	
	public void move(int x, int y) {
		this.x_pos += x;
		this.y_pos += y;
	}
	
	public void setDimen(int w, int h) {
		this.width = w;
		this.height = h;
	}
	
	public void setImage(Image i) {
		this.currentImage = i;
	}
}