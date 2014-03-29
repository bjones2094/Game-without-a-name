/*
 * Unnamed Game v0.2
 * Author: Brian Jones
 */

import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class Game extends JFrame {
	GameModel model;
	GameController controller;
	GameView view;

	// Constructor launches game thread
	
	public Game() {
		this.model = new GameModel();
		this.controller = new GameController(model);
		this.view = new GameView(controller, model);
	
		this.setTitle("Reflex Game");	// Temporary name
		this.setSize(1000, 700);
		this.add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		model.setView(view);
		model.initialize();
		
		// Game updates with actionListener and timer
		
		ActionListener gameUpdater = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Game.this.update();
				Game.this.render();
			}
		};
		new Timer(30, gameUpdater).start();
	}
	
	public void update() {
		this.model.update();
	}
	
	public void render() {
		this.view.repaint();
	}
	
	public static void main(String[] args) {
		new Game();
	}
}