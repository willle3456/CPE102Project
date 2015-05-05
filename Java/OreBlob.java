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
       
    /*def schedule_blob(self, world, ticks, i_store):
       self.schedule_action(world, self.create_ore_blob_action(world, i_store),
          ticks + self.get_rate())
       self.schedule_animation(world)
       
    def create_ore_blob_action(self, world, i_store):
       def action(current_ticks):
          self.remove_pending_action(action)

          entity_pt = self.get_position()
          vein = world.find_nearest(entity_pt, Vein)
          (tiles, found) = self.blob_to_vein(world,  vein)

          next_time = current_ticks + self.get_rate()
          if found:
             quake = world.create_quake(tiles[0], current_ticks, i_store)
             world.add_entity(quake)
             next_time = current_ticks + self.get_rate() * 2

          self.schedule_action(world, 
             self.create_ore_blob_action(world, i_store),
             next_time)

          return tiles
       return action*/
       
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
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          