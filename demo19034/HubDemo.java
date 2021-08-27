package demo19034;

import base.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class HubDemo extends Hub {
	private ArrayList<Truck> trucks;
	public HubDemo(Location loc) {
		super(loc);
		trucks  = new ArrayList<>();
	}

	@Override
	public synchronized boolean add(Truck truck)
	{
		if(trucks.size() < getCapacity())
		{
			trucks.add(truck);
			return true;
		}
		return false;
	}

	@Override
	public void remove(Truck truck) {
		if(trucks.size() > 0)
		{
			trucks.remove(truck);
		}
	}

//	@Override
//	public Highway getNextHighway(Hub from,Hub dest)
//	{
//		return this.getHighways().get(0);
//	}
	@Override
	public Highway getNextHighway(Hub from, Hub dest)
	{
		Highway nextHighway = null;
		int min = Integer.MAX_VALUE;

		Map<Hub,Boolean> visited = new HashMap<>();
		for(Hub hub : NetworkDemo.getHubs())
		{
			visited.put(hub,false);
		}
		visited.replace(this,true);
		if(from!=null)
		{
			visited.replace(from,true);
		}
//		Map<Hub,Boolean> copy = deepcopy(visited);

		for(Highway h : getHighways())
		{
			visited.replace(h.getEnd(),true);
			int length = pathLength(dest,h,visited);
			if(length < min)
			{
				min = length;
				nextHighway = h;
			}
			visited.replace(h.getEnd(),false);
		}
		return nextHighway;
	}

	private int pathLength(Hub dest,Highway h,Map<Hub,Boolean> visited) {
		Hub cur = h.getEnd();
		int hwlength = (int) Math.sqrt(h.getStart().getLoc().distSqrd(h.getEnd().getLoc()));
		if (cur.getLoc().getX()==dest.getLoc().getX() && cur.getLoc().getY()==dest.getLoc().getY()) {
			return hwlength;
		}
		int minlength = Integer.MAX_VALUE;
//		Map<Hub,Boolean> copy = deepcopy(visited);
		for (Highway hw : cur.getHighways()) {
			if (!visited.get(hw.getEnd())) {
				visited.replace(hw.getEnd(), true);
				int len = pathLength(dest, hw, visited);
				if (len < minlength) {
					minlength = len;
				}
				visited.replace(hw.getEnd(), false);
			}
		}
		return (minlength == Integer.MAX_VALUE) ? Integer.MAX_VALUE : hwlength + minlength;
	}
//		Map<Hub,Boolean> visited = new HashMap<>();
//		for(Highway h : this.getHighways())
//		{
//			//No self loops allowed in graph
//			if(h.getStart()!=h.getEnd())
//			{
//				visited.put(h.getEnd(),false);
//			}
//		}
//		visited.replace(from,true);	//ignore the node from which the truck came while computing a path
//		visited.replace(parent,true);	//don't visit parent node again
//
//		for(Highway h : getHighways())
//		{
//			if(!Isvisited(h.getEnd()))
//		}


//	private boolean Isvisited(Hub cur,Hub parent,Map<Hub,Boolean> visited)
//	{
//		if(visited.get(cur))
//		{
//			return true;
//		}
//		return Isvisited()




	//Assigns trucks to highways whenever the thread for the hub wakes up
	@Override
	public void processQ(int deltaT)
	{
		//Assigning trucks at this hub to respective highways
		for(Truck truck : trucks)
		{
			Hub destHub = Network.getNearestHub(truck.getDest());
			Highway hw = getNextHighway(truck.getLastHub(),destHub);
//			if(hw==null)
//			{
//				System.out.println("Stuck at " + this.getLoc().toString());
//			}
			if(hw!=null && hw.hasCapacity())
			{
				this.remove(truck);
				hw.add(truck);
				truck.enter(hw);
//				System.out.println("Entered highway : " + hw.getStart().getLoc().toString());
			}
		}
		//Adding trucks to this hub
//		for(Truck t : NetworkDemo.getTrucks())
//		{
//			//Truck at same location as this hub and not inside the hub(Waiting to be taken in the hub)
//			if(t.getLoc().equals(this.getLoc()) && !trucks.contains(t))
//			{
//				//This hub is not the destination hub for the truck
//				if(!this.equals(Network.getNearestHub(t.getDest())))
//				{
//					//Truck was added to this Hub successfully
//					if(this.add(t))
//					{
//						//Remove the truck if it was coming from any highway
//						for(Highway hwy : this.getHighways())
//						{
//							hwy.remove(t);
//						}
//					}
//				}
//				//Remove the truck from the highway if it has reached the destination hub
//				else
//				{
//					for(Highway hwy : getHighways())
//					{
//						hwy.remove(t);
//					}
//				}
//			}
//		}


//		for(Highway hwy : getHighways())
//		{
//			for(Truck t : trucks)
//			{
//				//Truck's location is same as the Hub
//				if(t.getLoc().equals(this.getLoc()))
//				{
//					//Remove truck from highway and add to this hub
//					hwy.remove(t);
//					this.add(t);
//				}
//			}
//		}

	}

	
}
