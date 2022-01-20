# Cell Society Design Lab Discussion
#### Names and NetIDs


### Simulations

* Commonalities
  * Behavior of each cell is based on its neighbors
  * All have a 2D grid format with a width and height in units
  * Always at some discrete state
  * Don't take input while simulation is running

* Variations
  * Some are probabilistic and some are deterministic
  * Algorithm that determines perpetuation
  * Dealing with edge cases
  * Implementations can utilize different graphics


### Discussion Questions

* How does a Cell know what rules to apply for its simulation?
  * Based on the given algorithm, it looks at each neighbor and performs some function
  on whether or not the neighbor is alive or dead
  * Maintain a current state and use it to calculate next state

* How does a Cell know about its neighbors?
  * Cell can maintain pointers to its 8 neighbors and call functionality
  on each. Cells will be stored in a 2D array.

* How can a Cell update itself without affecting its neighbors update?
  * Cells will update based on neighbor's current state, but its update
  will be saved into next state.

* What behaviors does the Grid itself have?
  * It could tell each cell to update
  * It could control its own simulation (speed, stop, play, etc)

* How can a Grid update all the Cells it contains?
  * Iterate its 2D array and call cell.update() on all of its cells

* What information about a simulation needs to be in the configuration file?
  * Number of rows and columns, which cells are alive at the beginning,
  type of algorithm/simulation to be run

* How is configuration information used to set up a simulation?
  * We instantiate a certain class to perform an algorithm based on
  an enumerated simulation option given in the configuration file, and
  all the other information can be used to set up the initial state.

* How is the graphical view of the simulation updated after all the cells have been updated?
  * We can render a grid of JavaFX rectangles and update its opacity when
  new state is calculated



### Alternate Designs

#### Design Idea #1

* Data Structure #1 and File Format #1

* Data Structure #2 and File Format #2


#### Design Idea #2

* Data Structure #1 and File Format #1

* Data Structure #2 and File Format #2



### High Level Design Goals



### CRC Card Classes

This class's purpose or value is to represent a customer's order:
![Order Class CRC Card](images/order_crc_card.png "Order Class")


This class's purpose or value is to represent a customer's order:

|Order| |
|---|---|
|boolean isInStock(OrderLine)         |OrderLine|
|double getTotalPrice(OrderLine)      |Customer|
|boolean isValidPayment (Customer)    | |
|void deliverTo (OrderLine, Customer) | |


This class's purpose or value is to represent a customer's order:
```java
public class Order {
     // returns whether or not the given items are available to order
     public boolean isInStock (OrderLine items)
     // sums the price of all the given items
     public double getTotalPrice (OrderLine items)
     // returns whether or not the customer's payment is valid
     public boolean isValidPayment (Customer customer)
     // dispatches the items to be ordered to the customer's selected address
     public void deliverTo (OrderLine items, Customer customer)
 }
 ```


This class's purpose or value is to manage something:
```java
public class Something {
     // sums the numbers in the given data
     public int getTotal (Collection<Integer> data)
	 // creates an order from the given data
     public Order makeOrder (String structuredData)
 }
```


### Use Cases

* Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)
```java
 Something thing = new Something();
 Order o = thing.makeOrder("coffee,large,black");
 o.update(13);
```

* Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)
```java
 Something thing = new Something();
 Order o = thing.makeOrder("coffee,large,black");
 o.update(13);
```

* Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically
```java
 Something thing = new Something();
 Order o = thing.makeOrder("coffee,large,black");
 o.update(13);
```

* Set a simulation parameter: set the value of a parameter, probCatch, for a simulation, Fire, based on the value given in a data file
```java
 Something thing = new Something();
 Order o = thing.makeOrder("coffee,large,black");
 o.update(13);
```

* Switch simulations: load a new simulation from a data file, replacing the current running simulation with the newly loaded one
```java
 Something thing = new Something();
 Order o = thing.makeOrder("coffee,large,black");
 o.update(13);
```