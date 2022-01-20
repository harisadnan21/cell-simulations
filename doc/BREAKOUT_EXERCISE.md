# Breakout Abstractions Lab Discussion
#### NAMEs


## Princple Slogans

* Single Responsibility

* Open Closed



### Block

This superclass's purpose as an abstraction:
```java
 public class Block {
     public int something ()
     // no implementation, just a comment about its purpose in the abstraction 
 }
```

#### Subclasses (the Open part)

This subclass's high-level behavorial differences from the superclass:
```java
 public class X extends Block {
     public int something ()
     // no implementation, just a comment about what it does differently 
 }
```

#### Affect on Game/Level class (the Closed part)



### Power-up

This superclass's purpose as an abstraction:
```java
 public class PowerUp {
     public int something ()
     // no implementation, just a comment about its purpose in the abstraction 
 }
```

#### Subclasses (the Open part)

This subclass's high-level behavorial differences from the superclass:
```java
 public class X extends PowerUp {
     public int something ()
     // no implementation, just a comment about what it does differently 
 }
```

#### Affect on Game/Level class (the Closed part)



### Level

This superclass's purpose as an abstraction:
```java
 public class Level {
     public int something ()
     // no implementation, just a comment about its purpose in the abstraction 
 }
```

#### Subclasses (the Open part)

This subclass's high-level behavorial differences from the superclass:
```java
 public class X extends Level {
     public int something ()
     // no implementation, just a comment about what it does differently 
 }
```

#### Affect on Game class (the Closed part)



### Others?
