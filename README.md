Cell Society
====

This project implements a cellular automata simulator

<img width="494" alt="Screen Shot 2022-08-17 at 6 03 32 PM" src="https://user-images.githubusercontent.com/91027112/185251591-d5f19b0b-4ce7-4a9b-bdcd-d472ecb685c2.png">

![Cell automata](https://user-images.githubusercontent.com/91027112/186397000-d1c18726-cac3-4f78-bdfa-2241635740bf.gif)



### Timeline

Start Date: January 23, 2022

Finish Date: February 6, 2022

Hours Spent: 70

### Resources Used
* FallingSand.properties

* GameOfLife.properties

* Percolation.properties

* SchellingSegregation.properties

* SimulationValues.properties

* SpreadingOfFire.properties

* WaTor.properties



### Running the Program

Main class: CellularAutomata

Data files needed: 
* Simulation configuration files located in data/simulation_configs
* SimulationValues.properties in src/main/resources/view
* One properties file per simulation type in src/main/resources/view

Features implemented:

The project can run 5 different types of simulations:

* FallingSand
* Gameoflife
* Percolation
* SchelingSegregatin
* Spreading of Fire
* WaTor

The GUI contains buttons that allow the simulation to Stop, play, run faster, or slow down.

### Notes/Assumptions

Assumptions or Simplifications:
* Config file must be specified in code, no solution for picking at runtime unfortunately
* Falling sand ignores neighborhoods
* Configuration files must include all tags asked for by simulation

Interesting data files:

Known Bugs:
* The GridView does not fit perfectly into the given screen. 
* Speed up and slow down buttons do not work

Noteworthy Features: 
* Cell configurations can be generated from the XML using a given configuration,
randomly, or from given probabilities for each state.

* Neighborhoods can be specified in the configuration file as any combination of
neighbors and non-neighbors around a reference cell

