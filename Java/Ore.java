public class Ore
    extends Actions
{
    private int rate = 5000;
    
    public Ore(String name, Point position, int rate)
    {
        super(name, position);
        this.rate = rate;
    }
    
    public int getRate()
    {
        return this.rate;
    }

        
    /*schedule_action(self, world, action, time):
       self.add_pending_action(action)
       world.schedule_action(action, time)
       
       
    def schedule_ore(self, world, ticks, i_store):
       self.schedule_action(world,
          self.create_ore_transform_action(world, i_store),
          ticks + self.get_rate())
          
    def create_ore_transform_action(self, world, i_store):
       def action(current_ticks):
          self.remove_pending_action(action)
          blob = world.create_blob(self.get_name() + " -- blob",
             self.get_position(),
             self.get_rate() // BLOB_RATE_SCALE,
             current_ticks, i_store)

          world.remove_entity(self)
          world.add_entity(blob)

          return [self.get_position()]
       return action
       
    def schedule_entity(self, world, i_store):
        self.schedule_ore(world, 0, i_store) */
}