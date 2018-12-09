package com.project;

import java.util.*;

class AirportSim
{
	static int Totsim = 0;
    private int numStationsCoach, numStationsFirst, passengerQLimit, simTime, avgArrivalTimeCoach, avgServiceRateCoach, avgArrivalTimeFirst, avgServiceRateFirst;

    private int totServed, passengerCount, serviceTimeCoach, serviceTimeFirst;

    private ServiceStation serviceStationCoach; // service Station object
    private ServiceStation serviceStationFirst; // service Station object
    private Random rand;       // Using random function for passenger info

    private boolean arrivalNewCoach, arrivalNewFirst;
    
    // initialize data fields
    AirportSim()
    {
        totServed = 0;
        passengerCount = 0;
    }

    private void inputDetails()
    {
        // Reading inputs

        Scanner input = new Scanner(System.in);

        
        do {
            System.out.print("Enter simulation time (max is 10000): ");
            simTime = input.nextInt();
        } while (simTime > 10000 || simTime < 0);
        do {
            System.out.print("Enter Coach average Arrival time: ");
            avgArrivalTimeCoach = input.nextInt();
        } while (avgArrivalTimeCoach > simTime || avgArrivalTimeCoach <= 0);
        do {
            System.out.print("Enter Coach average Service rate: ");
            avgServiceRateCoach = input.nextInt();
        } while (avgServiceRateCoach > 500 || avgServiceRateCoach < 0);
		do {
            System.out.print("Enter First Class average Arrival time: ");
            avgArrivalTimeFirst = input.nextInt();
        } while (avgArrivalTimeFirst > simTime || avgArrivalTimeFirst <= 0);
        do {
            System.out.print("Enter First Class average Service rate: ");
            avgServiceRateFirst = input.nextInt();
        } while (avgServiceRateFirst > 500 || avgServiceRateFirst < 0);
        do {
            passengerQLimit = 50;
        } while (passengerQLimit > 100 || passengerQLimit < 0);

        numStationsCoach = 3;
        numStationsFirst = 2;
        
        /*
        simTime = 180;
        avgArrivalTimeCoach = 2;
        avgServiceRateCoach = 5;
        avgArrivalTimeFirst = 45;
        avgServiceRateFirst = 5;
        
        passengerQLimit = 50;
        */
        input.close();
        rand = new Random();
    }

    private void getPassengerData()
    {
        // Logic for making the average arrival time from random generator function

        	Double a = (double) ((simTime)/avgArrivalTimeCoach);
        	arrivalNewCoach = ((rand.nextInt((simTime-40))+1) <= a);
        	serviceTimeCoach = rand.nextInt(avgServiceRateCoach)+1;
        	
        	Double b = (double) ((simTime)/avgArrivalTimeFirst);
        	arrivalNewFirst = ((rand.nextInt((simTime-40))+1) <= b);
        	serviceTimeFirst = rand.nextInt(avgServiceRateFirst)+1;
        
        
    }

