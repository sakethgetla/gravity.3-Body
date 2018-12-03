package gravity;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import static java.lang.Math.*;

import javax.swing.JFrame;
import javax.swing.JTextField;


public class movement extends Canvas implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Thread thread;
	boolean running = false, keyPush[] = new boolean[4];
	String name = "gravity";
	static int WIDTH = 1200;
	static int HEIGHT = WIDTH / 15 * 9;
	int ballHeight = WIDTH / 15;
	int moonHeight = WIDTH / 40;
	int sunWidth = WIDTH / 5;
	int sunHeight = sunWidth, ballWidth = ballHeight, moonWidth = moonHeight;
	int updateCounter = -1, stopButtonWidth = WIDTH / 12, stopButtonHeight = stopButtonWidth / 3,
			resetButtonWidth = stopButtonWidth, resetButtonHeight = stopButtonHeight;

	double[] futureSunCentre = new double[2];
	double[] futureBallCentre = new double[2];

	int mousexy[] = new int[2];
	boolean gravityON = false, button1 = false, button2 = false, p = false;;

	int[] sun_xy = new int[2], lineStartxy = new int[2], moonlineStartxy = new int[2], ballxy = new int[2],
			sunCentre = new int[2], ballCentre = new int[2], stopButton = new int[2], mouseMove_xy = new int[2],
			moonxy = new int[2], moonCentre = new int[2], resetButton = new int[2];

	int[] line1 = new int[51];
	int[] line2 = new int[51];
	int[] line3 = new int[51];
	int[] line4 = new int[51];
	int[] line5 = new int[51];
	int[] line6 = new int[51];

	double[] ballAccel = new double[2], ballVel = new double[2], sunAccel = new double[2], sunVel = new double[2],
			moonAccel = new double[2], moonVel = new double[2];
	double accelTot = 0.01, sun2moon = (sunWidth * sunHeight) / (moonHeight * moonWidth),
			sun2ball = (sunWidth * sunHeight) / (ballHeight * ballWidth),
			ball2moon = (ballWidth * ballHeight) / (moonHeight * moonWidth);

	public static void main(String args[]) {
		new movement("teacher");
		//new movement("student");

	}

	public movement(String name) {
		//reset();
		/*
		 * ballxy[0] = 200; ballxy[1] = 200; sun_xy[0] = (WIDTH - sunWidth) / 2;
		 * sun_xy[1] = (HEIGHT - sunHeight) / 2;
		 * 
		 * moonxy[0] = 500; moonxy[1] = 500;
		 * 
		 * ballVel[0] = 0; ballVel[1] = 0; ballCentre[0] = ballxy[0] + (ballWidth / 2);
		 * ballCentre[1] = ballxy[1] + (ballHeight / 2);
		 * 
		 * moonCentre[0] = moonxy[0] + (moonWidth / 2); moonCentre[1] = moonxy[1] +
		 * (moonHeight / 2);
		 * 
		 * futureBallCentre[0] = ballCentre[0]; futureBallCentre[1] = ballCentre[1];
		 * 
		 * lineStartxy[0] = ballCentre[0]; lineStartxy[1] = ballCentre[1];
		 * 
		 * sunCentre[0] = sun_xy[0] + (sunWidth / 2); sunCentre[1] = sun_xy[1] +
		 * (sunHeight / 2);
		 * 
		 * for (int i = 0; i <= 50; i++) {
		 * 
		 * line1[i] = ballCentre[0]; line2[i] = ballCentre[1]; line3[i] = sunCentre[0];
		 * line4[i] = sunCentre[1]; line5[i] = moonCentre[0]; line6[i] = moonCentre[1];
		 * }
		 * 
		 * 
		 */
		JFrame frame = new JFrame(name);
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		// frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(this);
		
		
		JTextField jt = new JTextField("type", 30) ;
		
		
		
		//frame2 studentWin  = new frame2(name);  

		
		
		addKeyListener(new UserInput(this));

		addMouseListener(new MouseInput(this));
		addMouseMotionListener(new MouseInput(this));

		// stopButtonWidth = this.getWidth() / 12 ;

		// stopButton[0] = this.getWidth()-stopButtonWidth -5;
		stopButton[0] = WIDTH - stopButtonWidth - 5;
		stopButton[1] = 5;

		resetButton[0] = 5;
		resetButton[1] = 5;

		start();
	}

	/*
	 * 
	 * public void keyPressed(KeyEvent e) { switch (e.getKeyCode()) {
	 * 
	 * case KeyEvent.VK_UP: keyPush[2] = true;
	 * 
	 * break; case KeyEvent.VK_DOWN: keyPush[3] = true;
	 * 
	 * break; case KeyEvent.VK_LEFT: keyPush[0] = true;
	 * 
	 * break; case KeyEvent.VK_RIGHT: keyPush[1] = true;
	 * 
	 * break; case KeyEvent.VK_SPACE: if (gravityON == false) { gravityON = true; }
	 * else { gravityON = false; } break;
	 * 
	 * } }
	 * 
	 * public void keyReleased(KeyEvent e) { switch (e.getKeyCode()) {
	 * 
	 * case KeyEvent.VK_UP:
	 * 
	 * keyPush[2] = false; break;
	 * 
	 * case KeyEvent.VK_DOWN: keyPush[3] = false; break;
	 * 
	 * case KeyEvent.VK_LEFT: keyPush[0] = false; break;
	 * 
	 * case KeyEvent.VK_RIGHT: keyPush[1] = false; break;
	 * 
	 * } }
	 */
	public synchronized void start() {

		thread = new Thread(this);
		thread.start();
		running = true;

	}

	private synchronized void stop() {

		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void run() {
		int Fps = 30, FPScount = 0;
		long milliSecTimer = System.currentTimeMillis(), compRefresh = 0;

		double before = System.nanoTime();
		double timeDiff = 0, now = 0, amountOfNanos = 0;

		while (running) {
			amountOfNanos = 1000000000 / Fps;
			now = System.nanoTime();
			timeDiff += (now - before) / amountOfNanos;
			before = now;
			while (timeDiff >= 1) {
				tick();
				timeDiff--;
				FPScount++;
			}
			compRefresh++;

			render();

			while (System.currentTimeMillis() - milliSecTimer > 1000) {
				milliSecTimer += 1000;
				System.out.println("comp> " + compRefresh + "   fps> " + FPScount + " " + ballxy[0] + " " + ballxy[1]);
				compRefresh = 0;
				FPScount = 0;
			}

		}
		stop();

	}

	private void render() {

		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;

		}

		Graphics g = bs.getDrawGraphics();

		///////////////////////

		graphics(g);

		///////////////////////

		g.dispose();
		bs.show();

	}

	private void graphics(Graphics g) {
	
		  g.setColor(Color.GRAY); g.fillRect(0, 0, WIDTH, HEIGHT);
		/*
		 * g.setColor(Color.ORANGE); g.fillOval(sun_xy[0], sun_xy[1], sunWidth,
		 * sunHeight);
		 * 
		 * g.setColor(Color.white); g.fillOval(moonxy[0], moonxy[1], moonWidth,
		 * moonHeight);
		 * 
		 * g.setColor(Color.BLUE); g.fillOval(ballxy[0], ballxy[1], ballWidth,
		 * ballHeight);
		 * 
		 * g.setColor(Color.black); g.fillRect((this.getWidth() / 2) - 3,
		 * (this.getHeight() / 2) - 3, 6, 6);
		 * 
		 * g.fillRect(lineStartxy[0] - 3, lineStartxy[1] - 3, 6, 6);
		 * g.fillRect(moonlineStartxy[0] - 3, moonlineStartxy[1] - 3, 6, 6);
		 * g.setColor(Color.WHITE); g.drawLine(lineStartxy[0], lineStartxy[1],
		 * ballCentre[0], ballCentre[1]); g.drawLine(moonlineStartxy[0],
		 * moonlineStartxy[1], moonCentre[0], moonCentre[1]);
		 * 
		 * g.setColor(Color.blue); g.drawPolyline(line1, line2, 51);
		 * g.setColor(Color.yellow); g.drawPolyline(line3, line4, 51);
		 * g.setColor(Color.white); g.drawPolyline(line5, line6, 51);
		 * 
		 * // g.drawLine(sunCentre[0], sunCentre[1], ballCentre[0] ,ballCentre[1]);
		 * g.fillRect(stopButton[0], stopButton[1], stopButtonWidth, stopButtonHeight);
		 * g.fillRect(resetButton[0], resetButton[1], resetButtonWidth,
		 * resetButtonHeight);
		 * 
		 * g.setColor(Color.blue); g.setFont(new Font("", Font.BOLD, 20));
		 * 
		 * 
		 * if (gravityON) { word = "stop"; } else { word = "start"; ; }
		 */
		String word;
		word = "hg";

		g.drawString(word, stopButton[0], stopButton[1] + stopButtonHeight);

		// g.setColor(Color.blue);
		// g.setFont(new Font("", Font.BOLD, 20));

		g.drawString("Reset", resetButton[0], resetButton[1] + resetButtonHeight);

		g.fillRect(stopButton[0], stopButton[1], stopButtonWidth, stopButtonHeight);
		g.fillRect(resetButton[0], resetButton[1], resetButtonWidth, resetButtonHeight);

	}

	private void tick() {

		
	}

	private void reset() {

		ballAccel[1] = 0;
		ballAccel[0] = 0;
		ballVel[0] = 0;
		ballVel[1] = 0;

		sunAccel[0] = 0;
		sunAccel[1] = 0;
		sunVel[1] = 0;
		sunVel[0] = 0;

		moonVel[0] = 0;
		moonVel[1] = 0;
		moonAccel[0] = 0;
		moonAccel[1] = 0;

		ballxy[0] = 200;
		ballxy[1] = 200;

		sun_xy[0] = (WIDTH - sunWidth) / 2;
		sun_xy[1] = (HEIGHT - sunHeight) / 2;

		moonxy[0] = 750;
		moonxy[1] = 250;

		sunCentre[0] = sun_xy[0] + (sunWidth / 2);
		sunCentre[1] = sun_xy[1] + (sunHeight / 2);

		ballCentre[0] = ballxy[0] + (ballWidth / 2);
		ballCentre[1] = ballxy[1] + (ballHeight / 2);

		moonCentre[0] = moonxy[0] + (moonWidth / 2);
		moonCentre[1] = moonxy[1] + (moonHeight / 2);

		lineStartxy[0] = ballCentre[0];
		lineStartxy[1] = ballCentre[1];

		moonlineStartxy[0] = moonCentre[0];
		moonlineStartxy[1] = moonCentre[1];

		for (int i = 0; i <= 50; i++) {

			line1[i] = ballCentre[0];
			line2[i] = ballCentre[1];
			line3[i] = sunCentre[0];
			line4[i] = sunCentre[1];
			line5[i] = moonCentre[0];
			line6[i] = moonCentre[1];
		}

	}

	public void mousePressed(MouseEvent e) {
		mousexy[0] = e.getX();
		mousexy[1] = e.getY();

		if (e.getButton() == MouseEvent.BUTTON1) {
			button1 = true;
			if (mousexy[0] > stopButton[0] && mousexy[0] < stopButton[0] + stopButtonWidth && mousexy[1] > stopButton[1]
					&& mousexy[1] < stopButton[1] + stopButtonHeight) {

				if (gravityON) {
					gravityON = false;

				} else {
					gravityON = true;
				}
			}

			if (mousexy[0] > resetButton[0] && mousexy[0] < resetButton[0] + resetButtonWidth
					&& mousexy[1] > resetButton[1] && mousexy[1] < resetButton[1] + resetButtonHeight) {
				reset();
				gravityON = false;
			}
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			button2 = true;
		}
	}

	public void mouseReleased(MouseEvent e) {
		mousexy[0] = e.getX();
		mousexy[1] = e.getY();
		if (e.getButton() == MouseEvent.BUTTON1) {

			p = false;
			button1 = false;

		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			button2 = false;
			System.out.println("clehf");
		}

	}

	public void mouseDragged(MouseEvent e) {
		mouseMove_xy[0] = e.getX();
		mouseMove_xy[1] = e.getY();
		if (gravityON == false) {

			if (pow(lineStartxy[0] - mouseMove_xy[0], 2) <= pow(ballWidth / 2, 2)
					&& (pow(lineStartxy[1] - mouseMove_xy[1], 2) <= pow(ballHeight / 2, 2))) {
				if (button1) {
					p = true;
					lineStartxy[0] = mouseMove_xy[0];
					lineStartxy[1] = mouseMove_xy[1];

				}

			} else if (pow(moonlineStartxy[0] - mouseMove_xy[0], 2) <= pow(moonWidth / 2, 2)
					&& (pow(moonlineStartxy[1] - mouseMove_xy[1], 2) <= pow(moonHeight / 2, 2))) {
				if (button1) {
					p = true;
					moonlineStartxy[0] = mouseMove_xy[0];
					moonlineStartxy[1] = mouseMove_xy[1];

				}

			}

			if (pow(ballCentre[0] - mouseMove_xy[0], 2) <= pow(ballWidth / 2, 2)
					&& (pow(ballCentre[1] - mouseMove_xy[1], 2) <= pow(ballHeight / 2, 2))) {
				if (button2) {
					ballCentre[0] = mouseMove_xy[0];
					ballCentre[1] = mouseMove_xy[1];
					lineStartxy[0] = mouseMove_xy[0];
					lineStartxy[1] = mouseMove_xy[1];
					for (int i = 0; i <= 50; i++) {

						line1[i] = ballCentre[0];
						line2[i] = ballCentre[1];

					}
				}
			} else if (pow(moonCentre[0] - mouseMove_xy[0], 2) <= pow(moonWidth / 2, 2)
					&& (pow(moonCentre[1] - mouseMove_xy[1], 2) <= pow(moonHeight / 2, 2))) {

				if (button2) {
					moonCentre[0] = mouseMove_xy[0];
					moonCentre[1] = mouseMove_xy[1];
					moonlineStartxy[0] = mouseMove_xy[0];
					moonlineStartxy[1] = mouseMove_xy[1];
					for (int i = 0; i <= 50; i++) {

						line5[i] = moonCentre[0];
						line6[i] = moonCentre[1];
					}
				}

			}
		}

	}

}
