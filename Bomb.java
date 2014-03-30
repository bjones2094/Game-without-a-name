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
	
	// Update called every 30ms
	
	public void update() {
		this.frameCounter++;
		
		if(this.frameCounter >= timeBeforeMove) {
			this.isMissed();
		}
		
		if(this.x_pos <= -50) {
			this.currentState = ClickObject.State.dead;
		}
		
		if(this.timesTouched == 0) {
			this.move(-10, 0);
		}
	}
	
	// Called when user clicks a bomb
	
	public void isClicked() {
		this.timesTouched++;
		this.frameCounter = 0;
		this.setImage(explodeImage);
	}
	
	public void isMissed() {
		this.timesMissed++;
		this.frameCounter = 0;
		
		if(this.timesTouched > 0) {
			this.currentState = ClickObject.State.dead;
		}
	}
}