package victory.engine;

import java.awt.event.KeyEvent;

import victory.engine.graphics.Screen;
import victory.engine.graphics.SpriteSheet;

/**
 * An extension of the Entity class that accepts user input and feeds it to the
 * player character.
 * 
 * @author Victoria Lacroix
 */
public class MapEditor extends Entity {
	KeyStateManager input;
	private static final double GRAVITY = 9.8 / 60;
	private static final int COUNTER_RESET = 30;
	private int animCounter = 0;
	private boolean flying = false;

	public MapEditor(double x, double y, KeyStateManager keyManager, Map m) {
		super(16, 16, m.tileset);
		getSprite().setIndex(0, 1);
		this.xpos = x;
		this.ypos = y;
		input = keyManager;
	}

	@Override
	public void update(Map m) {
		
		
		if(input.isDown(KeyEvent.VK_CONTROL)){
			if (wasButtonPressed(Button.LEFT) && !wasButtonPressed(Button.RIGHT)) {
				getSprite().setIndex(getSprite().getIndexX()-1, getSprite().getIndexY());
			} else if (wasButtonPressed(Button.RIGHT) && !wasButtonPressed(Button.LEFT)) {
				getSprite().setIndex(getSprite().getIndexX()+1, getSprite().getIndexY());
			}

			if (wasButtonPressed(Button.UP) && !wasButtonPressed(Button.DOWN)) {
				getSprite().setIndex(getSprite().getIndexX(), getSprite().getIndexY()-1);
			} else if (wasButtonPressed(Button.DOWN) && !wasButtonPressed(Button.UP)) {
				getSprite().setIndex(getSprite().getIndexX(), getSprite().getIndexY()+1);
			}
		}else{
			if (wasButtonPressed(Button.LEFT) && !wasButtonPressed(Button.RIGHT)) {
				xpos -= 16;
			} else if (wasButtonPressed(Button.RIGHT) && !wasButtonPressed(Button.LEFT)) {
				xpos += 16;
			}

			if (wasButtonPressed(Button.UP) && !wasButtonPressed(Button.DOWN)) {
				ypos -= 16;
			} else if (wasButtonPressed(Button.DOWN) && !wasButtonPressed(Button.UP)) {
				ypos += 16;
			}
		}
		
		if (isButtonDown(Button.A)){
			m.setTile((int)(xpos/width), (int)(ypos/height), (byte)(0x10 * getSprite().getIndexY() + getSprite().getIndexX()));
		}else if (isButtonDown(Button.B)){
			m.setTile((int)(xpos/width), (int)(ypos/height), (byte)(0x00));
		}
		
		animCounter = (animCounter >= COUNTER_RESET) ? 0 : animCounter + 1;
	}
	
	public void checkCollision(CollisionMap perm){
		return;
	}
	
	public void draw(int sx, int sy, Screen s){
		getSprite().draw(sx, sy, s);
		s.writePixel(sx-1, sy-1, 0xFF01FF);
		s.writePixel(sx+width, sy-1, 0xFF01FF);
		s.writePixel(sx+width, sy+height, 0xFF01FF);
		s.writePixel(sx-1, sy+height, 0xFF01FF);
	}

	// see button enum below that defines our keys.
	private boolean isButtonDown(Button b) {
		return input.isDown(b.index);
	}

	private boolean wasButtonPressed(Button b) {
		return input.isPressed(b.index);
	}

	/**
	 * Button enumerated type. It contains 12 common buttons.
	 * 
	 * @author Victoria
	 */
	public static enum Button {
		UP(KeyEvent.VK_UP), DOWN(KeyEvent.VK_DOWN), LEFT(KeyEvent.VK_LEFT), RIGHT(
				KeyEvent.VK_RIGHT), A(KeyEvent.VK_S), B(KeyEvent.VK_D), X(
				KeyEvent.VK_A), Y(KeyEvent.VK_W), L(KeyEvent.VK_Q), R(
				KeyEvent.VK_E), START(KeyEvent.VK_ENTER), SELECT(
				KeyEvent.VK_BACK_SPACE);
		int index;

		Button(int i) {
			index = i;
		}

		int get() {
			return index;
		}
	}

	@Override
	public void onCollide(Entity other) {
		// TODO Implement different entities

	}

	@Override
	public boolean getGarbage() {
		// TODO determine if this entity is to be processed at all
		return false;
	}
}
