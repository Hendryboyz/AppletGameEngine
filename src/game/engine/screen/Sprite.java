package game.engine.screen;
import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * About this class
 * 
 * @author Hendry
 * @version 0.1
 */

public class Sprite {
	/** the plane mode of the sprite */
	public static final int NULL_MODE = 0, GRP_MODE = 1, STR_MODE = 2, 
			DRW_MODE = 8, CENTER_STR_MODE = 6;
	/**  */
	int canvasWidth, canvasHeight;
	/**  */
	protected Container owner;
	/**  */
	protected Hashtable grp = null;
	/**  */
	protected BufferedImage dbImg = null;
	/**  */
	protected MediaTracker tracker;
	/**  */
	protected Hashtable planes;
	/**  */
	protected Integer spriteList[];
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @param con
	 */
	public Sprite(int width, int height, Container con) {
		grp = new Hashtable();
		
		planes = new Hashtable();
		
		this.canvasWidth = width;
		this.canvasHeight = height;
		this.owner = con;
		
		tracker = new MediaTracker(owner);
	}
	
	/**
	 * 
	 * @return
	 */
	public int getImageNumbers(){
		return grp.size();
	}
	
	public Image getGroupImage(int no){
		Image requst = (Image) grp.get(new Integer(no));
		
		return requst;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getPlanesSize(){
		return planes.size();
	}
	
	/**
	 * 
	 * @param info
	 * @param e
	 */
	private void information(String info, Exception e){
		System.out.println(info);
        System.out.println("java.version : "
            + System.getProperty("java.version"));
        System.out.println("java.vendor : "
            + System.getProperty("java.vendor"));
        if(e != null)
            e.printStackTrace();
	}
	
	public int getCanvasWidth(){ return canvasWidth; }
	/**
	 * 
	 * @param no
	 * @param file
	 * @return 
	 */
	public boolean addGrp(int no, String file){
		try{
			//System.out.println(file);
			grp.put(new Integer(no), 
				Toolkit.getDefaultToolkit().getImage(getClass().getResource(file)));
		}catch(Exception e){
			information("Warning : Do not create image data.", e);
            return false;
		}
		
		tracker.addImage(((Image) grp.get(new Integer(no))), 1);
		
		return true;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean waitLoad(){
		try {
			tracker.waitForID(1);
		} catch (InterruptedException e) {
			information("Warning : Problem occurred in waitLoad().", e);
            return false;
		}
		
		return true;
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int isLoaded(){
		if(tracker.checkID(1) == false)
			return 0;
		
		if(tracker.isErrorID(1) == false)
			return 1;
		
		return -1;
	}
	
	/**
	 * 
	 * @param planeNo
	 * @param animeNo
	 * @param grpNo
	 * @return
	 */
	public boolean setPlaneGrp(int planeNo, int animeNo, int grpNo){
		Integer pno = new Integer(planeNo);
		Plane pln = (Plane)planes.get(pno);
		if(pln == null) {
            pln = new Plane();
            planes.put(pno, pln);
        }
		
		pln.animeNo = new Integer(animeNo);
		pln.grp.put(pln.animeNo, grp.get(new Integer(grpNo)));
		pln.planeMode = GRP_MODE;
		pln.view = true;
		pln.str = null;
		pln.font = null;
		pln.color = null;
		pln.draw = null;
		
		return true;
	}
	
	/**
	 * 
	 * @param planeNo
	 * @param grpNo
	 * @return
	 */
	public boolean setPlaneGrp(int planeNo, int grpNo){
		Integer pno = new Integer(planeNo);
		Plane pln = (Plane)planes.get(pno);
		if(pln == null) {
            pln = new Plane();
            planes.put(pno, pln);
        }
		
		pln.animeNo = new Integer(0);
		pln.grp.put(pln.animeNo, grp.get(new Integer(grpNo)));
		pln.planeMode = GRP_MODE;
		pln.view = true;
		pln.str = null;
		pln.font = null;
		pln.color = null;
		pln.draw = null;
		
		return true;
	}
	
	/**
	 * 
	 * @param planeIndex
	 * @param animeNo
	 * @param grpNo
	 * @return
	 */
	public boolean setPlaneGrp(String planeIndex, int animeNo, int grpNo){
		String pindex = new String(planeIndex);
		Plane pln = (Plane)planes.get(pindex);
		if(pln == null) {
            pln = new Plane();
            planes.put(pindex, pln);
        }
		
		pln.animeNo = new Integer(animeNo);
		pln.grp.put(pln.animeNo, grp.get(new Integer(grpNo)));
		pln.planeMode = GRP_MODE;
		pln.view = true;
		pln.str = null;
		pln.font = null;
		pln.color = null;
		pln.draw = null;
		
		return true;
	}
	
	/**
	 * 
	 * @param planeIndex
	 * @param grpNo
	 * @return
	 */
	public boolean setPlaneGrp(String planeIndex, int grpNo){
		String pindex = new String(planeIndex);
		Plane pln = (Plane)planes.get(pindex);
		if(pln == null) {
            pln = new Plane();
            planes.put(pindex, pln);
        }
		
		pln.animeNo = new Integer(0);
		pln.grp.put(pln.animeNo, grp.get(new Integer(grpNo)));
		pln.planeMode = GRP_MODE;
		pln.view = true;
		pln.str = null;
		pln.font = null;
		pln.color = null;
		pln.draw = null;
		
		return true;
	}
	
	/**
	 * 
	 * @param planeNo
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean setPlanePos(int planeNo, int x, int y){
		Plane pln = (Plane)planes.get(new Integer(planeNo));
		if( pln == null)
			return false;
		
		pln.posX = x;
		pln.posY = y;
		
		return true;
	}
	
	/**
	 * 
	 * @param planeNo
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean setPlaneMov(int planeNo, int x, int y){
		Plane pln = (Plane)planes.get(new Integer(planeNo));
		if( pln == null)
			return false;
		
		pln.posX += x;
		pln.posY += y;
		return true;
	}
	
	/**
	 * 
	 * @param planeNo
	 * @return
	 */
	public int getPlanePosX(int planeNo){
		Plane pln = (Plane)planes.get(new Integer(planeNo));
		if( pln == null)
			return 0xffff;
		return pln.posX;
	}
	
	/**
	 * 
	 * @param planeNo
	 * @return
	 */
	public int getPlanePosY(int planeNo){
		Plane pln = (Plane)planes.get(new Integer(planeNo));
		if( pln == null)
			return 0xffff;
		return pln.posY;
	}
	
	/**
	 * 
	 * @param planeNo
	 * @param mode
	 * @return
	 */
	public boolean setPlaneAnime(int planeNo, boolean mode){
		Plane pln = (Plane)planes.get(new Integer(planeNo));
		if(pln == null)
			return false;
		if(pln.planeMode != GRP_MODE)
			return false;
		pln.animation = mode;
		if(pln.animation){
			pln.AnimeList = new Integer[pln.grp.size()];
			Enumeration enumer = pln.grp.keys();
			for(int i = 0; enumer.hasMoreElements(); i++)
				pln.AnimeList[i] = (Integer)enumer.nextElement();
			Arrays.sort(pln.AnimeList);
			
			//Have to use your sort function ? add below here
			
		}
		else
			pln.animation = (Boolean) null;
		return true;	
	}
	
	/**
	 * 
	 * @param planeIndex
	 * @param mode
	 * @return
	 */
	public boolean setPlaneAnime(String planeIndex, boolean mode){
		Plane pln = (Plane)planes.get(new Integer(planeIndex));
		if(pln == null)
			return false;
		if(pln.planeMode != GRP_MODE)
			return false;
		pln.animation = mode;
		if(pln.animation){
			pln.AnimeList = new Integer[pln.grp.size()];
			Enumeration enumer = pln.grp.keys();
			for(int i = 0; enumer.hasMoreElements(); i++)
				pln.AnimeList[i] = (Integer)enumer.nextElement();
			Arrays.sort(pln.AnimeList);
			
			//Have to use your sort function ? add below here
			
		}
		else
			pln.animation = (Boolean) null;
		return true;	
	}
	
	/**
	 * 
	 * @param planeNo
	 * @param str
	 * @return
	 */
	public boolean setPlaneString(int planeNo, String str){
		Integer pno = new Integer(planeNo);
		Plane pln = (Plane) planes.get(pno);
		if(pln == null){
			pln = new Plane();
			planes.put(pno, pln);
		}
		
		pln.font = new Font("Monospaced", Font.PLAIN, 16);
		pln.color = new Color(0, 0, 0);
		pln.str = str;
		pln.planeMode = STR_MODE;
		pln.view = true;
		pln.grp.clear();
		pln.animation = false;
		pln.animeNo = null;
		pln.draw = null;
		
		return true;
	}
	
	/**
	 * 
	 * @param planeNo
	 * @param str
	 * @return
	 */
	public boolean setPlaneCenterString(int planeNo, String str){
		Integer pno = new Integer(planeNo);
		Plane pln = (Plane) planes.get(pno);
		if(pln == null){
			pln = new Plane();
			planes.put(pno, pln);
		}
		
		pln.font = new Font("Monospaced", Font.PLAIN, 16);
		pln.color = new Color(0, 0, 0);
		pln.str = str;
		pln.planeMode = STR_MODE;
		pln.view = true;
		pln.grp.clear();
		pln.animation = false;
		pln.animeNo = null;
		pln.draw = null;
		
		return true;
	}
	
	/**
	 * 
	 * @param planeNo
	 * @param name
	 * @param style
	 * @param size
	 * @return
	 */
	public boolean setPlaneFont(int planeNo, String name, int style, int size){
		Plane pln = (Plane) planes.get(new Integer(planeNo));
		if(pln == null)
			return false;
		if(pln.planeMode != STR_MODE)
			return false;
		if(name == null)
			name = "Monospaced";
		if(style < 0)
			style = Font.PLAIN;
		if(size < 0)
			size = 16;
		pln.font = new Font(name, style, size);
		
		return true;
	}
	
	/**
	 * 
	 * @param planeNo
	 * @param R
	 * @param G
	 * @param B
	 * @return
	 */
	public boolean setPlaneColor(int planeNo, int R, int G, int B){
		Plane pln = (Plane) planes.get(new Integer(planeNo));
		if(pln == null)
			return false;
		if(pln.planeMode != STR_MODE)
			return false;
		if(R < 0 || R > 255)
			R = 0;
		if(G < 0 || G > 255)
			G = 0;
		if(B < 0 || B > 255)
			B = 0;
		
		pln.color = new Color(R, G, B);
		
		return true;
	}
	
	/**
	 * 
	 * @param planeNo
	 * @param draw
	 * @return
	 */
	public boolean setPlaneDraw(int planeNo, Draw draw){
		Integer pno = new Integer(planeNo);
		Plane pln = (Plane) planes.get(pno);
		if(pln == null){
			pln = new Plane();
			planes.put(pno, pln);
		}
		
		pln.font = null;
		pln.color = null;
		pln.str = null;
		pln.planeMode = DRW_MODE;
		pln.view = true;
		pln.grp.clear();
		pln.animation = false;
		pln.animeNo = null;
		pln.draw = draw;
		
		return true;
	}
	
	/**
	 * 
	 * @param planeNo
	 * @param view
	 * @return
	 */
	public boolean setPlaneView(int planeNo, boolean view){
		Plane pln = (Plane) planes.get(new Integer(planeNo));
		if(pln == null)
			return false;
		pln.view = view;
		
		return true;
	}
	
	public boolean getPlaneView(int planeNo){
		Plane pln = (Plane) planes.get(new Integer(planeNo));
		if(pln == null)
			return false;
		return pln.view;
	}
	
	/**
	 * 
	 * @param planeNo
	 * @return
	 */
	public boolean delPlane(int planeNo){
		planes.remove(new Integer(planeNo));
		
		return true;
	}
	
	/**
	 * 
	 * @param planeIndex
	 * @param view
	 * @return
	 */
	public boolean setPlaneView(String planeIndex, boolean view){
		Plane pln = (Plane) planes.get(new String(planeIndex));
		if(pln == null)
			return false;
		pln.view = view;
		
		return true;
	}
	
	/**
	 * 
	 * @param planeIndex
	 * @return
	 */
	public boolean delPlane(String planeIndex){
		planes.remove(new Integer(planeIndex));
		
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean delPlaneAll(){
		planes.clear();
		
		return true;
	}
	
	/**
	 * 
	 * @param g
	 * @return
	 */
	public boolean paintScreen(Graphics g){
		Graphics dbg;
		Plane pln;
		if(dbImg == null)
			dbImg = (BufferedImage) owner.createImage(canvasWidth, canvasHeight);
		dbg = dbImg.getGraphics();
		
		spriteList = new Integer[planes.size()];
		Enumeration enumer = planes.keys();
		
		for(int i = 0; enumer.hasMoreElements() ; i++)
			spriteList[i] = (Integer) enumer.nextElement();
		
		Arrays.sort(spriteList);
		
		//Want to use your sorting ? Writing below here and comment up line
		
		int drawNumbers = 0;
		
		dbg.clearRect(0, 0, canvasWidth, canvasHeight);
		
		for(int i = 0 ; i < spriteList.length ; i++){
			pln = (Plane)(planes.get(spriteList[i]));
			if(pln.view == false){
				continue;
			}
			
			if(pln.planeMode == GRP_MODE){
				dbg.drawImage((Image) (pln.grp.get(pln.animeNo)), pln.posX,
						pln.posY, owner);
				if(pln.animation == true){
					int j = 0;
					/* The value of the AnimationList is not continue, so
					 * we have to search where it is. 
					*/
					for(; pln.AnimeList[j]!= pln.animeNo ; j++){
						if( j >= pln.AnimeList.length - 1)
							break;
					}
					j = (j+1) % pln.AnimeList.length;
					pln.animeNo = pln.AnimeList[j];
				}
				
				drawNumbers += 1;
			}
			else if(pln.planeMode == STR_MODE){
				dbg.setFont(pln.font);
				dbg.setColor(pln.color);
				dbg.drawString(pln.str, pln.posX, pln.posY);
				drawNumbers += 1;
			}
			else if(pln.planeMode == CENTER_STR_MODE){
				dbg.setFont(pln.font);
				dbg.setColor(pln.color);
				dbg.drawString(pln.str, pln.posX - 
						(dbg.getFontMetrics().stringWidth(pln.str)/2), pln.posY);
				drawNumbers += 1;
			}
			else if(pln.planeMode == DRW_MODE){
				pln.draw.drawing(dbg, pln);
				drawNumbers += 1;
			}
				
		}
		
		dbg.dispose();
		g.drawImage(dbImg, 0, 0, owner);
		
		return true;
	}
}
