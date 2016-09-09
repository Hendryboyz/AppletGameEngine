package game.engine.swing;

import game.engine.audio.SoundPalette;
import game.engine.screen.Draw;
import game.engine.screen.Plane;
import game.engine.screen.Sprite;

import java.awt.Graphics;
import java.util.ArrayList;

public class GamePanel extends GameObjectManager{
	
	protected ArrayList<GameObject> components;
	protected int[] usesPlane;
	protected Sprite sprite;
	protected SoundPalette sp;
	protected boolean visible;
	
	public GamePanel(Sprite sprite, SoundPalette sp) {
		this.components = new ArrayList<GameObject>();
		this.usesPlane = new int[2];
		this.sprite = sprite;
		this.sp = sp;
		usesPlane[0] = sprite.getPlanesSize();
	}
	
	public void addComponent(GameObject component){
		this.components.add(component);
	}
	
	public void setPanelVisible(boolean visible){
		this.visible = visible;
		for(int i = 0 ; i < components.size() ; i++){
			if(usesPlane[0] + i <= usesPlane[1]){
				components.get(i).setVisible(visible);
			}
		}
	}
	
	public boolean getPlaneVisible(){ return this.visible; }
	
	public void finishAdding(){ usesPlane[1] = sprite.getPlanesSize() - 1; }
	
	public int getComponentsSize(){ return components.size(); }
	
	public GameObject getComponent(int index){
		return this.components.get(index);
	}

	public int getComponent_PlnNo(int index){
		return this.components.get(index).getPlnNo();
	} 
	
	public void removeComponent(int index){
		this.components.remove(index);
	}
}
