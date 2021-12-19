import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InstructionFrame {
	private JButton backButton;
	private JPanel instructions;
	private JFrame frame;
	
	public InstructionFrame () {
		frame = new JFrame();	
		frame.setTitle("How to Play");
		frame.setFont(new Font("Times New Roman", Font.PLAIN, 16));	
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		backButton = new JButton("Close");
		backButton.setFont(new Font("Times New Roman", Font.PLAIN, 16));	
		instructions = new JPanel(new GridBagLayout());
		backButton.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);	
			}
		});
		frame.getContentPane().setBackground(Color.WHITE);
		instructions.setBackground(Color.WHITE);
		frame.setResizable(false);
		frame.setLayout(new GridBagLayout());
		
		BufferedImage wasd = null;
		try {
	    	wasd = ImageIO.read(this.getClass().getResource("/img/arrow.png"));
	    } catch (IOException e) {
	    	System.err.println("Failed to read sprite.");
	    }
		
	    BufferedImage htp = null;
	    try {
	    	htp = ImageIO.read(this.getClass().getResource("/img/howtoplay2.png"));
	    } catch (IOException e){
	    	System.err.println("Failed to read sprite.");
	    }
	    
		JLabel sword = new JLabel(new Sprite(MazeFrame.swordSprite,48,48).getSprite());
		JLabel key = new JLabel(new Sprite(MazeFrame.keySprite,48,48).getSprite());
		JLabel coin = new JLabel(new Sprite(MazeFrame.coinSprite,48,48).getSprite());
		JLabel enemy = new JLabel(new Sprite(MazeFrame.enemySprite,48,48).getSprite());
		JLabel freeze = new JLabel(new Sprite(MazeFrame.snowflakeSprite, 48, 48).getSprite());
		JLabel enemyF = new JLabel(new Sprite(MazeFrame.killableEnemySprite, 48, 48).getSprite());

		JLabel keyboard = new JLabel(new ImageIcon(wasd));
		JLabel howtoplay = new JLabel(new ImageIcon(htp));
		
		sword.setText("Can make you kill the ghosts.");
		sword.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		key.setText("Make sure to pick this up to open the door.");
		key.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		coin.setText("Collect this to increase your score.");
		coin.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		enemy.setText("You'll die if they catch you.");
		enemy.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		enemyF.setText("When they freeze, go through them.");
		enemyF.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		keyboard.setText("Use arrow keys or WASD to move around");
		keyboard.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		freeze.setText("Pick this up to freeze the ghosts.");
		freeze.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		
		GridBagConstraints c= new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		
		c.gridy = 0;
		instructions.add(howtoplay, c);
		
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0,170,0,0);
		
		c.gridy = 2;
		instructions.add(enemy,c);
		
		c.gridy = 3;
		instructions.add(enemyF,c);
		
		c.gridx = 0;
		c.gridy = 4;
		instructions.add(sword,c);
		
		c.gridy= 5;
		instructions.add(freeze, c);
		
		c.gridy = 6;
		instructions.add(key,c);
		
		c.gridy= 7;
		instructions.add(coin,c);
		
		c.gridy = 8;
		instructions.add(keyboard, c);
		
		c.gridy= 9;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0,0,0,0);
		this.backButton.setEnabled(true);
		instructions.add(backButton,c);
		
		c.gridx= 0;
		frame.add(instructions);

		c.gridy = 1;
		frame.pack();
		frame.setVisible(false);
	}

	public void setVisible(boolean b) {
		frame.setVisible(b);
	}
}
