import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Maze {
	private Tile[][] grid;
	private int width;
	private int height;
	private Player player;
	private Enemy[] enemy;

	Sound sound = new Sound();
	
	public Maze (int w, int h, Player p) {
		grid = new Tile[w+2][h+2];	
		this.width = w+2;
		this.height = h+2;
		player = p;	
		enemy = new Enemy[(int)(((width-13)/3)+1)];
		for (int i = 0; i < enemy.length; i++) {
			enemy[i] = new Enemy(); 	
		}
		MazeGenerator mazeGenerator = new PrimMazeGenerator();
		createMaze(mazeGenerator);		
		startEnemy();	
	}
	
	private void createMaze(MazeGenerator mazeGenerator) {
		grid = mazeGenerator.genMaze(width, height);
		player.setLocation(grid[1][1]);	
		int numEnemy = 0;
		while (numEnemy < enemy.length) {	
			int randomX = 1 + (int)(Math.random()*(width-2));	
			int randomY = ((height-2)/3)*2 + (int)(Math.random()*((height-2)/3)+1);	
			if (grid[randomX][randomY].getType() == Tile.PATH &&	
				!grid[randomX][randomY].equals(player.getLocation())) {
				enemy[numEnemy].setLocation(grid[randomX][randomY]);	
				numEnemy++;
			}
		}
		grid[1][0].setType(Tile.DOOR);	
		grid[width-2][height-1].setType(Tile.DOOR);	

		grid[1][height-2].setType(Tile.KEY);	
		grid[width-2][1].setType(Tile.SWORD);	
		
		int numIcePower = 0;
		while (true) {
			int randomX = 1 + (int)(Math.random()*((width-2)/2));	
			int randomY = 1 + (int)(Math.random()*((height-2)/2));	
			if (grid[randomX][randomY].getType() == Tile.PATH &&	
				!grid[randomX][randomY].equals(player.getLocation())) {	
				grid[randomX][randomY].setType(Tile.ICE_POWER);
				numIcePower++;	
				break;	
			}
		}
		while (numIcePower < enemy.length-1) {	
			int randomX = 1 + (int)(Math.random()*(width-2));	
			int randomY = 1 + (int)(Math.random()*(height-2));	
			if (grid[randomX][randomY].getType() == Tile.PATH &&	
				!grid[randomX][randomY].equals(player.getLocation())) {	
				grid[randomX][randomY].setType(Tile.ICE_POWER);
				numIcePower++;	
			}
		}
		int i = 0;
		while (i < (int)(width-13)/3 + 3) {
			int randomX = 1 + (int)(Math.random()*((width-2)));		
			int randomY = 1 + (int)(Math.random()*((height-2)));	
			if (grid[randomX][randomY].getType() == Tile.PATH &&	
				!grid[randomX][randomY].equals(player.getLocation())) {		
				grid[randomX][randomY].setType(Tile.TREASURE);
				i++;	
			}
		}
	}
	
	public void showMaze () {
		Tile playerLoc = player.getLocation();
		for (int j = 0; j < width; j++) {
			for (int i = 0; i < height; i++) {
				if (grid[i][j].isWalkable()) {
					if (playerLoc != null && playerLoc.equals(grid[i][j])) {
						System.out.print("P");
					} else if (i == (width-1)-1 && j == (height-1)-1) {
						System.out.print("D");
					} else {
						System.out.print("0");
					}
				} else {
					System.out.print("-");	
				}
			}
			System.out.println();
		}
	}
	
	public boolean findPath (int x, int y, boolean[][] visited, HashMap<Tile,Tile> parent) {
		if (x == width-2 && y == height-2) return true;
		if (!grid[x][y].isWalkable() || visited[x][y]) return false;
		visited[x][y] = true;
		if (x != 0) {
			if (findPath(x-1,y,visited,parent)) {
				parent.put(grid[x-1][y], grid[x][y]);
				return true;
			}
		}
		if (x != width-1) {
			if (findPath(x+1,y,visited,parent)) {
				parent.put(grid[x+1][y], grid[x][y]);
				return true;
			}
		}
		if (y != 0) {
			if (findPath(x,y-1,visited,parent)) {
				parent.put(grid[x][y-1], grid[x][y]);
				return true;
			}
		}
		if (y != height-1) {
			if (findPath(x,y+1,visited,parent)) {
				parent.put(grid[x][y+1], grid[x][y]);
				return true;
			}
		}
		return false;
	}
	
	public boolean findPath (int x, int y, int x1, int y1, boolean[][] visited, HashMap<Tile,Tile> parent) {
		if (x == x1 && y == y1) return true;
		if (!grid[x][y].isWalkable() || visited[x][y]) return false;
		visited[x][y] = true;
		if (x != 0) {
			if (findPath(x-1,y,x1,y1,visited,parent)) {
				parent.put(grid[x-1][y], grid[x][y]);
				return true;
			}
		}
		if (x != width-1) {
			if (findPath(x+1,y,x1,y1,visited,parent)) {
				parent.put(grid[x+1][y], grid[x][y]);
				return true;
			}
		}
		if (y != 0) {
			if (findPath(x,y-1,x1,y1,visited,parent)) {
				parent.put(grid[x][y-1], grid[x][y]);
				return true;
			}
		}
		if (y != height-1) {
			if (findPath(x,y+1,x1,y1,visited,parent)) {
				parent.put(grid[x][y+1], grid[x][y]);
				return true;
			}
		}
		return false;
	}
	
	public List<Tile> giveHint(Tile t) {
		boolean[][] visited = new boolean[width][height];
		HashMap<Tile,Tile> parent = new HashMap<Tile,Tile>();
		parent.put(t, null);	
		boolean pathFound = findPath(t.getX(), t.getY(), visited, parent);
		if (!pathFound) {
			return null;		
		} else {
			LinkedList<Tile> path = new LinkedList<Tile>();
			Tile curr = grid[width-2][height-2];	
			path.addFirst(curr);
			while (parent.get(curr) != null) {	
				Tile prev = parent.get(curr);
				path.addFirst(prev);
				curr = prev;
			}
			List<Tile> pathInit = path.subList(0,Math.min(10,path.size()));
			return pathInit;
		}
	}
	
	public Tile getPlayerTile() {
		if (player.isDead()) {
			return null;
		}
		return player.getLocation();
	}
	
	public Tile getEnemyTile(int i) {
		if (i >= enemy.length || enemy[i].isDead()) {
			return null;
		}
		return enemy[i].getLocation();
	}
	
	public boolean isEnemyTile (Tile t) {
		for (int i = 0; i < enemy.length; i++) {
			if (!enemy[i].isDead() && enemy[i].getLocation().equals(t)) {
				return true;
			}
		}
		return false;
	}
	
	public Tile getDestDoor() {
		return grid[width-2][height-1];
	}
	
	public void updatePlayerLoc (int x, int y) {
		if (isValid(x,y) && !player.isDead()) {
			player.setLocation(grid[player.getLocation().getX()+x][player.getLocation().getY()+y]);
			Tile playerLoc = player.getLocation();	
			for (int i = 0; i < enemy.length; i++) {
				Tile enemyLoc = enemy[i].getLocation();	
				if (playerLoc.equals(enemyLoc) && !enemy[i].isDead()) {
					if (!itemCollected(Player.SWORD) && !itemCollected(Player.ICE_POWER)) {
						player.setDead(true);	
					} else {
						enemy[i].setDead(true);
						player.addEnemyKilled();
						player.setItemCollected(Player.SWORD, false);
					}
				}
			}
			if (playerLoc.getType() == Tile.KEY) {
				sound.playSE(2);
				player.setItemCollected(Player.KEY, true);
				playerLoc.setType(Tile.PATH);	
			} else if (playerLoc.getType() == Tile.TREASURE) {
				sound.playSE(3);
				player.addNumTreasureCollected();
				playerLoc.setType(Tile.PATH);	
			} else if (playerLoc.getType() == Tile.SWORD) {
				sound.playSE(4);
				player.setItemCollected(Player.SWORD, true);
				playerLoc.setType(Tile.PATH);
			} else if (playerLoc.getType() == Tile.ICE_POWER) {
				sound.playSE(5);
				player.setItemCollected(Player.ICE_POWER,true);
				playerLoc.setType(Tile.PATH);
			}
			checkReachedEnd();	
		}
	}
	
	private void startEnemy() {
		final Timer timer = new Timer();	
		timer.schedule(new TimerTask() {
			public void run() {
				for (int i = 0; i < enemy.length; i++) {	
					if (player.isDead()) {
						timer.cancel();
					} else if (enemy[i].isDead()) {	
						continue;
					} else if (player.isItemCollected(Player.ICE_POWER)) {
						try {
							Thread.sleep(5000);	
							player.setItemCollected(Player.ICE_POWER,false);	
						} catch (InterruptedException e) {
							
						}
					} else if (player.getLocation() != null) {	
						
						Tile enemyLoc = enemy[i].getLocation();
						HashMap<Tile,Tile> path = new HashMap<Tile,Tile>();
						path.put(grid[enemyLoc.getX()][enemyLoc.getY()], null);
						boolean[][] visitedTile = new boolean[width][height];
						findPath(enemyLoc.getX(), enemyLoc.getY(),
								player.getLocation().getX(), player.getLocation().getY(),
								visitedTile,path);
						Tile curr = player.getLocation();
						
						while (!path.get(curr).equals(enemyLoc)) {	
							curr = path.get(curr);
						}
						enemy[i].setLocation(curr);	
						if (enemy[i].getLocation().equals(player.getLocation())) {
							if (!itemCollected(Player.SWORD)) {
								player.setDead(true);	
							} else {
								enemy[i].setDead(true);	
								player.addEnemyKilled();
								player.setItemCollected(Player.SWORD, false);
							}
						}
					} 
					
				}
			}
		},1500,1500);	
						
	}

	public boolean playerDied () { return player.isDead(); }
	public boolean enemyDied (int i) { return enemy[i].isDead(); }
	public boolean allEnemyDied () { 
		for (int i = 0; i < enemy.length; i++) {
			if (!enemy[i].isDead()) {	
				return false;
			}
		}
		return true; 
	}
	
	public int getNumEnemies () { return enemy.length; }
	public boolean isValid (int x, int y) {
		Tile playerLoc = player.getLocation();
		if (x > 1 || x < -1 || y > 1 || y < -1) {	
			return false;
		} else if (x != 0 && y != 0) {	
			return false;
		} else if ((playerLoc.getX()+x) > (width-1) || (playerLoc.getY()+y) > (height-1)
			|| (playerLoc.getX()+x) < 0 || (playerLoc.getY()+y) < 0) {	
			return false;
		} else if (!grid[playerLoc.getX()+x][playerLoc.getY()+y].isWalkable()) { 
			return false;
		}
		return true;
	}
	
	public boolean checkReachedEnd() {
		Tile playerLoc = player.getLocation();
		if (player.isDead()) {
			return false;
		}
		
		boolean atEnd = false;
		if (playerLoc.getX() == (width-2) && playerLoc.getY() == (height-2)) {
			atEnd = true;
			if (itemCollected(Player.KEY)) {
				grid[width-2][height-1].setType(Tile.PATH);	
				grid[width-2][height-1].setWalkable();
			}
		}
		return atEnd;
	}

	public boolean atStart() {
		return (!player.isDead() && player.getLocation().getX() == 1 && player.getLocation().getY() == 1);
	}
	
	public boolean exitedMaze() {
		return (!player.isDead() && player.getLocation().getX() == (width-2) && player.getLocation().getY() == (height-1));
	}

	public int getNumTreasureCollected() { return player.getNumTreasureCollected(); }
	public boolean itemCollected(int itemNum) { return player.isItemCollected(itemNum); }
	
    public Tile getTile(int x, int y) {
		if (x >= 0 && y >= 0 && x <= (width-1) && y <= (height-1)) {
			return this.grid[x][y];
		} else {
			return null;
		}
	}

	public int getWidth() { return this.width; }
	public int getHeight() { return this.height; }
	public Tile[][] getGrid() { return this.grid; }
}