package victory.engine;

import java.awt.BorderLayout;
import java.awt.KeyboardFocusManager;

import javax.swing.*;

import victory.engine.graphics.Screen;
import victory.engine.graphics.SpriteSheet;

@SuppressWarnings("serial")
/**
 * Core class that performs the main logic in a game. It handles timings for graphics drawings, and calls various update() methods for different objects.
 * @author Victoria Lacroix
 *
 */
public class Core extends JPanel{
	
	// our output
	private Screen				screen;
	// visuals
	private static final int	FRAMERATE	= 60;	//Target FPS. NOTE: Entity movement is largely dependent on this number. You've been warned.
	// under-the-hood
	private boolean				running		= false;
	private final boolean		isUncapped	= true;	// whether or not FPS is totally uncapped. affects the update() method here.
	int							width, height;
	protected static int		tickCount	= 0;
	// game-ey stuff
	KeyStateManager				buttonManager;
	MapEngine					engine;
	
	public Core(int w, int h, int s){
		setLayout(new BorderLayout());
		buttonManager = new KeyStateManager();
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(buttonManager);
		engine = new MapEngine(w, h, new Map(32, 32, new SpriteSheet("res/world.png"), "res/map/csv/world.csv"));
		engine.addEntity(new Player(100, 130, buttonManager));
		width = w;
		height = h;
		screen = new Screen(w, h, s);
		add(screen);
		running = true;
	}
	
	public int getWidth(){
		return screen.getSize().width;
	}
	
	public int getHeight(){
		return screen.getSize().height;
	}
	
	/**
	 * Update method that is called indefinitely from Window.
	 * 
	 * @author Victoria Lacroix #0296738 (Current form)
	 * @author youtube.com/user/thech3rno (Original single-class form that included really basic software rendering (see screen
	 *         class))
	 */
	public void update(){
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / FRAMERATE;
		int ticks = 0;
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		while(running){
			long now = System.nanoTime();
			delta = (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = isUncapped; // pulls from the 'uncapped' boolean. if uncapped is false, then
												// shouldrender is false until it is the time to render the scene.
												// otherwise, the scene will try its damn-dest to go as fast as possible.
			ticks++;
			tick(delta);
			tickCount++;
			draw();
			shouldRender = true;
			try{
				Thread.sleep(2);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			if(shouldRender){
				render();
			}
			if(System.currentTimeMillis() - lastTimer > 1000){
				lastTimer += 1000;
				System.out.println(ticks + " - " + tickCount);
				ticks = 0;
			}
		}
	}
	
	/**
	 * Game logic method.
	 */
	protected void tick(double delta){
		engine.update(delta);
		buttonManager.update();
	}
	
	/**
	 * Graphics drawing method.
	 */
	protected void draw(){
		screen.clear(0x00C0FF);
		engine.draw(0, 0, screen);
	}
	
	/**
	 * Hardware render method.
	 */
	private void render(){
		screen.render();
	}
	
	/**
	 * Shallow return for the Core's Screen object.
	 * 
	 * @return this Core's Screen
	 */
	protected Screen getScreen(){
		return screen;
	}
}
