import java.util.ArrayList;
import java.util.List;

import processing.core.*;

public class Main extends PApplet
{
	public static final boolean RUN_AFTER_LOAD = true;
	public static final String IMAGE_LIST_FILE_NAME = "imagelist";
	public static final String WORLD_FILE = "gaia.sav";
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
	
	private Background createDefaultBackground(PImage[] img)
	{
		return new Background("bob", img);
	}
	private void loadWorld(WorldModel w, List<PImage> iStore, String filename)
	{
		
	}
	
	
	
	public void setup()
	{
		protected Background defaultBackground = createDefaultBackground(img);
		size(this.SCREEN_WIDTH, this.SCREEN_HEIGHT);
		imgs = new ArrayList<PImage>();
		//imgs.addAll(c);
		loadWorld(this.world, imgs, "hi");
	}
	
	public void draw()
	{
		view.updateView();
	}
	
	public static void main(String[] args)
	   {
	      PApplet.main("ProcessingIntro");
	   }

}
