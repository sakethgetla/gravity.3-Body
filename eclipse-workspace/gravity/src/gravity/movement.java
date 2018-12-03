package gravity;

import java.awt.Canvas;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;



public class movement extends Canvas implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Thread thread ;
	boolean running = false , keyPush[] = new boolean[4] ;
	String name = "gravity";
	int WIDTH = 900 ,HEIGHT = WIDTH/12 *9 , ballHeight = WIDTH/30, ballWidth= WIDTH/30 ,  
			sunWidth = WIDTH/12, sunHeight = WIDTH/12, accelTot = 1 ;
	boolean gravityON = false;
	
	int[] sun_xy = new int[2], lineStartxy = new int[2], lineEndxy = new int[2], ballxy  = new int [2],
			sunCentre = new int[2] , ballCentre = new int[2];
	
	double [] ballAccel = new double[2] , ballVel = new double[2] , sunAccel = new double[2],
			sunVel = new double[2];
	double totDist;
	public static void main(String args[]) {
		new movement();
				
	}
	private movement (){
		
		ballxy[0] = 200 ;
		ballxy[1] = 200 ;
		
		ballVel[0] = 0 ;
		ballVel[1] = 0;
		ballCentre[0] = ballxy[0] + (ballWidth/2) ;
		ballCentre[1] = ballxy[1] + (ballHeight/2) ;
			
		lineStartxy[0] =ballCentre[0]; 
		lineStartxy[1] = ballCentre[1];
		
		sun_xy[0] = (WIDTH  - sunWidth)/2; 
		sun_xy[1] = (HEIGHT  - sunHeight)/2;
		
		JFrame frame = new JFrame(name);
		frame.setPreferredSize(new Dimension( WIDTH, HEIGHT));
		frame.setMaximumSize(new Dimension( WIDTH, HEIGHT));
		frame.setMinimumSize(new Dimension( WIDTH, HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(this);
		
		addKeyListener(new UserInput(this));

		
		start();
	}
	
	public void keyPressed(KeyEvent e){
		switch (e.getKeyCode()) {
		
		case KeyEvent.VK_UP :
			keyPush[2]= true ;

			break;
		case KeyEvent.VK_DOWN :
			keyPush[3]= true ;

			break;
		case KeyEvent.VK_LEFT :
			keyPush[0]= true ;

			break;
		case KeyEvent.VK_RIGHT :
			keyPush[1]= true ;

			break;
		
		}		
	}
	public void keyReleased(KeyEvent e){
		switch (e.getKeyCode()) {
		
		case KeyEvent.VK_UP :
			
			keyPush[2]= false;
			break;
			
		case KeyEvent.VK_DOWN :
			keyPush[3]= false ;
			break;
			
		case KeyEvent.VK_LEFT :
			keyPush[0]= false ;
			break;
			
		case KeyEvent.VK_RIGHT :
			keyPush[1]= false;
			break;
				
		}		
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
	
	
	public void run() {
		int Fps = 25 , FPScount =0 ;
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
				System.out.println("comp> " + compRefresh + "   fps> " +FPScount +" "+ ballxy[0] +" "+ ballxy[1]);
				compRefresh =0 ;
				FPScount =0;
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
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.YELLOW);
		g.fillOval(sun_xy[0] ,sun_xy[1] , sunWidth, sunHeight);
		
		g.setColor(Color.blue);
		g.fillOval(ballxy[0], ballxy[1], ballWidth, ballHeight);
		
		g.setColor(Color.black);
		g.fillRect((this.getWidth()/2)- 3,(this.getHeight()/2)- 3 ,6 ,6);
		g.setColor(Color.WHITE);
		g.drawLine(lineStartxy[0] , lineStartxy[1] , ballCentre[0] ,ballCentre[1]);
		//g.drawLine(sunCentre[0], sunCentre[1], ballCentre[0] ,ballCentre[1]);
	}
	
	private void tick() {
		int counter =0 ;
		double sun2ballRatio = (sunHeight*sunWidth) /(ballWidth * ballHeight) ;
		double line2Vel = 0.25  ;
		
		
		
		
		if (keyPush[0]){
			gravityON = false;
			lineStartxy[0] -= 3 ; 
		}else if (keyPush[1]){
			gravityON = false;
			lineStartxy[0] += 3; 
		}
		if (keyPush[2]){
			gravityON = false;
			lineStartxy[1] -= 3 ; 
		}else if (keyPush[3]){
			gravityON = false;
			lineStartxy[1] += 3; 
		}
		
// /*
		
		counter =0;
// */
		
		
		sun_xy[0] = (this.getWidth()  - sunWidth)/2; 
		sun_xy[1] = (this.getHeight()  - sunHeight)/2;
		sunCentre[0] =  sun_xy[0] + (sunWidth/2);
		sunCentre[1] = sun_xy[1] + (sunHeight/2);
		
		ballCentre[0] = ballxy[0] + (ballWidth/2) ;
		ballCentre[1] = ballxy[1] + (ballHeight/2) ;
		
		if (gravityON && (ballVel[0] != 0 || ballVel[1] != 0)){
			
			
			
			double distX = (sunCentre[0] - ballCentre[0]) ;
			double distY = (sunCentre[1] - ballCentre[1]) ;
			totDist = Math.sqrt((distX*distX) + (distY*distY)) ;
			
// ball movement/////////////////////////
			ballAccel[0] = (accelTot* sun2ballRatio ) *(distX / totDist );
			ballAccel[1] = (accelTot* sun2ballRatio ) *(distY / totDist);
			//System.out.println(ballAccel[0]+" "+ ballAccel[1]);
			
			ballVel[0] += ballAccel[0] ;
			ballVel[1] += ballAccel[1] ;
			
			ballCentre[0] += ballVel[0];
			ballCentre[1] += ballVel[1];
			//System.out.println(ballVel[0]+" "+ ballVel[1]);	
			
			//System.out.println(ballxy[0]+" "+ ballxy[1]);	

			ballxy[0] = ballCentre[0] - (ballWidth/2) ;
			ballxy[1] = ballCentre[1] - (ballHeight/2) ;
			
// sun movement ///////////////////////////
			
			sunAccel[0] = (accelTot*(-1))* (distX / totDist) ;    
			sunAccel[1] = (accelTot*(-1))* (distY / totDist) ;
			//sunAccel[0] = (accelTot*(-1/sun2ballRatio))* (distX / totDist) ;    
			//sunAccel[1] = (accelTot*(-1/sun2ballRatio))* (distY / totDist) ;
			
			sunVel[0] += sunAccel[0] ;
			sunVel[1] += sunAccel[1] ;
			
			sunCentre[0] += sunVel[0];
			sunCentre[1] += sunVel[1];
			
			sun_xy[0] = sunCentre[0] - (sunWidth/2) ;
			sun_xy[1] = sunCentre[1] - (sunHeight/2) ;
			
			lineStartxy[0] = ballCentre[0];
			lineStartxy[1] = ballCentre[1];
		}else{
			for (int i=0 ; i <= 3; i++){
				if (keyPush[i]){
					//gravityON = false;
				}else{
					counter ++ ;
				}
				if (counter == 4 ){
					gravityON = true ;
					ballVel[0] =  (-lineStartxy[0]+ballCentre[0]) * line2Vel  ; 
					ballVel[1] =  (-lineStartxy[1]+ballCentre[1]) * line2Vel  ;
					lineStartxy[0] = ballCentre[0];
					lineStartxy[1] = ballCentre[1];
					reset();
					//System.out.println(ballVel[0]+" "+ ballVel[1]);
				}
				
			}
		}
		

	}
	private void reset(){
		ballAccel[0] =0; 
		//ballVel[0] =0 ;
		sunAccel[0] =0;
		sunVel[0] = 0;
		
		ballAccel[1] =0; 
		//ballVel[1] =0 ;
		sunAccel[1] =0;
		sunVel[1] = 0;
		
	}
	
	
}
