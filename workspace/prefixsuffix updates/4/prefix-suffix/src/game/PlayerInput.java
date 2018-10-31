package game;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class PlayerInput extends KeyAdapter {

		Main main ;
		
	public PlayerInput(Main main) {
		this.main =main ;
	}


	public void keyPressed(KeyEvent e) {
	
		main.keyPressed(e) ;
			
	}

	public void keyReleased(KeyEvent e) {
		main.keyReleased(e);
		
	}

}
