package victory.engine;

public class Triangle{
	public static double area(Point a, Point b, Point c){
		return Math
				.abs(a.getX() * (b.getY() - c.getY())
						+ (b.getX() * (c.getY() - a.getY()))
						+ (c.getX() * (a.getY() - b.getY()))) / 2;
	}
}
