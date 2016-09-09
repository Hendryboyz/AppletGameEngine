package game.engine.swing;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import game.engine.screen.Draw;
import game.engine.screen.Plane;
import game.engine.screen.Sprite;
import game.engine.system.InputEventTiny;

/**
 * 
 * @author Hendry
 * @version 0.1
 */
public class GameButton extends GameObject{
	protected String content;
	protected boolean imgButton = false;
	protected boolean isChoosed = false;
	protected boolean isClicked = false;
	protected ComponentAction action;
	protected int normalID, hoverID, pushID;
	
	protected drawButton Draw;
	
	public GameButton(String content, Sprite sprite, Dimension size, int x, int y) {
		this.content = content;
		this.sprite = sprite;
		this.size = size;
		this.plnNo = sprite.getPlanesSize();
		this.Draw = new drawButton();
		this.normalID = -1;
		this.hoverID = -1;
		this.pushID = -1;
		
		sprite.setPlaneDraw(plnNo, Draw);
		sprite.setPlanePos(plnNo, x, y);
		this.setVisible(true);
	}
	
	public void setIcon(String normal_path, String hover_path, String push_path){
		if(normal_path != null){
			normalID = sprite.getImageNumbers() ;
			sprite.addGrp(normalID, normal_path);
		}
		if(hover_path != null){
			hoverID = sprite.getImageNumbers();
			sprite.addGrp(hoverID, hover_path);
		}
			
		if(push_path != null){
			pushID = sprite.getImageNumbers();
			sprite.addGrp(pushID, push_path);
		}
		sprite.waitLoad();
		if(normalID != -1)
			this.imgButton = true;
	}
	
	public String getContent(){	return this.content; }
	
	@Override
	public void move() {
		
	}
	
	public void addAction(ComponentAction action){
		this.action = action;
	}
	
	public boolean getClick(){
		return this.isClicked;
	}
	
	public boolean getChoose(){
		return this.isChoosed;
	}
	
	private boolean isClick(int x, int y){
		int btn_posX = sprite.getPlanePosX(plnNo);
		int btn_posY = sprite.getPlanePosY(plnNo);
		if( (x >= btn_posX && x <= btn_posX + size.getWidth()) 
				&& (y >= btn_posY && y <= btn_posY + size.getHeight())){
			this.isClicked = true;
		}
		else
			this.isClicked = false;
		
		return isClicked;
	}
	
	private boolean isChoosed(int x, int y){
		int btn_posX = sprite.getPlanePosX(plnNo);
		int btn_posY = sprite.getPlanePosY(plnNo);
		if( (x >= btn_posX && x <= btn_posX + size.getWidth()) 
				&& (y >= btn_posY && y <= btn_posY + size.getHeight())){
			this.isChoosed = true;
			this.isClicked = false;
		}
		else{
			this.isChoosed = false;
			this.isClicked = false;
		}
		
		return isChoosed;
	}
	
	public void setChoosed(boolean choosed){
		this.isChoosed = choosed;
	}
	@Override
	public boolean enterMouse(InputEventTiny ipu) {
		boolean tmpChoosed = isChoosed;
		if(ipu.getID() == 503){
			if( tmpChoosed != isChoosed(ipu.getX(), ipu.getY()) ){
				if(action != null)
					action.runAction();
			}
		}//mouse motion event
		else if(ipu.getID() == 502){
			/*System.out.println(mouseOutput.getKeyCode() + ":[ " + mouseOutput.getX() + ", " + 
					mouseOutput.getY() + " ]");*/
			switch(ipu.getKeyCode()){
				case 1:
					this.isClicked = false;
					break;
				case 2:
					
					break; 
				case 3:
					
					break;
			}
		}//mouse released event
		else if(ipu.getID() == 501){
			/*System.out.println(mouseOutput.getID() + ":[ " + mouseOutput.getX() + ", " + 
					mouseOutput.getY() + " ]");*/
			switch(ipu.getKeyCode()){
				case 1:
					if(isClick(ipu.getX(), ipu.getY())){
						if(action != null)
							action.runAction();
					}
					break;
				case 2:
					
					break; 
				case 3:
					
					break;
			}
		}//mouse pressed event 
		return false;
	}

	@Override
	public boolean enterKey(InputEventTiny ipu) {
		return false;
	}

	
	class drawButton implements Draw{

		@Override
		public boolean drawing(Graphics g, Plane pln) {
			if(!imgButton){
				g.setFont(new Font("Monospaced", Font.PLAIN, 15));
				g.setColor(Color.BLACK);
				g.fillRect(pln.posX, pln.posY,
						(int)size.getWidth(), (int)size.getHeight());					
				if(isChoosed)
					g.setColor(Color.YELLOW);
				else
					g.setColor(Color.WHITE);
				g.drawString(content, (int)(pln.posX + size.getWidth() / 2 - g.getFontMetrics().stringWidth(content)/2), 
						(int)(pln.posY + size.getHeight()/2 + g.getFontMetrics(g.getFont()).getDescent()));
			}
			else{
				if(isClicked == false)
					g.drawImage(sprite.getGroupImage(normalID), pln.posX, pln.posY, null);
				else if(isClicked == true){
					if(pushID != -1)
						g.drawImage(sprite.getGroupImage(pushID), pln.posX, pln.posY, null);
					else
						g.drawImage(sprite.getGroupImage(normalID), pln.posX, pln.posY, null);
				}
				else if(isChoosed == true){
					if(hoverID != -1)
						g.drawImage(sprite.getGroupImage(hoverID), pln.posX, pln.posY, null);
					else
						g.drawImage(sprite.getGroupImage(normalID), pln.posX, pln.posY, null);
				}
			}
			return true;
		}
		
	}

}
