import java.util.List;
import java.util.Random;

import processing.core.PImage;


public class Dive 
	extends MobileAnimatedActor
	{
		public Dive(String name, Point position, int rate, int animation_rate,
		      List<PImage> imgs)
		   {
		      super(name, position, rate, animation_rate, imgs);
		   }
		
		protected boolean canPassThrough(WorldModel world, Point pt)
		{
			return !world.isOccupied(pt) || world.getTileOccupant(pt) instanceof Ore;
		}
	   
		private boolean move(WorldModel world, WorldEntity target)
		{
			if (target == null)
			{
				return false;
			}

			if (adjacent(getPosition(), target.getPosition()))
			{
				return true;
			}
			else
			{
				Point new_pt = nextPosition(world, target.getPosition());
				WorldEntity old_entity = world.getTileOccupant(new_pt);
				if (old_entity != null && old_entity != this)
				{
					old_entity.remove(world);
				}
				world.moveEntity(this, new_pt);
				return false;
			}
		}	
	
	
		public Action createAction(WorldModel world, ImageStore imageStore)
		{
			Action[] action = { null };
			action[0] = ticks -> {
				removePendingAction(action[0]);
	         
				WorldEntity target = world.findNearest(getPosition(), Kick.class);
				long nextTime = ticks + getRate();

				if (target != null)
				{
					Point tPt = target.getPosition();

					if (move(world, target))
					{
						/*Quake quake = createQuake(world, tPt, ticks, imageStore);
	               		world.addEntity(quake);*/
						nextTime = nextTime + getRate();
					}
				}

				scheduleAction(world, this, createAction(world, imageStore),
						nextTime);
	         
			};
			return action[0];
		}
}


