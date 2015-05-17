import java.util.ArrayList;
import java.util.List;


public class OrderedList 
{
	private ArrayList<ListItem> list;
	
	public OrderedList(ArrayList<ListItem> list)
	{
		this.list = list; 
	}
	
	public void insert(Object item, int ord)
	{
		int size = this.list.size();
		int idx = 0;
		ArrayList<ListItem> temp = new ArrayList<ListItem>();
		
		while(idx < size && this.list.get(idx).getOrd() < ord)
		{
			idx ++;
		}
		
		this.list.addAll(temp);
	}
	
	public void remove(Object item)
	{
		int size = this.list.size();
		int idx = 0;
		
		while(idx < size && this.list.get(idx).getItem() != item)
		{
			idx++;
		}
		
		if(idx < size)
		{
			this.list.remove(idx);
		}
	}
	
	public Object head()
	{
		if(this.list != null){
			return this.list.get(0);
		}
		return null; 
	}
	
	public Object pop()
	{
		if(this.list != null)
		{
			ListItem temp = this.list.get(0);
			this.list.remove(0);
			return temp;
		}
		return null; 
	}
	
}

class ListItem
{
	private Object item;
	private int ord;
	
	public ListItem(Object item, int ord)
	{
		this.item = item;
		this.ord = ord;
	}
	public Object getItem()
	{
		return this.item;
	}
	
	public Object getItem(int ord)
	{
		if(ord == this.ord)
		{
			return this.item;
		}
		return null; 
	}
	
	public int getOrd()
	{
		return this.ord;
	}
	
	public boolean equals(Object o)
	{
		if(o instanceof ListItem)
		{
			return ((ListItem) o).item.equals(this.item) && ((ListItem)o).ord == this.ord;
		}
		return false;
	}
}
