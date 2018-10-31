package game;

import java.awt.Canvas;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.image.BufferStrategy;
import java.util.Random;
import java.util.Stack;

import javax.swing.JFrame;


public class Main extends Canvas  implements Runnable{
	
	private static final int HEIGHT = 600 , WIDTH = (HEIGHT /9) *16 ;
	
	
	private static final long serialVersionUID = 1L;
	private Thread thread ;
	public boolean running = false ;
	private String name = " PREFIX-SUFFIX";
	Random random = new Random();
		
	int  [][] textBoxLoc = new int [10][2] , textBoxVel = new int [10][2], pastTextBoxLoc = new int [10][2] ; 
	//int[] panelWidth = new int [4] ; 
	int textBoxSpeed = 1  , panelWidth = 0, panelHeight = 0  ,textBoxHeight = HEIGHT/10,
			textBoxWidth = HEIGHT/10, shadowThis = 0 , shadowBefore =0  ;
	// Stack<int[]> pastLoc = new Stack<int[]>();
	int [] beforeLoc = new int[2], nowLoc = new int[2] , storageLoc = new int[2] ;
	String [] floatingLetters = new String[10] ;
	int [] panelXY = new int[2] ;
	
	public Main(){
		
		JFrame frame = new JFrame(name);
		frame.setPreferredSize(new Dimension( WIDTH, HEIGHT));
		frame.setMaximumSize(new Dimension( WIDTH, HEIGHT));
		frame.setMinimumSize(new Dimension( WIDTH, HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(this);
		
		panelWidth = WIDTH /3;
		panelHeight = HEIGHT /7;
		panelXY[0] = (WIDTH/2) - (panelWidth/2) ;
		panelXY[1] = (HEIGHT/2) - (panelHeight/2) ;
		/*
		MidWindow midWindow = new MidWindow();
		
		midWindow = panelWidth;
		panelHeight = HEIGHT /7;
		
		*/
		/*
		for (int i= 0; i <= 9 ; i++ ){
			public void paint(graphics g){
				Font floatingLetters[i] = new Font(""+i,Font.BOLD,35) ; 
					
			}
		}
		*/
		
		TextBox [] textBox = new TextBox[10];
		
		
		
		
		// /* 
		for (int i= 0; i <= 9 ; i++ ){
			
			
			
			floatingLetters[i] = ""+i;
			textBoxVel[i][0] = textBoxSpeed ;
			textBoxVel[i][1] = textBoxSpeed ;
			
			if (i <= 4) {

				textBoxLoc[i][0] = (WIDTH  / 5) * (i) ;
			}
			else if (i >= 5) {
				textBoxLoc[i][0] = (WIDTH  / 5) * (i-5) ;
			}
			
			if (i % 2 == 0) {
				textBoxLoc[i][1] = (HEIGHT  / 4 ) ;
			}else if (i % 2 == 1){
				textBoxLoc[i][1] = (HEIGHT  / 4 ) *3 ;
			}
			nowLoc[0] = textBoxLoc[i][0];
			nowLoc[1] = textBoxLoc[i][1];
			int r = random.nextInt(4);
			
			if (r == 1){
				textBoxVel[i][0] *= -1;
			}
			if (r== 2){
				textBoxVel[i][1] *= -1;
			}
				
		} 
		 // */
		
		start();
	}

	public synchronized void start(){
		
		thread = new Thread(this);
		thread.start();
		running = true ;
		
	}
	private synchronized void stop(){
		
		try {
			thread.join();
			running = false;
		}catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	public static void main(String args[]) {
		new Main();
		
		
	}

	public void run() {
		
		int Fps = 40 , FPScount =0 ;
		long milliSecTimer = System.currentTimeMillis() , compRefresh =0 ;
		
		double before = System.nanoTime();
		double timeDiff = 0 , now = 0 , amountOfNanos = 0 ;
		
		while (running) {
			amountOfNanos = 1000000000 / Fps ;
			now = System.nanoTime();
			timeDiff += (now - before)/ amountOfNanos ;
			before = now ;
			while ( timeDiff >= 1){
				tick();
				timeDiff--;
				FPScount ++;
			}
			compRefresh ++ ;
			if (running) render() ;
			
			while (System.currentTimeMillis() - milliSecTimer > 1000 ){
				milliSecTimer +=1000 ;
				System.out.println("comp> " + compRefresh + "   fps> " +FPScount+"  "+ random.nextInt(4));
				compRefresh =0 ;
				FPScount =0;
				
				textBoxVel[random.nextInt(10)][0] *= -1;
				textBoxVel[random.nextInt(10)][1] *= -1;
				
			}
		   
		} stop(); 
	}
	
	private void render() {
	
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null){
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
		
		g.setColor(Color.gray);
		g.fillRect(0, 0,WIDTH , HEIGHT );
		
		g.setColor(Color.black);
		g.fillRect(panelXY[0] , panelXY[1],panelWidth, panelHeight);
		//g.fillArc(50, 50, 100, 100, 50, 150);
		
		/*
		TextBox textBox = new textBox 
		textBoxLoc = TextBox.class 
		*/
		
		for (int i = 0 ; i <= 9 ; i++){
			
			
			g.setColor(Color.green);
			g.fillRect(textBoxLoc[i][0], textBoxLoc[i][1], textBoxWidth   , textBoxHeight);
			
			g.setColor(Color.black);
			g.setFont(new Font("ak",Font.BOLD , 60 ));
			g.drawString(floatingLetters[i],textBoxLoc[i][0], textBoxLoc[i][1] + HEIGHT /10);
			
			g.setColor(Color.white);
			g.drawLine(textBoxLoc[2][0], textBoxLoc[2][1], textBoxLoc[1][0], textBoxLoc[1][1]);
			g.drawLine(textBoxLoc[2][0], textBoxLoc[2][1], textBoxLoc[3][0], textBoxLoc[3][1]);
			//g.setFont(new Font());
			
				

			for (int p = 0 ;p <= 5; p++ ){
				
				if ( p ==0 ){
				
					shadowBefore = textBoxWidth ;
					beforeLoc[0] = pastTextBoxLoc[i][0];
					beforeLoc[1] = pastTextBoxLoc[i][1];
				}
				
			
				else{
					shadowThis = shadowBefore / 2 ;
					storageLoc[0] =nowLoc[0]  ;
					nowLoc[0] = beforeLoc[0] ;
					beforeLoc[0] = storageLoc[0] ;
					
					storageLoc[1] = nowLoc[1];
					nowLoc[1] = beforeLoc[1] ;
					beforeLoc[1] = storageLoc[1] ;
					
					g.fillRect( nowLoc[0], nowLoc[1] , 50,50);
				}
			}
			
		}
	
	}

		
	
	private void tick() {
		double totVel =  0 ;
		
			
		
		for(int i =0 ; i<=9 ; i++){
		//TextBox.TextBox(args);	
		// /*
		//	totVel = Math.sqrt((textBoxVel[i][0])^2 + (textBoxVel[i][1])^2);
			
		if (textBoxLoc[i][0] > this.getWidth() - textBoxWidth ||  textBoxLoc[i][0] < 0 )    {
			textBoxVel[i][0] *= (-1) ;
			
		}
		
		if (textBoxLoc[i][1] > this.getHeight() - textBoxHeight  ||  textBoxLoc[i][1] < 0 )    {
			textBoxVel[i][1] *= (-1) ;
			
		}
		
		
			
			
		pastTextBoxLoc[i][0] = textBoxLoc[i][0];
		pastTextBoxLoc[i][1] = textBoxLoc[i][1];
		textBoxLoc[i][0] += textBoxVel[i][0]; 	
		textBoxLoc[i][1] += textBoxVel[i][1];
		// */
		
		}
				
	}

	
}
