package victory.engine;

import victory.engine.graphics.SpriteSheet;
import victory.engine.KeyStateManager.Button;

/**
 * An extension of the Entity class that accepts user input and feeds it to the
 * player character.
 * 
 * @author Victoria Lacroix
 */
public class Player extends Entity{
	
	private static final int	COUNTER_RESET	= 20;
	private int					animCounter		= 0;
	private boolean				flying			= true;
	private KeyStateManager		input;
	
	public Player(double x, double y, KeyStateManager keyManager){
		super(16, 16, new SpriteSheet("res/td-char.png"));
		sprite.setIndex(0, 0);
		this.xpos = x;
		this.ypos = y;
		input = keyManager;
	}
	
	@Override
	public void update(){
		if(input.wasButtonPressed(Button.X)){
			flying = !flying;
			gravitating = !gravitating;
		}
		// left/right input
		if(input.isButtonDown(Button.LEFT) && !input.isButtonDown(Button.RIGHT)){
			xvel = (input.isButtonDown(Button.R) || flying) ? -1 : -2;
			sprite.setFlipX(true);
		}else if(input.isButtonDown(Button.RIGHT) && !input.isButtonDown(Button.LEFT)){
			xvel = (input.isButtonDown(Button.R) || flying) ? 1 : 2;
			sprite.setFlipX(false);
		}else{
			xvel = 0;
		}
		if(!flying){
			// jumping
			if(input.wasButtonPressed(Button.A) && (int)yposlast == (int)ypos && yvel >= 0){
				yvel = -5;
			}
		}else{
			if(input.isButtonDown(Button.UP) && !input.isButtonDown(Button.DOWN)){
				yvel = -1;
			}else if(input.isButtonDown(Button.DOWN) && !input.isButtonDown(Button.UP)){
				yvel = 1;
			}else{
				yvel = 0;
			}
		}
		
		// animation counting
		// walking on ground
		if(flying){
			sprite.setIndex(1, 0);
		}else if(xvel != 0 && yvel == 0){
			animCounter++;
			if(animCounter < 5){
				sprite.setIndex(0, 0);
			}else if(animCounter < 10){
				sprite.setIndex(1, 0);
			}else if(animCounter < 15){
				sprite.setIndex(0, 0);
			}else if(animCounter < 20){
				sprite.setIndex(1, 0);
			}else if(animCounter == COUNTER_RESET){
				sprite.setIndex(0, 0);
				animCounter = 0;
			}
			// in air
		}else if(yvel < 0){
			animCounter = 0;
			sprite.setIndex(1, 0);
		}else if(yvel > 0){
			animCounter = 0;
			sprite.setIndex(0, 0);
			// not moving at all
		}else if(xvel == 0 && yvel == 0){
			animCounter = 0;
			sprite.setIndex(0, 0);
		}
	}
	
	@Override
	public void onCollide(Entity other){
		//TODO I dunno, something.
	}
	
	@Override
	public boolean getGarbage(){
		// TODO determine if this entity is to be processed at all
		return false;
	}
}
