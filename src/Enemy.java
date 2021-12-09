public class Enemy {
	boolean isDead;	
	Tile location;

	Sound sound = new Sound();

	public Enemy (Tile t) {
		location = t;
	}
	
	public Enemy () {
	
	}
	
	public void setDead (boolean dead) {
		isDead = dead;
		if(dead)
			sound.playSE(7);
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
