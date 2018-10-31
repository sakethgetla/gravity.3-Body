package pongGesser;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import pongGesser.movement;

public class input extends KeyAdapter{
	movement movement;
	input(movement movement){
		this.movement = movement ;
	}
/*	
	public void keyPushed(KeyEvent e){
		
	}
	*/
	public void keyPressed(KeyEvent e){
		movement.keyPressed(e);
	}
	public void keyReleased(KeyEvent e){
		movement.keyReleased(e);
	} 
	
}