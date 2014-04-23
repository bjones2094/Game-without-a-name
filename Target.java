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
	
	// Gold target has special attributes
	
	public boolean isGold;
	
	public Target(int x, int y, GameView view) {
		super(x, y, view);
		this.loadImages();
	}
	
	// Update called every 30ms
	
	public void update() {
		this.frameCounter++;
		
		if(this.frameCounter >= this.timeBeforeMove) {
			this.isMissed();
		}
	}
	
	// Called when user successfully clicks target
	
	public void isClicked() {
		this.timesTouched++;
		this.updateState();
		this.relocate();
	}
	
	// Called when target isn't clicked in time
	
	public void isMissed() {
		this.timesMissed++;
		
		// Targets slow down if not clicked
		
		int missesBeforeReset;
					
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
		else {
			missesBeforeReset = 0;
		}
		
		if(this.timesMissed == missesBeforeReset) {
			this.timesMissed = 0;
			
			if(this.timesTouched > 0) {
				this.timesTouched--;
				this.updateState();
			}
		}
		
		this.relocate();
	}
	
	// Targets change color, speed, and state with more clicks
	
	public void updateState() {
		this.timeBeforeMove = 90 - this.timesTouched * 8;
	
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
			this.setImage(this.highImage);
		}
	}
	
	// Statically loads all images needed for targets on creation of first target
	
	public void loadImages() {
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
		}
		catch(Exception e) {
			throw new IllegalArgumentException("Failed to load file");
		}
		
		this.setImage(lowImage);
	}
}
