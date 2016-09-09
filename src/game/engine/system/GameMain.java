package game.engine.system;

import javax.swing.JPanel;

/**
 * GameMain must be named with "GameName + Main" in order to
 * catch this class by Game2DSystem
 * 
 * @author Hendry
 * @version 0.1
 */
public abstract class GameMain{
	
	public GameMain() {
		
	}
	/**
	 * implements this abstract method for game update
	 * @return true or false
	 */
	abstract public boolean gameUpdate();
	
	/**
	 * implements this abstract method for game render
	 * @return true or false
	 */
	abstract public boolean gameRender();
}
