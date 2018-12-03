package hackathon;


import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Messages extends JPanel implements ActionListener{
	static int countmsgs = 0;
	int count;
	JButton voteButton , ansButton;
	int rctx, rcty;
	int rctHeight = 50;
	public String msg;
	private teacher teacher;
	private int voteCount = 0;

	
	
	public Messages(String msg, int v, teacher teach) {
		//count = countmsgs;
		voteCount = v;
		teacher = teach;
		Dimension size = new Dimension(400,20);
		setPreferredSize(size);
		setLocation(0,0);
		setLayout(null);
		setBackground(Color.PINK);
		
		count++;
		rctx = 50;
		rcty = count * rctHeight;
		this.msg = msg;
		voteButton = new JButton("vote " + voteCount);
		
		JLabel label = new JLabel(msg);
		//label.setFont();
		label.setBounds(0,0,400,20);
		add(label);
		add(voteButton);
		voteButton.setSize(100,20);
		voteButton.setLocation(400-100-10,0);
		voteButton.addActionListener(this);
		
		ansButton = new JButton("ans " );
		add(ansButton);
		ansButton.setSize(75,20);
		ansButton.setLocation(150,0);
		ansButton.addActionListener(this);
		//System.out.println("hdd");
		//render();
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals("vote " + voteCount)) {
			this.voteCount++;
			voteButton.setText("vote " + voteCount);
			//System.out.println("mhdc"+ voteCount);
			teacher.voter(voteCount);
		}
		//voteButton 
	}
	
	
	
	public int getCount() {
		return this.voteCount;
	}

	private void render() {
		
		
		/*BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;

		}

		Graphics g = bs.getDrawGraphics();

		///////////////////////

		graphics(g);

		///////////////////////

		g.dispose();
		bs.show();*/

	}

}