    private void mainSim()
    {

        // Initialize ServiceStation
        serviceStationCoach = new ServiceStation(numStationsCoach, passengerQLimit, 1);
        serviceStationFirst = new ServiceStation(numStationsFirst, passengerQLimit, 1);

        // Service station simulation loop
        for (int currentTime = 0; currentTime < simTime; currentTime++) {

            System.out.println("---------------------------------------------------------------"); //printing real time simulation
            System.out.println("Time  : " + (currentTime+1));
            


            // Step 1: New passenger arriving at station
            getPassengerData();

            if (arrivalNewFirst) {
            	if(currentTime > simTime-40) {	// Not allowing passengers arriving 40 minutes before total time
            	}
            	else {
                // Passenger details
                passengerCount++;
                System.out.println("\tFirst class Passenger #" + passengerCount + " arrives with service time " + serviceTimeFirst + " min(s).");

                System.out.println("\tFirst class Passenger #" + passengerCount + " waits in the First Passenger queue.");
                serviceStationFirst.addPassengerQ( new Passenger(passengerCount, serviceTimeFirst, currentTime) );
            	}
            }else
            {
                System.out.println("\tNo new First class Passenger!");
        
            }
            
            if (arrivalNewCoach) {
            	if(currentTime > simTime-40) {	// Not allowing passengers arriving 40 minutes before total time
            		
            	}
            	else {
                    // Passenger details
                    passengerCount++;
                    System.out.println("\t Coach Passenger #" + passengerCount + " arrives with service time " + serviceTimeCoach + " min(s).");

                    if(serviceStationFirst.numBusyStation() == 0) {
                    	System.out.println("\tCoach class Passenger #" + passengerCount + " waits in the First class Passenger queue.");
                        serviceStationFirst.addPassengerQ( new Passenger(passengerCount, serviceTimeCoach, currentTime) );
                        	
                    }else if(serviceStationFirst.numBusyStation() > 0)  {
                    System.out.println("\t Coach Passenger #" + passengerCount + " waits in the Coach Passenger queue.");
                    serviceStationCoach.addPassengerQ( new Passenger(passengerCount, serviceTimeCoach, currentTime) );
                    }else
                    	{
                    		System.out.println("\tNo new Coach Passenger!");
            
                    	}
                	}
            }else
            {
                System.out.println("\tNo new Coach Passenger!");
        
            }
            
 
            
            // Step 2: Add to First Stations
            while (serviceStationFirst.numBusyStation() > 0 && serviceStationFirst.getFrontBusyStationQ().getEndEngagedIntTime() == currentTime) {
                Stations stationFirst = serviceStationFirst.delBusyStationQ();
                stationFirst.engagedToFree();
                serviceStationFirst.addFreeStationQ(stationFirst);

                System.out.println("\tFirst class Passenger #" + stationFirst.getPassenger().getPassengerID() + " is checked.");
                System.out.println("\tFirst class Station #" + stationFirst.getPassengerID() + " is available.");
            }
            
            // Step 2: Add to Coach Stations
            while (serviceStationCoach.numBusyStation() > 0 && serviceStationCoach.getFrontBusyStationQ().getEndEngagedIntTime() == currentTime) {
                Stations stationCoach = serviceStationCoach.delBusyStationQ();
                stationCoach.engagedToFree();
                serviceStationCoach.addFreeStationQ(stationCoach);

                System.out.println("\tCoach Passenger #" + stationCoach.getPassenger().getPassengerID() + " is checked.");
                System.out.println("\tCoach Station #" + stationCoach.getPassengerID() + " is available.");
            }
            


            // Step 3: Free First stations to serve Passengers
            while (serviceStationFirst.numFreeStation() > 0 && serviceStationFirst.numWaitingPassenger() > 0) {
                Passenger passengerFirst = serviceStationFirst.delPassengerQ();
                Stations stationFirst = serviceStationFirst.delFreeStationQ();
                stationFirst.freeToEngaged(passengerFirst, currentTime);
                serviceStationFirst.addBusyStationQ(stationFirst);
                totServed++;

                System.out.println("\tFirst class Passenger #" + passengerFirst.getPassengerID() + " gets First class Station #" + stationFirst.getPassengerID() + " for " + passengerFirst.getServiceTime() + " min(s).");
            }
            
            // Step 3: Adding coach passengers to First class station
            while (serviceStationFirst.numFreeStation() > 0 && serviceStationFirst.numWaitingPassenger() > 0) {
                Passenger passengerCoach = serviceStationFirst.delPassengerQ();
                Stations stationFirst = serviceStationFirst.delFreeStationQ();
                stationFirst.freeToEngaged(passengerCoach, currentTime);
                serviceStationCoach.addBusyStationQ(stationFirst);
                totServed++;
                System.out.println("HAPPY");
                System.out.println("\tCoach Passenger #" + passengerCoach.getPassengerID() + " gets First Station #" + stationFirst.getPassengerID() + " for " + passengerCoach.getServiceTime() + " min(s).");
            }
            
            
            // Step 3: Free Coach stations to serve Passengers
            while (serviceStationCoach.numFreeStation() > 0 && serviceStationCoach.numWaitingPassenger() > 0) {
                Passenger passengerCoach = serviceStationCoach.delPassengerQ();
                Stations stationCoach = serviceStationCoach.delFreeStationQ();
                stationCoach.freeToEngaged(passengerCoach, currentTime);
                serviceStationCoach.addBusyStationQ(stationCoach);
                totServed++;

                System.out.println("\tCoach Passenger #" + passengerCoach.getPassengerID() + " gets Coach Station #" + stationCoach.getPassengerID() + " for " + passengerCoach.getServiceTime() + " min(s).");
            }


        } 
        
        
    }

    private void printDetails()
    {
        // printing out simulation details


        System.out.println("\n===============================================================\n");
        System.out.println("\t End of simulation \n\n");
        System.out.println("\t\t# total arrived passengers : " + passengerCount);
        System.out.println("\t\t# Passengers served        : " + totServed);

        
        //Coach
        System.out.println("\n\t Coach Service station details. \n\n");
        if (!serviceStationCoach.emptyFreeStationQ()) {
            while (serviceStationCoach.numFreeStation() > 0) {
                Stations station = serviceStationCoach.delFreeStationQ();
                station.setEndIntTime(simTime, 0);
                station.printDetails();
                	
            }
        } else {
            System.out.println("\t\tNo Service station.\n");
        }
        
        //First
        System.out.println("\n\t First class Service station details. \n\n");
        if (!serviceStationFirst.emptyFreeStationQ()) {
            while (serviceStationFirst.numFreeStation() > 0) {
                Stations station = serviceStationFirst.delFreeStationQ();
                station.setEndIntTime(simTime, 0);
                station.printDetails();
                	
            }
        } else {
            System.out.println("\t\tNo Service station.\n");
        }
        
        
        System.out.println("\t\tDuration of Simulation            	: "+Totsim);
        System.out.println();
        System.out.println("\n===============================================================\n");
        
    }

    //  main method to run simulation 

    public static void main(String[] args)
    {
        AirportSim runAirportSim=new AirportSim();
        runAirportSim.inputDetails();
        runAirportSim.mainSim();
        runAirportSim.printDetails();
    }

}
