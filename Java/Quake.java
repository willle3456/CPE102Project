public class Quake
    extends Animation
{
    public Quake(String name, Point position, int animation_rate)
    {
        super(name, position, animation_rate);
    }
    
    /*def schedule_quake(self, world, ticks):
        self.schedule_animation(world,QUAKE_STEPS) 
        self.schedule_action(world,self.create_entity_death_action(world),
        ticks + QUAKE_DURATION)
        
       
    def create_entity_death_action(self, world):
       def action(current_ticks):
          self.remove_pending_action(action)
          pt = self.get_position()
          world.remove_entity(self)
          return [pt]
       return action*/
}