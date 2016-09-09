package game.engine.audio;

import java.applet.*;
import java.util.*;

/**
 * 
 * @author Hendry
 * @version 0.1
 */

public class SoundPalette {
	/** */
	protected Hashtable bgms = null;
	
	/** */
	protected Hashtable ses = null;
	
	protected String javaVersion;
	
	protected int nowBgm;
	
	protected AudioClip nowSe = null;
	
	protected boolean isApplet;
	
	protected Applet owner;
	
	/**
	 * 
	 */
	public SoundPalette() {
		this.owner = null;
		this.isApplet = false;
		
		bgms = new Hashtable();
		ses = new Hashtable();
		
		javaVersion = System.getProperty("java.version");
	}
	
	/**
	 * 
	 * @param owner The manage applet of SoundPalette class
	 */
	public SoundPalette(Applet owner){
		this.owner = null;
		this.isApplet = false;
		
		//The instanceof keyword can be used to test if an object is of a specified type
		if(owner instanceof Applet){
			this.owner = owner;
			this.isApplet = true;
		}
		
		bgms = new Hashtable();
		ses = new Hashtable();
		
		javaVersion = System.getProperty("java.version");
	}
	
	/**
	 * 
	 * @param no
	 * @param file
	 * @param pool
	 * @return
	 */
	protected boolean loadData(int no, String file, Hashtable pool){
		AudioClip ac = null;
		System.out.println(file);
		ac = Applet.newAudioClip(getClass().getResource(file));
				
		if(ac == null)
			return false;
		
		pool.put(new Integer(no), ac);
		
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getNowBGM(){
		return this.nowBgm;
	}
	
	/**
	 * 
	 * @param index
	 * @param file
	 * @param pool
	 * @return
	 */
	protected boolean loadData(String index, String file, Hashtable pool){
		AudioClip ac = null;
		ac = Applet.newAudioClip(getClass().getResource(file));
		
		if(ac == null)
			return false;
		
		pool.put(new Integer(index), ac);
		
		return true;
	}
	
	/**
	 * 
	 * @param no
	 * @param file
	 * @return
	 */
	public boolean addBgm(int no, String file){
		return loadData(no, file, bgms);
	}
	
	/**
	 * 
	 * @param no
	 * @param file
	 * @return
	 */
	public boolean addSe(int no, String file){
		return loadData(no, file, ses);
	}
	
	/**
	 * 
	 * @param no
	 * @param pool
	 * @return
	 */
	protected AudioClip getAc(int no, Hashtable pool){
		AudioClip ac = null;
		ac = (AudioClip) pool.get(new Integer(no));
		
		return ac;
	}
	
	/**
	 * 
	 * @param index
	 * @param file
	 * @return
	 */
	public boolean addBgm(String index, String file){
		
		return loadData(index, file, bgms);
	}
	
	/**
	 * 
	 * @param index
	 * @param file
	 * @return
	 */
	public boolean addSe(String index, String file){
		return loadData(index, file, ses);
	}
	
	/**
	 * 
	 * @param index
	 * @param pool
	 * @return
	 */
	protected AudioClip getAc(String index, Hashtable pool){
		AudioClip ac = null;
		ac = (AudioClip) pool.get(index);
		
		return ac;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean pauseBgm(){
		AudioClip ac = getAc(nowBgm, bgms);
		if(ac == null)
			return false;
		
		ac.stop();
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean restartBgm(){
		AudioClip ac = getAc(nowBgm, bgms);
		if(ac == null)
			return false;
		
		ac.loop();
		return true;
	}
	
	/**
	 * 
	 * @param no
	 * @return
	 */
	public boolean playBgm(int no){
		AudioClip ac = getAc(nowBgm, bgms);
		
		if( ac != null )
			ac.stop();
		nowBgm = no;
		ac = getAc(nowBgm, bgms);
		if(ac == null)
			return false;
		ac.loop();
		
		return true;
	}
	
	/**
	 * 
	 * @param no
	 * @return
	 */
	public boolean playSe(int no){
		if(nowSe != null)
			nowSe.stop();
		
		nowSe = getAc(no, ses);
		
		if(nowSe == null)
			return false;
		
		nowSe.play();
		return true;
			
	}
	
}
