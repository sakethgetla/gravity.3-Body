package gravity;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener ,MouseMotionListener{

	int mousexy[] = new int[2];
/*	
	private MouseInput(){
		movement movement = new movement() ;
		
	}
*/	

	movement movement ;
	MouseInput(movement movement){
		this.movement = movement ;
	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("awkhd");

	}

	@Override
	public void mousePressed(MouseEvent e) {
	
		mousexy[0]= e.getX();
		mousexy[1]= e.getY();

		if (mousexy[0] > 0 && mousexy[0] < movement.WIDTH && 
				mousexy[1] > 0 && mousexy[1] < movement.HEIGHT){
			movement.mousePressed(e);
			//System.out.println("awkhd");

		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (mousexy[0] > 0 && mousexy[0] < movement.WIDTH && 
				mousexy[1] > 0 && mousexy[1] < movement.HEIGHT){
			movement.mouseReleased(e);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		movement.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
