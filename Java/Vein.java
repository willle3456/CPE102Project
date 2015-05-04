public class Vein
    extends Actions
{
    private int rate;
    private int resource_distance = 1;
    
    public Vein(String name, Point position, int rate, int resource_distance)
    {
        super(name,position);
        this.rate = rate;
        this.resource_distance = resource_distance;
    }
    
    public int getRate()
    {
        return this.rate;
    }
    
    public int getResourceDistance()
    {
        return this.resource_distance;
    }
       
    /*def schedule_action(self, world, action, time):
        self.add_pending_action(action)
        world.schedule_action(action, time)
        
    def schedule_vein(self, world, ticks, i_store):
        self.schedule_action(world,self.create_vein_action(world,i_store),
        ticks + self.get_rate())

    def create_vein_action(self, world, i_store):
       def action(current_ticks):
          self.remove_pending_action(action)

          open_pt = world.find_open_around(self.get_position(),
             self.get_resource_distance())
          if open_pt:
             ore = world.create_ore(
                "ore - " + self.get_name() + " - " + str(current_ticks),
                open_pt, current_ticks, i_store)
             world.add_entity(ore)
             tiles = [open_pt]
          else:
             tiles = []

          self.schedule_action(world,
             self.create_vein_action(world, i_store),
             current_ticks + self.get_rate())
          return tiles
       return action  

    def remove_entity(self, world):
       for action in self.get_pending_actions():
          world.unschedule_action(action)
       self.clear_pending_actions()
       world.remove_entity(self) 
       
    def schedule_entity(self, world, i_store):
        self.schedule_vein(world, 0, i_store)*/
}