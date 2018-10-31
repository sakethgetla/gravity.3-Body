package evolution;

import java.awt.Canvas;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Main extends Canvas implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean running = false, clockwise , antiClockwise , up = false, down = false, left = false, right = false;
	static int WIDTH = 900, HEIGHT = (WIDTH / 4) * 3;
	int boneWidth = 200, boneHeight = 50, counter = 0;
	Thread thread;

	double bone[][][] = new double[3][2][4], rotSpeed = 0.01, boneCentre[] = new double[2];
	double gradient, boneLoc[][] = new double[3][2], angle[] = new double[4], dist[] = new double[4];
	double[][] rotCentre = new double[3][2], pastBoneloc = new double[3][2];

	public static void main(String args[]) {
		new Main();

	}

	public Main() {
		angle[0] = 0;
		angle[1] = 0;
		angle[2] = 0;
		angle[3] = 0;
		boneLoc[0][0] = 0;
		boneLoc[0][1] = 0;

		for (int i = 0; i <= 2; i++) {
			for (int q = 0; q <= 3; q++) {
				bone[i][0][q] = 0;
				bone[i][1][q] = 0;
			}
		}

		String title = "evolution";

		JFrame frame = new JFrame(title);
		// /*
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(this);

		addKeyListener(new input(this));

		start();
	}

	private void start() {

		bone[0][0][0] = 300;
		bone[0][1][0] = 300;

		bone[0][0][1] = bone[0][0][0] + boneWidth;
		bone[0][1][1] = bone[0][1][0];

		bone[0][0][3] = bone[0][0][0];
		bone[0][1][3] = bone[0][1][0] + boneHeight;

		bone[0][0][2] = bone[0][0][0] + boneWidth;
		bone[0][1][2] = bone[0][1][0] + boneHeight;

		boneCentre[0] = (bone[0][0][0] + bone[0][0][2]) / 2;
		boneCentre[1] = (bone[0][1][0] + bone[0][1][2]) / 2;

		rotCentre[0][0] = boneCentre[0];
		rotCentre[0][1] = boneCentre[1];
		// rotCentre[0][0]= 500;
		// rotCentre[0][1]= 500;
		// boneCentre[0][0] = bone[0][0][1] - (boneWidth/2);
		// boneCentre[0][1] = bone[0][1][1] + (boneHeight/2);

		thread = new Thread();
		thread.start();
		running = true;
		run();
		// System.out.println("akwdh");

	}

	private void stop() {
		try {
			thread.join();
			running = false;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {

		int fps = 30, FPScounter = 0, compRefresh = 0;
		double then = System.nanoTime(), amountOfNanos = 1000000000 / fps, now, timeDiff,
				timer = System.currentTimeMillis();
		while (running) {

			now = System.nanoTime();
			timeDiff = now - then;
			while (timeDiff > amountOfNanos) {
				// System.out.println("akwdh");
				then += amountOfNanos;
				timeDiff = now - then;
				FPScounter++;
				tick();
			}
			render();
			compRefresh++;
			if (System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				System.out.printf("%s %s %s %s \n", "FPS>", FPScounter, "refresh Rate>", compRefresh);
				// System.out.println("FPS >" +FPScounter + " computer refresh
				// >" + compRefresh);
				FPScounter = 0;
				compRefresh = 0;
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
		// TODO Auto-generated method stub
		g.setColor(Color.BLUE);

		// for(int i =0 ;i <= 2 ; i++){

		int[] p = new int[4], o = new int[4];

		p[0] = (int) bone[0][0][0];
		o[0] = (int) bone[0][1][0];

		p[1] = (int) bone[0][0][1];
		o[1] = (int) bone[0][1][1];

		p[2] = (int) bone[0][0][2];
		o[2] = (int) bone[0][1][2];

		p[3] = (int) bone[0][0][3];
		o[3] = (int) bone[0][1][3];

		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.yellow);
		// g.drawOval((int) (bone[0][0][0] + bone[0][0][1]) /2 ,(int)
		// (bone[0][1][0] + bone[0][1][1]) /2, 5, 5);
		g.drawPolygon(p, o, 4);
		p[0] = (int) bone[1][0][0];
		o[0] = (int) bone[1][1][0];

		p[1] = (int) bone[1][0][1];
		o[1] = (int) bone[1][1][1];

		p[2] = (int) bone[1][0][2];
		o[2] = (int) bone[1][1][2];

		p[3] = (int) bone[1][0][3];
		o[3] = (int) bone[1][1][3];
		
		g.drawPolygon(p, o, 4);
		g.setColor(Color.pink);		
		g.fillOval((int) rotCentre[0][0], (int) rotCentre[0][1], 4, 4);

		// }
	}

	private void tick() {

		rotCentre[0][0] = boneCentre[0];
		rotCentre[0][1] = boneCentre[1];

		double distx, disty;

		// rotCentre = boneCentre ;

		// boneCentre[0][0] +=1 ;

		// gradient = (bone[0][1][0]- rotCentre[0][1]) / (bone[0][0][0]-
		// rotCentre[0][0]) ;

		rotSpeed = 0.1;
		
		for (int i = 0; i <= 3; i++) {
			distx = (rotCentre[0][0] - bone[0][0][i]);
			disty = (rotCentre[0][1] - bone[0][1][i]);
			dist[i] = Math.sqrt((distx * distx) + (disty * disty));
			// if (counter <= 3){
			// counter ++;
			angle[i] = Math.atan2(rotCentre[0][1] - bone[0][1][i], bone[0][0][i] - rotCentre[0][0]);

			// }else{
			if (clockwise) {
				angle[i] += rotSpeed;

			} else if (antiClockwise) {
				angle[i] -= rotSpeed;
			}
			
			
			// }
			// System.out.println( angle[i] +" "+Math.sin(angle[i]));
			bone[0][1][i] = rotCentre[0][1] - (dist[i] * Math.sin(angle[i]));
			bone[0][0][i] = rotCentre[0][0] + (dist[i] * Math.cos(angle[i]));
			if (angle[i] > 2 * Math.PI) {
				angle[i] -= 2 * Math.PI;
			} else if (angle[i] < (-2 * Math.PI)) {
				angle[i] += 2 * Math.PI;
			}
			
			
			
		}

		if (down == true) {
			boneCentre[1] += 5;
			movementBoneTop(5);
		}
		if (up == true) {
			boneCentre[1] -= 5;
			movementBoneTop(-5);
		}
		if (left == true) {
			boneCentre[0] -= 5;
			movementBoneLeft(-5);
		}
		if (right == true) {
			boneCentre[0] += 5;
			movementBoneLeft(5);
		}

		// rotCentre[0][0] +=1;
		// rotCentre[0][1] +=1;

		// System.out.println(dist *Math.cos(angle) +" "+ angle +" "+
		// (boneCentre[0][1] - bone[0][1][0])) ;

		/*
		 * bone[0][1][2] = boneCentre[0][1] + (dist * Math.sin(angle));
		 * bone[0][0][2] = boneCentre[0][0] - (dist * Math.cos(angle));
		 * 
		 * angle = Math.atan2( rotCentre[0][1] - bone[0][1][1] , bone[0][0][1]-
		 * rotCentre[0][0] ) ; angle += 0.1 ;
		 * 
		 * bone[0][1][1] = boneCentre[0][1] - (dist * Math.sin(angle));
		 * bone[0][0][1] = boneCentre[0][0] + (dist * Math.cos(angle));
		 * 
		 * 
		 * bone[0][1][3] = boneCentre[0][1] + (dist * Math.sin(angle));
		 * bone[0][0][3] = boneCentre[0][0] - (dist * Math.cos(angle));
		 * 
		 */

	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

		if (e.getKeyCode() == KeyEvent.VK_A) {
			clockwise = true;

		}
		
		if (e.getKeyCode() == KeyEvent.VK_S) {
			antiClockwise = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			up = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = true;
		}

	}

	private void movementBoneLeft(int move) {

		bone[0][0][0] += move;
		bone[0][0][1] += move;
		bone[0][0][2] += move;
		bone[0][0][3] += move;

	}

	private void movementBoneTop(int move) {

		bone[0][1][0] += move;
		bone[0][1][1] += move;
		bone[0][1][2] += move;
		bone[0][1][3] += move;

	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A){
			clockwise = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_S){
			antiClockwise = false;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = false;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			up = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = false;
		}

	}

	public void keyPushed(KeyEvent e) {

	}

}
