package victory.engine;

import victory.engine.graphics.Screen;
import victory.engine.graphics.ScreenController;

/**
 * Map Engine that handles map logic and logic for the entities that inhabit it.
 * 
 * @author Victoria Lacroix
 * 
 */
public class MapEngine implements ScreenController{
	
	public final int	TILE_WIDTH, TILE_HEIGHT;
	public final int	SCREEN_WIDTH, SCREEN_HEIGHT;
	
	/**
	 * Animation counter
	 */
	private double animCounter = 0;
	/**
	 * Animation reset. Resets the animation counter if it's equal to or greater than this. 
	 */
	private static final double COUNTER_RESET = 20;
	
	/**
	 * Camera Coordinates, used in drawing.
	 */
	private int			camX, camY;
	/**
	 * The entity that the camera is attached to.
	 */
	private Entity		cameraman;
	/**
	 * A list of entities on the map.
	 */
	private Entity[]	entities;
	/**
	 * A count of enemies on the map.
	 */
	private int			manyEntities	= 0;
	/**
	 * Points to the map that is laoded in memory,
	 */
	private Map			loadedMap;
	
	public MapEngine(int screenWidth, int screenHeight, Map startmap){
		SCREEN_WIDTH = screenWidth;
		SCREEN_HEIGHT = screenHeight;
		
		//hard-coded, for now.
		TILE_WIDTH = 16;
		TILE_HEIGHT = 16;
		
		camX = 0;
		camY = 0;
		
		entities = new Entity[32];
		
		loadedMap = startmap;
	}
	
	/**
	 * Adds entity e to the list.
	 * @param e
	 */
	public void addEntity(Entity e){
		if(manyEntities <= entities.length){
			int i;
			for(i = 0; entities[i] != null; i++);
			entities[i] = e;
			manyEntities++;
			if(cameraman == null){
				cameraman = e; // attaches camera if it is null.
			}
		}else{
			System.err.println("Entites full in MapEngine.");
		}
	}
	
	/**
	 * Removes at index.
	 * @param i
	 */
	public void removeEntity(int i){
		if(manyEntities > 0){
			entities[i] = entities[--manyEntities];
			entities[manyEntities] = null;
		}
	}
	
	/**
	 * Removes equating (note: bad method)
	 * @param target
	 */
	public void removeEntity(Entity target){
		int i;
		for(i = 0; !(target == entities[i]); i++);
		if(i == entities.length){
			return;
		}
		removeEntity(i);
	}
	
	/**
	 * Attaches control of the camera to
	 * 
	 * @param i
	 *            entity at index i
	 */
	public void attachCamera(int i){
		if(i < manyEntities){
			cameraman = entities[i];
		}
	}
	
	/**
	 * Updates the entities logic, then collision, and then algorithms (velocity
	 * etc).
	 */
	public void update(double delta){
		for(int i = 0; i < entities.length; i++){
			if(entities[i] != null){
				entities[i].update();
			}
		}
		
		handleCollision();
		
		for(int i = 0; i < entities.length; i++){
			if(entities[i] != null){
				entities[i].nextFrame(delta);
			}
		}
		
		//following
		camX = (int)(cameraman.getX() - SCREEN_WIDTH / 2 + cameraman.getWidth() / 2);
		camY = (int)(cameraman.getY() - SCREEN_HEIGHT / 2 + cameraman.getHeight() / 2);
		//fix out-of-bounds
		camX = (camX < 0) ? 0 : camX;
		camX = (camX + SCREEN_WIDTH > loadedMap.MAP_WIDTH * loadedMap.TILE_WIDTH) ? loadedMap.MAP_WIDTH * loadedMap.TILE_WIDTH - SCREEN_WIDTH : camX;
		camY = (camY < 0) ? 0 : camY;
		camY = (camY + SCREEN_HEIGHT > loadedMap.MAP_HEIGHT * loadedMap.TILE_HEIGHT) ? loadedMap.MAP_HEIGHT * loadedMap.TILE_HEIGHT - SCREEN_HEIGHT : camY;
		
		for(int i = 0; i < manyEntities; ++i){
			if(entities[i].getGarbage()){
				removeEntity(i);
			};
		}
		
		//Animate the loaded map if we've passed the animation counter.
		animCounter += delta;
		if(animCounter >= COUNTER_RESET){
			animCounter = 0;
			loadedMap.animate();
		}
	}
	
	/**
	 * Handles the collision of all entities within eachother.
	 */
	public void handleCollision(){
		for(int i = 0; i < entities.length; i++){
			if(entities[i] != null){
				entities[i].checkCollision(loadedMap.cmap);
			}
		}
		for(int i = 0; i < manyEntities; ++i){
			for(int j = 0; j < manyEntities; ++j){
				if(i != j){
					if(entities[i].collidedWith(entities[j])){
						entities[i].onCollide(entities[j]);
					}
				}
			}
		}
	}
	
	@Override
	public void draw(int sx, int sy, Screen s){
		loadedMap.draw(-camX, -camY, s);
		for(int i = 0; i < manyEntities; i++){
			entities[i].draw((int)entities[i].getX() - camX, (int)entities[i].getY() - camY, s);
		}
	}
}
