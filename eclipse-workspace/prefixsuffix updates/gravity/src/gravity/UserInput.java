package gravity;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UserInput extends KeyAdapter{
	movement movement;
	UserInput(movement movement){
		this.movement = movement ;
	}
	
	public void keyPushed(KeyEvent e){
		
	}
	public void keyPressed(KeyEvent e){
		movement.keyPressed(e);
	}
	public void keyReleased(KeyEvent e){
		movement.keyReleased(e);
	}
	

}
