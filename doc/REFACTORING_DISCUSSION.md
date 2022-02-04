# Refactoring Lab Discussion
#### NAMES: Matt, Edison, Haris
#### TEAM 5


## Principles

### Current Abstractions

#### Abstraction #1: CellAutomataAlgorithm
* Open/Close
  * Extensions are particular algorithms, but none of them get to decide how to lay out their initial configurations

* Liskov Substution
  * All subclasses implement methods to run the algorithm, which is a core feature of updating the grid and cells

#### Abstraction #2: CellState
* Open/Close
  * Extensions represent enums of states for particular algorithms

* Liskov Substution
  * As long as type is correct, we can get an array of all the enum values using .values() and iterate over them to assign
  states to cells


### New Abstractions

#### Abstraction #1: Cell
* Open/Close
  * All cells will maintain some form of state and must have a function to update itself, however subclasses may
    store its own additional values (such as a Shark or Fish for WaTor)

* Liskov Substution
  * Soon to be: SharkCell, FishCell, which will still be able to update themselves and return a state which is an instance
    of CellState


## Issues in Current Code

### GridView
* Assigning a color to a cell depends on the CellState of the cell. 
Currently, GridView has a series of _instanceof_ checks 
to determine the correct color. 



## Refactoring Plan

* What are the code's biggest issues?
  * Multiple methods within _WaTor_ and _SchellingSegregation_ 
  are far too long. 
  * Code within addNeighbors is far too long. 

* Which issues are easy to fix and which are hard?
  * The methods within _WaTor_ and _SchellingSegregation_
  should be easy to extract to separate methods. 
  * The methods within the method _addNeighbors_ is far too long. 

* What are good ways to implement the changes "in place"?
  * Refactoring these methods should not change any additional code. 


## Refactoring Work

* Issue chosen: Fix and Alternatives

Issue: Frame rate should be a constant, and before it was an int that could be changed. We solved this problem by calling the update method after
a changeable number of frames pass by. The Frame rate is now constant

* Issue chosen: Fix and Alternatives

