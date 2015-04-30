package victory.engine;

import victory.engine.input.KeyStateManager;
import victory.engine.input.KeyStateManager.Button;

@Deprecated
public class ScriptManager{
	/**
	 * The windows for the engine.
	 */
	private Window[]			windowList;
	private int					manyWindows;
	
	public ScriptManager(){
		
	}
	
	public void addWindow(Window w){
		if(manyWindows == windowList.length - 1){
			ensureWindows(manyWindows*2);
		}
		windowList[manyWindows] = w;
		manyWindows++;
	}
	
	public void reduceWindows(int i){
		
	}
	
	public void killWindows(){
		for(Window w: windowList){
			w = null;
		}
		manyWindows = 0;
	}
	
	private void ensureWindows(int c){
		if(c <= windowList.length){
			return;
		}else{
			Window[] res = new Window[c];
			System.arraycopy(windowList, 0, res, 0, manyWindows);
			windowList = res;
		}
	}
	
	public void windowUpdate(KeyStateManager s){
		if(s.isButtonDown(Button.A)){
		}
	}
}
