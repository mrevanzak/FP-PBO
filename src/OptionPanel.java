import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class OptionPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JButton setMax;
	private JButton setPeter;
	private JButton setVira;
	private JTextField nameField;	
	
	private JRadioButton easyDifficulty;	
	private JRadioButton mediumDifficulty;
	private JRadioButton hardDifficulty;

	public OptionPanel(Game g){
		final Game game = g;
		this.setLayout(new GridBagLayout());		
		GridBagConstraints gbc = new GridBagConstraints();
		
		Sprite maxPanel = new Sprite(MazeFrame.MaxSprite, 48, 48);
		Sprite peterPanel = new Sprite(MazeFrame.PeterSprite, 35, 48);
		Sprite viraPanel = new Sprite(MazeFrame.ViraSprite, 40, 48);
		
		setMax = new JButton("Max - The Handsome", maxPanel.getSprite() );
		setPeter = new JButton("Peter - The Brave", peterPanel.getSprite());
		setVira = new JButton("Vira - The Beauty", viraPanel.getSprite());

		setMax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.getPlayer().setCharacter(MazeFrame.MaxSprite);
			}
		});
		setPeter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.getPlayer().setCharacter(MazeFrame.PeterSprite);
			}
		});
		setVira.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.getPlayer().setCharacter(MazeFrame.ViraSprite);
			}
		});;
		this.add(setMax);
		this.add(setPeter);
		this.add(setVira);
		
		nameField = new JTextField("");
		nameField.setPreferredSize(new Dimension(200,20));

		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.insets = new Insets(20,20,0,0);
		
		JLabel nameText = new JLabel(String.format("Name:"));
		this.add(nameText, gbc);
		
		gbc.gridx = 1;
		this.add(nameField, gbc);

		gbc.gridy = 2;
		gbc.gridx = 0;
		JLabel difficultyText = new JLabel("Choose difficulty:");
		this.add(difficultyText, gbc);

		ButtonGroup difficultyButtons = new ButtonGroup();
		easyDifficulty = new JRadioButton("Easy");
		mediumDifficulty = new JRadioButton("Medium", true);
		hardDifficulty = new JRadioButton("Hard");
		difficultyButtons.add(easyDifficulty);
		difficultyButtons.add(mediumDifficulty);
		difficultyButtons.add(hardDifficulty);

		gbc.gridy = 3;
		gbc.gridx = 0;
		gbc.insets = new Insets(10,10,0,0);
		this.add(easyDifficulty,gbc);
		
		gbc.gridx = 1;
		this.add(mediumDifficulty,gbc);
		
		gbc.gridx = 2;
		this.add(hardDifficulty,gbc);
		
		easyDifficulty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.setDifficulty(Game.EASY);
			}
		});
		mediumDifficulty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.setDifficulty(Game.MEDIUM);
			}
		});
		hardDifficulty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.setDifficulty(Game.HARD);
			}
		});
	}

	public String getName(){
		return nameField.getText();
	}
}
