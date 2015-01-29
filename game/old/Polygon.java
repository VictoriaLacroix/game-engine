package victory.engine.graphics;

import victory.engine.Point;
import victory.engine.Triangle;

public class Polygon implements Drawable{
	private Point center;
	private Point[] summits;
	private int color;
	
	public Polygon(Point c, int color, Point... s){
		center = new Point(c);
		// width = w;
		// height = h;
		this.color = color;
		if(s.length < 3){ return; }
		summits = new Point[s.length];
		for(int i = 0; i < s.length; i++){
			summits[i] = new Point(s[i].getX() + center.getX(), s[i].getY() + center.getY());
		}
	}
	
	/**
	 * Rotate the quad
	 * 
	 * @param angle
	 *            amount in radians to rotate.
	 */
	public void rotate(double angle){
		for(Point p: summits){
			p.rotate(center, angle);
		}
	}
	
	public void update(){
		rotate(Math.PI / 120);
	}
	
	/**
	 * A method that calculates the proximity from a point to this Quad
	 * 
	 * @param p
	 *            point to calculate
	 * @return
	 */
	public boolean contains(Point p){
		//TODO broekn
		double pointArea = 0;
		for(int i = 0; i < summits.length; i++){
			if(i + 1 != summits.length){
				pointArea += Triangle.area(p, summits[i], summits[i + 1]);
			}else{
				pointArea += Triangle.area(p, summits[i], summits[0]);
			}
		}
		// System.out.print(area);
		return((int)pointArea - 1 <= area());
	}
	
	public double area(){
		double area = 0;
		for(int i = 1; i < summits.length - 1; i++){
			area += Triangle.area(summits[0], summits[i], summits[i + 1]);
		}
		return area;
	}
	
	@Override
	public void draw(){
		//TODO broekn
		drawW = 0;
		drawH = 0;
		for(int i = 0; i < summits.length; i++){
			double distance = Math.abs(summits[i].getX() - center.getX());
			drawW = (int)((distance > drawW) ? distance : drawW);
		}
		for(int i = 0; i < summits.length; i++){
			double distance = Math.abs(summits[i].getY() - center.getY());
			drawH = (int)((distance > drawH) ? distance : drawH);
		}
		drawW *= 2;
		drawH *= 2;
		drawP.setX(center.getX() - (drawW / 2));
		drawP.setY(center.getY() - (drawH / 2));
	}
	
	@Override
	public int[] getDrawPixels(){
		int[] pixels = new int[drawW * drawH];
		for(int y = 0; y < drawH; y++){
			for(int x = 0; x < drawW; x++){
				if(contains(new Point((center.getX() - drawW / 2) + x, (center.getY() - drawH / 2) + y))){
					pixels[x + (y * drawW)] = color;
				}else{
					pixels[x + (y * drawW)] = 0xFFFF00FF;
				}
			}
		}
		return pixels;
	}

	@Override
	public int getDrawX(){
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDrawY(){
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDrawW(){
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDrawH(){
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMask(){
		// TODO Auto-generated method stub
		return 0;
	}
}
