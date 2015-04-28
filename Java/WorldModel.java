
public class WorldModel {
	public static final int BLOB_RATE_SCALE = 4;
	public static final int	BLOB_ANIMATION_RATE_SCALE = 50;
	public static final int	BLOB_ANIMATION_MIN = 1;
	public static final int	BLOB_ANIMATION_MAX = 3;

	public static final int	ORE_CORRUPT_MIN = 20000;
	public static final int	ORE_CORRUPT_MAX = 30000;

	public static final int	QUAKE_STEPS = 10;
	public static final int	QUAKE_DURATION = 1100;
	public static final int	QUAKE_ANIMATION_RATE = 100;
	public static final int VEIN_SPAWN_DELAY = 500;
	public static final int	VEIN_RATE_MIN = 8000;
	public static final int	VEIN_RATE_MAX = 17000;
	
	private int num_rows, num_cols;
	private OCCGrid background, occupancy;
	//private String background;
	
	public WorldModel(int num_rows, int num_cols, OCCGrid background, OCCGrid occupancy)
	{
		this.num_rows = num_rows;
		this.num_cols = num_cols;
		this.background = background;
		this.occupancy = occupancy;
	}
	
	public Entities getBackground(Point pt)
	{
		if(withinBounds(pt))
		{
			return this.background.getCell(pt);
		}
		
		return null;
	}
	
	public void setBackgrounds(Point pt, Entities b)
	{
		if(withinBounds(pt))
		{
			this.background.setCell(pt,b);
		}
	}
	
	public Entities getTileOccupant(Point pt)
	{
		if (withinBounds(pt))
		{
			return this.occupancy.getCell(pt);
		}
		return null; 
	}
	
	public Point nextPosition(Point ePt, Point destPt)
	{
		int horz = Entities.sign((int)(destPt.getX() - ePt.getY()));
		Point newPoint = new Point(ePt.getX() + horz, ePt.getY());
		
		if( horz == 0 || isOccupied(newPoint))
		{
			int vert = Entities.sign((int)(destPt.getY() - ePt.getY()));
			newPoint = new Point(ePt.getX(), ePt.getY() + vert);
			
			if(vert == 0 || isOccupied(newPoint))
			{
				newPoint = new Point(ePt.getX(), ePt.getY());
			}
		}
		
		return newPoint;
	}
	/**
	 * Checks to see if a point is in the world
	 * @param: a point
	 * @return: True if point is in the world; if otherwise, false
	 */
	
	public boolean withinBounds(Point pt)
	{
		boolean xBounds = (pt.getX() >= 0) && (pt.getX() < this.num_cols);
		boolean yBounds = (pt.getY() >= 0) && (pt.getY() < this.num_rows);
		return xBounds && yBounds; 
	}
	
	public boolean isOccupied(Point pt)
	{
		return withinBounds(pt) && (this.occupancy.getCell(pt) != null);
	}
	
	public Point[] moveEntity(Entities e, Point pt)
	{
		Point [] tiles = new Point [2];
		if (withinBounds(pt))
		{
			Point oldPoint = e.getPosition();
			this.occupancy.setCell(oldPoint, null);
			tiles[0] = oldPoint;
			this.occupancy.setCell(pt, e);
			tiles[1] = pt;
			e.setPosition(pt);
		}
		
		return tiles;
	}
	
	public Point findOpenAround(Point pt, double distance)
	{
		for (int i = (int)(-distance); i < (int)(distance+1); i++)
		{
			for(int j = (int)(-distance); j < (int)(distance+1); j++)
			{
				Point newPoint = new Point((pt.getX() + i),(pt.getY()+ j));
				
				if(withinBounds(newPoint) && !isOccupied(newPoint))
				{
					return newPoint;
				}
			}
		}
		return null;
	}	
	
}
