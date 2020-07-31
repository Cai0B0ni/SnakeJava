import java.awt.Color;
import java.awt.Graphics;

public class BodyPart {
	
	private int xCord, yCord, width, height;
	
	public BodyPart(int xCord, int yCord, int tileSize) {
		this.xCord = xCord;
		this.yCord = yCord;
		width = tileSize;
		height = tileSize;
		
	}
	
	public void tick() {
		
	}
	
	public void draw(Graphics g, int i) {
		if(i == 0) {
			g.setColor(Color.BLUE);

		}else {
			g.setColor(Color.YELLOW);
		}
		g.fillRect(xCord * width, yCord * height, width, height);
	}
	
	public void setyCord(int yCord) {
		this.yCord = yCord;
	}

	public int getxCord() {
		return xCord;
	}

	public void setxCord(int xCord) {
		this.xCord = xCord;
	}

	public int getyCord() {
		return yCord;
	}
	
}
