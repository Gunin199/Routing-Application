package demo19034;
import base.*;
import java.lang.Math;
import java.util.ArrayList;

public class NetworkDemo extends Network
{
    private static ArrayList<Hub> hubs;
    private static ArrayList<Highway> hwys;
    private static ArrayList<Truck> trucks;

    public NetworkDemo()
    {
        super();
        hubs = new ArrayList<>();
        hwys = new ArrayList<>();
        trucks = new ArrayList<>();
    }

    public void add(Hub hub)
    {
        hubs.add(hub);
    }

    @Override
    public void add(Highway hwy){
        hwys.add(hwy);
    }

    @Override
    public void add(Truck truck) {
        trucks.add(truck);
    }

    @Override
    public void start() {
        for(Hub hub : hubs)
        {
            hub.start();
        }
        for(Truck truck : trucks)
        {
            truck.start();
        }
    }

    @Override
    public void redisplay(Display disp) {
        for(Hub hub:hubs)
        {
            hub.draw(disp);
        }
        for(Highway hwy:hwys)
        {
            hwy.draw(disp);
        }
        for(Truck truck:trucks)
        {
            truck.draw(disp);
        }
    }

    protected Hub findNearestHubForLoc(Location loc) {
        double min = Double.MAX_VALUE;
        Hub minDistHub = null;
        for (Hub hub : hubs)
        {
            double dist = Math.sqrt(loc.distSqrd(hub.getLoc()));
            if ( dist < min) {
                min = dist;
                minDistHub = hub;
            }
        }
        return minDistHub;
    }

    public static ArrayList<Hub> getHubs() {
        return hubs;
    }

    public static ArrayList<Truck> getTrucks() {
        return trucks;
    }

    public static ArrayList<Highway> getHwys() {
        return hwys;
    }
}
