package game.engine.swing;
import game.engine.screen.Draw;
import game.engine.screen.Plane;
import game.engine.screen.Sprite;
import game.engine.system.InputEventTiny;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Timer;




/**
 * 
 * @author Hendry
 * @version 0.1
 */

public class GameTextField extends GameObject{
	protected String content;
	protected int fontWidth;
	protected int wordLength;
	protected boolean isChoosed = false;
	protected boolean isClicked = false;
	protected boolean blinked = false;
	protected boolean isFocusOn = false;
	protected drawTextField Draw;
	
	protected Timer blinkTimer;
	
	/**
	 * 
	 * @param sprite
	 * @param length
	 * @param x
	 * @param y
	 */
	public GameTextField(Sprite sprite, int length, int x, int y) {
		this.content = "";
		this.sprite = sprite;
		this.wordLength = length;
		this.size = new Dimension(length, 25);
		this.plnNo = sprite.getPlanesSize();
		this.Draw = new drawTextField();
		sprite.setPlaneDraw(this.plnNo, Draw);
		sprite.setPlanePos(this.plnNo, x, y);
		sprite.setPlaneView(this.plnNo, true);
		this.setVisible(true);
		
		ActionListener blinkedAction = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(isFocusOn){
					if(blinked)
						setBlinked(false);
					else
						setBlinked(true);
				}
				else{
					setBlinked(false);
				}
			}
			
		};
		blinkTimer = new Timer(700, blinkedAction);
		
	}
	
	/**
	 * 
	 * @param color
	 */
	public void setContentColor(Color color){
		Draw.contentColor = color;
	}
	
	@Override
	/**
	 * 
	 */
	public void move() {
		
	}
	
	/**
	 * 
	 * @param width
	 */
	public void setFontWidth(int width){
		this.fontWidth = width;
	}
	
	/**
	 * 
	 * @param blink
	 */
	private void setBlinked(boolean blink){
		this.blinked = blink;
	}
	
	/**
	 * 
	 * @return String : content of this textfield
	 */
	public String getContent(){
		return this.content;
	}
	
	/**
	 * 
	 * @param InputEventTiny
	 * @return boolean
	 */
	@Override
	public boolean enterMouse(InputEventTiny ipu){
		if(ipu.getID() == 503){
			isChoosed(ipu.getX(), ipu.getY());
		}//mouse motion event
		else if(ipu.getID() == 502){
			/*System.out.println(mouseOutput.getKeyCode() + ":[ " + mouseOutput.getX() + ", " + 
					mouseOutput.getY() + " ]");*/
			switch(ipu.getKeyCode()){
				case 1:
					isClick(ipu.getX(), ipu.getY());
					break;
				case 2:
					
					break; 
				case 3:
					
					break;
			}
		}//mouse pressed event
		else{
			/*System.out.println(mouseOutput.getID() + ":[ " + mouseOutput.getX() + ", " + 
					mouseOutput.getY() + " ]");*/
			
		}//mouse released 
		return false;
	}
	
	/**
	 * 
	 * @param InputEventTiny
	 * @return boolean
	 */
	@Override
	public boolean enterKey(InputEventTiny ipu) {
		if(isFocusOn){
			if(ipu.getKeyCode() == KeyEvent.VK_BACK_SPACE && content.length() > 0)
				this.content = this.content.substring(0, content.length()-1);
			else if(ipu.getKeyCode() >= KeyEvent.VK_A && ipu.getKeyCode() <= KeyEvent.VK_Z){
				if(content.length() < wordLength){
					int getCharValue = ipu.getKeyCode() - KeyEvent.VK_A + 'a';
					char getChar = (char) getCharValue;
					this.content += getChar;
				}
			}
			else if(ipu.getKeyCode() >= KeyEvent.VK_0 && ipu.getKeyCode() <= KeyEvent.VK_9){
				if(content.length() < wordLength){
					int getCharValue = ipu.getKeyCode() - KeyEvent.VK_0 + '0';
					char getChar = (char) getCharValue;
					this.content += getChar;
				}
			}
				
			return true;
		}
		else
			return false;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return true or false
	 */
	public boolean isClick(int x, int y){
		int btn_posX = sprite.getPlanePosX(plnNo);
		int btn_posY = sprite.getPlanePosY(plnNo);
		if( (x >= btn_posX && x <= btn_posX + size.getWidth() * this.fontWidth) 
				&& (y >= btn_posY && y <= btn_posY + size.getHeight())){
			this.isClicked = true;
			if(!isFocusOn)
				this.content = "";
			blinkTimer.start();
			this.isFocusOn = true;
			
		}
		else{
			blinkTimer.stop();
			this.isClicked = false;
			this.isFocusOn = false;
			this.blinked = false;
		}
		
		return isClicked;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isChoosed(int x, int y){
		int btn_posX = sprite.getPlanePosX(plnNo);
		int btn_posY = sprite.getPlanePosY(plnNo);
		if( (x >= btn_posX && x <= btn_posX + size.getWidth() * this.fontWidth) 
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
	
	/**
	 * 
	 * @param choosed
	 */
	public void setChoosed(boolean choosed){
		this.isChoosed = choosed;
	}
	
	/**
	 * like paint components of the JSwing, we have to implements it
	 * to paint it
	 * 
	 * @author Hendry
	 * @version
	 */
	class drawTextField implements Draw{
		public Color contentColor = Color.BLACK;
		
		@Override
		public boolean drawing(Graphics g, Plane pln) {
			g.setColor(Color.LIGHT_GRAY);
			
			g.fillRect( pln.posX, pln.posY,
					(int)wordLength * 16, (int)size.getHeight());
			if(isFocusOn)
				g.setColor(Color.YELLOW);
			else
				g.setColor(Color.BLACK);
			g.drawRect( pln.posX, pln.posY,
					(int)wordLength * 16, (int)size.getHeight());
			
			
			
			String tmpContent = "";
			if(blinked){
				tmpContent = content + "|";
			}
			else{
				tmpContent = content;
			}
			
			g.setColor(contentColor);
			g.setFont(new Font("Monospaced", Font.PLAIN, 20));
			g.drawString(tmpContent, (int)(pln.posX), 
					(int)(pln.posY+ size.getHeight()/2 + g.getFontMetrics(g.getFont()).getDescent()));
			setFontWidth(g.getFontMetrics(g.getFont()).getAscent());
			return true;
		}
		
	}

	

}
