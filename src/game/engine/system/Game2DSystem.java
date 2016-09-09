package game.engine.system;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.util.*;

import game.engine.screen.*;
import game.engine.audio.*;

import javax.swing.*;

import com.sun.j3d.utils.timer.*;

/**
 * GameSystem of this game engine
 * this class includes
 * 1. main loop of the game
 * 2. handle key input method
 * 
 * , and we support web by extending JApplet
 * 
 * @author Hendry
 * @version 0.1
 */
public class Game2DSystem extends JApplet {
	/**  */
	protected static String GAME_NAME;

	/**  */
	protected static String GAME_MAIN_NAME;

	/**  */
	protected static int CANVAS_WIDTH = 800, CANVAS_HEIGHT = 600;

	/**
	 * Number of frames with a delay of 0 ms before the animation thread yield
	 * to other running threads
	 */
	private static final int NO_DELAYS_PER_YIELD = 16;

	/** no. of frames that can be skipped in any one animation loop */
	private static int MAX_FRAME_SKIPS = 5;

	protected static long period = 100;

	private volatile boolean running = false;
	private volatile boolean gameOver = false;
	private volatile boolean isPaused = false;
	private volatile boolean Stopped = false;

	/**  */
	protected int KEY_SPEED = 50;

	protected int KEY_DELAY = 3;

	protected boolean pressUp = false, pressDown = false, pressLeft = false,
			pressRight = false;

	protected boolean mouseOnFrame = false;

	protected boolean appletFlag = true;

	protected Sprite sprite;

	protected SoundPalette sp;

	protected GameMain gm;

	protected Queue keyQ;

	protected Queue mouseQ;

	protected GameLoop timerTask;
	
	/**
	 * 
	 */
	public Game2DSystem() {
		try {
			GAME_NAME = getClass().getName();
			Class[] mbrs = getClass().getClasses();
			if (mbrs.length == 0) {
				GAME_MAIN_NAME = GAME_NAME + "$" + GAME_NAME + "Main";
				information("Warning : I can not getClasses().", null);
			}
			
			for (int i = 0; i < mbrs.length; i++) {
				if (mbrs[i].getSuperclass().getName()
						.compareTo("game.engine.system.GameMain") == 0){
					GAME_MAIN_NAME = mbrs[i].getName();
				}
			}
			
		} catch (Exception e) {
			information("Error : I can not finish Game2DSystem constructor.", e);
		}
		System.out.println("456");
	}

	/**
	 * 
	 * @param info
	 * @param e
	 */
	protected static void information(String info, Exception e) {
		System.out.println(info);
		System.out.println("java.version : "
				+ System.getProperty("java.version"));
		System.out
				.println("java.vendor : " + System.getProperty("java.vendor"));
		if (e != null) {
			if (e.getClass().getName()
					.compareTo("java.lang.reflect.InvocationTargetException") == 0)
				((InvocationTargetException) e).getTargetException()
						.printStackTrace();
			else
				e.printStackTrace();
		}
		if (info.indexOf("Error") == 0)
			System.exit(0);
	}

	/**
	 * 
	 * @param game2dClassName
	 */
	protected static void startGame(String game2dClassName) {
		GAME_NAME = game2dClassName;
		try {
			Game2DSystem game2D = (Game2DSystem) (Class
					.forName(game2dClassName).newInstance());
			game2D.newGameSystem();
		} catch (Exception e) {
			information("Error : I can not create Game2DSystem or newGame2D().", e);
		}
	}

	/**
	 * 
	 * @return
	 */
	protected GameMain newGame2DMain() {
		try {
			Class argClass[] = { getClass() };
			
			Constructor g2dmCon = Class.forName(GAME_MAIN_NAME).getConstructor(
					argClass);
			Object initArgs[] = { this };
			return (GameMain) (g2dmCon.newInstance(initArgs));
		} catch (Exception e) {
			information("Error : I can not create Game2DMain.", e);
		}
		return null;
	}
	
