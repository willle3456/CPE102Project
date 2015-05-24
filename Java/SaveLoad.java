
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import processing.core.*;


public class SaveLoad 
{
	public static final int PROPERTY_KEY = 0;
	
	public static final String BGND_KEY = "background";
	public static final int BGND_NUM_PROPERTIES = 4;
	public static final int BGND_NAME = 1;
	public static final int BGND_COL = 2;
	public static final int BGND_ROW = 3; 
	
	public static final String MINER_KEY = "miner";
	public static final int MINER_NUM_PROPERTIES = 7;
	public static final int MINER_LIMIT = 4; 
	public static final int MINER_NAME = 1;
	public static final int MINER_COL = 2;
	public static final int MINER_ROW = 3;
	public static final int MINER_RATE = 5;
	public static final int MINER_ANIMATION_RATE = 6;
	
	public static final String OBSTACLE_KEY = "obstacle";
	public static final int OBSTACLE_NUM_PROPERTIES = 4;
	public static final int OBSTACLE_NAME = 1;
	public static final int OBSTACLE_COL = 2;
	public static final int OBSTACLE_ROW = 3; 
	
	public static final String ORE_KEY = "ore";
	public static final int ORE_NUM_PROPERTIES = 5;
	public static final int ORE_NAME = 1;
	public static final int ORE_COL = 2;
	public static final int ORE_ROW = 3;
	public static final int ORE_RATE = 4;
	
	public static final String SMITH_KEY = "blacksmith";
	public static final int SMITH_NUM_PROPERTIES = 7;
	public static final int SMITH_NAME = 1;
	public static final int SMITH_COL = 2;
	public static final int SMITH_ROW = 3; 
	public static final int SMITH_LIMIT =4;
	public static final int SMITH_RATE = 5;
	public static final int SMITH_REACH = 6;
	
	public static final String VEIN_KEY = "vein";
	public static final int VEIN_NUM_PROPERTIES = 6;
	public static final int VEIN_NAME = 1;
	public static final int VEIN_COL = 2;
	public static final int VEIN_ROW = 3;
	public static final int VEIN_RATE = 4;
	public static final int VEIN_REACH = 5;
	
	public Miner createMiner(String[] props, HashMap<String,ArrayList<PImage>> iStore)
	{
		if(props.length == MINER_NUM_PROPERTIES)
		{
			Miner miner = new MinerNotFull(props[MINER_NAME], new Point(Integer.parseInt(props[MINER_COL]),Integer.parseInt(props[MINER_ROW])),ImageStore.getImages(iStore,props[PROPERTY_KEY]), 
					Integer.parseInt(props[MINER_RATE]),Integer.parseInt(props[MINER_LIMIT]), 
							Integer.parseInt(props[MINER_ANIMATION_RATE]));
			return miner;
		}
		return null;
	}
			
	public Vein createVein(String[] props, HashMap<String,ArrayList<PImage>> iStore)
	{
		if(props.length == VEIN_NUM_PROPERTIES)
		{
			Vein vein = new Vein(props[VEIN_NAME], new Point(Integer.parseInt(props[VEIN_COL]),Integer.parseInt(props[VEIN_ROW])),ImageStore.getImages(iStore,props[PROPERTY_KEY]), 
					Integer.parseInt(props[VEIN_RATE]), Integer.parseInt(props[VEIN_REACH]));
			return vein;
		}
		return null;
	}
	
	public Ore createOre(String[] props, HashMap<String,ArrayList<PImage>> iStore)
	{
		if(props.length == ORE_NUM_PROPERTIES)
		{
			Ore ore = new Ore(props[ORE_NAME],
					new Point(Integer.parseInt(props[ORE_COL]),Integer.parseInt(props[ORE_ROW])),
					ImageStore.getImages(iStore,props[PROPERTY_KEY]), Integer.parseInt(props[ORE_RATE]));
			return ore;
		}
		return null;
	}
	
	public Blacksmith createBlacksmith(String[] props, HashMap<String,ArrayList<PImage>> iStore)
	{
		if(props.length == SMITH_NUM_PROPERTIES)
		{
			Blacksmith smith = new Blacksmith(props[SMITH_NAME], 
					new Point(Integer.parseInt(props[SMITH_COL]),Integer.parseInt(props[SMITH_ROW])),
					ImageStore.getImages(iStore,props[PROPERTY_KEY]), 
					Integer.parseInt(props[SMITH_LIMIT]),
					Integer.parseInt(props[SMITH_RATE]), 
					Integer.parseInt(props[SMITH_REACH]));
			return smith;
		}
		return null;
	}
	
	public Obstacle createObstacle(String[] props, HashMap<String,ArrayList<PImage>> iStore)
	{
		if(props.length == OBSTACLE_NUM_PROPERTIES)
		{
			Obstacle obs = new Obstacle(props[OBSTACLE_NAME], 
					new Point(Integer.parseInt(props[VEIN_COL]),Integer.parseInt(props[VEIN_ROW])),
					ImageStore.getImages(iStore,props[PROPERTY_KEY]));
			return obs;
		}
		return null;
	}
	
	public Entities createFromProperties(String[] props, HashMap<String,ArrayList<PImage>> iStore)
	{
		String key = props[PROPERTY_KEY];
		
		if(props != null)
		{
			if(key.equals(MINER_KEY))
			{
				return createMiner(props,iStore);
			}
			else if(key.equals(VEIN_KEY))
			{
				return createVein(props,iStore);
			}
			else if(key.equals(ORE_KEY))
			{
				return createOre(props,iStore);
			}
			else if(key.equals(SMITH_KEY))
			{
				return createBlacksmith(props,iStore);
			}
			else if(key.equals(OBSTACLE_KEY))
			{
				return createObstacle(props,iStore);
			}
		}
		return null;
	}
	
	public void addBackground(WorldModel world, String[] props, HashMap<String,ArrayList<PImage>> iStore)
	{
		if(props.length >= BGND_NUM_PROPERTIES)
		{
			Point pt = new Point(Integer.parseInt(props[BGND_COL]), Integer.parseInt(props[BGND_ROW]));
			String name = props[BGND_NAME];
			world.setBackground(pt,new Background(name, pt, ImageStore.getImages(iStore,name)));
		}
	}
	
	public void addEntity(WorldModel world, String[] props, HashMap<String,ArrayList<PImage>> iStore,  long time)
	{
	
		if(createFromProperties(props,iStore).getClass() == Obstacle.class)
		{
			
			Entities e = createFromProperties(props,iStore);
			if(e != null)
			{
				world.addEntity(e);
			}
			return; 
		}
		
		Actions eT = (Actions) createFromProperties(props, iStore);
		if (eT != null)
		{
			
			world.addEntity(eT);
				
				if(props[PROPERTY_KEY].equals(MINER_KEY) || props[PROPERTY_KEY].equals(VEIN_KEY) || props[PROPERTY_KEY].equals(ORE_KEY))
				{
					//System.out.println("here");
					eT.scheduleEntity(world,time + eT.getRate() ,iStore);
					//System.out.println("scheduled");
				}
		}
		
	}
	public void loadWorld(WorldModel world, HashMap<String,ArrayList<PImage>> images, Scanner file, long time)
	{
		try
		{
			while(file.hasNextLine())
			{
				String temp = file.nextLine();
				String [] props = temp.split(" ");

				if(props[PROPERTY_KEY].equals(BGND_KEY))
				{
					addBackground(world,props,images);
				}
				else
				{
					addEntity(world,props,images, time);
				}
			}
		}
		finally
		{
			
		}

	}
}
