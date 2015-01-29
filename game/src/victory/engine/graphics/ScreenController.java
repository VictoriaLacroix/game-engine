package victory.engine.graphics;

/**
 * A simple interface that forces a class to implement a 'draw()' method detailed here.
 * @author Victoria Lacroix
 *
 */
public interface ScreenController{
	/**
	 * Draws this screen-controller to the given screen
	 * @param sx screen's x-coord to start drawing
	 * @param sy screen's y-coord to start drawing
	 * @param s screen to draw to
	 */
	public void draw(int sx, int sy, Screen s);
}
