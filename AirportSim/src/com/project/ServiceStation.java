package com.project;

import java.util.*;
import java.util.Queue;

class CompareStation implements Comparator<Stations>
{
    @Override
    public int compare(Stations o1, Stations o2)
    {
        return o1.getEndEngagedIntTime() - o2.getEndEngagedIntTime();
    }
}

class ServiceStation
{
    // One priority queue
    private PriorityQueue<Stations> busyStationQ;

    // Two FIFO queues
    private Queue<Passenger> passengerQ;
    private Queue<Stations> freeStationQ;

    private int passengerQLimit;

    public ServiceStation()
    {
        this(1,1,1);
    }

    public ServiceStation(int numStations, int customerQlimit, int startStationID)
    {
        passengerQ = new ArrayDeque<Passenger>(customerQlimit);
        freeStationQ = new ArrayDeque<Stations>(numStations);
        busyStationQ = new PriorityQueue<Stations>( numStations, new CompareStation());
        passengerQLimit = customerQlimit;

        for (int i = 0; i < numStations; i++) {
            addFreeStationQ( new Stations(startStationID++) );
        }
    }

    public Stations delFreeStationQ()
    {
        // delete and return a free station
        return freeStationQ.poll();
    }

    public Stations delBusyStationQ()
    {
        // delete and return a busy station
        return busyStationQ.poll();
    }

    public Passenger delPassengerQ()
    {
        // delete and return a customer
        return passengerQ.poll();
    }

    public void addFreeStationQ(Stations teller)
    {
        // add a free Station
        freeStationQ.add(teller);
    }

    public void addBusyStationQ(Stations teller)
    {
        // add a busy Station
        busyStationQ.add(teller);
    }

    public void addPassengerQ(Passenger customer)
    {
        // add a passenger
        passengerQ.add(customer);
    }

    public boolean emptyFreeStationQ()
    {
        // is freeStationQ empty?
        return freeStationQ.isEmpty();
    }

    public boolean emptyBusyStationQ()
    {
        // is busyStationQ empty?
        return busyStationQ.isEmpty();
    }

    public boolean emptyPassengerQ()
    {
        // is passengerQ empty?
        return passengerQ.isEmpty();
    }

    public int numFreeStation()
    {
        // number of free station
        return freeStationQ.size();
    }

    public int numBusyStation()
    {
        // number of busy stations
        return busyStationQ.size();
    }

    public int numWaitingPassenger()
    {
        // number of passenger
        return passengerQ.size();
    }

    public Stations getFrontBusyStationQ()
    {
        return busyStationQ.peek();
    }

    public boolean isPassengerQTooLong()
    {
        // is passengerQ too long?
        return passengerQ.size() == passengerQLimit;
    }

}
