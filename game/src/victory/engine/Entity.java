package victory.engine;

import victory.engine.graphics.Screen;
import victory.engine.graphics.ScreenController;
import victory.engine.graphics.Sprite;
import victory.engine.graphics.SpriteSheet;

/**
 * An abstract class to describe an entity.
 * 
 * @author Victoria Lacroix
 *
 */
public abstract class Entity implements ScreenController{
	
	/**
	 * Axis Coordinate
	 */
	protected double	xpos, ypos;
	protected double	xposlast, yposlast;
	
	/**
	 * Axis Velocity
	 */
	protected double	xvel, yvel;
	
	/**
	 * Axis Velocity Cap
	 */
	protected double	xvelmax, yvelmax;
	
	/**
	 * Dimensions (Size)
	 */
	protected int		width, height;
	
	/**
	 * Gravity for this entity.
	 */
	protected double	gravity = 9.8/60;
	protected boolean	gravitating;
	
	/**
	 * Current graphic setting
	 */
	protected Sprite		sprite;
	
	//protected CollisionMap	collisions;
	
	/**
	 * New abstract entity with SpriteSheet 'sheet'
	 * 
	 * @param sheet
	 */
	public Entity(int w, int h, SpriteSheet sheet){
		xvel = 0;
		yvel = 0;
		xvelmax = 12;
		yvelmax = 12;
		width = w;
		height = h;
		gravitating = true;
		sprite = new Sprite(w, h, sheet);
	}
	
	/**
	 * Logic method, to be implemented by subclass.
	 */
	public abstract void update();
	
	/**
	 * Collision detection/reaction method for tilemaps/collisionmaps.
	 * 
	 * @param cmap
	 *            CollisionMap to correspond to.
	 */
	public void checkCollision(CollisionMap cmap){
		/*
		 * Okay. Bear with me. This is going to be a bit of a long explanation.
		 * 
		 * First of all, we have to use the x,y values for the next frame to
		 * determine what the hell is going on, otherwise we'll get weird
		 * "snapping" when an entity is consistently trying to move in a direction
		 * (eg gravity).
		 * 
		 * Next up, we manipulate WHERE the collision points on each side are
		 * (an if statement represents one side). The result is an octagon shape
		 * for the collision hitbox. The reasoning behind this is that a 4-point
		 * bounding box doesn't give us enough info on *where* collisions are.
		 * Case in point, if the bottom left corner is activated, is the
		 * character against a wall? Or leaning off a cliff? It leaves too much
		 * ambiguity.
		 * 
		 * Finally I wanted to address the math going on... because it's fucking
		 * wild, and I try not to cuss much. Basically, when trying to find a
		 * coordinate, one must first ask whether or not the axis corresponds to
		 * our collision. If we're looking left-right then our x axis
		 * corresponds to it, otherwise the y axis. You need to add/remove 4
		 * from the other axis to simulate the octagon shape. Then, for all
		 * coordinates, (DON'T add .5), and if you're using width/height (right
		 * side/ bottom), then substract it by one. This shrinks the bounding
		 * box to prevent java's imperfect double-int conversion from detecting
		 * the wrong block. Basically, it's an automated rounding. The final
		 * resulting octagon is a little more inside of the entity than outside.
		 * This leaves a few problems but nothing that cannot later be
		 * addressed.
		 * 
		 * Collision detection, in some cases, is still a little hoiky. This is
		 * super-early beta stuff so you shouldn't, like, expect miracles.
		 */
		
		// First, we check to see if a full side is collided by going && for our
		// two points.
		
		// Vertical collision (top/bottom)
		if(cmap.getAt(xpos + xvel + 4, ypos + yvel + height - 1) && cmap.getAt(xpos + xvel + width - 1 - 4, ypos + yvel + height - 1)){
			// bottom
			ypos += yvel;
			ypos -= ypos % height;
			yvel = (yvel > 0) ? 0 : yvel;
		}else if((cmap.getAt((xpos) + xvel + 4, (ypos) + yvel) && cmap.getAt((xpos) + xvel + (width - 1) - 4, (ypos) + yvel))){
			// top
			if(yvel < 0){
				ypos += yvel; // change position
				ypos += height - (ypos % height);
				yvel = (yvel < 0) ? 0 : yvel; // fix velocity if needed
			}
			
		}
		
		// horizontal collision (left/right)
		if((cmap.getAt((xpos) + xvel, (ypos) + yvel + 4) && cmap.getAt((xpos) + xvel, (ypos) + yvel + (height - 1) - 4))){
			// left
			xpos += xvel;
			xpos += width - (xpos % width);
			xvel = (xvel < 0) ? 0 : xvel;
		}else if((cmap.getAt((xpos) + xvel + (width - 1), (ypos) + yvel + 4) && cmap.getAt((xpos) + xvel + (width - 1), (ypos) + yvel + (height - 1) - 4))){
			// right
			xpos += xvel;
			xpos -= xpos % width;
			xvel = (xvel > 0) ? 0 : xvel;
		}
		
		// Then we check that our side has partially collided by going "OR" for
		// our two points instead of "AND".
		
		// Vertical collision (top/bottom)
		if(cmap.getAt(xpos + xvel + 4, ypos + yvel + height - 1) || cmap.getAt(xpos + xvel + width - 1 - 4, ypos + yvel + height - 1)){
			// bottom
			ypos += yvel;
			ypos -= ypos % height;
			yvel = (yvel > 0) ? 0 : yvel;
		}else if((cmap.getAt((xpos) + xvel + 4, (ypos) + yvel) || cmap.getAt((xpos) + xvel + (width - 1) - 4, (ypos) + yvel))){
			// top
			ypos += yvel; // change position
			ypos += height - (ypos % height);
			yvel = (yvel < 0) ? 0 : yvel; // fix velocity if needed
		}
		
		// horizontal collision (left/right)
		if((cmap.getAt((xpos) + xvel, (ypos) + yvel + 4) || cmap.getAt((xpos) + xvel, (ypos) + yvel + (height - 1) - 4))){
			// left
			xpos += xvel;
			xpos += width - (xpos % width);
			xvel = (xvel < 0) ? 0 : xvel;
		}else if((cmap.getAt((xpos) + xvel + (width - 1), (ypos) + yvel + 4) || cmap.getAt((xpos) + xvel + (width - 1), (ypos) + yvel + (height - 1) - 4))){
			// right
			xpos += xvel;
			xpos -= xpos % width;
			xvel = (xvel > 0) ? 0 : xvel;
		}
	}
	
