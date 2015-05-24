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
	private PApplet screen;
	private Point mousePt;
	private WorldModel world;
	private int viewCols,viewRows; 
	private int tileWidth, tileHeight;
	private int numRows, numCols;
	public WorldView(int viewCols, int viewRows, PApplet screen, WorldModel world, int tileWidth, int tileHeight)
	{

		this.viewCols = viewCols;
		this.viewRows = viewRows; 
		this.view = new Rectangle(0,0,viewCols, viewRows);
		this.screen = screen; 
		this.mousePt = new Point(0,0);
		this.world = world; 
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight; 
		this.numRows = world.getNum_rows();
		this.numCols = world.getNum_cols();
	}
	
	public void drawBackground()
	{
		for(int y = 0; y < this.viewRows; y++)
		{
			for(int x = 0; x < this.viewCols; x++)
			{
				Point wPt = viewportToWorld(new Point(x,y));
				System.out.println("bacground points" + " " + (wPt.getX() <  this.view.x)+ " " + wPt.getY()); 
				PImage img = world.getBackgroundImage(wPt);
				screen.image(img, x * this.tileWidth, y * this.tileHeight);	
			}
		}
	}
	
	public void drawEntities()
	{
		for( Entities e: world.getEntities())
		{
			if(contains(view,e.getPosition()))
			{
				//System.out.println("Entities shown: " + e.getName());
				Point vPt = worldToViewport(e.getPosition());
				screen.image(e.getImage(), ((int)vPt.getX()) * this.tileWidth, ((int)vPt.getY()) * this.tileHeight);
			}
		}
	}
	
	public void drawViewport()
	{
		//System.out.println(view.x + " " + view.y + " " + view.height + " " + view.width);
		drawBackground();
		drawEntities();
	}
	
	/*public Rectangle updateTile(Point viewTilePt, PImage surface)
	{
		int absX = (int)viewTilePt.getX() * this.tileWidth;
		int absY = (int)viewTilePt.getY() * this.tileHeight;
		
		screen.image(surface, absX, absY);
		
		return new Rectangle(absX, absY, this.tileWidth, this.tileHeight);
		
	}
	
	public PImage getTileImage(Point viewTilePt)
	{
		Point pt = viewportToWorld(viewTilePt);
		PImage bgnd = this.world.getBackgroundImage(pt);
		Entities occupant = this.world.getTileOccupant(pt);
		if(occupant != null)
		{
			screen.image(bgnd, 0, 0);
			screen.image(occupant.getImage(), 0, 0);
			return null; 
			
		}
		return bgnd; 
	}
	
	/*public void mouseMove()
	{
		Point temp = new Point(screen.mouseX + this.view.x, screen.mouseY + this.view.y);
		if(contains(this.view, temp))
		{
			this.mousePt = temp;
		}
	}*/
	
	public void updateView(int deltaX, int deltaY)
	{
		this.view = createShiftedViewport(deltaX,deltaY, this.numCols, this.numRows);
		drawViewport();
	}
	
	/*public void updateViewTiles(ArrayList<Point> tiles)
	{
		for(Point tile: tiles)
		{
			if(contains(this.view,tile))
			{
				Point vPt = worldToViewport(tile);
				PImage img = getTileImage(vPt);
			}
		}
	}*/
	
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
		//System.out.println("high " + high);
		if(v <= low)
		{
			return low;
		}
		else if(v >= high)
		{
			return high;
		}
		return v; 

	}
	public Rectangle createShiftedViewport(int deltaX, int deltaY, int numCols, int numRows)
	{
		/***
		 * moves the viewport around
		 * @param deltaX: movement in x dir
		 * @param deltaY: movement in y dir
		 * @param numRows: num rows in grid
		 * @param numCols num cols in grid
		 * @return a new viewport
		 */
		//System.out.println("view cols: " + numCols);
		//System.out.println("rows: " + numRows);
		int newX = clamp((int)this.view.getX() + deltaX, 0, numCols - this.viewCols);
		int newY = clamp((int)this.view.getY() + deltaY, 0, numRows - this.viewRows);
		
		return new Rectangle(newX, newY, this.viewCols, this.viewRows); 


	}
	
	public boolean contains(Rectangle rect, Point pt)
	{
		System.out.println(rect.getX() + rect.getWidth());
		System.out.println(rect.getY() + rect.getHeight());
		boolean a = pt.getX() > rect.getX() && pt.getX() < rect.getWidth() + rect.getX();
		boolean b = pt.getY() > rect.getY() && pt.getY() < rect.getHeight() + rect.getY();
		
		return a && b;
	}
	
}
