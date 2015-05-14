import processing.core.*;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class WorldView 
{
	public final static int MOUSE_HOVER_ALPHA = 120;
	public final static int[] MOUSE_HOVER_EMPTY_COLOR = {0,255,0};
	public final static int[] MOUSE_HOVER_OCC_COLOR = {255,0,0};
	
	private Rectangle view;
	//screen
	private Point mousePt = new Point(0,0);
	private WorldModel world;
	private int tileWidth, tileHeight;
	private PImage mouseImg;
	private PApplet p;
	public WorldView(Rectangle view, Point mousePt, WorldModel world, int tileWidth, int tileHeight, int numRows, int numCols, PImage mouseImg, PApplet p)
	{
		this.view = view;
		this.mousePt = mousePt;
		this.world = new WorldModel(numRows, numCols);
		this.p = p;
		this.mouseImg = mouseImg;
	}
	
	public Point viewportToWorld(Point pt)
	{
		return new Point(pt.getX() + view.getX(), pt.getY() + view.getY());
	}
	
	public Point worldToViewport(Point pt)
	{
		return new Point(pt.getX() - view.getX(), pt.getY() - view.getY());
	}
	
	public int clamp(int v, int low, int high)
	{
		return Math.min(high, Math.max(v, low));
	}
	/***
	 * moves the viewport around
	 * @param deltaX: movement in x dir
	 * @param deltaY: movement in y dir
	 * @param numRows: num rows in grid
	 * @param numCols num cols in grid
	 * @return a new viewport
	 */
	public Rectangle createShiftedViewport(int deltaX, int deltaY, int numRows, int numCols)
	{
		int newX = clamp(view.x + deltaX,0,numCols - (int)view.getWidth());
		int newY = clamp(view.y + deltaY,0,numRows + (int)view.getHeight());
		
		return new Rectangle(newX, newY, view.width, view.height);
	}
	
	public void drawBackground()
	{
		for(int y = 0; y < view.height; y++)
		{
			for(int x = 0; x < view.width; x++)
			{
				Point wPt = viewportToWorld(new Point(x,y));
				PImage img = world.getBackgroundImage(wPt);
				p.image(img, x * this.tileWidth, y * this.tileHeight);	
			}
		}
	}
	
	public boolean contains(Rectangle rect, Point pt)
	{
		boolean a = pt.getX() > rect.getX() && pt.getX() < rect.getWidth();
		boolean b = pt.getY() > rect.getY() && pt.getY() < rect.getHeight();
		
		return a && b;
	}
	
	public void drawEntities()
	{
		for( Entities e: world.getEntities())
		{
			if(contains(view,e.getPosition()))
			{
				Point vPt = worldToViewport(e.getPosition());
				p.image(e.getImage(), (int)vPt.getX() * this.tileWidth, (int)vPt.getY() * this.tileHeight);
			}
		}
	}
	
	public void drawViewport()
	{
		drawBackground();
		drawEntities();
	}
	
}
