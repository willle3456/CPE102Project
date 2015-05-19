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
	private int tileWidth, tileHeight;
	private int numRows, numCols;
	public WorldView(int viewCols, int viewRows, PApplet screen, WorldModel world, int tileWidth, int tileHeight)
	{
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
		for(int y = this.view.y; y < view.height; y++)
		{
			for(int x = this.view.x ; x < view.width; x++)
			{
				Point wPt = viewportToWorld(new Point(x,y));
				PImage img = world.getBackgroundImage(wPt);
				screen.image(img, x * this.tileWidth, y * this.tileHeight);	
			}
		}
	}
	
	public void drawEntities(int x, int y)
	{
		for( Entities e: world.getEntities())
		{
			if(contains(view,e.getPosition()))
			{
				System.out.println(e.getPosition().getX() + " " + e.getPosition().getY());
				Point vPt = worldToViewport(e.getPosition());
				if(this.view.width -x < this.view.width && this.view.width - x > 0 &&  this.view.height -y < this.view.height && this.view.height - y > 0)
				{
					screen.image(e.getImage(), ((int)vPt.getX() - x) * this.tileWidth, ((int)vPt.getY() - y) * this.tileHeight);
				}
			}
		}
	}
	
	public void drawViewport(int x, int y)
	{
		drawBackground();
		drawEntities(x,y);
	}
	
	public Rectangle updateTile(Point viewTilePt, PImage surface)
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
	
	public void mouseMove()
	{
		Point temp = new Point(screen.mouseX + this.view.x, screen.mouseY + this.view.y);
		if(contains(this.view, temp))
		{
			this.mousePt = temp;
		}
	}
	
	public void updateView(int deltaX, int deltaY)
	{
		this.view = createShiftedViewport(deltaX,deltaY, this.numRows + deltaX, this.numCols + deltaY);
		drawViewport(deltaX, deltaY);
		//mouseMove(); 
	}
	
	public void updateViewTiles(ArrayList<Point> tiles)
	{
		for(Point tile: tiles)
		{
			if(contains(this.view,tile))
			{
				Point vPt = worldToViewport(tile);
				PImage img = getTileImage(vPt);
			}
		}
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
	public Rectangle createShiftedViewport(int deltaX, int deltaY, int numRows, int numCols)
	{
		/***
		 * moves the viewport around
		 * @param deltaX: movement in x dir
		 * @param deltaY: movement in y dir
		 * @param numRows: num rows in grid
		 * @param numCols num cols in grid
		 * @return a new viewport
		 */
		int newX = clamp(view.x + deltaX,0,numCols - (int)view.getWidth());
		int newY = clamp(view.y + deltaY,0,numRows + (int)view.getHeight());
		
		return new Rectangle(newX, newY, view.width, view.height);
	}
	
	public boolean contains(Rectangle rect, Point pt)
	{
		boolean a = pt.getX() > rect.getX() && pt.getX() < rect.getWidth();
		boolean b = pt.getY() > rect.getY() && pt.getY() < rect.getHeight();
		
		return a && b;
	}
	
}
