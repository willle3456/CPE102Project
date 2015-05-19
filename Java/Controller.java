import processing.core.*;

public class Controller extends PApplet{
	public static final int KEY_DELAY = 400;
	public static final int KEY_INTERVAL = 100;
	public static final int TIMER_FREQUENCY = 100;
	
	public int[] onKeydown()
	{
		int x_delta = 0;
		int y_delta = 0;
		switch(keyCode)
		{
			//left
			case LEFT:
				x_delta -= 1;
				break;
			//up
			case UP:
				y_delta -= 1;
				break;
			//right
			case RIGHT:
				x_delta += 1;
				break;
			//down
			case DOWN:
				y_delta += 1;
				break;
			default:
				break;
			
		}
		int[] a = {x_delta,y_delta};
		return a;
	}

}
