import java.util.ArrayList;
import java.util.List;

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
	private List<Entities> entity = new ArrayList<Entities>();
	//private String background;
	
	public WorldModel(int num_rows, int num_cols, OCCGrid background, OCCGrid occupancy)
	{
		this.num_rows = num_rows;
		this.num_cols = num_cols;
		this.background = background;
		this.occupancy = occupancy;
		entity.add(new Ore("bobetta", new Point(1,1),10));
	}
	/***
	 * determines which entity is at a point
	 * @param pt, a point
	 * @return the entity at pt, otherwise null
	 */
	public Entities getBackground(Point pt)
	{
		if(withinBounds(pt))
		{
			return this.background.getCell(pt);
		}
		
		return null;
	}
	/***
	 * puts an entity at a point
	 * @param pt, a point
	 * @param b, an entity
	 * sets the cell (specified from pt) to a specific entity (b)
	 */
	
	public void setBackgrounds(Point pt, Entities b)
	{
		if(withinBounds(pt))
		{
			this.background.setCell(pt,b);
		}
	}
	/***
	 * determines which entity is currently at a point
	 * @param pt, a point
	 * @return if the point is on the screen, return the entity that is at that point
	 * otherwise: null
	 */
	
	public Entities getTileOccupant(Point pt)
	{
		if (withinBounds(pt))
		{
			return this.occupancy.getCell(pt);
		}
		return null; 
	}
	
	public List<Entities> getEntities()
	{
		return this.entity;
	}
	
	/***
	 * determines where an entity is going to move next 
	 * @param ePt, point with an entity
	 * @param destPt, point where the entity will go
	 * @return the point where the entity will move next
	 */
	
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
	/***
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
	/***
	 * finds the entity of a certain type that is closest to a point in the world
	 * @param indexes, list of indexes of entities of that certain type
	 * @param pt, point you're comparing to
	 * @return the entity closest to that point
	 */
	public Entities nearestEntity(ArrayList<Integer> indexes, Point pt)
	{
		if(indexes.size() > 0)
		{
			int near = indexes.get(0);
			for( int i = 0; i < indexes.size(); i++)
			{
				double temp = this.entity.get(near).getPosition().distance(pt);
				double temp2 = this.entity.get(i).getPosition().distance(pt);
				
				if(temp2 < temp)
				{
					near = i;
				}
						
			}
			return this.entity.get(near);
		}
		return null;
	}
	/***
	 * returns index of the ones of the same type w/ nearest
	 * @param pt, a point in the world
	 * @param e, the type of entity you're checking
	 * @return int array of the indexes of the checked Entity type
	 */
	public Entities findNearest(Point pt, Entities e)
	{
		ArrayList<Integer> same = new ArrayList<Integer>();
		for(int i = 0; i < this.entity.size(); i++)
		{
			if( this.entity.get(i).getClass() == e.getClass())
			{
				same.add(i);
			}
		}
		return nearestEntity(same, pt);
	}
	
	/***
	 * moves entity to a new point
	 * @param e, an entity
	 * @param pt, a point
	 * @return the points where the entity was and will be
	 */
	
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
	
	public void removeEntityAt(Point pt)
	{
		if(isOccupied(pt))
		{
			Entities e = this.occupancy.getCell(pt);
			//e.setPosition(new Point(-1,-1));
			this.entity.remove(this.entity.indexOf(e));
			this.occupancy.setCell(pt, null);
		}
	}
	
	public void removeEntity(Entities e)
	{
		removeEntityAt(e.getPosition());
	}
	
	/***
	 * checks if the area around is point is open
	 * @param pt, a point
	 * @param distance, the distance around the point
	 * @return an open point if it's on the screen & isn't occupied, otherwise, null
	 */
	
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
	
	public Point blobNextPosition(Point ePt, Point destPt)
	{
		int horz = Entities.sign(destPt.getX() - ePt.getX());
		Point newPt = new Point(ePt.getX() + horz, ePt.getY());
		if(horz == 0 || (isOccupied(newPt) && !(getTileOccupant(newPt) instanceof Ore)))
		{
			int vert = Entities.sign(destPt.getY() - ePt.getY());
			newPt = new Point(ePt.getX(),ePt.getY()+ vert);
			
			if (vert == 0 || (isOccupied(newPt) && !(getTileOccupant(newPt) instanceof Ore)))
			{
				newPt = new Point(ePt.getX(), ePt.getY());
			}
		}
		return newPt;
	}
	
	public void addEntity(Entities e)
	{
		Point pt = e.getPosition();
		this.occupancy.setCell(pt, e);
		this.entity.add(e);
		
	}
	
}
