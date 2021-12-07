public class Game {
    private Maze maze;
    private Player player;	
    private Controller c;	
    private int difficulty;	
    private int score;	
    private int level;	
    private boolean finishedLevel;
    
    private volatile boolean inGame;	
    private volatile boolean isGameOver;	
    										
    private static final int START_LEVEL_WIDTH = 11;
    private static final int START_LEVEL_HEIGHT = 11;
    private static final int POINTS_ENEMY_KILLED = 10;
    public static final int MAX_LEVEL = 10;
    
    public static final int EASY = -1;
    public static final int MEDIUM = 0;
    public static final int HARD = 1;

    Sound sound = new Sound();
    
    public Game() {
        isGameOver = false;
        inGame = false;
        
		player = new Player("Default", "link");
        score = 0;	
        level = 0;	
        difficulty = MEDIUM;	
    }
    
    public void createMaze() {
    	this.maze =  new Maze(START_LEVEL_WIDTH + (2*(level+difficulty)), 
    						START_LEVEL_HEIGHT + (2*(level+difficulty)), player);
		c = new Controller(maze);	
    }
    
    public void setIsGameOver(boolean isGameOver) {
    	this.isGameOver = isGameOver;
        if(isGameOver)
            sound.stopMusic();
    }
    
    public boolean isGameOver() {
        return isGameOver;
    }
    
    public void setIsInGame(boolean inGame) {
    	this.inGame = inGame;
        if(inGame)
            sound.playMusic(0);
    }
    
    public boolean isInGame() {
    	return inGame;
    }
    
    public void setController(Controller c) {
    	this.c = c;
    }
    
    public Controller getController() {
    	return c;
    }
    
    public Player getPlayer() {
    	return this.player;
    }
    
    public Maze getMaze() {
    	return this.maze;
    }
    
    public void updateScore(){
    	score = player.getNumTreasureCollected() + POINTS_ENEMY_KILLED * player.getEnemyKilled();
    }
    
	public int getScore() {
		return score;
	}

	public void checkNextLevel () {
		if (maze.exitedMaze()) {
			finishedLevel = true;	
			level++;
			player.clearInventory();	
			if (level == MAX_LEVEL) {
				setIsGameOver(true);	
				setIsInGame(false);
			} else {
				createMaze();	
			}
		}
	}

	public int getLevel () {
		return level;
	}

	public void setDifficulty (int difficulty) {
		this.difficulty = difficulty;
	}
	
	public boolean getFinishedLevel() {
		return finishedLevel;
	}
	
	public void setFinishedLevel(boolean b) {
		finishedLevel = b;
	}
	
	public void clearGame() {
    	score = 0;
    	level = 0;
	}
}