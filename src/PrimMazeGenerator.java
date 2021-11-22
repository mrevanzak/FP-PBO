import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class PrimMazeGenerator implements MazeGenerator {
	public Tile[][] genMaze(int width, int height) {
		Tile[][] grid = new Tile[width][height];
		final double weights[][] = new double[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Tile t = new Tile(false,i,j); 
				grid[i][j] = t;
				weights[i][j] = Math.random();
			}
		}

		PriorityQueue<TileEdge> edges = new PriorityQueue<TileEdge>(100, new Comparator<TileEdge>() { 
			public int compare (TileEdge e1, TileEdge e2) {
				double weightDiff = (weights[e1.getTile0().getX()][e1.getTile0().getY()] +
						weights[e1.getTile1().getX()][e1.getTile1().getY()] +
						weights[e1.getTile2().getX()][e1.getTile2().getY()])
						-(weights[e2.getTile0().getX()][e2.getTile0().getY()]
						+ weights[e2.getTile1().getX()][e2.getTile1().getY()]
						+ weights[e2.getTile2().getX()][e2.getTile2().getY()]);
				if (weightDiff > 0) return 1;
				if (weightDiff < 0) return -1;
				return 0;
			}
		});
		HashSet<TileEdge> edgesAdded = new HashSet<TileEdge>();
		HashSet<Tile> visited = new HashSet<Tile>();
		ArrayList<TileEdge> neighbours = getNeighbouringEdges(grid[1][1],grid);
		for (int i = 0; i < neighbours.size(); i++) {
			edges.add(neighbours.get(i));	
			edgesAdded.add(neighbours.get(i));	
		}
		visited.add(grid[1][1]);	
		int numVertices = ((width-1)/2)*((height-1)/2);
		
		while (visited.size() < numVertices) {
			TileEdge curr = edges.remove();
			if ((visited.contains(curr.getTile0()) && visited.contains(curr.getTile2()))
				|| (!visited.contains(curr.getTile0()) && !visited.contains(curr.getTile2()))) {
				continue;
			}
			
			if (visited.contains(curr.getTile0())) {
				neighbours = getNeighbouringEdges(curr.getTile2(),grid);
				visited.add(curr.getTile2());	
			} else {
				neighbours = getNeighbouringEdges(curr.getTile0(),grid);
				visited.add(curr.getTile0());	
			}
			for (int i = 0; i < neighbours.size(); i++) {
				if (!edgesAdded.contains(neighbours.get(i))) {
					edges.add(neighbours.get(i));	
					edgesAdded.add(neighbours.get(i));	
				}
			}
			curr.getTile0().setWalkable();		
			curr.getTile1().setWalkable();
			curr.getTile2().setWalkable();
		}
		return grid;
	}

	public ArrayList<TileEdge> getNeighbouringEdges (Tile curr, Tile[][] grid) {
		ArrayList<TileEdge> neighbouringEdges = new ArrayList<TileEdge>();
		int width = grid[0].length;
		int height = grid.length;
		for (int i = Math.max(curr.getX()-2,1); i <= Math.min(curr.getX()+2,width-1); i+=2) {
			for (int j = Math.max(curr.getY()-2,1); j <= Math.min(curr.getY()+2,height-1); j+=2) {
				TileEdge newEdge = null;
				if (curr.getX()-i == 2 && curr.getY()-j == 0) {
					newEdge = new TileEdge(grid[i][curr.getY()],
										   grid[i+1][curr.getY()],curr);
				} else if (curr.getX()-i == -2 && curr.getY()-j == 0) {
					newEdge = new TileEdge(curr,grid[i-1][curr.getY()],
												grid[i][curr.getY()]);
				} else if (curr.getX()-i == 0 && curr.getY()-j == 2) {
					newEdge = new TileEdge(grid[curr.getX()][j],
										   grid[curr.getX()][j+1], curr);
				} else if (curr.getX()-i == 0 && curr.getY()-j == -2) {
					newEdge = new TileEdge(curr, grid[curr.getX()][j-1],
												 grid[curr.getX()][j]);
				} else {
					continue;
				}
				neighbouringEdges.add(newEdge);	
			}
		}
		return neighbouringEdges;
	}
}
