package game.engine.screen;

import java.awt.*;
import java.util.*;

/**
 * this class is the basic drawing unit
 * 
 * @author Hendry
 * @version 0.1
 */
public class Plane {
	
	public boolean view = false;
	
	public boolean animation = false;
	
	public int AnimationType = 0;
	
	public int posX = 0, posY = 0;
	
	public Integer animeNo = null;
	
	public Integer[] AnimeList = null;
	
	public int planeMode = 0;
	
	public Hashtable grp = new Hashtable();
	
	public String str = null;
	
	public Font font = null;
	
	public Color color = null;
	
	public Draw draw = null;
}
