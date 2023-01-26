# CSC 133 Final Project---RainMaker
 _This was a half-semester long project that demonstrated the application of object oriented principles and design by developing a small game that showed understanding of these methods using JavaFX_

## Key Features
- Application of a state design pattern to determine what actions needed to be ran based on the "state" of the object.
- Application of an observer pattern to determine which objects would have access to the wind, thus allowing these objects to move across the window.
- The use of JavaFX shapes to design a helicopter, cloud, pond, and blimp programmatically.
- The use of bezier curves to draw interesting cloud shapes.
- The use panes to separate and organize the different objects in the game, thus eliminating interference with each other.

## How to play
- The player controls the speed of the helicopter using the up and down arrow keys.
- The player controls the angle of the helicopter using the left and right arrow keys
- The player must "seed" clouds, using the spacebar.
- If the cloud reaches to 30%, the cloud would then proceed to rain on the nearest pond, filling it.
- To win this game, the player must fill all the ponds to at least 70% of the total capacity of all the ponds
- For each second that the helicopter is flying, fuel is consumed and the player must fill all the ponds before the fuel reaches zero.
- Blimps could be used to refuel the helicopter; the helicopter must fly over the blimp while maintaining the same speed as the blimp and using the spacebar to refuel.
- The score is dependent on the remaining fuel and the total percentage of the ponds that is filled.