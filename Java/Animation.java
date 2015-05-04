public class Animation
    extends Actions
{
    
    private int animation_rate;
    
    public Animation(String name, Point position, int animation_rate)
    {
        super(name,position);
        this.animation_rate = animation_rate;
    }
    
    public int getAnimationRate()
    {
        return this.animation_rate;
    }
    
    /*def schedule_animation(self, world, repeat_count=0):
        self.schedule_action(world,
        self.create_animation_action(world, repeat_count),
        self.get_animation_rate())
        
    def schedule_action(self, world, action, time):
       self.add_pending_action(action)
       world.schedule_action(action, time)
       
    def create_animation_action(self, world, repeat_count):
       def action(current_ticks):
          self.remove_pending_action(action)

          self.next_image()

          if repeat_count != 1:
             self.schedule_action(world,
                self.create_animation_action(world, max(repeat_count - 1, 0)),
                current_ticks + self.get_animation_rate())

          return [self.get_position()]
       return action*/
}