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
	
	// Gold target has special attributes
	
	public boolean isGold;
	
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
		
		this.isGold = false;
	}
	
	// Update called every 30ms
	
	public void update() {
		this.frameCounter++;
		
		// Targets move faster as they are clicked more (minimum 10ms between switches)
		
		this.timeBeforeMove = 90 - this.timesTouched * 8;
			
		if(this.timeBeforeMove < 10) {
			this.timeBeforeMove = 10;
		}
		
		// Targets change color the more they are touched
		
		if(this.timesTouched < 3) {
			this.currentState = ClickObject.State.slow;
			this.setImage(this.lowImage);
		}
		else if(this.timesTouched >= 3 && this.timesTouched < 6) {
			this.currentState = ClickObject.State.mid;
			this.setImage(this.midImage);
		}
		else if(this.timesTouched >= 6 && this.timesTouched < 9) {
			this.currentState = ClickObject.State.midFast;
			this.setImage(this.midHighImage);
		}
		else if(this.timesTouched >= 9) {
			this.currentState = ClickObject.State.fast;
			if(!this.isGold) {		// Gold targets stay gold
				this.setImage(this.highImage);
			}
		}
		
		if(this.frameCounter >= this.timeBeforeMove) {
			this.relocate();
			this.timesMissed++;
			
			// Targets slow down if they aren't clicked 
			
			int missesBeforeReset = 2;
			
			if(this.state() == ClickObject.State.slow) {
				missesBeforeReset = 1;
			}
			else if(this.state() == ClickObject.State.mid) {
				missesBeforeReset = 2;
			}
			else if(this.state() == ClickObject.State.midFast) {
				missesBeforeReset = 3;
			}
			else if(this.state() == ClickObject.State.fast) {
				missesBeforeReset = 5;
			}
			
			if(this.isGold) {		// Special case for gold target
				missesBeforeReset = 10;
			}
			
			if(this.timesMissed == missesBeforeReset) {
				this.timesMissed = 0;
				
				// Gold targets die instead of slowing down
				
				if(this.isGold) {
					this.currentState = ClickObject.State.dead;
				}
				else {
					if(this.timesTouched > 0) {
						this.timesTouched--;
					}
				}
			}
		}
	}
	
	// Called when user successfully clicks target
	
	public void isTouched() {
		this.timesTouched++;
		this.relocate();
	}
}
