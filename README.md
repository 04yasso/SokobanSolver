Sokoban Solver
==============

Yaseen Elmehrek

Personal Project

Usage
-----

Compile files

    cd src
    javac *.java

SokobanMain contains the main method. The program takes command-line arguments with
the following options.

    java SokobanMain [Sokoban input file]


NO command-line input validation is done. It's a very simple command-line as I
was focused more on the implementation of the searches rather than the prettiness
of the UI.

Input
-----

The Sokoban files must be in the following format.

    [Number of columns]
    [Number of rows]
    [Rest of the puzzle]

With the following state mappings (note that when the player or box is on the
goal, the mapping changes).

    #         Wall (Obstacle)
    S         Empty goal (Storage)
    P         Player on floor (Robot)
    \+        Player on goal
    B         Box on floor (Block)
    \*        Box on goal

Output
------

The output is in the following format.

    1. String representation of initial state
    2. String representation of the final state
    3. Move solution