import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {
	private Maze maze;
    
	public Controller(Maze maze){
		this.maze = maze;	
	}
		
	@Override
	public void keyPressed(KeyEvent e) {
		int dx = 0;	
		int dy = 0;	
		 
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D){
			dx = 1;	
			dy = 0;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A){
			dx = -1;	
			dy = 0;
		} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			dx = 0;	
			dy = -1;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
			dx = 0;	
			dy = 1;
		} else {	
			return;
		}
		 
		if (maze.isValid(dx, dy)) {
			maze.updatePlayerLoc(dx, dy);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
