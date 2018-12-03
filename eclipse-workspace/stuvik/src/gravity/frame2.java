package gravity;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class frame2  extends Canvas {
	static int WIDTH = 1200;
	static int HEIGHT = WIDTH / 15 * 9;

	public frame2(String name) {
		JFrame frame = new JFrame(name);
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		// frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(frame, this);
		
		JTextField jt = new JTextField("type", 30) ;
	}
	
}
