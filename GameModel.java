/*
 * Model handles most of the game's logic and updating
 */

import java.util.Random;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.ArrayList;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;

public class GameModel {
	public int score;
	public int lives;
	
	public ArrayList<Target> targets;
	public ArrayList<Sprite> bombs;	// Bombs do not have their own class since they only require the basic methods provided by the sprite class
	
	public static Image bombImage;
	
	public int frameCounter;
	
	public Random rand;
	
	public GameView gameView;
	public int viewWidth;
	public int viewHeight;
	
	public GameModel() {
		this.score = 0;
		this.lives = 5;
		this.frameCounter = 0;
		
		this.rand = new Random();
		
		this.targets = new ArrayList<Target>();
		this.bombs = new ArrayList<Sprite>();
		try {
			bombImage = ImageIO.read(new File("img/bomb.png"));
		}
		catch(Exception e) {
			throw new IllegalArgumentException("Failed to open file");
		}
	}
	
	public void update() {
		this.frameCounter++;
	
		this.viewWidth = this.gameView.getWidth();
		this.viewHeight = this.gameView.getHeight();
	
		// Adds more targets to the game based on score (stops at 15 score)
	
		if(this.score < 15) {
			if(this.score % 3 == 0) {
				int Factor = this.score / 3;
				int expectedSize = 1 + (Factor - 1) * Factor / 2;	// Some clever math to prevent continuously adding targets
				if(this.targets.size() == expectedSize) {
					this.addTargets(Factor);
				}
			}
		}
		
		// Adds a bomb every 3 seconds (max of 10 bombs on screen), and shuffles bomb locations
		
		if(this.frameCounter == 90) {
			this.frameCounter = 0;
			
			Iterator<Sprite> it = this.bombs.iterator();
			while(it.hasNext()) {
				Sprite tempBomb = it.next();
				tempBomb.setPos(this.rand.nextInt(this.viewWidth - tempBomb.width), this.rand.nextInt(this.viewHeight - tempBomb.height));
			}
		
			if(this.bombs.size() < 10) {
				this.bombs.add(new Sprite(this.rand.nextInt(this.viewWidth - 50), this.rand.nextInt(this.viewHeight - 50), this.bombImage));
			}
		}
	
		Iterator<Target> it = this.targets.iterator();
		while(it.hasNext()) {
			Target tempTarget = it.next();
			tempTarget.update();
		}
	}
	
	// Draws everything to the view (panel)
	
	public void draw(Graphics g) {
		Iterator<Target> targetIt = this.targets.iterator();
		while(targetIt.hasNext()) {
			Target tempTarget = targetIt.next();
			tempTarget.draw(g);
		}
		
		Iterator<Sprite> bombIt = this.bombs.iterator();
		while(bombIt.hasNext()) {
			Sprite tempBomb = bombIt.next();
			tempBomb.draw(g);
		}
	}
	
	// Adds first target to the game
	
	public void initialize() {
		this.viewWidth = this.gameView.getWidth();
		this.viewHeight = this.gameView.getHeight();

		this.targets.add(new Target(this.viewWidth / 2, this.viewHeight / 2, this.gameView));
	}
	
	// Handles all mouse clicks
	
	public void mouseClicked(int x, int y) {
		boolean successClick = false;
	
		// Checks all targets for clicks
	
		Iterator<Target> it = this.targets.iterator();
		while(it.hasNext()) {
			Target tempTarget = it.next();
			if(checkMouseCollide(x, y, tempTarget)) {
				tempTarget.isTouched();
				successClick = true;
			}
		}
		
		// Checks all bombs for clicks
		
		Iterator<Sprite> it2 = this.bombs.iterator();
		while(it2.hasNext()) {
			Sprite tempBomb = it2.next();
			if(checkMouseCollide(x, y, tempBomb)) {
				it2.remove();
				successClick = false;
			}
		}
		if(successClick) {
			this.addScore(1);
		}
		else {
			this.loseLife();
		}
	}
	
	// Mouse Collisions
	
	public static boolean checkMouseCollide(int x, int y, Sprite s) {
		boolean horizontalCollide = (x > s.x_pos && x < s.x_pos + s.width);
		boolean verticalCollide = (y > s.y_pos && y < s.y_pos + s.height);
		
		return (horizontalCollide && verticalCollide);
	}
	
	public void addScore(int amount) {
		this.score += amount;
	}
	
	public void loseLife() {
		this.lives--;
		if(this.lives <= 0) {
			this.gameOver();
		}
	}
	
	// Temporary game over ends program
	
	public void gameOver() {
		System.exit(0);
	}
	
	// setView used to provide a reference to the game's view
	
	public void setView(GameView view) {
		this.gameView = view;
	}
	
	// Add targets to model
	
	public void addTargets(int amount) {
		for(int i = 0; i < amount; i++) {
			this.targets.add(new Target(this.rand.nextInt(this.viewWidth - 50), this.rand.nextInt(this.viewHeight - 50), this.gameView));
		}
	}
}