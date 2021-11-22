import java.awt.*;
import javax.swing.ImageIcon;

public class Sprite {
	
	private ImageIcon sprite;
    public Sprite(String imageName, int x, int y) {
    	try {	
    		sprite = new ImageIcon(this.getClass().getResource("/img/" + imageName + ".gif"));
    		Image image = sprite.getImage().getScaledInstance(x, y, Image.SCALE_FAST);
    		sprite.setImage(image);	
    	}
    	catch (Exception e) {}	
    }
    public ImageIcon getSprite(){
    	return this.sprite;
    }
}
