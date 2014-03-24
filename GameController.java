/*
 * Controller handles all inputs from the user
 * Currently only handles mouse clicks using MouseListener interface
 */

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class GameController implements MouseListener {
	public GameModel model;

	public GameController(GameModel m) {
		this.model = m;
	}
	
	public void mousePressed(MouseEvent e) {
		this.model.mouseClicked(e.getX(), e.getY());
	}
	
	public void mouseReleased(MouseEvent e) {    }
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }
}