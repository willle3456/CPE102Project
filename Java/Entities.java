import java.util.List;
import java.util.function.*;
import java.util.LinkedList;

public class Entities 
{
	
	private String name;
	private Point position;
	
	private List<PImage> imgs;
	private int current_img;
	
	public Entities(String name, Point position, List<PImage> imgs)
	{
		this.name = name;
		this.position = position;
        this.current_img = 0;
		this.imgs = imgs;
		
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
		return this.imgs;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void nextImage()
	{
		this.current_img = (this.current_img + 1) % imgs.size();
	}
    
    public Integer getCurrentImage()
    {
        return this.current_img;
    }
    
    public void scheduleEntity(WorldModel world, List<String> i_store)
    {
    }
    
    public PImage getImage()
    {
        return this.imgs(this.current_img);
    }
}
