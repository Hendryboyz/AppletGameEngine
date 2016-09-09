package game.engine.swing;
import java.awt.Dimension;

import game.engine.screen.Sprite;
import game.engine.system.InputEventTiny;
import game.engine.system.Queue;

/**
 * 
 * @author Hendry
 * @version 0.1
 */

public abstract class GameObject {
	public Sprite sprite;
	public Queue keyQ;
	public GameObjectManager gom;
	
	protected static final int BLUE_HERO = 0, RED_HERO = 1;
	
	protected int plnNo;
	
	protected int attribute;
	
	protected int targetPlnNo;
	
	protected Dimension size;
	
	
	protected boolean isChoosed = false;
	
	protected int zPosition;
	
	public GameObject() {
		
	}
	
	public GameObject(int plnNo, Sprite sprite, Queue keyQ, int x
			, int y, GameObjectManager gom, int targetPlnNo){
		
		this.plnNo = plnNo;
		this.sprite = sprite;
		this.keyQ = keyQ;
		this.gom = gom;
		this.targetPlnNo = targetPlnNo;
	}
	public boolean isVisible() { return sprite.getPlaneView(this.plnNo); }
	
	public void setVisible(boolean visible){ sprite.setPlaneView(this.plnNo, visible); }
	
	public int getX(){ return sprite.getPlanePosX(plnNo); }
	
	public int getY(){ return sprite.getPlanePosY(plnNo); }
	
	abstract public void move();
	
	abstract public boolean enterMouse(InputEventTiny ipu);
	
	abstract public boolean enterKey(InputEventTiny ipu);
	
	public int getPlnNo(){
		return plnNo;
	}
	
}
