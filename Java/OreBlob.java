import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.*;
import java.util.ArrayList;
import java.util.LinkedList;

import processing.core.*;
import java.util.HashMap;

public class OreBlob
    extends Animation
{
    private long rate;
    private List<Object> pending_actions;
    
    public OreBlob(String name, Point position, List<PImage> imgs, int animation_rate, long rate)
    {
        super(name,position,imgs,animation_rate);
        this.rate = rate;
        this.pending_actions = new ArrayList<Object>();
    }
    
    public long getRate()
    {
        return this.rate;
    }
       
    public void scheduleBlob(WorldModel world, long ticks,HashMap<String, ArrayList<PImage>> i_store)
    {
       this.scheduleAction(world, this.createOreBlobAction(world, i_store),
          ticks + this.getRate());
       this.scheduleAnimation(world,0);
    }
       
    public Object createOreBlobAction(WorldModel world, HashMap<String,ArrayList<PImage>> i_store)
    {
       LongConsumer[] action = { null };
        action[0] = (long current_ticks) -> 
       {
          this.removePendingAction(action[0]);

          Point entity_pt = this.getPosition();
          Vein vein = (Vein) world.findNearest(entity_pt, Vein.class);
          boolean found = this.blobToVein(world,  vein);

           long next_time = current_ticks + this.getRate();
          if(found)
          {
            Quake quake = world.createQuake(vein.getPosition(), current_ticks, i_store);
             world.addEntity(quake);
             next_time = current_ticks + this.getRate() * 2;
          }
          this.scheduleAction(world, 
             this.createOreBlobAction(world, i_store),
             next_time);

        };
       return action;
    }
       
    public boolean blobToVein(WorldModel world, Vein vein)
    {
           Point entity_pt = this.getPosition();
           List<Point> tiles = new ArrayList<Point>();
           //List<Point> tiles = new LinkedList<Point>();           
           if(!(vein instanceof Vein))
           {
              //tiles.add(entity_pt);
              //return new TilesBool(tiles, false); 
        	   return false;
           }
           Point vein_pt = vein.getPosition();
           if (entity_pt.adjacent(vein_pt))
           {
              world.removeEntity(vein);
              //tiles.add(vein_pt);
             // return new TilesBool(tiles, true); 
              return true;
           }
           else
           {
              Point new_pt = world.blobNextPosition(entity_pt, vein_pt);
              Entities old_entity = world.getTileOccupant(new_pt);
              if(old_entity instanceof Ore)
              {
                 world.removeEntity(old_entity);
              }
              //tiles.add(new_pt);
             // return new TilesBool(tiles, false);
              return false;
           }
    }
}
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          