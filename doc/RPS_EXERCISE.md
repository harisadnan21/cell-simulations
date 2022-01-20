# Rock Paper Scissors Lab Discussion
#### Matt Knox (mjk63), Edison Ooi (ezo), Haris Adnan(ha109)


### High Level Design Goals




### CRC Card Classes

This class's purpose or value is to represent a playable item in RPS:
| RPSClass                             |   Item, Referee  |
|--------------------------------------|------|
| makeGraph(List<Item>)                |  |
| updateGraph(Item)                    |  |

| Item                                 |      |
|--------------------------------------|------|
| boolean beats(Item)                  ||
| void render(Group)                   ||

| Player          |      |
|-----------------|------|
| Item makePlay() | Item |
| void winTurn()  |      |
| void loseTurn() |      |

| Referee                              |        |
|--------------------------------------|--------|
| void playRound(List<player> players) | Player |
| void startGame(int numPlayers)       ||
| void stopGame()                      ||



This class's purpose or value is to represent a playable item in RPS:
```java
public class Item {
     // returns whether or not the given items are available to order
     public boolean beats (Item opponent);
     // sums the price of all the given items
     public void render (Group root);
}
 ```

This class's purpose or value is to represent one player in the RPS game:
```java
public class Player {
     // Prompts user for an item choice and then plays that item for a turn
     public void makePlay();
	 // Provides functionality for if a player wins a turn
     public void winTurn();
      // Provides functionality for if a player loses a turn
      public void loseTurn();
       
 }
```

This class's purpose or value is to represent a referee in the RPS game
```java
public class Referee {
    // Dictates every set of players to play an opponent
    public void playRound();
    // Begins the game
    public void startGame(int numPlayers);
    // Ends the game
    public void stopGame();
}
```


### Use Cases

* A new game is started with five players, their scores are reset to 0.
 ```java
//what if we just did this?
ref.startGame(5);

 List<Player> players = new ArrayList<>();
 for(int i = 0; i < numPlayers; i++) {
   players.add(new Player()); //We assume the player constructor initializes their score to 0
    }
 ```

* A player chooses his RPS "weapon" with which he wants to play for this round.
 ```java
 Player player = new Player();
Item playerChoice = player.makePlay();
 ```

* Given three players' choices, one player wins the round, and their scores are updated.
 ```java
Referee.playRound();
//winTurn updates score
Player.winTurn();

 ```

* A new choice is added to an existing game and its relationship to all the other choices is updated.
 ```java
  List<Item> itemsThatThisItemBeats = new ArrayList<>();
  itemsThatThisItemBeats.add(Item.SCISSORS);
 Item item = new Item("Rock", itemsThatThisItemBeats);
 ```

* A new game is added to the system, with its own relationships for its all its "weapons".
 ```java
 updateGraph(Item newitem);

 ```
