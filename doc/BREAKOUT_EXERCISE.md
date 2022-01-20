# Breakout Abstractions Lab Discussion
#### Matt Knox, Edison Ooi, Haris Adnan


## Principle Slogans

* Single Responsibility

* Open Closed



### Block

This superclass's purpose as an abstraction:

A Block encapsulates a Rectangle and some metadata. It has the ability to take damage. 
```java
 public class Block {
     public void takeDamage ()
     // all blocks should respond to taking damage in their own way
 }
```

#### Subclasses (the Open part)

This subclass's high-level behavorial differences from the superclass:

A SmallBlock is a weaker type of block, so it takes more damage when hit. 

```java
 public class SmallBlock extends Block {
     public void takeDamage ()
     // a small block takes more damage than other blocks
 }
```

#### Affect on Game/Level class (the Closed part)

A Block, no matter the type, provides collision to the ball, so the ball will bounce correctly. 


### Power-up

This superclass's purpose as an abstraction:

Define what kind of functionality and properties should exist for all Powerups.

```java
 public class PowerUp {
     public void deploy ()
     // Implements different powerups like increasing length of paddle and spawning more balls.
 }
```

#### Subclasses (the Open part)

This subclass's high-level behavorial differences from the superclass:

This class specifically makes the paddle longer, as opposed to some other arbitrary powerup. 

```java
 public class LengthenPaddle extends PowerUp {
     public void deploy ()
     // this makes the paddle larger 
 }
```

#### Affect on Game/Level class (the Closed part)
Can extend different powerups that are variations of the basic powerups.


### Level

This superclass's purpose as an abstraction:

Provides functionality that all levels should have, for example all levels should be resettable.
```java
 public class Level {
    public void reset() {
      // All levels should be able to be reset back to its original state
    }
 }
```

#### Subclasses (the Open part)

This subclass's high-level behavorial differences from the superclass:

Provides functionality for certain powerups or controls that may not exist in all levels.
```java
 public class ExtremeLevel extends Level {
     public void makePaddlesVisible ()
     // An ExtremeLevel has four paddles, so it has extra code to display all the paddles
 }
```

#### Affect on Game class (the Closed part)

Code implemented with private methods such that code inside the closed part can't be altered. Two basic different types of levels.


### Others?
We can have a separate class for collision handling. 

### Collision

This superclass's purpose as an abstraction:
```java
 public class Collision {
    public static void collide() {
      // To perform collision calculations, blocks need to call this method. 
    }
 }
```

#### Subclasses (the Open part)

This subclass's high-level behavorial differences from the superclass:
```java
 public class SmallBlockCollision extends Collision {
     public static void collide ()
     // A SmallBlock has different collision properties than a Block, so it needs to call this method. 
 }
```

#### Affect on Game class (the Closed part)
Any class can call the methods from these classes to perform collision calculations. 