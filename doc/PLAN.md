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

* see the simulation's descriptive information (or make it easily accessible, like in a dialog box created by pressing an "About" button):
its type, name, author, description, state colors, (default or set for this run) and parameter values (if any)
* animate a simulation from its initial state indefinitely until they choose to stop it, displaying the current states of the cells in the grid with different colors
* pause and resume the simulation, as well as step forward through it
* speed up or slow down the simulation's animation rate
* load a new configuration file, which stops the current simulation and starts the new one
NOTE, the app's size should not change based on the size of the grid to be displayed (i.e., the display size of an individual cell should be calculated to fit within the app's fixed screen size)
* save the current state of the simulation as an XML configuration file that can be loaded in as a simulation starting configuration (i.e., in the same format as is read in)
NOTE, this requires prompting the user with fields for entering or changing information such as the title, author, and description

Here is our amazing UI:

![This is cool, too bad you can't see it](images/29-sketched-ui-wireframe.jpg "An alternate design")

taken from [Brilliant Examples of Sketched UI Wireframes and Mock-Ups](https://onextrapixel.com/40-brilliant-examples-of-sketched-ui-wireframes-and-mock-ups/).


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
