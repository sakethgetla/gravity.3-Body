package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;


public class Main extends Canvas implements Runnable{
	
	private static final int HEIGHT = 600 , WIDTH = (HEIGHT /9) *16 ;
	
	
	private static final long serialVersionUID = 1L;
	private Thread thread ;
	public boolean running = false ;
	private String name = " PREFIX-SUFFIX";
	int  [][] textBoxLoc = new int [10][2] , textBoxVel = new int [10][2] , pastTextBoxLoc = new int[10][2] ;
	int [] movement = new int[4]  , targetLoc = new int[2];
	int dirRight = 3 , dirLeft =2 , dirDown = 1 , dirUp = 0 , selectionNo = 0 , Xval=0 ;
	Random random = new Random();
	double then [] = new double[4];

	
	
	int textBoxSpeed = 1 ,panelWidth = 0 , panelHeight = 0 , textBoxWidth = HEIGHT /10 ,
			textBoxHeight = HEIGHT /10,	targetAccel[] = new int [4] , targetVel = textBoxSpeed *2;
	
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
		
		addKeyListener(new PlayerInput(this));

		
		
		
		panelWidth = WIDTH /4;
		panelHeight = HEIGHT /7;
		
		targetAccel[0] = 1 ;
		targetAccel[1] = 1 ;
		targetAccel[2] = 1 ;
		targetAccel[3] = 1 ;
	
			
		panelXY[0] = (WIDTH/2) - (panelWidth/2) ;
		panelXY[1] = (HEIGHT/2) - (panelHeight/2) ;
		
		/*
		for (int i= 0; i <= 9 ; i++ ){
			public void paint(graphics g){
				Font floatingLetters[i] = new Font(""+i,Font.BOLD,35) ; 
					
			}
		}
		*/
		 
