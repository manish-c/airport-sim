package com.project;

class Passenger
{
    private int passengerID;
    private int serviceTime;
    private int arrivalTime;

    Passenger()
    {
        this(1,1,1);
    }

    Passenger(int customerid, int serviceduration, int arrivaltime)
    {
        passengerID = customerid;
        serviceTime = serviceduration;
        arrivalTime = arrivaltime;
    }

    int getServiceTime()
    {
        return serviceTime;
    }

    int getArrivalTime()
    {
        return arrivalTime;
    }

    int getPassengerID()
    {
        return passengerID;
    }

    public String toString()
    {
        return ""+passengerID+":"+serviceTime+":"+arrivalTime;
    }

}
