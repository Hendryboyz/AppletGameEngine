package game.engine.swing;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;

import game.engine.screen.Draw;
import game.engine.screen.Plane;
import game.engine.screen.Sprite;
import game.engine.system.InputEventTiny;


public class GameLabel extends GameObject{
	protected String content;
	protected int labelID;
	protected Dimension size;
	private boolean stringMode = true;
	
	protected drawLabel Draw;
	
	public GameLabel(String content, Sprite sprite, int x, int y) {
		this.content = content;
		this.sprite = sprite;
		this.plnNo = sprite.getPlanesSize();
		this.labelID = -1;
		
		this.Draw = new drawLabel();
		sprite.setPlaneDraw(plnNo, Draw);
		sprite.setPlanePos(plnNo, x, y);
		this.setVisible(true);
	}
	
	public boolean loadImage(String path, Dimension size){
		this.labelID = sprite.getImageNumbers();
		sprite.addGrp(labelID, path);
		sprite.waitLoad();
		this.size = size;
		stringMode = false;
		return true;
	}
	
	public void setContentColor(Color color){
		Draw.contentColor = color;
	}
	
	@Override
	public void move() {
		
	}
	
	@Override
	public boolean enterMouse(InputEventTiny ipu) {
		return false;
	}

	@Override
	public boolean enterKey(InputEventTiny ipu) {
		// TODO Auto-generated method stub
		return false;
	}
	
	class drawLabel implements Draw{
		public Color contentColor = Color.BLACK;
		
		@Override
		public boolean drawing(Graphics g, Plane pln) {
			g.setFont(new Font("Monospaced", Font.PLAIN, 15));
			g.setColor(contentColor);
			if(stringMode){
				g.drawString(content, pln.posX, pln.posY + g.getFontMetrics(g.getFont()).getAscent());
			}
			else{	
				//g.setColor(img.getGraphics().getColor());
				g.drawImage(sprite.getGroupImage(labelID), pln.posX, pln.posY, (int)size.getWidth(), (int)size.getHeight(), null);
				//g.drawString("fuck me", pln.posX, pln.posY + g.getFontMetrics(g.getFont()).getAscent());
			}
			return true;
		}
		
	}

	
}
