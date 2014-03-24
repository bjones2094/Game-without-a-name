/*
 * GameView acts as an area for all images to be displayed
 */

import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;


public class GameView extends JPanel {
	public GameController controller;
	public GameModel model;
	
	public JTextArea scoreTextArea;
	public JTextArea livesTextArea;
	
	public Image backgroundImage;

	public GameView(GameController c, GameModel m) {
		this.controller = c;
		this.model = m;
		
		// Displays score
		
		this.scoreTextArea = new JTextArea("Score: 0");
		this.scoreTextArea.setEditable(false);
		this.scoreTextArea.setOpaque(false);
		this.add(this.scoreTextArea);
		
		// Displays lives
		
		this.livesTextArea = new JTextArea("Lives: 5");
		this.livesTextArea.setEditable(false);
		this.livesTextArea.setOpaque(false);
		this.add(this.livesTextArea);
		
		// background.png used as background image
		
		try {
			this.backgroundImage = ImageIO.read(new File("img/background.png"));
		}
		catch(Exception e) {
			throw new IllegalArgumentException("Failed to load file");
		}
		
		// Uses GameController for mouseListener
		
		this.addMouseListener(c);
	}
	
	// Draws everything in order (background, model, text)
	
	public void paintComponent(Graphics g) {
		g.drawImage(backgroundImage,0,0,getSize().width,getSize().height,this);
		this.model.draw(g);
		this.scoreTextArea.setText("Score: " + String.valueOf(this.model.score));
		this.livesTextArea.setText("Lives: " + String.valueOf(this.model.lives));
		revalidate();
	}
}