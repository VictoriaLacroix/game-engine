package victory.engine;

import victory.engine.graphics.Screen;
import victory.engine.graphics.ScreenController;
import victory.engine.graphics.Sprite;
import victory.engine.graphics.SpriteSheet;


public class Window implements ScreenController{
	private	int	width, height;
	/**
	 * 0x10 = Top Left
	 * 0x11 = Top
	 * 0x12 = Top Right
	 * 0x13 = Left
	 * 0x14 = Right
	 * 0x15 = Bottom Left
	 * 0x16 = Bottom
	 * 0x17 = Bottom Right
	 * 0x20 = Blank/Space
	 * > 0x20 = ASCII text
	 */
	private char[] win;
	static SpriteSheet gfx = new SpriteSheet("res/text.png");
	
	private String text;
	private double tickCount;
	private double tickLength;
	
	public Window(int w, int h){
		width = (w > 1) ? w: 2;
		height = (h > 1) ? h: 2;
		win = new char[width * height];
		tickLength = 10;
		setup();
	}

	/**
	 * Set up a blank, bordered window.
	 */
	private void setup(){
		for(int i = 0; i<width*height; ++i){
			if(i < width){
				if(i == 0){
					win[i] = 0x10;	//Top Left
				}else if(i < width -1){
					win[i] = 0x11;	//Top
				}else{
					win[i] = 0x12;	//Top Right
				}
			}else if(i < width * (height-1)){
				if(i % width == 0){
					win[i] = 0x13;	//Left
				}else if(i % width < width-1){
					win[i] = 0x20;	//Space
				}else{
					win[i] = 0x14;
				}
			}else{
				if(i % width == 0){
					win[i] = 0x15;	//Bottom Left
				}else if(i % width < width-1){
					win[i] = 0x16;	//Bottom
				}else{
					win[i] = 0x17;	//Bottom Right
				}
			}
		}
	}
	
	public void write(String s){
		text = s;
		char str[] = s.toCharArray();
		int x = 1; int y = 1;
		for(int i = 0; i < str.length; ++i){
			if(str[i] == '\n' || x >= width-1){ x = 1; ++y; }
			else{
				win[x+(y*width)] = str[i];
				++x;
			}
			if(y == height-1) return;
		}
	}
	
	@Override
	public void draw(int sx, int sy, Screen s){
		Sprite brush = new Sprite(8, 8, gfx);
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				brush.setIndex(win[x+(y*width)]%0x10, win[x+(y*width)]/0x10);
				brush.draw(x*8, y*8, s);
			}
		}
	}
}
