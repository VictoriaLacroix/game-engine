package victory.engine.graphics;

import victory.engine.Player;

public abstract class HeadsUpDisplay implements ScreenController{
	
	private Player	following;
	
	public HeadsUpDisplay(Player p){
		following = p;
	}
	
	public void draw(int sx, int sy, Screen s){
		
	}
}
