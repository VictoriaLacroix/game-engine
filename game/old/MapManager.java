package victory.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import victory.engine.graphics.Drawable;
import victory.engine.graphics.Screen;
import victory.engine.graphics.ScreenController;

public class MapManager implements ScreenController{
	private String path;
	private boolean editing;
	
	private final int TILE_WIDTH, TILE_HEIGHT;
	private final int SCREEN_WIDTH, SCREEN_HEIGHT;
	
	private int camX, camY;
	private Entity[] entities;
	private Map loadedMap;
	
	
	public MapManager(int screenWidth, int screenHeight, Map startmap){
		SCREEN_WIDTH = screenWidth;
		SCREEN_HEIGHT = screenHeight;
		
		TILE_WIDTH = 16;
		TILE_HEIGHT = 16;
	}
	
	public void update(KeyStateManager ksm){}

	@Override
	public void draw(int sx, int sy, Screen s) {
		// TODO Auto-generated method stub
		
	}
}
