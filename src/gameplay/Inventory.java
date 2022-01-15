package gameplay;

import java.util.ArrayList;
import java.lang.String;
import pickups.Pickup;

/**
 * Inventory
 * <p>Contains an {@code ArrayList} of {@code Pickup} object</p>
 *
 * @author Auckland University of Technology
 * @version 1.0
 */
public class Inventory 
{
	
	private ArrayList<Pickup> items;
	//------------------------------------
	public Inventory()
	{
		this.items = new ArrayList<Pickup>();
	}
	//------------------------------------
	public String toString()
	{	
		String out = "";		
		for(Pickup p : items)
		{
			if(p!=null)
			{
				out+= p+",";
			}
		}
		if(out.isEmpty()) out = "nothing";
		return out;
	}
	//------------------------------------
	public Pickup remove(String id)
	{
		Pickup p = this.select(id);

		this.items.remove(p);			

		return p;
	}
	//------------------------------------
	public Pickup select(String id)
	{
		for(Pickup p : this.items)
		{
			if (p != null && p.getID().equalsIgnoreCase(id))//part of entity
			{
				return p;
			}
		}
		return null;
	}
	//------------------------------------
	public void add(Pickup p)
	{
		this.items.add(p);
	}
	//------------------------------------
	public void remove(Pickup p)
	{
		this.items.remove(p);
	}
	//------------------------------------
	public Pickup[] getItems()
	{
		Pickup[] items = new Pickup[this.items.size()];
		for(int i=0;i<items.length;i++)
		{
			items[i] = this.items.get(i);
		}
		return items;
	}
}