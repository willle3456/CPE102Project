import processing.core.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.abs;

public abstract class MobileAnimatedActor
   extends AnimatedActor
{
	private PApplet p = new PApplet(); 
   public MobileAnimatedActor(String name, Point position, int rate,
      int animation_rate, List<PImage> imgs)
   {
      super(name, position, rate, animation_rate, imgs);
   }
   
   public int distance(Point currPt, Point goalPt)
   {
	   return Math.abs((currPt.x - goalPt.x) + (currPt.y - goalPt.y));
   }
   
   public List<Point> getAdjacentPts(WorldModel world, Point pt, Point goalPt)
   {
	   List<Point> temp = new LinkedList<Point>();
	   List<Point> adj = new LinkedList<Point>();
	   temp.add(new Point(pt.x - 1, pt.y));
	   temp.add(new Point(pt.x + 1, pt.y));
	   temp.add(new Point(pt.x, pt.y + 1));
	   temp.add(new Point(pt.x, pt.y - 1));
	   
	   for(Point pt1 : temp)
	   {
		   if((world.withinBounds(pt1) && canPassThrough(world, pt1)) || (goalPt.x == pt1.x && goalPt.y == pt1.y))
		   {
			   adj.add(new Point(pt1.x,pt1.y, pt)); 
		   }
	   }
	   
	   return adj; 
   }
   
   public int getNodeValue(Point startPt, Point goalPt, Point currPt)
   {
	   return distance(startPt, currPt) + distance(currPt, goalPt);
   }
   
   public List<Point> sortOpen(List<Point> open, Point start, Point goal)
   {
	   List<Long> values = new LinkedList<Long>();
	   List<Point> sorted = new LinkedList<Point>();
	   
	   for(Point pt: open)
	   {
		   values.add((long)getNodeValue(start, goal, pt));
	   }
	   
	   while(!open.isEmpty() && !values.isEmpty())
	   {
		   int lowest = 0;
		   for(int i = 0; i < values.size(); i++)
		   {
			   if(values.get(i) < values.get(lowest))
			   {
				   lowest = i;
			   }
		   }
		   sorted.add(open.get(lowest));
		   open.remove(lowest);
		   values.remove(lowest);
	   }
	   return sorted; 
	   
   }
   
   public List<Point> getClosedSet(WorldModel world, Point startPt, Point goalPt)
   {
	   Point currPt = startPt; 
	   List<Point> open = new LinkedList<Point>();
	   List<Point> closed = new LinkedList<Point>(); 
	   
	   open.add(startPt);
	   closed.add(startPt); 
	   
	   while(!open.isEmpty())
	   {
		   open = sortOpen(open,startPt,goalPt);
		   
		   currPt = open.get(0);
		   
		   //List<PImage> temp1 = new LinkedList<PImage>();
		   //temp1.add(p.loadImage("../images/checkedgrass.bmp"));
		   
		   //world.setBackground(currPt, new Background("checked",temp1));
 
		   open.remove(0); 
		   List<Point>  temp = getAdjacentPts(world, currPt, goalPt);
		   for(int i = 0; i < temp.size(); i++)
		   {
			   if(!open.contains(temp.get(i)) && !closed.contains(temp.get(i)))
			   {
				   open.add(temp.get(i));
			   }
		   }
		   
		   if(!closed.contains(currPt))
		   {
			   closed.add(currPt);
		   }
		   if(currPt.x == goalPt.x && currPt.y == goalPt.y)
		   {
			   break; 
		   }
	   }
	   return closed; 

	   
   }
   
   public List<Point> traceback(WorldModel world, List<Point> closedSet, Point goal, Point start)
   {
	   List<Point> path = new LinkedList<Point>();
	   Point current = closedSet.get(closedSet.size()-1);
	   //path.add(current);
	   while(current != start)
	   {
		   current = current.cameFrom;
		   path.add(current);
	   }	   
	   return path;
   }
   
   public Point pathFind(WorldModel world, Point startPt, Point goalPt)
   {
	   List<Point>path = traceback(world, getClosedSet(world, startPt, goalPt), goalPt, startPt);
	   return path.get(path.size()-2);
   }

   protected Point nextPosition(WorldModel world, Point dest_pt)
   {

      Point new_pt = pathFind(world, getPosition(), dest_pt);
      
      //List<PImage> temp1 = new LinkedList<PImage>();
	   //temp1.add(p.loadImage("../images/pathgrass.bmp")); 
	   //world.setBackground(new_pt, new Background("checked path",temp1));
	   
      return new_pt;
   }

   protected static boolean adjacent(Point p1, Point p2)
   {
      return (p1.x == p2.x && abs(p1.y - p2.y) == 1) ||
         (p1.y == p2.y && abs(p1.x - p2.x) == 1);
   }

   protected abstract boolean canPassThrough(WorldModel world, Point new_pt); 
}
