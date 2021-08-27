package demo19034;

import base.Highway;
import base.Truck;

import java.util.ArrayList;

class HighwayDemo extends Highway {

	private ArrayList<Truck> truckList = new ArrayList<>();
	public HighwayDemo() {}

	@Override
	public synchronized boolean hasCapacity() {

		return truckList.size() < this.getCapacity();	//no. of trucks on highway less than the capacity of the highway
	}

	@Override
	public  synchronized boolean add(Truck truck) {
		if(this.hasCapacity())
		{
			truckList.add(truck);
			return true;	//we were able to add the truck to the highway
		}
		return false;
	}

	@Override
	public synchronized void remove(Truck truck) {
		if(truckList.size() > 0)
		{
			truckList.remove(truck);
		}
	}

}
