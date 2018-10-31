package evolution;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class input extends KeyAdapter {
	
	Main main;
		input(Main main){
			this.main = main ;
		}
		
		public void keyPushed(KeyEvent e){
			main.keyPushed(e);
		}
		public void keyPressed(KeyEvent e){
			main.keyPressed(e);
		}
		public void keyReleased(KeyEvent e){
			main.keyReleased(e);
		}
		
		

	}

