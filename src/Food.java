import java.awt.Color;
import java.awt.Graphics;

public class Food {
	
	private int xCord, yCord, width, height;
	
	public Food(int xCord, int yCord, int tileSize) {
		this.xCord = xCord;
		this.yCord = yCord;
		width = tileSize;
		height = tileSize;
		
	}
	
	public void tick() {
		
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(xCord * width, yCord * height, width, height);
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

	public void setyCord(int yCord) {
		this.yCord = yCord;
	}
	
	
	
	
}
