package victory.engine.graphics;

/**
 * An instance-based wrapper for the spritesheet class.
 * @author Victoria Lacroix
 *
 */
public class Sprite implements ScreenController{
	SpriteSheet sheet;
	private int indX, indY; // xy index
	private final int tileX, tileY; // how many tiles horizontally, vertically.
	private final int width, height; // width of the image in question.
	private boolean flipX, flipY; // horizontal/vertical flipping.
	private final int MASK = 0xFFFF00FF;
	
	public Sprite(int w, int h, SpriteSheet spritesheet){
		sheet = spritesheet;
		width = w;
		height = h;
		tileX = sheet.getWidth() / width;
		tileY = sheet.getHeight() / height;
	}
	
	/**
	 * Sets the current sprite index.
	 * 
	 * @param x
	 * @param y
	 */
	public void setIndex(int x, int y){
		if(x < tileX && x >= 0){
			indX = x;
		}
		if(y < tileY && y >= 0){
			indY = y;
		}
	}
	
	/**
	 * 
	 * @return x-index on the spritesheet
	 */
	public int getIndexX(){
		return indX;
	}
	
	/**
	 * 
	 * @return y-index on the spritesheet
	 */
	public int getIndexY(){
		return indY;
	}
	
	/**
	 * 
	 * @return whether the sprite is to be flipped horizontally. good way to store direction.
	 */
	public boolean getFlipX(){
		return flipX;
	}
	
	/**
	 * 
	 * @param fx whether to flip or not.
	 */
	public void setFlipX(boolean fx){
		flipX = fx;
	}
	
	/**
	 * 
	 * @return whether the sprite is to be flipped vertically
	 */
	public boolean getFlipY(){
		return flipY;
	}
	
	/**
	 * 
	 * @param fy whether to flip or not
	 */
	public void setFlipY(boolean fy){
		flipY = fy;
	}
	
	@Override
	public void draw(int sx, int sy, Screen s){
		for(int j = 0; j < height; j++){
			for(int i = 0; i < width; i++){
				if(sheet.getPixel(i + (indX * width), (j + (indY * height))) != MASK){
					if(!flipX){
						s.writePixel(i + sx, j + sy, sheet.getPixel((i + (indX * width)), (j + (indY * height))));
					}else{
						s.writePixel(sx + ((width - i)-1), j + sy, sheet.getPixel((i + (indX * width)),
								(j + (indY * height))));
					}
				}
			}
		}
	}
}
