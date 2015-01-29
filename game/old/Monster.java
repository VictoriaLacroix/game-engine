package victory.engine;

import victory.engine.graphics.SpriteSheet;

public class Monster extends Entity{
	
	private static final double	GRAVITY			= 9.8 / 60;
	private static final int		COUNTER_RESET	= 20;
	private int					animCounter		= 0;
	
	public Monster(int x, int y){
		super(16, 16, new SpriteSheet("res/sheet1.png"));
		xpos = x;
		ypos = y;
		xvel = 1;
	}
	
	@Override
	public void update(){
		if(xvel < 0){
			sprite.setFlipX(true);
		}else if(xvel > 0){
			sprite.setFlipX(false);
		}
		//animation
		animCounter++;
		if(animCounter < 5){
			sprite.setIndex(0, 0);
		}else if(animCounter < 10){
			sprite.setIndex(1, 0);
		}else if(animCounter < 15){
			sprite.setIndex(0, 0);
		}else if(animCounter < 20){
			sprite.setIndex(2, 0);
		}else if(animCounter == COUNTER_RESET){
			sprite.setIndex(0, 0);
			animCounter = 0;
		}
		if((int)(Math.random() * 100) == 50){
			xvel = -xvel;
		}
		if(xvel == 0){
			switch((int)(Math.random() * 2)){
				case 0:
					xvel = 1;
					break;
				case 1:
					xvel = -1;
					break;
			}
		}
	}
	
	@Override
	public void onCollide(Entity other){
		return;
	}
	
	@Override
	public boolean getGarbage(){
		return false;
	}
	
}
