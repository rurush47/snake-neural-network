# Snake
Implementation of a neural network that plays simple game of snake in Java.
The goal of the project is to teach the network to pick up apples and avoid walls and hitting it's own body.

#How it works
Fitrstly, the population of individual snakes is made. Snakes in population have their own neural networks connected to them which plays 
the game for them. Neural network is initialized with random values. Snakes play for specified number of turns or until they die 
(hit the wall or own body). After all snakes finished playing new population is created using generic algorithm (snakes with best scores
are favourised). Neural net has few inputs: distances to apple (in X and Y coordinates) and what is in front/left/right of snake's head.
The goal function rewards snakes which pick many apples in short periods of time.

There are many options that can be configured concerning generic algorithm and neural net. Playing with them can result in better 
(or worse :p) performance.

#How to use
Simply run Main function and algorithm will start working. A window will pop up and show avarage fitness of the population, 
and population number. Press 's' button to stop evolution and pick the best snake. Game window will pop up and you can see AI plaing.
Press space to make snake start over from the beginning.

#Things to do
UI is terrible and can be replaced in the future. Also adinng possibility to save a net in XML file would be nice. 
I will work on that when I will have more time (maybe :p)
