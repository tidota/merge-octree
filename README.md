# Merging Octree Maps

This is my design and analysis of an algorithm to efficently merge two octree maps.
The algorithm deals with log odds as occupancy probabilities.
Instead of placing values at leaves, my approach lets internal nodes have differential log odds. An occupancy probability is thereby retrieved by summing values on nodes from the root to the leaf node. 
The time complexity to retrieve a value is equivalent to that of the naive method. Rather, the merging speed for octree maps is improved since it does not need to access all nodes.

This is my course project in the past and the final report is [here](./finalrep.pdf).

