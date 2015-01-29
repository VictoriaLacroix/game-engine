package victory.engine;

import victory.engine.graphics.*;

public class TileMap implements Drawable{
	int mapMin;
	int mapMax;
	int width;
	int height;
	int[][] tiles;
	
	public TileMap(int w, int h){
		mapMin = h / 4;
		mapMax = 3 * h / 4;
		width = w;
		height = h;
		int[] heightMap = generateHeightMap(w / 4);
		heightMap = iterateFractal(heightMap);
		heightMap = iterateFractal(heightMap);
		tiles = fractalToMap(heightMap);
	}
	
	public int[] generateHeightMap(int s){
		int[] result = new int[s];
		for(int i = 0; i < s; i++){
			result[i] = (int)(Math.random() * ((double)mapMax - (double)mapMin)) + mapMin;
		}
		return result;
	}
	
	public int[] iterateFractal(int[] fractal){
		int[] result = new int[fractal.length * 2];
		for(int i = 0; i < fractal.length; i++){
			result[i * 2] = fractal[i];
		}
		for(int i = 1; i < result.length; i += 2){
			if(i != result.length - 1){
				result[i] = (int)(Math.random() * Math.abs((double)result[i - 1] - result[i + 1])) + mapMin;
			}else{
				result[i] = (int)(Math.random() * Math.abs((double)result[i - 1] - result[0])) + mapMin;
			}
		}
		String debug = "";
		for(int i = 0; i < result.length; i++){
			debug += result[i];
		}
		System.out.println(debug);
		return result;
	}
	
	public int[][] fractalToMap(int[] fractal){
		int[][] result = new int[width][height];
		if(fractal.length != width){ return null; }
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				result[x][y] = (fractal[x] + y >= height) ? 1 : 0;
			}
		}
		return result;
	}
	
	@Override
	public int getDrawX(){
		return 0;
	}
	
	@Override
	public int getDrawY(){
		return 0;
	}
	
	@Override
	public int getDrawW(){
		return 256;
	}
	
	@Override
	public int getDrawH(){
		return 192;
	}
	
	@Override
	public boolean isTransparent(){
		return true;
	}
	
	@Override
	public int getMask(){
		return 0xFFFF00FF;
	}
	
	@Override
	public boolean getHorizontalReflection(){
		return false;
	}
	
	@Override
	public boolean getVerticalReflection(){
		return false;
	}
	
	@Override
	public int[] getDrawPixels(){
		int[] pixels = new int[width * 16 * height * 16];
		for(int y = 0; y < height * 16; y++){
			for(int x = 0; x < width * 16; x++){
				pixels[x + (y * width * 16)] = (tiles[x / 16][y / 16] == 1) ? 0xFF000000 : 0xFFFF00FF;
			}
		}
		return pixels;
	}
}
