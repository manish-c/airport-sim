package com.project;

class Stations
{
	
    private int startTime, endTime, stationID;
    
    private Passenger currentPassenger;

    private int totFreeTime, totBusyTime, totPassenger;

    Stations()
    {
        this(1);
    }

    Stations(int stationId)
    {
        stationID = stationId;
    }

    int getPassengerID()
    {
        return stationID;
    }

    Passenger getPassenger()
    {
        return currentPassenger;
    }

    int getEndEngagedIntTime()
    {
        // return end time of busy interval
        return endTime;
    }

    void freeToEngaged (Passenger currentCustomer, int currentTime)
    {
        // To switch from free interval to busy interval
        totFreeTime += (currentTime - startTime);
        startTime = currentTime;
        endTime = startTime + currentCustomer.getServiceTime();
        this.currentPassenger = currentCustomer;
        totPassenger++;
    }

    Passenger engagedToFree ()
    {
        // To switch from busy interval to free interval
        totBusyTime += (endTime - startTime);
        startTime = endTime;
        return currentPassenger;
    }

    void setEndIntTime (int endsimulationtime, int intervalType)
    {
        endTime = endsimulationtime;

        if (intervalType == 0) {
            totFreeTime += endTime - startTime;
        } else {
            totBusyTime += endTime - startTime;
        }
    }

    void printDetails ()
    {

        System.out.println("\t\tStation ID                	: "+stationID);
        System.out.println("\t\tMaximum length of Queue	 	: "+totPassenger);
        System.out.println("\t\tMaximum waiting time     	: "+totFreeTime);
        int total = totFreeTime + totBusyTime;
        System.out.println("\t\tTotal duration of simulation	: " + total );

        if (totPassenger > 0) {
            System.out.format("\t\tAverage Waiting time  		: %.2f\n",
                    (totBusyTime*1.0)/totPassenger);
        }
        int rate = (totBusyTime*100)/total;
        System.out.println("\t\tRate of occupancy		: " + rate + "%");
        
        
        
        if (AirportSim.Totsim < total) {
        	AirportSim.Totsim = total;
        	}
        
        
        System.out.println();
    }
    
 
    @Override
    public String toString()
    {
        return "Stations:"+stationID+":"+startTime+"-"+endTime+":Passenger:"+currentPassenger;
    }

}

