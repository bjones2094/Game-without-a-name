import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;

public class GoldTarget extends Target {
	static Image goldImage;

	public GoldTarget(int x, int y, GameView view) {
		super(x, y, view);
		
		this.timeBeforeMove = 15;
		
		this.currentState = ClickObject.State.fast;
		
		try {
			if(goldImage == null) {
				goldImage = ImageIO.read(new File("img/goldTarget.png"));
			}
		}
		catch(Exception e) {
			throw new IllegalArgumentException("Failed to load file");
		}
		
		this.setImage(goldImage);
	}
	
	@Override
	public void isMissed() {
		this.timesMissed++;
		
		if(this.timesMissed == 15) {
			this.currentState = ClickObject.State.dead;
		}
		
		this.relocate();
	}
	
	@Override
	public void updateState() {
		// Do nothing
	}
}