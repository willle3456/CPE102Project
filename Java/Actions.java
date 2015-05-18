import java.util.List;
import java.util.function.*;
import java.util.LinkedList;

public class Actions
    extends Entities
{
    
    private List<Object> pending_actions;
    
    public Actions(String name, Point position, List<PImage> imgs)
    {
        super(name, position, imgs);
        this.pending_actions = new LinkedList<Object>();
    }
    
    public List<Object> getPendingActions()
    {
        if(this instanceof Actions)
        {
            return this.pending_actions;
        }
        else
        {
            return new LinkedList<Object>();
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
            this.pending_actions = new LinkedList<Object>();
        }
    }
}