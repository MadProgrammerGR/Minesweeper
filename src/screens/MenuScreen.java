package screens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class MenuScreen extends JFrame implements ActionListener {
	private static final long serialVersionUID = -1090588307827636514L;

	JLabel header;
	JButton small, normal, large;
	JRadioButton easy, medium, hard;
	
	public MenuScreen(){
		header = new JLabel("MineSweeper");
		header.setForeground(Color.BLUE);
		header.setFont(new Font(header.getName(), Font.BOLD, 50));
		add(header);
		
		small = new JButton("8 x 8");
		normal = new JButton("10 x 10");
		large = new JButton("12 x 12");
		small.addActionListener(this);
		normal.addActionListener(this);
		large.addActionListener(this);
		small.setPreferredSize(new Dimension(100, 100));
		normal.setPreferredSize(new Dimension(100, 100));
		large.setPreferredSize(new Dimension(100, 100));
		add(small);
		add(normal);
		add(large);
		
		easy = new JRadioButton("Easy", true);
		medium = new JRadioButton("Medium");
		hard = new JRadioButton("Hard");
		ButtonGroup group = new ButtonGroup();
		group.add(easy);
		group.add(medium);
		group.add(hard);
		add(easy);
		add(medium);
		add(hard);
		
		
		setTitle("MineSweeper - Menu");
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 260);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		float difficulty = 0;
		if(easy.isSelected())
			difficulty = 0.10f;
		else if(medium.isSelected())
			difficulty = 0.15f;
		else if(hard.isSelected())
			difficulty = 0.20f;
		
		if(e.getSource() == small){
			new GameScreen(8, 8, (int)(difficulty * 8 * 8));
		}else if(e.getSource() == normal){
			new GameScreen(10, 10, (int)(difficulty * 10 * 10));
		}else if(e.getSource() == large){
			new GameScreen(12, 12, (int)(difficulty * 12 * 12));
		}
		close();
	}

	public void close(){
		setVisible(false);
		dispose();
	}
	
}
