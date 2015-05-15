public class OreBlob
    extends Animation
{
    private int rate;
    
    public OreBlob(String name, Point position, int animation_rate, int rate)
    {
        super(name,position,animation_rate);
        this.rate = rate;
    }
    
    public int getRate()
    {
        return this.rate;
    }
       
    public void scheduleBlob(Worldmodel world, int ticks,List<String> i_store)
    {
       this.scheduleAction(world, this.createOreBlobAction(world, i_store),
          ticks + this.getRate());
       this.scheduleAnimation(world);
    }
       
    public ? createOreBlobAction(Worldmodel world, List<String> i_store)
    {
       public List<String> action(int current_ticks)
       {
          this.removePendingAction(action);

          Point entity_pt = this.getPosition();
          Vein vein = world.findNearest(entity_pt, Vein);
          (tiles, found) = this.blobToVein(world,  vein);

          ing next_time = current_ticks + this.getRate();
          if(found)
          {
            Quake quake = world.createQuake(tiles[0], current_ticks, i_store);
             world.addEntity(quake);
             int next_time = current_ticks + this.getRate() * 2;
          }
          this.scheduleAction(world, 
             this.createOreBlobAction(world, i_store),
             next_time);

          return tiles;
       }
       return action;
    }
       
    public boolean blobToVein(WorldModel world, Vein vein)
    {
           Point entity_pt = this.getPosition();
           if(!(vein instanceof Vein))
           {
              return (false); 
           }
           Point vein_pt = vein.getPosition();
           if (entity_pt.adjacent(vein_pt))
           {
              world.removeEntity(vein);
              return (true); 
           }
           else
           {
              Point new_pt = world.blobNextPosition(entity_pt, vein_pt);
              Entities old_entity = world.getTileOccupant(new_pt);
              if(old_entity instanceof Ore)
              {
                 world.removeEntity(old_entity);
              }
              return (false);
           }
    }
}
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          