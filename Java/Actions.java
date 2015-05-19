import java.util.List;
import java.util.function.*;
import java.util.ArrayList;
import processing.core.*;

public class Actions
    extends Entities
{
    
    private List<Object> pending_actions;
    
    public Actions(String name, Point position, List<PImage> imgs)
    {
        super(name, position, imgs);
        this.pending_actions = new ArrayList<Object>();
    }
    
    public List<Object> getPendingActions()
    {
        if(this instanceof Actions)
        {
            return this.pending_actions;
        }
        else
        {
            return new ArrayList<Object>();
        }
    }

    public void removePendingAction(Object action)
    {
        if(this instanceof Actions)
        {
            this.pending_actions.remove(action);
        }
    }

    public void addPendingAction(Object action)
    {
        if(this instanceof Actions)
        {
            this.pending_actions.add(action);
        }
    }

    public void clearPendingActions()
    {
        if(this instanceof Actions)
        {
            this.pending_actions = new ArrayList<Object>();
        }
    }
}