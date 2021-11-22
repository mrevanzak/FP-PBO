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
	
	private JButton setLink;	
	private JButton setWilliam;
	private JButton setKainen;
	private JTextField nameField;	
	
	private JRadioButton easyDifficulty;	
	private JRadioButton mediumDifficulty;
	private JRadioButton hardDifficulty;

	public OptionPanel(Game g){
		final Game game = g;
		this.setLayout(new GridBagLayout());		
		GridBagConstraints gbc = new GridBagConstraints();
		
		Sprite linkPanel = new Sprite(MazeFrame.LinkSprite, 48, 48);
		Sprite williamPanel = new Sprite(MazeFrame.WilliamSprite, 48, 48);
		Sprite kainenPanel = new Sprite(MazeFrame.KainenSprite, 48, 48);
		
		setLink = new JButton("Link - The Legend", linkPanel.getSprite() );
		setWilliam = new JButton("William - The Knight", williamPanel.getSprite());
		setKainen = new JButton("Kainen - The Dark Prince", kainenPanel.getSprite());

		setLink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.getPlayer().setCharacter(MazeFrame.LinkSprite);
			}
		});
		setWilliam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.getPlayer().setCharacter(MazeFrame.WilliamSprite);
			}
		});
		setKainen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				game.getPlayer().setCharacter(MazeFrame.KainenSprite);
			}
		});;
		this.add(setLink);	
		this.add(setWilliam);
		this.add(setKainen);
		
		nameField = new JTextField("Enter Name");
		nameField.setPreferredSize(new Dimension(200,20));

		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.insets = new Insets(20,20,0,0);
		
		JLabel nameText = new JLabel("Introduce yourself, Champion: ");
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
