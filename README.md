#  🥢

A desktop version of the hand game **Chopsticks**, built for fun in Java Swing. Play against a friend or against a computer opponent that uses minimax search.

<!-- Add a screenshot here: drag an image into this file on GitHub, or save it in the repo and use ![Gameplay](screenshot.png) -->

## How to play

- Each player starts with **1 finger on each hand**.
- On your turn, tap one of the opponent's hands with one of yours. Their hand gains as many fingers as your tapping hand has.
- A hand that reaches **exactly 5** is out. Totals above 5 wrap around (for example, 4 + 3 = 2).
- Lose both hands, or have no legal move, and you lose.

## Modes

- **Player vs. Player:** two people on one computer.
- **Player vs. Computer:** a minimax AI with selectable difficulty.

## Running it

Requires Java 17 or newer.

- **IntelliJ IDEA:** open the folder and run `GameLauncher`.
- **Command line:**
  ```bash
  javac -d out src/*.java
  java -cp out GameLauncher
  ```

## Code overview

| Class | Role |
|---|---|
| `ChopsticksGame` | Game rules and state (moves, win check) |
| `Minmax` | Minimax search for the computer player |
| `ChopsticksGameGUI` | Swing interface: hands, buttons, settings |
| `TwoPlayersVsAIGame` | Mode setup |
| `VictoryAnimationPanel` | End-of-game animation |
| `GameLauncher` | Entry point and mode picker |
