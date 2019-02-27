This was a school project by me (Olof Gren) and Fredrik Svahn for the course
DD1349 Projektuppgift i introduktion till datalogi.

# Project Description

## What is the project about?
    
Our project is a top down roguelike and turn-based, similar to another game called [DROD](http://caravelgames.com/Articles/Games_2/TSS.html). The game will be 2D.

### Game rules

The game map is a grid and you can move in eight directions. You have a sword you can also point in eight directions. There will be enemies on the map that move towards you every time you make a move. If the sword touches an enemy, the enemy takes damage. If the enemy touches you, you die. The goal of the game is to kill all the enemies on the map and continue to the next map. 

### Controls

You use these buttons to move your character:

7 8 9

u i o 

j k l 

And q, w to rotate the sword counter-clockwise and clockwise.
    
## How do i install and use the project?
  
Run this code from the root directory of the project:

```
javac src/*.java
java -classpath "./src" Game
```
    
## What libraries, frameworks or methods are you using?

We are using Java's built-in package Swing, for rendering the game. For the tests, we use JUnit 4.12.
