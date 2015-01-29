package victory.engine.graphics;

/**
 * An interface that describes an object to be drawn to a Screen object. Screen uses generics that must implement this.
 * 
 * @author LU ID 0296738
 */
@Deprecated
public interface Drawable{
	
	/**
	 * @return x coord to start drawing.
	 */
	public int getDrawX();
	
	/**
	 * 
	 * @return y coord to start drawing.
	 */
	public int getDrawY();
	
	/**
	 * 
	 * @return width of image to draw
	 */
	public int getDrawW();
	
	/**
	 * 
	 * @return height of image to draw
	 */
	public int getDrawH();
	
	/**
	 * 
	 * @return whether this object is transparent
	 */
	public boolean isTransparent();
	
	/**
	 * 
	 * @return this object's transparency mask
	 */
	public int getMask();
	
	/**
	 * 
	 * @return whether this object is reflected horizontally or not
	 */
	public boolean getHorizontalReflection();
	
	/**
	 * 
	 * @return whether this object is reflected vertically or not
	 */
	public boolean getVerticalReflection();
	
	/**
	 * Pixels to draw with used by the Screen object.
	 * 
	 * @return pixels to draw with used by the Screen object.
	 */
	public abstract int[] getDrawPixels();
}
