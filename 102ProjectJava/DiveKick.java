import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import processing.core.PImage;
import processing.core.PApplet;
	
public abstract class DiveKick extends AnimatedActor{

	public int lastSpeedX, lastSpeedY;
	//private PApplet screen = new PApplet(); 
	private Point pixelPoint;
	
	public DiveKick(String name, Point position, int rate, int animation_rate,
			List<PImage> imgs) {
		super(name, position, rate, animation_rate, imgs);
		//this.position = new Point(position.x * 32, position.y * 32);
		//System.out.println(position.toString());
		// TODO Auto-generated constructor stub
		// the range for point declarations is different
		this.pixelPoint = new Point(position.x * 32, position.y * 32);
	}
	
	public Point getPixelPosition()
	{
		return this.pixelPoint;
	}
	
	public void setPixelPosition(Point pixelPoint)
	{
		this.pixelPoint = pixelPoint; 
	}
	
	public abstract Action createAction(WorldModel world, ImageStore imageStore);
	
	public int roll()
	{
		Random r = new Random();
		int roll = r.nextInt(6) + 1; 
		return roll; 
	}

	public void move(int speedX, int speedY, WorldEntity target)
	{		
		this.setPixelPosition(new Point(this.getPixelPosition().x + speedX, this.getPixelPosition().y + speedY));
		//System.out.println("Speed components: " + " " + speedX + " " + speedY);
		//System.out.println(this.getPixelPosition().toString());
		//this.setPosition(new Point(this.getPosition().x + speedX, this.getPosition().y + speedY));
	}
	
	public boolean hit(Point pt, Point enemyPt)
	{
		/**
		 * @param pt: must be a pixel position point of this DiveKick
		 * @param enemyPt: must be a pixel position point of enemy
		 * @return: if the DiveKick and enemy collide, return true; otherwise false
		 */
		List<Point> img_bounds = new ArrayList<Point>();
		
		img_bounds.add(0, new Point(pt.x, pt.y));
		img_bounds.add(1, new Point(pt.x + 32, pt.y));
		img_bounds.add(2, new Point(pt.x, pt.y + 32));
		img_bounds.add(3, new Point(pt.x + 32, pt.y + 32));
		
		for(Point ptz : img_bounds)
		{
			if((ptz.x < enemyPt.x + 32 && ptz.x > enemyPt.x) && (ptz.y < enemyPt.y + 32 && ptz.y > enemyPt.y))
			{
				return true; 
			}
		}
		return false;
	}
	
	public boolean victory(int roll, int enemyRoll)
	{
		if(roll == enemyRoll)
		{
			Random r = new Random();
			int coin_flip = r.nextInt(2);
			if(coin_flip == 0)
			{
				return true;
			}
			return false;
		}
		return roll > enemyRoll; 
	}
	
	public void losingAnimation()
	{
		int speedX = -(lastSpeedX);
		int speedY = -(lastSpeedY);
		int tempX = this.getPixelPosition().x;
		int tempY = this.getPixelPosition().y;
		
		this.setPixelPosition(new Point(tempX + speedX, tempY + speedY));
		//screen.scale((float) 1.25);
		//screen.rotate((float) 90);
		
	}
	
	
}
