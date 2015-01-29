package victory.engine;

/**
 * Conatins data for movement permissions.
 * @author Victoria Lacroix
 *
 */
public class CollisionMap {
	public final int WIDTH, HEIGHT;
	public final int TILE_WIDTH, TILE_HEIGHT;
	boolean[] permissions;
	
	/**
	 * Black constructor, allocates size.
	 * @param w
	 * @param h
	 */
	public CollisionMap(int w, int h){
		WIDTH = w; HEIGHT = h;
		TILE_WIDTH = 16;
		TILE_HEIGHT = 16;
		permissions = new boolean[WIDTH * HEIGHT];
	}
	
	/**
	 * Generates a new collisionmap using Map m
	 * @param m
	 */
	public CollisionMap(Map m, boolean d){
		WIDTH = m.getWidth();
		HEIGHT = m.getHeight();
		TILE_WIDTH = m.TILE_WIDTH;
		TILE_HEIGHT = m.TILE_HEIGHT;
		permissions = new boolean[WIDTH * HEIGHT];
		readPermissions(m, d);
	}
	
	/**
	 * Returns the permissions at a certain tile
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean getAt(int x, int y){
		return permissions[x + (y * WIDTH)];
	}
	public void setAt(int x, int y, boolean permission){
		permissions[x + (y * WIDTH)] = permission;
	}
	
	/**
	 * Returns permission at a double coordinate (entity coordinate)
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean getAt(double x, double y){
		x -= x % TILE_WIDTH;
		y -= y % TILE_HEIGHT;
		x /= TILE_WIDTH;
		y /= TILE_HEIGHT;
		if(x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT){
			return permissions[(int)x + (int)(y * WIDTH)];
		}
		return false;
		
	}
	
	/**
	 * read permissions from a map m
	 * @param m
	 */
	public void readPermissions(Map m, boolean d){
		for(int j = 0; j < HEIGHT; j++){
			for(int i = 0; i < WIDTH; i++){
				//TODO implement collision tables.
				permissions[i + (j * WIDTH)] = (m.getTile(i, j) == 0x00) ? false: d;
			}
		}
	}
}