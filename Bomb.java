/*
 * Bomb class is a child of the ClickObject class, so it can be clicked by the user
 * Bombs should be avoided by user
 */

import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;

public class Bomb extends ClickObject {
	static Image regularImage;
	static Image explodeImage;
	
	public boolean isExploded;
	public boolean isDead;		// Dead bombs will be removed by the model
	
	public Bomb(int x, int y, GameView view) {
		super(x, y, view);
	
		try {
			if(regularImage == null) {
				regularImage = ImageIO.read(new File("img/bomb.png"));
			}
			if(explodeImage == null) {
				explodeImage = ImageIO.read(new File("img/boom.png"));
			}
		}
		catch(Exception e) {
			throw new IllegalArgumentException("Failed to load file");
		}
		
		this.currentImage = regularImage;
	}
	
	// Update called every 33ms
	
	public void update() {
		this.frameCounter++;
		
		if(this.frameCounter >= timeBeforeMove) {
			if(this.isExploded) {
				this.isDead = true;
			}
			else {
				this.relocate();
			}
		}
	}
	
	// Called when user clicks a bomb
	
	public void isTouched() {
		this.timesTouched++;
		this.frameCounter = 0;
		this.isExploded = true;
		this.setImage(explodeImage);
	}
}