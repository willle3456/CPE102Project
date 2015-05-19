import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.*;
import java.util.LinkedList;
import processing.core.*;
public class Entities 
{
	
	private String name;
	private Point position;
	
	private List<PImage> imgs;
	private int current_img;
	
	public Entities(String name, Point position, List<PImage> imgs2)
	{
		this.name = name;
		this.position = position;
        this.current_img = 0;
		this.setImgs(imgs2);
	
	}

	/***
	 * @param x, an integer
	 * @return -1 if x < 0, 0 is x = 0, otherwise 1
	 */
	public static int sign(double x)
	{
		if (x < 0)
		{
			return -1;
		}
		else if(x > 0)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	public static String entitySting(Entities e)
	{
		if(e instanceof MinerNotFull)
		{
			return "Miner " + e.getName() + " " + e.getPosition().getX() + " " + e.getPosition().getY();
		}
		
		else if( e instanceof Vein)
		{
			return "Vein " + e.getName() + " " + e.getPosition().getX() + " " + e.getPosition().getY();
		}
		else if(e instanceof Ore)
		{
			return "Ore " + e.getName() + " " + e.getPosition().getX() + " " + e.getPosition().getY();
		}
		else if(e instanceof Blacksmith)
		{
			return "Blacksmith " + e.getName() + " " + e.getPosition().getX() + " " + e.getPosition().getY();
		}
		else if(e instanceof Obstacle)
		{
			return "Obstacle " + e.getName() + " " + e.getPosition().getX() + " " + e.getPosition().getY();
		}
		else
		{
			return "unknown";
		}
	}
	
	public Point getPosition()
	{
		return this.position;
	}
	
	public void setPosition(Point position)
	{
		this.position = position;
	}
	
    public List<PImage> getImages()
	{
		return this.getImgs();
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void nextImage()
	{
		this.current_img = (this.current_img + 1) % getImgs().size();
	}
    
    public Integer getCurrentImage()
    {
        return this.current_img;
    }
    
    public void scheduleEntity(WorldModel world, HashMap<String, ArrayList<PImage>> iStore)
    {
    }
    
    public PImage getImage()
    {
        return this.getImgs().get(this.current_img);
    }

	public List<PImage> getImgs() {
		return imgs;
	}

	public void setImgs(List<PImage> imgs) {
		this.imgs = imgs;
	}
}
