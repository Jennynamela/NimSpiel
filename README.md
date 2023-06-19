# NimSpiel

The game of Nim is a strategy game where players take turns removing stones from rows. There are multiple rows of stones, and players alternate taking any number of stones from a single row. The objective is to force the opponent to take the last stone.

## Instructions

To run the game, follow these steps:

1. Make sure you have Java installed on your system.
2. Clone this repository to your local machine.
3. Open the project in your favorite Java IDE.
4. Run the `MySketch` class, which contains the `main` method.

## Game Controls

- Use the arrow keys to navigate through the rows and select the desired row.
- Press the left arrow key to decrease the number of sticks in the selected row.
- Press the right arrow key to increase the number of sticks in the selected row.
- Press the enter key to make a move and see the computer's response.

## Game Rules

- The game starts with a predefined number of sticks in each row.
- Players take turns removing sticks from a chosen row.
- The goal is to avoid being the player to remove the last stick.
- The computer opponent will make optimal moves to try to win.

## Game overview

![NimSpiel](https://github.com/Jennynamela/NimSpiel/blob/main/Screenshot%20(439).png)


## Implementation Details

- The code uses the Processing library to create a graphical user interface.
- The `Nim` class represents the game state and handles the game logic.
- The `Move` class represents a move made by a player.
- The `NimGame` interface defines the game interface and provides utility methods.
- The `draw` method in the `Nim` class is responsible for rendering the game board.

Feel free to explore the code and make any modifications or improvements as needed.

