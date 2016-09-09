package game.engine.screen;

import java.awt.Graphics;

/**
 * 
 * @author Hendry
 * @version 0.1
 * 
 */

public interface Draw {
	/**
	 * 
	 * 
	 * @param g
	 * @param plane
	 * @return true or false
	 */
	public boolean drawing(Graphics g, Plane plane);
}
