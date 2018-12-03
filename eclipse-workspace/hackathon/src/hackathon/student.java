package hackathon;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class student extends JFrame implements ActionListener{
	static int studentCount = 0;
	JLabel label;
	JTextField text;
	JButton button;
	JPanel panel;
	int count ;
	student(){
		count = studentCount;
		label = new JLabel("Student");
		add(label);
		setLayout(new FlowLayout());
		text = new JTextField(20);
		button = new JButton("okay");
		
		add(text);
		
		add(button);
		setVisible(true);
		setSize(400,400);
		setTitle("student");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		Dimension size = new Dimension(400,400);
		panel.setPreferredSize(size);
		panel.setLocation(0,0);
		
		//add(panel);
		//panel.setLayout(null);
		//setLayout(new FlowLayout());
	}
	
	public void addJPanel(JPanel panel){
		
		//panel.setBackground(Color.ORANGE);
		add(panel);
		this.panel.revalidate();
		this.panel.repaint();
		revalidate();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("okay")){
			
		}
	}
	
	
}
	