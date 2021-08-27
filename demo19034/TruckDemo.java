package demo19034;

import base.*;



class TruckDemo extends Truck
{
	@Override
	public void update(int deltaT)
	{
//		System.out.println("Truck : " + this.getLoc().toString());
		time += deltaT;
//		System.out.println("time : " + time);

		//After start time
		if(time >= this.getStartTime())
		{
//			System.out.println("Start time elapsed");
			int d = deltaT*this.speedatroads;
			//At a road from src to first hub
			if(lastHub==null)
			{
//				System.out.println("Going from src location to nearest hub");
				Hub srchub = Network.getNearestHub(this.getSource());
				if(!IsSameLoc(this.getLoc(),srchub.getLoc()))
				{
					//We can't jump beyond the road so we stop at the srchub
					if(Math.sqrt(this.getLoc().distSqrd(srchub.getLoc())) < d)
					{
//						System.out.println("At first hub loc");
						this.setLoc(srchub.getLoc());
					}
					else
					{
						double roadlen = Math.sqrt(this.getSource().distSqrd(srchub.getLoc()));
						double cos = (srchub.getLoc().getX() - this.getSource().getX()) / roadlen;
						double sin = (srchub.getLoc().getY() - this.getSource().getY()) / roadlen;
						this.setLoc(new Location(this.getLoc().getX() + (int)(d*cos),this.getLoc().getY() + (int)(d*sin)));

//
//						System.out.println("truck not at first hub of route");
//						int slope = (srchub.getY()-this.getSource().getY())/(srchub.getX()-this.getSource().getX());
//						int dx = (int) (d*Math.cos(Math.atan(slope)));
//						int dy = (int) (d*Math.sin(Math.atan(slope)));
//						this.setLoc(new Location(this.getLoc().getX()+dx,this.getLoc().getY()+dy));
//						System.out.println("New Location : " + this.getLoc().toString());
					}
				}
				else
				{
					//Try to add truck to the srchub
					if(!AtHub)
					{
//						System.out.println("Trying to be Added to first hub");
						if(srchub.add(this))
						{
//							System.out.println("Added to srchub");
							AtHub = true;
							currentHub = srchub;
						}
					}
				}
				return;
			}
			Hub desthub = Network.getNearestHub(this.getDest());
			//Truck at destination hub
			if(IsSameLoc(this.getLoc(),desthub.getLoc()))
			{
//				System.out.println("At dest hub");
				leftfordest = true;
				currenthwy.remove(this);
				currenthwy = null;
				AtHighway = false;
//				if(Math.sqrt(this.getLoc().distSqrd(this.getDest())) < d)
//				{
//					this.setLoc(this.getDest());	//Reached the destination
//					reacheddest = true;
//				}
//				else
//				{
//					int slope = (this.getDest().getY()-desthub.getY())/(this.getDest().getX()-desthub.getX());
//					int dx = (int) (d*Math.cos(Math.atan(slope)));
//					int dy = (int) (d*Math.sin(Math.atan(slope)));
//					this.setLoc(new Location(this.getLoc().getX()+dx,this.getLoc().getY()+dy));
//					leftfordest = true;	//indicates that we are on the road to destination
//				}
//				return;
			}
			if(leftfordest)
			{
				if(IsSameLoc(this.getLoc(),this.getDest()))
				{
//					System.out.println("Reached the destination-1");
					reachedDestloc = true;
				}
				if(!reachedDestloc)
				{
					if(Math.sqrt(this.getLoc().distSqrd(this.getDest())) < d)
					{
						this.setLoc(this.getDest());	//Reached the destination
						reachedDestloc = true;
//						System.out.println("Reached the destination-2");
					}
					else
					{
//						System.out.println("Moving forward to destination");
						double roadlen = Math.sqrt(this.getDest().distSqrd(desthub.getLoc()));
						double cos = (this.getDest().getX() - desthub.getLoc().getX()) / roadlen;
						double sin = (this.getDest().getY() - desthub.getLoc().getY()) / roadlen;
						this.setLoc(new Location(this.getLoc().getX() + (int)(d*cos),this.getLoc().getY() + (int)(d*sin)));

//						int slope = (this.getDest().getY()-desthub.getY())/(this.getDest().getX()-desthub.getX());
//						int dx = (int) (d*Math.cos(Math.atan(slope)));
//						int dy = (int) (d*Math.sin(Math.atan(slope)));
//						this.setLoc(new Location(this.getLoc().getX()+dx,this.getLoc().getY()+dy));
////						leftfordest = true;	//indicates that we are on the road to destination
					}
				}
				return;
			}
			if(AtHighway)
			{
				Location hwyendpt = currenthwy.getEnd().getLoc();
				if(IsSameLoc(this.getLoc(),hwyendpt))
				{
					if(!AtHub)
					{
						if(currenthwy.getEnd().add(this))
						{
//							System.out.println("Entered hub from highway");
							currenthwy.remove(this);
							AtHub = true;
							currentHub = currenthwy.getEnd();
							AtHighway = false;
							currenthwy = null;
						}
					}
				}
				else
				{
//					System.out.println("Truck at highway");
					int dist = deltaT*currenthwy.getMaxSpeed();
					if(Math.sqrt(this.getLoc().distSqrd(hwyendpt)) < dist)
					{
						this.setLoc(hwyendpt);
					}
					else
					{
//						System.out.println("Moving on highway");
						double length = Math.sqrt(currenthwy.getStart().getLoc().distSqrd(currenthwy.getEnd().getLoc()));
						double cos = (currenthwy.getEnd().getLoc().getX() - currenthwy.getStart().getLoc().getX()) / length;
						double sin = (currenthwy.getEnd().getLoc().getY() - currenthwy.getStart().getLoc().getY()) / length;
						this.setLoc(new Location(this.getLoc().getX() + (int)(dist*cos),this.getLoc().getY() + (int)(dist*sin)));
						//						int slope = (hwyendpt.getY()-hwystartpt.getY())/(hwyendpt.getX()-hwystartpt.getX());
//						int dx = (int) (d*Math.cos(Math.atan(slope)));
//						int dy = (int) (d*Math.sin(Math.atan(slope)));
//						this.setLoc(new Location(this.getLoc().getX()+dx,this.getLoc().getY()+dy));
					}
				}
			}

		}
	}

	@Override
	public Hub getLastHub()
	{
		return lastHub;
	}

	@Override
	public String getTruckName()
	{
		return "Truck19034";
	}
	@Override
	public void enter(Highway hwy)
	{
		lastHub = hwy.getStart();	//truck has left the start point of the highway
		currenthwy = hwy;
		currentHub = null;
		AtHub = false;
		AtHighway = true;
	}

	private boolean IsSameLoc(Location l1,Location l2)
	{
		return l1.getX()==l2.getX() && l1.getY()==l2.getY();
	}

	private Hub lastHub = null;
	private Highway currenthwy = null;
	private int time = 0;
	private Hub currentHub = null;
	private boolean AtHub = false;
	private boolean AtHighway = false;
	private boolean reachedDestloc = false;
//	private boolean leftstartloc = false;
//	private boolean waiting = false;
	private boolean leftfordest=false;
	private int speedatroads = 2;	//20 pixels/secs
}
