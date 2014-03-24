import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;
import java.util.Random;
import java.util.Iterator;

public class Target extends Sprite {
	static Image lowImage;
	static Image midImage;
	static Image midHighImage;
	static Image highImage;
	
	public int timesTouched;
	
	public Random rand;
	
	int frameCounter;
	public int timeBeforeMove;
	
	public GameView gameView;
	
	public Target(int x, int y, GameView view) {
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
		
		this.setImage(this.lowImage);
		
		
		this.timesTouched = 0;
		
		this.frameCounter = 0;
		this.timeBeforeMove = 90;
	
		this.x_pos = x;
		this.y_pos = y;
		
		this.width = 50;
		this.height = 50;
		
		this.gameView = view;
		
		this.rand = new Random();
	}
	
	public void update() {
		this.frameCounter++;
		
		this.timeBeforeMove = 90 - this.timesTouched * 8;
			
		if(this.timeBeforeMove < 10) {
			this.timeBeforeMove = 10;
		}
		
		if(this.frameCounter >= this.timeBeforeMove) {
			this.relocate();
		}
	}
	
	public void isTouched() {
		this.timesTouched++;
		if(this.timesTouched == 3) {
			this.setImage(this.midImage);
		}
		else if(this.timesTouched == 6) {
			this.setImage(this.midHighImage);
		}
		else if(this.timesTouched == 9) {
			this.setImage(this.highImage);
		}
		this.relocate();
	}
	
	public void relocate() {
		this.frameCounter = 0;
		this.setPos(this.rand.nextInt(this.gameView.getWidth() - this.width), this.rand.nextInt(this.gameView.getHeight() - this.height));
		this.avoidTargetCollision();
	}
	
	public void avoidTargetCollision() {
		Iterator<Target> collisionIt = this.gameView.model.targets.iterator();
		while(collisionIt.hasNext()) {
			Target tempTarget = collisionIt.next();
			if(tempTarget != this) {
				while(checkTargetCollide(this, tempTarget)) {
					this.setPos(this.rand.nextInt(this.gameView.getWidth() - this.width), this.rand.nextInt(this.gameView.getHeight() - this.height));
				}
			}
		}
		Iterator<Sprite> bombIt = this.gameView.model.bombs.iterator();
		while(bombIt.hasNext()) {
			Sprite tempBomb = bombIt.next();
			while(checkTargetCollide(this, tempBomb)) {
				this.setPos(this.rand.nextInt(this.gameView.getWidth() - this.width), this.rand.nextInt(this.gameView.getHeight() - this.height));
			}
		}
	}
	
	public static boolean checkTargetCollide(Sprite t1, Sprite t2) {
		boolean leftCollide = (t1.x_pos > t2.x_pos && t1.x_pos < t2.x_pos + t2.width);
		boolean rightCollide = (t1.x_pos + t1.width > t2.x_pos && t1.x_pos + t1.width < t2.x_pos + t2.width);
		boolean topCollide = (t1.y_pos > t2.y_pos && t1.y_pos < t2.y_pos + t2.height);
		boolean bottomCollide = (t1.y_pos + t1.height > t2.y_pos && t1.y_pos + t1.height < t2.y_pos + t2.height);
		
		return ((leftCollide || rightCollide) && (topCollide || bottomCollide));
	}
}