# Project Title

Implementing a queue data type. Using it to simulate the evolution of waiting queues by invoking several instances of the queue data type.

## Description

Simulating a check-in operation for an airline company at an airport terminal.

Assumtions:
* We have two classes of service: first class and coach. Each class of service has a dedicated queue.
* There are two service stations for first class and three for coach.
* Each service station takes passengers from the corresponding queue; but if a first class service station is free and the queue for coach is not empty then the service station serves passengers from the coach queue.
* Passenger arrival times are random, but subject to average arrival times; for example, we get a first class passenger every five minutes and a coach passenger every two minutes on average. Actual arrival times are random.
* Passenger service times are also random, but subject to average service times; for example, first class passengers usually require six minutes of service and coach passengers get on average 2 minutes of service. These are average times; actual times vary. All times are measured in minutes.
* The simulation starts with empty waiting queues and free service stations. At some point in time (usually 40 minutes prior to departure time) the company closes the queues (no more passengers are admitted); then the simulation ends when all the queues are empty and all the service stations are free.

## Running the tests

Five inputs will be needed to run the test:
* Simulation time.
* Coach average arrival rate
* Coach average service rate.
* First class average arrival rate
* First class average service rate.

### Outputs

* The duration of the simulation (which may be longer than the input parameter, as when checkin closes, there may be passengers in the waiting queues and service stations).
* The maximum length of the queue for each queue.
* The average and maximum waiting time for each queue.
* The rate of occupancy of each service station (percentage of time each station was busy).
* Show the real-time evolution of the queues during the run-time simulation.

#### Real-time simulation

At each instance of time, the changes for First class and Coach queues are shown

```
---------------------------------------------------------------
Time : 1
No new First class Passenger!
No new Coach Passenger!
---------------------------------------------------------------
Time : 2
No new First class Passenger!
No new Coach Passenger!
---------------------------------------------------------------
```

When a new passenger arrives and checks:
```
---------------------------------------------------------------
Time : 11
First class Passenger #1 arrives with service time 1 min(s).
First class Passenger #1 waits in the First Passenger queue.
No new Coach Passenger!
First class Passenger #1 gets First class Station #1 for 1 min(s).
---------------------------------------------------------------
Time : 12
No new First class Passenger!
No new Coach Passenger!
First class Passenger #1 is checked.
First class Station #1 is available.
---------------------------------------------------------------
```

When Coach passenger arrives and coach queues is full while First class queue is empty:
* Each service station takes passengers from the corresponding queue; but if a first class service station is free and the queue for coach is not empty then the service station serves passengers from the coach queue.
```
---------------------------------------------------------------
Time : 196
No new First class Passenger!
Coach Passenger #13 arrives with service time 1 min(s).
Coach class Passenger #13 waits in the First class Passenger queue.
First class Passenger #13 gets First class Station #1 for 1 min(s).
---------------------------------------------------------------
```

In this particular example Passenger #13 is a coach passenger, but due to busy queue and First class station availability it is moved First class.
```
* Coach Passenger #13 arrives with service time 1 min(s).
  * It’s a coach passenger
* Coach class Passenger #13 waits in the First class Passenger queue.
  * It is moved to First class queue
* First class Passenger #13 gets First class Station #1 for 1 min(s).
  * Now this passenger is assigned First class station
```

