# Cell Society Design Plan
### Team Number: 5
### Names: Matt Knox, Edison Ooi, Haris Adnan


## USE THIS TO FILL OUT LATER FIELDS

* Classes: Main, Simulation, XMLReader, Cell, Grid, SimulationType (extends Game of Life, Spreading of Fire, etc.)

* Data in XML File: Number of rows, number of columns, 
type of simulation (get from an enum), starting configuration, csv section contained with 0s and 1s,
title of configuration, author of configuration, description of simulation, specific parameters to this simulation type (probability of propogation etc.)

* User Interface: 
  * Presses buttons to: 
    * start/pause simulation
    * speed up/slow down simulation
    * step forward
    * load new config file
    * save current state to config file

## Introduction

We are planning to design a simulation platform that hosts many different 2D Cellular Automata (CA) models, such 
as Game of Life, Spreading of Fire, etc. This simulation platform will be flexible enough to allow different CA 
models to be easily implemented. Users can interface with this program via XML config files and using the mouse/keyboard during runtime. 


## Overview

## User Interface

The screen of this program is split into two sections: one on the left and one on the right.
The left side contains the 2D grid of cells that shows the simulation.
The right side contains data about the running simulation and buttons for the user to press. 
The data shown on the right side displays the following:
* Type
* Name
* Author
* Description
* Colors
* Parameters

There are two rows of buttons the user can press. 
The first row contains buttons and information about the speed of the simulation,
and the second row contains buttons to save and load simulations. 

While playing, the first row contains:
* Button: Pause
* Button: Slow down
* Information: current speed
* Button: Speed up

While paused, the first row contains:
* Button: Play
* Button: Step (steps through the simulation by one cycle)

When __load__ is pressed, a system dialogue comes up, where the user can select a new simulation file to load. 

When __save__ is pressed, the right side is replaced by a series of text fields for the user to enter information about the file to save:
* Name
* Author
* Description
* Button: Done

The user presses the __done__ button when they are done. 

A summary of the UI is shown here:

![This is cool, too bad you can't see it](images/UserInterface.png "An alternate design")


## Configuration File Format


## Design Details

Here is a graphical look at my design:

![This is cool, too bad you can't see it](images/online-shopping-uml-example.png "An initial UI")

made from [a tool that generates UML from existing code](http://staruml.io/).


## Use Cases



## Design Considerations

#### Design Issue #1: Which class is in charge of telling cells to update

 * Alernative #1
   * The overarching Simulation class, which will likely contain the main step() function for the entire simulation

 * Alernative #2
   * The Grid class, which contains the individual cells, tells the cells to update. 

 * Trade-offs
   * Using the simulation class for this will allow us to more easily use methods inside the same class to tell eache cell how to update.
   Having this done by the grid class makes the grid smarter but this may interfere with the algorithm we use.

#### Design Issue #2: What functionality should we implement and define in our "SimulationType" abstract class?

 * Alernative #1
   * Have an abstract update() function that takes a cell as a parameter and invokes the proper functions on that cell
   given the rules of this simulation type

 * Alernative #2
   * Each child class has its own update() function (possible with different parameter types).

 * Trade-offs
   * Alternative #1 allows for better inheritance heirarchy, 
   whereas alternative #2 allows for greater flexibility. 



## Team Responsibilities

 * Team Member #1

 * Team Member #2

 * Team Member #3


#### Proposed Schedule