	/**
	 * Calculate/Run anything that needs to be finalized.
	 * 
	 * A note on the delta, all velocity values are roughly equal to what the entity will traverse in 1/60th of a second, in pixels.
	 */
	public void nextFrame(double delta){
		xposlast = xpos;
		yposlast = ypos;
		xvel = xvel > xvelmax ? xvelmax : xvel; // correct larger-than-intended velocities
		yvel = yvel > yvelmax ? yvelmax : yvel;
		xvel = -xvel > xvelmax ? -xvelmax : xvel; // negative case
		yvel = -yvel > yvelmax ? -yvelmax : yvel;
		xpos += xvel*delta;
		ypos += yvel*delta;
		if(gravitating) yvel += gravity*delta; //add gravity to the velocity. this is applied after movement in order to factor in the next frame.
	}
	
	/**
	 * Entity collision, to be implemented by subclass.
	 * 
	 * @param other
	 */
	public abstract void onCollide(Entity other);
	
	public boolean collidedWith(Entity other){
		// step1
		if(xpos >= other.xpos && xpos < other.xpos + other.width && ypos >= other.ypos && ypos < other.ypos + other.height){
			return true;
		}
		// step2
		if(other.xpos >= xpos && other.xpos < xpos + width && other.ypos >= ypos && other.ypos < ypos + height){
			return true;
		}
		return false;
	}
	
	public final double getX(){
		return xpos;
	}
	
	public double getVelocityX(){
		return xvel;
	}
	
	public final double getY(){
		return ypos;
	}
	
	public final double getVelocityY(){
		return yvel;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	/**
	 * Determines whether or not this entity should be removed from an active
	 * list. I.E if its health is 0, etc.
	 * 
	 * @return
	 */
	public abstract boolean getGarbage();
	
	/**
	 * The sprite this entity is using
	 * 
	 * @return
	 */
	public Sprite getSprite(){
		return sprite;
	}
	
	/**
	 * To be accessed by other entities, perhaps? BE CAREFUL.
	 * 
	 * @param nx
	 */
	public void setX(int nx){
		xpos = nx;
	}
	
	/**
	 * To be accessed by other entities, perhaps? BE CAREFUL.
	 * 
	 * @param ny
	 */
	public void setY(int ny){
		ypos = ny;
	}
	
	@Override
	public final void draw(int sx, int sy, Screen s){
		sprite.draw(sx, sy, s);
	}
	
	@Override
	public boolean equals(Object other){
		if(other instanceof Entity){
			return ((Entity)other).xpos == xpos && ((Entity)other).ypos == ypos;
		}else{
			return false;
		}
	}
}
