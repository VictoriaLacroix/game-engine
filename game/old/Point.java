package victory.engine;

/**
 * A point class. S'a point.
 * 
 * @author Victoria Lacroix #0296738
 */
public class Point{
	private double x, y;
	
	/**
	 * New point
	 * 
	 * @param x
	 *            coord
	 * @param y
	 *            coord
	 */
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Copy point
	 * 
	 * @param p
	 *            point to copy
	 */
	public Point(Point p){
		this.x = p.getX();
		this.y = p.getY();
	}
	
	/**
	 * Sets new x
	 * 
	 * @param x
	 *            new x value
	 */
	public void setX(double x){
		this.x = x;
	}
	
	/**
	 * Sets new y
	 * 
	 * @param y
	 *            new y value
	 */
	public void setY(double y){
		this.y = y;
	}
	
	/**
	 * Get x value
	 * 
	 * @return x value
	 */
	public double getX(){
		return x;
	}
	
	/**
	 * Get y value
	 * 
	 * @return y value
	 */
	public double getY(){
		return y;
	}
	
	/**
	 * Vector rotation method
	 * 
	 * @param origin
	 *            rotation origin
	 * @param theta
	 *            rotation angle
	 */
	public void rotate(Point origin, double theta){
		double oldX = getX();
		double oldY = getY();
		setX(((oldX - origin.getX()) * Math.cos(theta % (2 * Math.PI)))
				- ((oldY - origin.getY()) * Math.sin(theta % (2 * Math.PI))) + (origin.getX()));
		setY(((oldX - origin.getX()) * Math.sin(theta % (2 * Math.PI)))
				+ ((oldY - origin.getY()) * Math.cos(theta % (2 * Math.PI))) + (origin.getY()));
	}
}