		for (int i= 0; i <= 9 ; i++ ){
			
			
			floatingLetters[i] = ""+i;
			textBoxVel[i][0] = textBoxSpeed ;
			textBoxVel[i][1] = textBoxSpeed ;
			int rr = random.nextInt(4);
			
			switch (rr){
		
				case 0 :
					textBoxVel[i][0] *= -1 ;
				break;
				case 1 :
					textBoxVel[i][1] *= -1 ;
				break;
				
			}
				
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
				
		}
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
				System.out.println("comp> " + compRefresh + "   fps> " +FPScount +"  ");
				compRefresh =0 ;
				FPScount =0;
				
//				textBoxVel[random.nextInt(10)][0] *= -1;
//				textBoxVel[random.nextInt(10)][1] *= -1;
				
			}
			
		   
		} stop(); 
	}
	
	private void render() {
	
		BufferStrategy bs = getBufferStrategy();
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

		g.setColor(Color.gray);
		g.fillRect(0, 0,WIDTH , HEIGHT );
		
		g.setColor(Color.black);
		g.fillRect(panelXY[0], panelXY[1],panelWidth, panelHeight);
		//g.fillArc(50, 50, 100, 100, 50, 150);
		
		for (int i = 0 ; i <= 9 ; i++){
			
			
			g.setColor(Color.green);
			//if (textBoxSelected[i]) {
			if (selectionNo == i ){
				g.setColor(Color.BLUE);
			}
			g.fillRect(textBoxLoc[i][0], textBoxLoc[i][1], textBoxWidth ,textBoxHeight );
			
			g.setColor(Color.black);
			g.setFont(new Font("", Font.BOLD, 70 ));
			g.drawString(floatingLetters[i],textBoxLoc[i][0], textBoxLoc[i][1] + HEIGHT /10);
			
			g.setColor(Color.blue);
			g.drawOval(targetLoc[0], targetLoc[1], textBoxWidth, textBoxHeight);
			//g.drawLine(textBoxLoc[1][0],textBoxLoc[1][1], textBoxLoc[0][0], textBoxLoc[0][1]);
			//g.setFont(new Font());
			
			
		}
	
	}

	
	
	private void tick() {
		
		for(int i =0 ; i<=9 ; i++){
			pastTextBoxLoc [i][0] = textBoxLoc[i][0] ;
			pastTextBoxLoc [i][1] = textBoxLoc[i][1] ;
			
			textBoxLoc[i][0] += textBoxVel[i][0]; 	
			textBoxLoc[i][1] += textBoxVel[i][1];
		
			// bounce off walls
			if (textBoxLoc[i][0] > this.getWidth() - textBoxWidth ||  textBoxLoc[i][0] < 0 ){
					
				textBoxVel[i][0] *= (-1) ;
				
			}
			if (textBoxLoc[i][0] > panelXY[0] - textBoxWidth  && textBoxLoc[i][0] < panelXY[0] + panelWidth 
					&& textBoxLoc[i][1] > panelXY[1] - textBoxHeight && textBoxLoc[i][1] < panelXY[1] + panelHeight) {
				
				if (pastTextBoxLoc[i][0] > panelXY[0] + panelWidth ||pastTextBoxLoc[i][0] < panelXY[0] - textBoxWidth) {
					textBoxVel[i][0] *= -1 ;
				}else if (pastTextBoxLoc[i][1] > panelXY[1] + panelHeight ||pastTextBoxLoc[i][1] < panelXY[1] - textBoxHeight) {
					textBoxVel[i][1] *= -1 ;
				}
			}
			
			if (textBoxLoc[i][1] > this.getHeight() - textBoxHeight ||  textBoxLoc[i][1] < 0 )    {
				textBoxVel[i][1] *= (-1) ;
				
			}
			
			
			// bounce off each other
			
			for (int j = 0 ; j<= 9; j++){
				if(textBoxLoc[i][0] <= textBoxLoc[j][0] &&  textBoxLoc[j][0] <= textBoxLoc[i][0] + textBoxWidth){
					if((textBoxLoc[i][1] <= textBoxLoc[j][1] &&  textBoxLoc[j][1] <= textBoxLoc[i][1] + textBoxHeight)
							|| (textBoxLoc[i][1] <= textBoxLoc[j][1] + textBoxHeight &&  textBoxLoc[j][1] + textBoxHeight <= textBoxLoc[i][1] + textBoxHeight)){
						if (intersection(j,i)){ 
						//( textBoxLoc[j][1] + textBoxHeight > intersection(i,j)&& intersection(i,j) > textBoxLoc[j][1]){
							textBoxVel[j][0] *= -1;
							textBoxVel[i][0] *= -1;
						}else{	
							textBoxVel[j][1] *= -1;
							textBoxVel[i][1] *= -1;
						
							
						}
					}
				}
			}
			
		}
				
		
		// movement of the target and its acceleration 
// /*

		if (movement[dirUp] == 1){
			goUp();
			
			if (then[dirUp] == 0 ){
				then[dirUp] = System.currentTimeMillis();

			}
			if ( System.currentTimeMillis() - then[dirUp]  >= 1000/3){
				targetAccel[dirUp] += 1  ;
				then[dirUp] += 1000/3 ;

			}
			
		} else{
			then[dirUp] = 0 ;
			targetAccel[dirUp] = 1 ;
		}
		
		if (movement[dirDown] == 1){
			goDown();
			
			if (then[dirDown] == 0 ){
				then[dirDown] = System.currentTimeMillis();
		
			}
			if ( System.currentTimeMillis() - then[dirDown]  >= 1000/3){
				targetAccel[dirDown] += 1  ;
				then[dirDown] += 1000/3 ;
			}
			
		} else{
			then[dirDown] = 0 ;
			targetAccel[dirDown] = 1 ;
		}
		
		
		if (movement[dirLeft] == 1){
			goLeft();
			
			if (then[dirLeft] == 0 ){
				then[dirLeft] = System.currentTimeMillis();

			}
			if ( System.currentTimeMillis() - then[dirLeft]  >= 1000/3){
				targetAccel[dirLeft] += 1  ;
				then[dirLeft] += 1000/3 ;

			}
			
		} else{
			then[dirLeft] = 0 ;
			targetAccel[dirLeft] = 1 ;
		}
		
		
		if (movement[dirRight] == 1){
			goRight();
			
			if (then[dirRight] == 0 ){
				then[dirRight] = System.currentTimeMillis();

			}
			if ( System.currentTimeMillis() - then[dirRight]  >= 1000/3){
				targetAccel[dirRight] += 1  ;
				then[dirRight] += 1000/3 ;

			}
		} else{
			then[3] = 0 ;
			targetAccel[dirRight] = 1 ;
		}
 //*/
  /*
		if (movement[dirUp] == 1){
			goUp();
		}
		if (movement[dirDown] == 1){
			goDown();
		}
		if (movement[2] == 1){
			goLeft();
			
		}
		if (movement[3] == 1){
			goRight();
		}		 
  */
		
	}
	
	
	private boolean intersection( int boxNo2 ,int boxNo1) {
		
		int X1p = pastTextBoxLoc[boxNo1][0], Y1p = pastTextBoxLoc[boxNo1][1], 
				X2 = textBoxLoc[boxNo2][0] , Y2 = textBoxLoc[boxNo2][1] , 
				X1 = textBoxLoc[boxNo1][0] , Y1 = textBoxLoc[boxNo1][1] ;
		double Yval = 0 , gradient = 0 ;
		boolean sideIntersect = false , bottomIntersect = false ;
		
		gradient = (Y1p - Y1) /(X1p - X1);
		Yval = (gradient*(X2+ textBoxWidth - X1p))+ Y1p ;
		
		
		//while (sideIntersect == false && bottomIntersect == false) {
			if ((Yval > Y2 -  textBoxHeight && Y2 + textBoxHeight < Yval)){
				//(Yval > Y2 -  textBoxHeight  && Y2 + textBoxHeight < Yval)||(Yval+3 > Y1 -  textBoxHeight && Y1 + textBoxHeight < Yval+3)||(Yval-3 -  textBoxHeight > Y1 && Y1 + textBoxHeight < Yval-3)
				System.out.println(boxNo1+" " +gradient+" "+ Yval);
				
				sideIntersect = true;
			}
			/*if (){
				bottomIntersect = true;
			}*/
		//}
		return sideIntersect ;
		
	}
	
	private void goRight() {
		targetLoc[0] += targetVel * targetAccel[dirRight] ;
		
	}

	private void goLeft() {
		targetLoc[0] -= targetVel * targetAccel[dirLeft];
		
	}

	private void goDown() {
		targetLoc[1] += targetVel * targetAccel[dirDown];
		
	}

	private void goUp() {
		targetLoc[1] -= targetVel * targetAccel[dirUp];
		
	}

	public void keyPressed(KeyEvent e) {
		
		switch (e.getKeyCode()) {

		case KeyEvent.VK_UP:
			movement[0] = 1;
			

			break;
		case KeyEvent.VK_W:
			movement[0] = 1;
			break;

		case KeyEvent.VK_DOWN:
			movement[1] = 1;
			break;
			
		case KeyEvent.VK_S:
			movement[1] = 1;
			break;

		case KeyEvent.VK_LEFT:
			movement[2] = 1;
			break;
			
		case KeyEvent.VK_A:
			movement[2] = 1;
			break;

		case KeyEvent.VK_RIGHT:
			movement[3] = 1;
			break;
		case KeyEvent.VK_D:
			movement[3] = 1;
			break;
		case KeyEvent.VK_SPACE:
			for (int i = 0 ; i <=9; i++){
				if (targetLoc[0] > textBoxLoc[i][0] - textBoxWidth && targetLoc[0] < textBoxLoc[i][0] + textBoxWidth ) {
					if (targetLoc[1] > textBoxLoc[i][1] - textBoxHeight && targetLoc[1] < textBoxLoc[i][1] + textBoxHeight ) {
						selectionNo = i ;
					}
				}
		
			}
		}

	}

	public void keyReleased(KeyEvent e) {

		switch (e.getKeyCode()) {

		case KeyEvent.VK_UP:
			movement[0] = 0;
			
			break;
		case KeyEvent.VK_W:
			movement[0] = 0;

			break;

		case KeyEvent.VK_DOWN:
			movement[1] = 0;
			break;
		case KeyEvent.VK_S:
			movement[1] = 0;
			break;

		case KeyEvent.VK_LEFT:
			movement[2] = 0;
			break;
		case KeyEvent.VK_A:
			movement[2] = 0;
			break;

		case KeyEvent.VK_RIGHT:
			movement[3] = 0;
			break;
		case KeyEvent.VK_D:
			movement[3] = 0;
			break;

		}
	}

/*
	 public void render() {
	        BufferStrategy b = null;
	        if (super.getBufferStrategy() == null) {
	            super.createBufferStrategy(3);
	        }
	        b = super.getBufferStrategy();
	        Graphics2D g = (Graphics2D) b.getDrawGraphics();
	        g.setColor(Color.BLACK);
	        g.fillRect(0,  0, getWidth(), getHeight());
	        g.dispose();
	        b.show();
	    }
*/
	
}
