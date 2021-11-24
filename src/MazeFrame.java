import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class MazeFrame {

	private int height;	
	private int width;
	private Game g;	
	
	private JFrame frame;
	private JPanel mazeGrid;	
	private JLayeredPane[][] mazeGridComp;	
	private Dimension blockSize;	
	
	private JPanel sidePanel;
	private JLabel score;	
	private JLabel level;	
	private JButton exitButton;	
	private JButton hintButton;	
	private ArrayList<JLabel> inventory; 
	
	private Tile lastPlayerPos;	
	private Tile[] lastEnemyPos;	
	private boolean updatedOnce;
	
	private HashMap<String, Sprite> sprites;	
	
	public static final String wallSprite = "steel_wall";
	public static final String pathSprite = "carpet";
	
	private String playerSprite;
	public static final String LinkSprite = "link";
	public static final String WilliamSprite = "william";
	public static final String KainenSprite = "kainen";

	public static final String doorSprite = "locked_door";
	public static final String keySprite = "key";
	public static final String coinSprite = "coin";
	public static final String enemySprite = "cyan_pacman_monster";
	public static final String killableEnemySprite = "dead_pacman_monster";
	public static final String swordSprite = "master_sword";
	public static final String snowflakeSprite = "snowflake";
	public static final String hintSprite = "hint";
	
	public MazeFrame(Game game, int width, int height) {
		frame = new JFrame();	
		this.height = height; 	
		this.width = width;		
		this.g = game;		
		
		mazeGrid = new JPanel();
		mazeGridComp = new JLayeredPane[this.width][this.height];
		sidePanel = new JPanel();
		exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				Object[] options = {"Yes", "Cancel"};
				Icon icon = UIManager.getIcon("OptionPane.informationIcon");
				
				int dialogResult = JOptionPane.showOptionDialog(frame, "Are you sure you want to exit to the main menu?\n\nAll game progress will be lost.", "Exit Warning", 
																JOptionPane.YES_NO_OPTION, JOptionPane.YES_NO_OPTION, icon, options, options);
				
				if (dialogResult == JOptionPane.YES_OPTION) {
					g.setIsGameOver(true);
					g.setIsInGame(false);
				} else {
					frame.requestFocus();	
				}
			} 
		});
		
		hintButton = new JButton("Hint");
		hintButton.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				Maze m = g.getMaze();
				List<Tile> hintTiles = m.giveHint(m.getPlayerTile());
				for (int i = 0; i < hintTiles.size(); i++) {
					JLabel hintImage = new JLabel(sprites.get(hintSprite).getSprite());
					
					int numComponents = mazeGridComp[hintTiles.get(i).getX()][hintTiles.get(i).getY()].getComponentCount();
					if (numComponents >= 3) {	
						continue;
					}
					
					if (numComponents == 1 && g.getMaze().getTile(hintTiles.get(i).getX(),hintTiles.get(i).getY()).getType() == Tile.PATH) {
						mazeGridComp[hintTiles.get(i).getX()][hintTiles.get(i).getY()].add(hintImage, new Integer(0));
					} else if (g.getMaze().getTile(hintTiles.get(i).getX(),hintTiles.get(i).getY()).getType() != Tile.PATH) {
						
						
						mazeGridComp[hintTiles.get(i).getX()][hintTiles.get(i).getY()].add(hintImage, new Integer(-1));
					} else {	
						continue;
					}
					mazeGridComp[hintTiles.get(i).getX()][hintTiles.get(i).getY()].repaint();	
					frame.pack();
				}
			}
		});
		inventory = new ArrayList<JLabel>();	
		
		Toolkit tk = Toolkit.getDefaultToolkit();  
		int xSize = ((int) tk.getScreenSize().getWidth());  
		int ySize = ((int) tk.getScreenSize().getHeight()); 
		Dimension fullscreen = new Dimension(xSize, ySize);
		frame.setPreferredSize(fullscreen);

		blockSize = new Dimension((int)((ySize*0.95/this.height)), (int)((ySize*0.95/this.height)));	
		sprites = new HashMap<String, Sprite>();
		
		int x = (int)blockSize.getWidth();
		int y = (int)blockSize.getHeight();
		
		playerSprite = g.getPlayer().getCharacter();

		sprites.put(wallSprite, new Sprite(wallSprite,x,y));
		sprites.put(pathSprite, new Sprite(pathSprite,x,y));
		sprites.put(playerSprite, new Sprite(playerSprite,x,y));
		sprites.put(doorSprite, new Sprite(doorSprite,x,y));
		sprites.put(keySprite, new Sprite(keySprite,x,y));
		sprites.put(enemySprite, new Sprite(enemySprite,x,y));
		sprites.put(coinSprite, new Sprite(coinSprite,x,y));
		sprites.put(swordSprite, new Sprite(swordSprite,x,y));
		sprites.put(snowflakeSprite, new Sprite(snowflakeSprite,x,y));
		sprites.put(killableEnemySprite, new Sprite(killableEnemySprite,x,y));
		sprites.put(hintSprite, new Sprite(hintSprite,x,y));
		
		sidePanel.setPreferredSize(new Dimension((int) ((this.width * blockSize.getWidth())*0.4),
													(int) (this.height * blockSize.getHeight())));
		sidePanel.setBackground(new Color(240, 240, 240));
		sidePanel.setLayout(new GridBagLayout());
		sidePanel.setBorder(new LineBorder(Color.BLACK, 2));
		
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);  
		frame.setResizable(false); 
		frame.setLayout(new GridBagLayout()); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		frame.setUndecorated(true);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void update(Maze m) {
		Tile curPlayerPos = m.getPlayerTile();
		if (!m.playerDied() || lastPlayerPos != null) {
			if (!lastPlayerPos.equals(curPlayerPos)) {
				updateBlock(m, lastPlayerPos);
				if (!m.playerDied()) {
					updateBlock(m, curPlayerPos);
				}
				score.setText("Score: " + Integer.toString(g.getScore())); 
				
				if (m.itemCollected(Player.SWORD)){
					inventory.get(Player.SWORD).setVisible(true);
				} else {
					inventory.get(Player.SWORD).setVisible(false);
				}
				if (m.itemCollected(Player.KEY)){
					inventory.get(Player.KEY).setVisible(true);
				}
				if (m.itemCollected(Player.ICE_POWER)){
					inventory.get(Player.ICE_POWER).setVisible(true);
				} else {
					inventory.get(Player.ICE_POWER).setVisible(false);	
				}
				
				if (m.checkReachedEnd()) {	
					updateBlock(m,m.getDestDoor());
				}
			}
			lastPlayerPos = curPlayerPos;
		}
		
		for (int i = 0; i < m.getNumEnemies(); i++) {
			Tile curEnemyPos = m.getEnemyTile(i);
			if (!m.enemyDied(i)) {	
				if (!lastEnemyPos[i].equals(curEnemyPos)) {
					updateBlock(m, lastEnemyPos[i]);
					updateBlock(m, curEnemyPos);
					updatedOnce = false;
				} else if (m.itemCollected(Player.ICE_POWER) && !updatedOnce) {
					updateBlock(m, lastEnemyPos[i]);
					if (i == m.getNumEnemies()-1) {	
						updatedOnce = true;			
					}
				}
				lastEnemyPos[i] = curEnemyPos;
			} else if (lastEnemyPos[i] != null) {
				updateBlock(m, lastEnemyPos[i]);
				lastEnemyPos[i] = null;
			}
		}
	}
	
	private void updateBlock(Maze m, Tile old) {	
		int numComponents = this.mazeGridComp[old.getX()][old.getY()].getComponentCount();
		for (int i = 0; i < numComponents-1; i++) {
			this.mazeGridComp[old.getX()][old.getY()].remove(0);
		}
		String overLaySprite = "";	
		int index = 0;
		
		if (m.getPlayerTile() != null && m.getPlayerTile().equals(old)) {
			overLaySprite = g.getPlayer().getCharacter();
		} 
		
		else if (m.isEnemyTile(old)) {
			if (m.itemCollected(Player.ICE_POWER)) {
				overLaySprite = killableEnemySprite;
			} else {
				overLaySprite = enemySprite;
			}
		}
		
		else if (m.getDestDoor().equals(old) && m.getDestDoor().getType() == Tile.PATH) {
			this.mazeGridComp[old.getX()][old.getY()].remove(0);
			overLaySprite = pathSprite;
			index = -2;
		}
		
		else if (old.getType() == Tile.DOOR) {
			overLaySprite = doorSprite;
		}
		
		else if (old.getType() == Tile.KEY) {
			overLaySprite = keySprite;
		}
		else if (old.getType() == Tile.TREASURE) {
			overLaySprite = coinSprite;
		}
		else if (old.getType() == Tile.SWORD){
			overLaySprite = swordSprite;
		}
		else if (old.getType() == Tile.ICE_POWER) {
			overLaySprite = snowflakeSprite;
		}
		if (overLaySprite != "") {	
			JLabel overlayImage = new JLabel(sprites.get(overLaySprite).getSprite());
			this.mazeGridComp[old.getX()][old.getY()].add(overlayImage, new Integer(index));	
			frame.pack();
		}
	}
	
	public void init(Maze m) {
		mazeGrid.removeAll();
		sidePanel.removeAll();

		mazeGrid.setLayout(new GridLayout(width, height));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		
		lastEnemyPos = new Tile[m.getNumEnemies()];
		for (int i = 0; i < m.getNumEnemies(); i++) {
			lastEnemyPos[i] = new Tile();
		}
		
		for (int y = 0; y < height; y++){
			gbc.gridy = y;	
			for (int x = 0; x < width; x++){
				gbc.gridx = x; 
				Tile t = m.getTile(x, y);
				
				JLayeredPane block = new JLayeredPane();
				this.mazeGridComp[x][y] = block;	
				
				block.setPreferredSize(blockSize);
				block.setLayout(new OverlayLayout(block));
				
				String blockSprite = pathSprite;	
				String overLaySprite = "";	
				
				if (m.getPlayerTile() != null && m.getPlayerTile().equals(t)) {
					overLaySprite = playerSprite;
					this.lastPlayerPos = t; 
				}
				
				else if (m.isEnemyTile(t)) {
					for (int i = 0; i < m.getNumEnemies(); i++) {	
						if (m.getEnemyTile(i) != null && m.getEnemyTile(i).equals(t)) {
							overLaySprite = enemySprite;
							this.lastEnemyPos[i] = t;
							break;
						}
					}
				}
				
				else if (t.getType() == Tile.DOOR) {
					blockSprite = wallSprite;
					overLaySprite = doorSprite;
				}
				
				else if (t.getType() == Tile.KEY) {
					overLaySprite = keySprite;
				}
				else if (t.getType() == Tile.TREASURE) {
					overLaySprite = coinSprite;
				}
				else if (t.getType() == Tile.SWORD){
					overLaySprite = swordSprite;
				}
				else if (t.getType() == Tile.ICE_POWER) {
					overLaySprite = snowflakeSprite;
				}
				
				else if (t.getType() == Tile.WALL){
					blockSprite = wallSprite;
				}
				
				JLabel spriteImage = new JLabel(sprites.get(blockSprite).getSprite());
				block.add(spriteImage, new Integer(-2));
				
				if (overLaySprite != "") {
					JLabel overlayImage = new JLabel(sprites.get(overLaySprite).getSprite());
					block.add(overlayImage, new Integer(0));
				}
				
				mazeGrid.add(block, gbc);
			}
		}
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.CENTER;
		frame.add(mazeGrid, gbc);

		JPanel playerPanel = new JPanel(new GridLayout(3,1));
		playerPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		
		ImageIcon title = new ImageIcon(this.getClass().getResource("/img/mazerunner.png"));
		Image image = title.getImage().getScaledInstance(288, 144, Image.SCALE_FAST);
		title.setImage(image);	
		JLabel titleImage = new JLabel(title);
		playerPanel.add(titleImage);

		Sprite player = new Sprite(g.getPlayer().getCharacter(),96,96);	
		JLabel playerImage = new JLabel(player.getSprite());
		playerImage.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
		playerPanel.add(playerImage);

		JPanel playerStats = new JPanel(new GridLayout(4,1));
		JLabel name = new JLabel("Name: " + g.getPlayer().getName());
		JLabel character = new JLabel("Character: " + g.getPlayer().getCharacter().substring(0, 1).toUpperCase() + g.getPlayer().getCharacter().substring(1));
		score = new JLabel("Score: " + g.getScore());
		level = new JLabel("Level: " + (g.getLevel()+1)); 

		name.setFont(new Font("Arial", Font.PLAIN, 16));
		character.setFont(new Font("Arial", Font.PLAIN, 16));
		score.setFont(new Font("Arial", Font.BOLD, 18));
		level.setFont(new Font("Arial", Font.BOLD, 18));

		playerStats.add(name);
		playerStats.add(character);
		playerStats.add(score);
		playerStats.add(level);	
		playerPanel.add(playerStats);	
		
		gbc.gridwidth = 4;
		gbc.gridx = 0;
		gbc.gridy = 0;
		sidePanel.add(playerPanel);	
		
		gbc.gridx = 0;
		gbc.gridy = -3;

		hintButton.setMargin(new Insets(5, 10, 5, 10));
		exitButton.setToolTipText("Click here to exit to main menu.");
		sidePanel.add(hintButton, gbc);
		exitButton.setMargin(new Insets(5, 10, 5, 10));
		sidePanel.add(exitButton, gbc);

		inventory.add(Player.KEY, new JLabel(new Sprite(keySprite,48,48).getSprite()));
		inventory.add(Player.SWORD, new JLabel(new Sprite(swordSprite,48,48).getSprite()));
		inventory.add(Player.ICE_POWER, new JLabel(new Sprite(snowflakeSprite,48,48).getSprite()));
		
		JPanel invPanel = new JPanel();
        invPanel.setPreferredSize(new Dimension(200,60));
        
        gbc.gridwidth = 1;
        gbc.gridy = 0;
        gbc.gridx = 0;
        invPanel.add(inventory.get(Player.SWORD),gbc);
        
        gbc.gridy = 0;
        gbc.gridx = 1;
        invPanel.add(inventory.get(Player.KEY),gbc);
        
        gbc.gridy = 0;
        gbc.gridx = 2;
        invPanel.add(inventory.get(Player.ICE_POWER),gbc);
        
        for (int i = 0; i < Player.NUM_INVENTORY_ITEMS; i++) {
        	inventory.get(i).setVisible(false);
        }
         
        gbc.gridy = 2;
        gbc.gridx = 0;
        sidePanel.add(invPanel,gbc);
         
         
        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(sidePanel, gbc);
        
         
        frame.pack();
	}

	public JFrame getFrame() {
		return frame;
	}	
}