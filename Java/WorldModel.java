import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.LongConsumer;

import processing.core.*;

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
	private List<Entities> entity;
	public OrderedList<LongConsumer> actionQueue;
	public WorldModel(int num_rows, int num_cols)
	{
		this.num_cols = num_cols;
		this.num_rows = num_rows;
		this.background = new OCCGrid(100,100, null);
		this.occupancy = new OCCGrid(100,100, null);
	}
	public WorldModel(int num_cols, int num_rows, Entities background)
	{

		//System.out.println("rows: " + num_rows);
		//System.out.println("cols: " + num_cols);
		this.num_rows = num_rows;
		this.num_cols = num_cols;
		this.background = new OCCGrid(num_cols, num_rows, background);
		this.occupancy = new OCCGrid(num_cols, num_rows, null);
		this.entity = new ArrayList<Entities>();
		this.actionQueue = new OrderedList<LongConsumer>();
	}
	
	public void addEntity(Entities e)
	{
		Point pt = e.getPosition();
		if(withinBounds(pt))
		{
			Actions oldEntity = (Actions) this.occupancy.getCell(pt);
			if(oldEntity != null)
			{
				oldEntity.clearPendingActions();
			}
		}
		this.occupancy.setCell(pt, e);
		this.entity.add(e);
		
	}
	
	public boolean withinBounds(Point pt)
	{
		/***
		 * Checks to see if a point is in the world
		 * @param: a point
		 * @return: True if point is in the world; if otherwise, false
		 */
		boolean xBounds = (pt.getX() >= 0) && (pt.getX() < this.num_cols);
		boolean yBounds = (pt.getY() >= 0) && (pt.getY() < this.num_rows);
		return xBounds && yBounds; 
	}
	
	public boolean isOccupied(Point pt)
	{
		return withinBounds(pt) && (this.occupancy.getCell(pt) != null);
	}
	
	public Entities findNearest(Point pt, Class type)
	{
		/***
		 * returns index of the ones of the same type w/ nearest
		 * @param pt, a point in the world
		 * @param e, the type of entity you're checking
		 * @return int array of the indexes of the checked Entity type
		 */
		ArrayList<Integer> same = new ArrayList<Integer>();
		for(int i = 0; i < this.entity.size(); i++)
		{
			if( this.entity.get(i).getClass() == type)
			{
				same.add(i);
			}
		}
		return nearestEntity(same, pt);
	}
	
	public List<Point> moveEntity(Entities e, Point pt)
	{
		/***
		 * moves entity to a new point
		 * @param e, an entity
		 * @param pt, a point
		 * @return the points where the entity was and will be
		 */
		ArrayList<Point> tiles = new ArrayList<Point>();
		if (withinBounds(pt))
		{
			Point oldPoint = e.getPosition();
			this.occupancy.setCell(oldPoint, null);
			tiles.add(oldPoint);
			this.occupancy.setCell(pt, e);
			tiles.add(pt);
			e.setPosition(pt);
		}
		return tiles;
	}
	
	public void removeEntity(Entities e)
	{
		removeEntityAt(e.getPosition());
	}
	
	public void removeEntityAt(Point pt)
	{
		if(isOccupied(pt))
		{
			Entities e = this.occupancy.getCell(pt);
			e.setPosition(new Point(-1,-1));
			this.entity.remove(this.entity.indexOf(e));
			this.occupancy.setCell(pt, null);
		}
	}
	
	public void scheduleAction(LongConsumer action, long time)
	{
		actionQueue.insert(action, time);
		System.out.println(actionQueue.size());
	}
	
	public void unscheduleAction(LongConsumer action)
	{
		actionQueue.remove(action);
	}
	
	public void updateOnTime(long ticks)
	{
		OrderedList.ListItem<LongConsumer> next = actionQueue.head();
		
		while(next != null && next.ord < ticks)
		{
			actionQueue.pop();
			next.item.accept(ticks);
			System.out.println("here");
			next = actionQueue.head();
		}
		

	}
	
	public PImage getBackgroundImage(Point pt)
	{
		if(withinBounds(pt))
		{
			return this.background.getCell(pt).getImage();
		}
		return null; 
	}
	
	public Entities getBackground(Point pt)
	{
		/***
		 * determines which entity is at a point
		 * @param pt, a point
		 * @return the entity at pt, otherwise null
		 */
		if(withinBounds(pt))
		{
			return this.background.getCell(pt);
		}
		
		return null;
	}
	
	public void setBackground(Point pt, Entities b)
	{
		/***
		 * puts an entity at a point
		 * @param pt, a point
		 * @param b, an entity
		 * sets the cell (specified from pt) to a specific entity (b)
		 */
		if(withinBounds(pt))
		{
			this.background.setCell(pt,b);
		}
	}
	
	public Entities getTileOccupant(Point pt)
	{
		/***
		 * determines which entity is currently at a point
		 * @param pt, a point
		 * @return if the point is on the screen, return the entity that is at that point
		 * otherwise: null
		 */
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
	
	public Point nextPosition(Point ePt, Point destPt)
	{
		/***
		 * determines where an entity is going to move next 
		 * @param ePt, point with an entity
		 * @param destPt, point where the entity will go
		 * @return the point where the entity will move next
		 */
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
	
	public Vein createVein(String name, Point pt, long ticks, HashMap<String, ArrayList<PImage>> iStore)
	{
		Random rand = new Random();
		int randInt = rand.nextInt(this.VEIN_RATE_MAX- this.VEIN_RATE_MIN) + this.VEIN_RATE_MIN;
		
		Vein vein = new Vein("vein" + name, pt, ImageStore.getImages(iStore, "vein"), randInt);
		return vein;
	}
	
	public void clearPendingActions(Actions e)
	{
		for(LongConsumer action: e.getPendingActions())
		{
			unscheduleAction(action);
		}
		e.clearPendingActions();
	}
	
	public Point findOpenAround(Point pt, double distance)
	{
		/***
		 * checks if the area around is point is open
		 * @param pt, a point
		 * @param distance, the distance around the point
		 * @return an open point if it's on the screen & isn't occupied, otherwise, null
		 */
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
	
	public Ore createOre(String name, Point pt, long current_ticks, HashMap<String, ArrayList<PImage>> iStore)
	{
		Random rand = new Random();
		int temp = rand.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN) + ORE_CORRUPT_MIN;
		Ore ore = new Ore(name, pt, ImageStore.getImages(iStore, "ore"),temp);
		ore.scheduleOre(this,current_ticks, iStore);
		
		return ore;
	}
	
	public OreBlob createBlob(String name, Point pt, long rate, long ticks, HashMap<String, ArrayList<PImage>> i_store)
	{
		Random rand = new Random();
		int temp = rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN) + BLOB_ANIMATION_MIN;
		
		OreBlob blob = new OreBlob(name, pt,ImageStore.getImages(i_store, "blob"), temp * BLOB_ANIMATION_RATE_SCALE, rate);
		blob.scheduleEntity(this,ticks,i_store);
		return blob;
	}
	
	public Quake createQuake(Point pt, long ticks, HashMap<String, ArrayList<PImage>> iStore)
	{
		Quake quake = new Quake("quake", pt, ImageStore.getImages(iStore, "quake"), QUAKE_ANIMATION_RATE);
		quake.scheduleEntity(this,ticks, iStore);
		return quake;
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
	
	public int getNum_rows() {
		return num_rows;
	}
	public void setNum_rows(int num_rows) {
		this.num_rows = num_rows;
	}
	public int getNum_cols() {
		return num_cols;
	}
	public void setNum_cols(int num_cols) {
		this.num_cols = num_cols;
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
	
	
}
