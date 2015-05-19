import java.awt.Rectangle;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import processing.core.*;

public class Main extends PApplet
{
	public static final boolean RUN_AFTER_LOAD = true;
	public static final String IMAGE_LIST_FILE_NAME = "../images/imagelist";
	public static final String WORLD_FILE = "../images/gaia.sav";
	public static final int WORLD_WIDTH_SCALE = 2;
	public static final int WORLD_HEIGHT_SCALE = 2;
	public static final int SCREEN_WIDTH = 640;
	public static final int SCREEN_HEIGHT = 480;
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;

	private List<PImage> imgs;
	private int numCols = this.SCREEN_WIDTH / this.TILE_WIDTH;
	private int numRows = this.SCREEN_HEIGHT/ this.TILE_WIDTH;
	private WorldModel world;
	private WorldView view; 
	
	private Background createDefaultBackground(List<PImage> img)
	{
		return new Background(ImageStore.DEFAULT_IMAGE_NAME, new Point(0,0), img);
	}
	private void loadWorld(WorldModel w, HashMap<String,ArrayList<PImage>> iStore, String filename) throws FileNotFoundException
	{
		Scanner in = new Scanner(new FileReader(filename)); 
		SaveLoad save = new SaveLoad();
		save.loadWorld(w, iStore, in);
	}
	
	HashMap<String, ArrayList<PImage>> iStore = null;
	
	
	public void setup()
	{
		size(SCREEN_WIDTH,SCREEN_HEIGHT);
		//protected Background defaultBackground = createDefaultBackground(img);
		//size(this.SCREEN_WIDTH, this.SCREEN_HEIGHT);
		ImageStore image_store = new ImageStore();
		try {
			iStore = image_store.loadImages(IMAGE_LIST_FILE_NAME, TILE_WIDTH, TILE_HEIGHT, this);

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int numCols = this.SCREEN_WIDTH / this.TILE_WIDTH * this.WORLD_WIDTH_SCALE;
		int numRows = this.SCREEN_HEIGHT / this.TILE_HEIGHT * this.WORLD_HEIGHT_SCALE;
		
		Background defaultBackground = createDefaultBackground(image_store.getImages(iStore, image_store.DEFAULT_IMAGE_NAME));
		
		 world = new WorldModel(numRows, numCols, defaultBackground);
		
		 view = new WorldView(this.SCREEN_WIDTH/ this.TILE_WIDTH , this.SCREEN_HEIGHT/ this.TILE_HEIGHT, this, world, TILE_WIDTH, TILE_HEIGHT);
		//System.out.println(view);
		
		try {
			loadWorld(world,iStore,WORLD_FILE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Entities e: world.getEntities())
		{
			System.out.println(e.getName());
		}
		
		view.drawViewport(0,0);

	}
	
	long nextTime = 0; 
	
	public void keyPressed()
	{
		switch(this.keyCode)
		{
		case LEFT:
			System.out.println("left");
			view.updateView(-1,0);
			break;
		case RIGHT:
			view.updateView(1, 0);
			break;
		case DOWN:
			view.updateView(0, 1);
			break;
		case UP:
			view.updateView(1, 0);
			break;
			default:
				view.updateView(0, 0);
				break; 
		}
	}
	
	public void draw()
	{
		
		/*long time = System.currentTimeMillis();
		System.out.println(time >= nextTime);
		if(time >= nextTime)
		{
			System.out.println("ionofei");
			world.updateOnTime(time);
			nextTime += 100; 
			System.out.println(nextTime);
		}
		*/
		
		
		/*Controller ctrl = new Controller();
		
		if(keyPressed)
		{
			int[] delta = ctrl.onKeydown();
			System.out.println(delta[0] + " " +  delta[1]);
			//view.updateView(delta);
		}*/

	}
	
	public static void main(String[] args)
	   {
	      PApplet.main("ProcessingIntro");
	   }

}
