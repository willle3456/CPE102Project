
public class Entities 
{
	
	private String name;
	private Point position;
	//private String[] imgs;
	private int current_img;
	
	public Entities(String name, Point position)
	{
		this.name = name;
		this.position = position;
        this.current_img = 0;
		//this.imgs = imgs;
		
	}
	
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
	
	public Point getPosition()
	{
		return this.position;
	}
	
	public void setPosition(Point position)
	{
		this.position = position;
	}
	
	/*public String getImages()
	{
		return this.imgs;
	}*/
	
	public String getName()
	{
		return this.name;
	}
	
	/*public void nextImage()
	{
		this.current_img = (this.current_img + 1) % imgs.length;
	}*/
	

}
