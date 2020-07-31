import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Gamepanel extends JPanel implements Runnable, KeyListener{
	
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 600, HEIGHT = 600;
	private boolean running;
	private boolean right = true, left = false, up = false, down = false;
	private Thread thread;
	private BodyPart b;
	private ArrayList<BodyPart> snake;
	private int xCord = 10, yCord = 10, size = 5;
	private int ticks = 0;
	private Food food;
	private ArrayList<Food> meal;
	private int totalMeal = 0;
	private Random rand;
	private boolean gameOver = false;
	private boolean help = false;
	private boolean settings = false;
	private boolean menu = true;
	private String[] menuList = {"PLAY", "HELP", "SETTINGS"};
	private int selectedMenuItem = 0;
	
	public Gamepanel() {
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		addKeyListener(this);

		snake = new ArrayList<BodyPart>();
		meal = new ArrayList<Food>();
		
		rand = new Random();
		
		start();
	}

	public void start() {
		thread = new Thread(this);
		thread.start();
	}
	
	public void stop() {
		
		running = false;
		gameOver = true;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void tick() {
		if(snake.size() == 0) {
			b = new BodyPart(xCord, yCord, 10);
			snake.add(b);
		}
		ticks++;
		if(ticks > 500000) {
			System.out.println(snake.size());
			if(right) xCord++;
			if(left) xCord--;
			if(up) yCord--;
			if(down) yCord++;
			ticks = 0;
			b = new BodyPart(xCord, yCord, 10);
			snake.add(b);
			
			if(snake.size() > size) {
				snake.remove(0);
			}
		}
		
		if(meal.size() == 0) {
			int xCord = rand.nextInt(59);
			int yCord = rand.nextInt(59);
			
			food = new Food(xCord, yCord, 10);
			meal.add(food);
		}
		
		for(int i = 0; i < meal.size(); i++) {
			if(xCord == meal.get(i).getxCord() && yCord == meal.get(i).getyCord()) {
				totalMeal++;
				size++;
				meal.remove(i);
			}
		}
		
		if(xCord < 0 || yCord < 0 || xCord > 59 || yCord > 59) {
			stop();
		}
		
		for(int i = 0; i < snake.size(); i++) {
			if(xCord == snake.get(i).getxCord() && yCord == snake.get(i).getyCord()) {
				if(i != snake.size() - 1) {
					stop();
				}
			}
		}
	}
	
	public void paint(Graphics g) {
		if(running) {
			gamePaint(g);
		}else if(help) {
			helpPaint(g);
		}else if(settings) {
			settingsPaint(g);
		}else {
			menu(g);
		}
	}

	private void menu(Graphics g) {
		g.clearRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		for(int i = 0; i < menuList.length; i++) {
			if(selectedMenuItem == i) {
				g.setColor(Color.YELLOW);
			}else {
				g.setColor(Color.RED);
			}
			g.setFont(new Font("Purisa", Font.PLAIN, 48));
			g.drawString(menuList[i], WIDTH / 3, (i+1) * 100 + 100);			
		}
	}  

	private void gamePaint(Graphics g) {
		g.clearRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		for(int i = 0 ; i < WIDTH/10 ; i++) {
			g.drawLine(i * 10, 0, i*10, HEIGHT);
		}
		
		for(int i = 0 ; i < WIDTH/10 ; i++) {
			g.drawLine(0, i * 10, HEIGHT, i * 10);
		}
		for(int i = 0; i < snake.size(); i++) {
			snake.get(i).draw(g, i);
		}
		for(int i = 0; i < meal.size(); i++) {
			meal.get(i).draw(g);
		}
		
        g.setFont(new Font("Purisa", Font.PLAIN, 24));
		g.drawString("Points: " + totalMeal, 20, 30);

		if(gameOver) {
	        g.setFont(new Font("Purisa", Font.PLAIN, 50));
			g.drawString("Game Over Loser", WIDTH/ 2 - 200, 200);
		}		
	}

	private void settingsPaint(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	private void helpPaint(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		while(running) {
			tick();
			repaint();
		}
		
	}
	
	public void keyPressed(KeyEvent e) {
		if(menu) {
			int key = e.getKeyCode();
			System.out.println(selectedMenuItem);

			if(key == KeyEvent.VK_DOWN) {
				System.out.println("22");

				if(selectedMenuItem == menuList.length -1) {
					selectedMenuItem = 0;
				}else {
					System.out.println("down");

					selectedMenuItem++;
				}
			}else if(key == KeyEvent.VK_UP) {
				System.out.println("up");

				if(selectedMenuItem == 0) {
					selectedMenuItem = menuList.length - 1;
				}else {
					selectedMenuItem--;
				}
			}
			
		}else {
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_RIGHT && !left && snake.get(snake.size() - 1).getyCord() != snake.get(snake.size() - 2).getyCord()) {
				right = true;
				up = false;
				down = false;
			}
			if(key == KeyEvent.VK_LEFT && !right && snake.get(snake.size() - 1).getyCord() != snake.get(snake.size() - 2).getyCord()) {
				left = true;
				up = false;
				down = false;
			}
			if(key == KeyEvent.VK_UP && !down && snake.get(snake.size() - 1).getxCord() != snake.get(snake.size() - 2).getxCord()) {
				up = true;
				left = false;
				right = false;
			}
			if(key == KeyEvent.VK_DOWN && !up && snake.get(snake.size() - 1).getxCord() != snake.get(snake.size() - 2).getxCord() ) {
				down = true;
				left = false;
				right = false;
			}	
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
