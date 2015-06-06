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

			WorldEntity target = world.findNearest(getPosition(), Dive.class);
			long nextTime = ticks + getRate();

			if (target != null && world.getBackground(target.getPosition()).getName() == "arena")
			{
				Dive temp = (Dive) target;
				System.out.println(" kick state your enemy: " + target.getName());
				int diffX = (this.getPixelPosition().x - temp.getPixelPosition().x);
				int diffY = (this.getPixelPosition().y - temp.getPixelPosition().y);
				int speedX = -(diffX/10);
				int speedY = -(diffY/10);
				this.move(speedX, speedY, target);

				
				if(hit(this.getPixelPosition(), temp.getPixelPosition()))
				{
					int roll = this.roll();
					int enemyRoll = temp.roll();
					
					if(victory(roll,enemyRoll))
					{
						
						this.setPixelPosition(new Point(temp.getPosition().x * 32, temp.getPosition().y *32));
						target.remove(world);
						//world.removeEntity(target);
						System.out.println("victory kick");
						System.out.println("good bye" + target.getName() + target.getPosition().toString());
					}
				}
		     }

			scheduleAction(world, this, createAction(world, imageStore),nextTime);
		         
		      };
		      return action[0];
	}

}
