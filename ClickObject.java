/*
 * ClickObject class acts as an outline for any object that can be clicked by the user
 */

import java.util.Random;
import java.util.Iterator;

public abstract class ClickObject extends Sprite {
	// States that a ClickObject can be in

	public enum State {slow, mid, midFast, fast, dead};

	int frameCounter;
	int timesTouched;
	int timeBeforeMove;
	
	GameView gameView;
	
	Random rand;
	
	public State currentState;
	
	public ClickObject() {
		this.timesTouched = 0;
		
		this.frameCounter = 0;
		this.timeBeforeMove = 90;
	
		this.x_pos = 0;
		this.y_pos = 0;
		
		this.width = 50;
		this.height = 50;
		
		this.currentState = State.slow;
		
		this.gameView = null;
		
		this.rand = new Random();
	}
	
	public ClickObject(int x, int y, GameView view) {
		this.timesTouched = 0;
		
		this.frameCounter = 0;
		this.timeBeforeMove = 90;
	
		this.x_pos = x;
		this.y_pos = y;
		
		this.width = 50;
		this.height = 50;
		
		this.currentState = State.slow;
		
		this.gameView = view;
		
		this.rand = new Random();
	}
	
	// Relocate used to move object to new random location
	
	public void relocate() {
		this.frameCounter = 0;
		this.setPos(this.rand.nextInt(this.gameView.getWidth() - this.width), this.rand.nextInt(this.gameView.getHeight() - this.height));
		this.avoidCollisions();
	}
	
	// Attempt at preventing collisions between targets and bombs (needs to be fixed)
	
	public void avoidCollisions() {
		Iterator<Target> collisionIt = this.gameView.model.targets.iterator();
		while(collisionIt.hasNext()) {
			Target tempTarget = collisionIt.next();
			if(tempTarget != this) {
				while(checkTargetCollide(this, tempTarget)) {
					this.setPos(this.rand.nextInt(this.gameView.getWidth() - this.width), this.rand.nextInt(this.gameView.getHeight() - this.height));
				}
			}
		}
		Iterator<Bomb> bombIt = this.gameView.model.bombs.iterator();
		while(bombIt.hasNext()) {
			Bomb tempBomb = bombIt.next();
			if(tempBomb != this) {
				while(checkTargetCollide(this, tempBomb)) {
					this.setPos(this.rand.nextInt(this.gameView.getWidth() - this.width), this.rand.nextInt(this.gameView.getHeight() - this.height));
				}
			}
		}
	}
	
	// Checks if two sprites overlap
	
	public static boolean checkTargetCollide(Sprite t1, Sprite t2) {
		boolean leftCollide = (t1.x_pos > t2.x_pos && t1.x_pos < t2.x_pos + t2.width);
		boolean rightCollide = (t1.x_pos + t1.width > t2.x_pos && t1.x_pos + t1.width < t2.x_pos + t2.width);
		boolean topCollide = (t1.y_pos > t2.y_pos && t1.y_pos < t2.y_pos + t2.height);
		boolean bottomCollide = (t1.y_pos + t1.height > t2.y_pos && t1.y_pos + t1.height < t2.y_pos + t2.height);
		
		return ((leftCollide || rightCollide) && (topCollide || bottomCollide));
	}
	
	// Allows for custom speeds
	
	public void setTimesTouched(int amount) {
		this.timesTouched = amount;
	}
	
	public State state() {
		return this.currentState;
	}
	
	// Abstract methods needed for all child classes
	
	public abstract void update();
	public abstract void isTouched();
}