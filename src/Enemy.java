public class Enemy {
	boolean isDead;	
	Tile location;

	public Enemy (Tile t) {
		location = t;
	}
	
	public Enemy () {
	
	}
	
	public void setDead (boolean dead) {
		isDead = dead;
	}

	public boolean isDead() {
		return isDead;
	}
	
	public Tile getLocation () {
		return location;
	}
	
	public void setLocation (Tile t) {
		location = t;
	}
}