	/**
	 * 
	 */
	public void newGameSystem() {

		appletFlag = false;
		init();
		
		Frame frame = new Frame(GAME_NAME);
		frame.pack();
		frame.setVisible(true);
		frame.setVisible(false);
		frame.pack();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((d.width - CANVAS_WIDTH) / 2,
				(d.height - CANVAS_HEIGHT) / 2);

		int left, right, top, bottom;

		left = frame.getInsets().left;
		right = frame.getInsets().right;
		top = frame.getInsets().top;
		bottom = frame.getInsets().bottom;
		frame.setSize(CANVAS_WIDTH + left + right, CANVAS_HEIGHT + top + bottom);
		
		frame.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
			public void windowIconified(WindowEvent e) {
				stop();
			}
			
			public void windowDeiconified(WindowEvent e) {
				start();
			}
			
		});
		
		frame.setResizable(false);
		frame.add(this);
		
		frame.setVisible(true);

		if (left != frame.getInsets().left || right != frame.getInsets().right
				|| top != frame.getInsets().top
				|| bottom != frame.getInsets().bottom) {

			left = frame.getInsets().left;
			right = frame.getInsets().right;
			top = frame.getInsets().top;
			bottom = frame.getInsets().bottom;

			frame.setSize(CANVAS_WIDTH + left + right, CANVAS_HEIGHT + top
					+ bottom);
		}
	}
	
	/**
	 * 
	 */
	@Override
	public void init() {
		
		enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK
				| AWTEvent.KEY_EVENT_MASK);
		
		keyQ = new Queue();
		mouseQ = new Queue();
		
		if(appletFlag)
			sp = new SoundPalette(this);
		else
			sp = new SoundPalette();
		System.out.println("111");
		sprite = new Sprite(CANVAS_WIDTH, CANVAS_HEIGHT, this);
		
		gm = newGame2DMain();
		running = true;
		timerTask = new GameLoop();
		timerTask.start();
		requestFocus();
		requestFocusInWindow();
		
		if(!appletFlag)
			start();
		
	}
	
	/**
	 * 
	 */
	@Override
	public void start() {
		sp.restartBgm();
		
		synchronized (timerTask) {
			timerTask.notify();
		}
		
		this.isPaused = false;
	}

	private synchronized void resumeGame() {
		timerTask.notify();
	}

	/**
	 * 
	 */
	@Override
	public void stop() {
		sp.pauseBgm();
		
		stopGame();
		
		this.isPaused = true;
	}

	private synchronized void stopGame() {
		notify();
	}

	/**
	 * 
	 */
	@Override
	public void destroy() {
		this.Stopped = true;
		try{
			
		}catch(Exception e){
			information("Error : I can not finish destroy().", e);
		}
	}
	
	public void processMouseEvent(MouseEvent e){
		switch(e.getID()){
			case MouseEvent.MOUSE_ENTERED:
				mouseOnFrame = true;
				break;
			case MouseEvent.MOUSE_EXITED:
				mouseOnFrame = false;
				break;
			case MouseEvent.MOUSE_PRESSED:
				if(mouseOnFrame){
					mouseQ.enqueue(new InputEventTiny(
							e.getID(),
							e.getX() - getInsets().left,
							e.getY() - getInsets().top, 
							e.getButton()));
				}
				break;
			case MouseEvent.MOUSE_RELEASED:
				if(mouseOnFrame){
					mouseQ.enqueue(new InputEventTiny(
							e.getID(),
							e.getX() - getInsets().left,
							e.getY() - getInsets().top, 
							e.getButton()));
				}
				break;
		}
	}
	
	public void processMouseMotionEvent(MouseEvent e){
		if(!mouseOnFrame || e.getID() != MouseEvent.MOUSE_MOVED
				|| e.getX() < getInsets().left
				|| e.getX() > CANVAS_WIDTH + getInsets().left
				|| e.getY() < getInsets().top
				|| e.getY() > CANVAS_HEIGHT + getInsets().top){
			return;
		}
		mouseQ.enqueue(new InputEventTiny(e.getID(), 
				e.getX() - getInsets().left,
				e.getY() - getInsets().top, 
				e.getButton()));
	}
	
	public void processKeyEvent(KeyEvent e){
		switch (e.getID()) {
			case KeyEvent.KEY_PRESSED:
				
				switch(e.getKeyCode()){
				
					case KeyEvent.VK_UP:
						if(!pressUp){
							keyQ.enqueue(new InputEventTiny(e.getID()
									, e.getKeyCode()));
						}
						pressUp = true;
						break;
					case KeyEvent.VK_DOWN:
						if(!pressDown){
							keyQ.enqueue(new InputEventTiny(e.getID()
									, e.getKeyCode()));
						}
						pressDown = true;
						break;
					case KeyEvent.VK_LEFT:
						if(!pressLeft){
							keyQ.enqueue(new InputEventTiny(e.getID()
									, e.getKeyCode()));
						}
						pressLeft = true;
						break;
					case KeyEvent.VK_RIGHT:
						if(!pressRight){
							keyQ.enqueue(new InputEventTiny(e.getID()
									, e.getKeyCode()));
						}
						pressRight = true;
						break;
					default:
						keyQ.enqueue(new InputEventTiny(e.getID()
								, e.getKeyCode()));
						break;
				}
				break;
	
			case KeyEvent.KEY_RELEASED:
				
				switch(e.getKeyCode()){
					case KeyEvent.VK_UP:
						pressUp = false;
						break;
					case KeyEvent.VK_DOWN:
						pressDown = false;
						break;
					case KeyEvent.VK_LEFT:
						pressLeft = false;
						break;
					case KeyEvent.VK_RIGHT:
						pressRight = false;
						break;
				}
				break;
		}
	}
	
	@Override
	public void update(Graphics g) {
		sprite.paintScreen(g);
	}
	
	public void paint(Graphics g) {
		sprite.paintScreen(g);
		requestFocus();
	}
	
	/**
	 * 
	 * @author Hendry
	 * @version 0.1
	 * 
	 */
	class KeyRepeater extends Thread {
		
		public void run(){
			long processTime, pressDownCount = 0, pressUpCount = 0,
					pressRightCount = 0, pressLeftCount = 0, pressOtherCount = 0;
			
			while( running ){
				try{
					processTime = J3DTimer.getValue();
					
					if(pressUp){
						if(pressUpCount > KEY_DELAY){
							keyQ.enqueue(new InputEventTiny(KeyEvent.KEY_PRESSED
									, KeyEvent.VK_UP));
						}
						else{
							pressUpCount++;
						}
					}
					else{
						pressUpCount = 0;
					}
					
					if(pressDown){
						if(pressDownCount > KEY_DELAY){
							keyQ.enqueue(new InputEventTiny(KeyEvent.KEY_PRESSED
									, KeyEvent.VK_DOWN));
						}
						else{
							pressDownCount++;
						}
					}
					else{
						pressDownCount = 0;
					}
					
					if(pressLeft){
						if(pressLeftCount > KEY_DELAY){
							keyQ.enqueue(new InputEventTiny(KeyEvent.KEY_PRESSED
									, KeyEvent.VK_LEFT));
						}
						else{
							pressLeftCount++;
						}
					}
					else{
						pressLeftCount = 0;
					}
					
					if(pressRight){
						if(pressRightCount > KEY_DELAY){
							keyQ.enqueue(new InputEventTiny(KeyEvent.KEY_PRESSED
									, KeyEvent.VK_RIGHT));
						}
						else{
							pressRightCount++;
						}
					}
					else{
						pressRightCount = 0;
					}
					
					processTime = J3DTimer.getValue() - processTime;
					
					if((KEY_SPEED - processTime) < 0){
						information("Warning : Processing delay in KeyRepeater.", null);
					}
					else{
						sleep(KEY_SPEED - processTime); 
					}
					while(isPaused && running){
						yield();
					}
				}catch(Exception e){
					information("Error : Problem occurred in KeyRepeater.", e);
				}
			}
		}
	}
	
	/**
	 * 
	 * @author Hendry
	 * @version 0.1
	 * 
	 */
	class GameLoop extends Thread {
		@Override
		public void run() {
			long beforeTime, afterTime, timeDiff, sleepTime;
			long overSleepTime = 0L;
			long excess = 0L;
			int noDelays = 0;

			beforeTime = J3DTimer.getValue();
			while (running) {
				try {
					if (isPaused) {
						synchronized (this) {
							while (isPaused && running){
								this.wait();
								
							}
						}
					} // end if
				} // of try block
				catch (InterruptedException e) {
				}
				
				gm.gameUpdate(); // game state is updated
				
				gm.gameRender(); // game sprite is updated
				SwingUtilities.invokeLater(new Runnable(){

					@Override
					public void run() {
						repaint(); // draw buffer to screen
					}
					
				});
				

				afterTime = J3DTimer.getValue();
				timeDiff = afterTime - beforeTime;
				sleepTime = (period - timeDiff) - overSleepTime; // time left in
																	// this loop

				if (sleepTime > 0) {
					try {
						Thread.sleep(sleepTime / 1000000L); // nano -> ms
					} catch (InterruptedException ex) {
					}
					overSleepTime = (J3DTimer.getValue() - afterTime)
							- sleepTime;
				} else { // sleepTime <= 0 ; frame took longer than the period
					excess -= sleepTime;// store excess time value
					overSleepTime = 0L;

					if (++noDelays >= NO_DELAYS_PER_YIELD) {
						yield(); // give another thread a chance to run
						noDelays = 0;
					}
				}

				beforeTime = J3DTimer.getValue();

				/*
				 * If frame animation is taking too long, update the game state
				 * without rendering it, to get the updates/sec nearer to the
				 * required FPS.
				 */

				int skips = 0;
				while ((excess > period) && (skips < MAX_FRAME_SKIPS)) {
					excess -= period;
					gm.gameUpdate(); // update state but don't render
					skips++;
				}
			}

			System.exit(0);
		}
	}

}
