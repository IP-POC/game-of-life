# Game of Life Standalone application
This console application is an implementation of Conway’s game of life.

## Background
The playing field for Conway’s game of life consists of a two dimensional grid of 
cells. Each cell is identified as either alive or dead.
The application is calculating the next state of the playing field given any initial grid 
state. To find the next state, it follows these rules: 
1. Any live cell with fewer than two live neighbors dies, as if caused by underN
population. 
2. Any live cell with more than three live neighbors dies, as if by overcrowding. 
3. Any live cell with two or three live neighbors lives on to the next generation. 
4. Any dead cell with exactly three live neighbors becomes a live cell. 
5. A cell’s neighbors are those cells which are horizontally, vertically or 
diagonally adjacent. Most cells will 

## Features
  * Loop through states: updated state becomes initial state and recalculate 
  * Support arbitrarily sized grids setup from input file. The 'O' character represents live cell. And any other character represents dead cell.

## Running the application
 To run this application you need to install Java SE Runtime Environment 7 or newer. To build this project you need also to install Apache Maven. You can find instruction for Apache Maven installation here [https://maven.apache.org/install.html]. 
 Clone this repository.  
 Run "mvn package" in the project root directory to create the game-of-life console application.  
 Then run this application directly by name:  
```
     > java -jar target/game-of-life-1.0.jar -f [file]
```
e.g.
```
     > java -jar target/game-of-life-1.0.jar -f InitGridState.config
```
  Running application without a parameter will show you a help information.
  
  If you don't provide the file name then application will assume default 8x6 initial grid below.
```   
 ......O.
 OOO...O.
 ......O.
 ........
 ...OO...
 ...OO...
``` 
 Note: The 'O' character represents live cell. And '.' character represents dead cell.

## Running unit tests for the application
This application has a good unit tests coverage. Run "mvn test" in the project root directory to execute the entire unit tests in application.
