import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GameFrame {
	private InstructionFrame instructions;
	private AboutFrame about;
	private JPanel optionPanel;
	private JFrame frame;
	
	private JButton playButton;
	private JButton howButton;
	private JButton aboutButton;
	private JButton exitButton;

	public GameFrame(Game game, int width, int height) {
		frame = new JFrame();
		Dimension minSize = new Dimension(600, 600);
		frame.setMinimumSize(minSize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		final Game g = game;
		this.instructions = new InstructionFrame();
		this.optionPanel = new OptionPanel(g);
		this.about = new AboutFrame();
		
		
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setLayout(new GridBagLayout());
		frame.getContentPane().setBackground(Color.WHITE);

		ImageIcon max = new ImageIcon(GameFrame.this.getClass().getResource("/img/max-home.gif"));
		JLabel maxImage = new JLabel(max);
		
		ImageIcon title = new ImageIcon(GameFrame.this.getClass().getResource("/img/mazerunner.png"));
		JLabel titleImage = new JLabel(title);
		
	    GridBagConstraints c = new GridBagConstraints();
	    
	    c.gridheight = 15;
	    c.gridwidth = 10;
	    c.gridy = 0;
	    c.gridx = 1;
	    frame.add(titleImage,c);
	    
		c.gridwidth = 2;
		c.gridheight = 10;
		c.gridy = 15;
		c.gridx = 4;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(-50,120,0,120);
	    frame.add(maxImage, c);
	    
		c.gridwidth = 1;
		c.gridheight = 1;
		
	    c.gridy = 20;
	    c.gridx = 6;
	    c.insets = new Insets(0,0,0,0);
	    playButton = new JButton("Play Game!");
		playButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
		this.playButton.setBackground(Color.WHITE);
		frame.add(playButton,c); 
		this.playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null,optionPanel,"Choose Name & Character ",
						JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
					g.getPlayer().setName(optionPanel.getName());
					g.createMaze(); 
					g.setIsInGame(true);
					g.setIsGameOver(false);
					frame.setVisible(false);
				}
			}
		});
		
		c.insets = new Insets(0,0,0,0);
		c.gridy = 21;
	    c.gridx = 6;
	    howButton = new JButton("How to Play");
		howButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
		this.howButton.setBackground(Color.WHITE);
		this.howButton.setEnabled(true);
		frame.add(howButton,c);
		this.howButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instructions.setVisible(true);
			}
		});

		c.insets = new Insets(0,0,0,0);
		c.gridy = 22;
	    c.gridx = 6;
	    aboutButton = new JButton("About");
		aboutButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
		this.aboutButton.setBackground(Color.WHITE);
		this.aboutButton.setEnabled(true);
		frame.add(aboutButton,c);
		this.aboutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				about.setVisible(true);
			}
		});
		
		c.insets = new Insets(0,0,0,0);
		c.gridy = 23;
	    c.gridx = 6;
	    exitButton = new JButton("Exit");
		exitButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
		this.exitButton.setBackground(Color.WHITE);
		frame.add(exitButton,c);		
		this.exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public JFrame getFrame() {
		return frame;
	}
}
