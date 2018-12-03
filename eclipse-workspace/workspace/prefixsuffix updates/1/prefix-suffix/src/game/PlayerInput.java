package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerInput extends KeyAdapter {

		Main main ;
		
	public PlayerInput(Main main) {
		this.main =main ;
	}

	


	public void actionPerformed(ActionEvent e) {
		

	}


	public void keyTyped(KeyEvent e) {

	}


	public void keyPressed(KeyEvent e) {
	
		main.keyPressed(e) ;
			
	}

	public void keyReleased(KeyEvent e) {
		main.keyReleased(e);
		
	}

}
