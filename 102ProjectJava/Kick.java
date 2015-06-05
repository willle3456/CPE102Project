import java.util.List;
import java.util.Random;

import processing.core.PImage;
import processing.core.PApplet;


public class Kick //from miner
		extends DiveKick
{

	public Kick(String name, Point position, int rate, int animation_rate,
			List<PImage> imgs) {
		super(name, position, rate, animation_rate, imgs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Action createAction(WorldModel world, ImageStore imageStore) {
		// TODO Auto-generated method stub
		Action[] action = { null };
		action[0] = ticks -> {
			removePendingAction(action[0]);

			WorldEntity target = world.findNearest(getPosition(), DiveKick.class);
			long nextTime = ticks + getRate();

			if (target != null)
			{
				DiveKick temp = (DiveKick) target;
				int diffX = (this.getPixelPosition().x - temp.getPixelPosition().x);
				int diffY = (this.getPixelPosition().y - temp.getPixelPosition().y);
				int speedX = diffX;
				int speedY = diffY;
				//System.out.println("hi");
				this.move(speedX, speedY, target);
				//System.out.println("hi1");
				/*System.out.println(this.getPixelPosition().toString());
				System.out.println(this.getPosition().toString());
				System.out.println();
				System.out.println(temp.getPixelPosition().toString());
				System.out.println(temp.getPosition().toString() +"\n");*/
				
				if(hit(this.getPixelPosition(), temp.getPixelPosition()))
				{
					int roll = this.roll();
					int enemyRoll = temp.roll();
					
					if(victory(roll,enemyRoll))
					{
						
						this.setPixelPosition(new Point(this.getPosition().x * 32, this.getPosition().y *32));
						world.removeEntity(target);
					}
					else
					{
						this.losingAnimation();
						world.removeEntity(this);
					}
				}
		     }

			scheduleAction(world, this, createAction(world, imageStore),nextTime);
		         
		      };
		      return action[0];
	}

}
