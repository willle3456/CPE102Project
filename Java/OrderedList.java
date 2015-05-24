import java.util.LinkedList;
import java.util.List;


public class OrderedList<LongConsumer>
{
   private List<ListItem<LongConsumer>> list;

   public OrderedList()
   {
      list = new LinkedList<>();
   }

   public void insert(LongConsumer item, long ord)
   {
      int idx = 0;
      for (ListItem<LongConsumer> lItem : list)
      {
         if (lItem.ord >= ord)
         {
            break;
         }
         idx++;
      }

      list.add(idx, new ListItem<>(item, ord));
   }

   public void remove(LongConsumer item)
   {
      int idx = 0;
      for (ListItem<LongConsumer> lItem : list)
      {
         if (lItem.item.equals(item))
         {
            break;
         }
         idx++;
      }

      if (idx < list.size())
      {
         list.remove(idx);
      }
   }

   public ListItem<LongConsumer> head()
   {
      if (!list.isEmpty())
      {
         return list.get(0);
      }
      else
      {
         return null;
      }
   }

   public void pop()
   {
      if (!list.isEmpty())
      {
         list.remove(0);
      }
   }

   public int size()
   {
      return list.size();
   }

   public static class ListItem<LongConsumer>
   {
      public final LongConsumer item;
      public final long ord;

      public ListItem(LongConsumer item, long ord)
      {
         this.item = item;
         this.ord = ord;
      }
   }
}
