import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class GameManager {
	private GameFrame gameFrame;	
	private MazeFrame mazeFrame;	
	private Game g;

	Sound sound = new Sound();

	public GameManager() {
		g = new Game();	
	}
	 
	public void run() {
		gameFrame = new GameFrame(g, 800, 800);
		while (true) {
			if (!g.isInGame())
				continue;

			createNewMazeFrame();			
			long lastUpdateTime = System.currentTimeMillis();		
			
			while (!g.isGameOver()) {
				if (System.currentTimeMillis() - lastUpdateTime > 20) {	
					g.updateScore();	
					if (!g.getFinishedLevel()) {
						updateMazeFrame(); 
					} else {
						createNewMazeFrame();	
						g.setFinishedLevel(false);	
					}			
					lastUpdateTime = System.currentTimeMillis(); 
				}
			}
			showGameFrame();	
		}
	}
    
    private void updateMazeFrame() {
        this.mazeFrame.update(g.getMaze());	
        this.mazeFrame.getFrame().requestFocus();
        this.mazeFrame.getFrame().repaint();
 
        if (g.getPlayer().isDead()) {
 			Object[] options = {"End campaign"};
 			JOptionPane.showOptionDialog (mazeFrame.getFrame(), "Pacman monster killed you!","OH NO!", 
 								JOptionPane.CLOSED_OPTION,JOptionPane.PLAIN_MESSAGE,
 								new ImageIcon(this.getClass().getResource("/img/dead_pacman_monster.gif")),options,options[0]);
 			g.setIsGameOver(true);	
 			g.setIsInGame(false);
 			mazeFrame.getFrame().requestFocus();	
        } else if (g.getMaze().exitedMaze()) {
			sound.playSE(6);
			Object[] options = {"Next level"};
			JOptionPane.showOptionDialog (mazeFrame.getFrame(), "The next journey awaits you...\n" +
								"What unknown challenges lay ahead?","Room " + (g.getLevel()+1) + " cleared!", 	
								JOptionPane.OK_OPTION,JOptionPane.PLAIN_MESSAGE,
								new ImageIcon(this.getClass().getResource("/img/door_open.gif")),options,options[0]);
			g.checkNextLevel(); 
			mazeFrame.getFrame().requestFocus();	
		}
     }
    
    private void showGameFrame() {
    	if (g.getLevel() == Game.MAX_LEVEL) {
    		Object[] options = {"Exit"};
			JOptionPane.showOptionDialog (this.mazeFrame.getFrame(), "Congratulations, warrior!\n" +
						"Your skill is worthy of mention but who knows\n" + "what challenges we may see ahead?\n"
						+ "We will require your assistance when the time comes...",
						"Tower cleared!", 1,0,new ImageIcon(this.getClass().getResource("/img/door_open.gif")), options, options[0]);
    	} 
    	g.getPlayer().clearStats();	
    	g.clearGame();	
    	mazeFrame.getFrame().dispose(); 
    	gameFrame.getFrame().setVisible(true);	
    }
    
    private void createNewMazeFrame() {
    	if (mazeFrame != null) {
    		this.mazeFrame.getFrame().dispose();
    	}
    	
    	this.mazeFrame = new MazeFrame(g,g.getMaze().getWidth(), 
    									g.getMaze().getHeight());
    	
        this.mazeFrame.init(g.getMaze()); 
        this.mazeFrame.getFrame().requestFocus();
		this.mazeFrame.getFrame().addKeyListener(g.getController()); 
    }
}
