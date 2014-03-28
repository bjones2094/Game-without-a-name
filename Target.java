/*
 * Target class is a child of the ClickObject class, and is the main objective of the game.
 * Targets should be clicked before they disappear.
 */

import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;

public class Target extends ClickObject {
	// All images needed for targets

	static Image lowImage;
	static Image midImage;
	static Image midHighImage;
	static Image highImage;
	static Image goldImage;
	
	public Target(int x, int y, GameView view) {
		super(x, y, view);
	
		// Statically loads all images needed for targets on creation of first target
	
		try {
			if(this.lowImage == null) {
				this.lowImage = ImageIO.read(new File("img/targetLow.png"));
			}
			if(this.midImage == null) {
				this.midImage = ImageIO.read(new File("img/targetMid.png"));
			}
			if(this.midHighImage == null) {
				this.midHighImage = ImageIO.read(new File("img/targetMidHigh.png"));
			}
			if(this.highImage == null) {
				this.highImage = ImageIO.read(new File("img/targetHigh.png"));
			}
			if(this.goldImage == null) {
				this.goldImage = ImageIO.read(new File("img/goldTarget.png"));
			}
		}
		catch(Exception e) {
			throw new IllegalArgumentException("Failed to load file");
		}
		
		this.setImage(this.lowImage);
	}
	
	// Update called every 33ms
	
	public void update() {
		this.frameCounter++;
		
		// Targets move faster as they are clicked more (minimum 10ms between switches)
		
		this.timeBeforeMove = 90 - this.timesTouched * 8;
			
		if(this.timeBeforeMove < 10) {
			this.timeBeforeMove = 10;
		}
		
		if(this.frameCounter >= this.timeBeforeMove) {
			this.relocate();
		}
	}
	
	// Called when user successfully clicks target
	
	public void isTouched() {
		this.timesTouched++;
		
		// Targets change color the more they are clicked
		
		if(this.timesTouched == 3) {
			this.currentState = ClickObject.State.mid;
			this.setImage(this.midImage);
		}
		else if(this.timesTouched == 6) {
			this.currentState = ClickObject.State.midFast;
			this.setImage(this.midHighImage);
		}
		else if(this.timesTouched == 9) {
			this.currentState = ClickObject.State.fast;
			this.setImage(this.highImage);
		}
		this.relocate();
	}
}