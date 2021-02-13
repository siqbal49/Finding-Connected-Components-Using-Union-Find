To run the program do: ./asn2 infile

The infile is a text file that contains an ASCII image. The program takes the infile as a command line argument and then does 4 different outputs.
Output 1: Simply displays the ASCII image as it is. 
Output 2: Displays the ASCII image with alphabetical labels for each connected component. Symbols are considered connected if there is another symbol above, below, or beside it. A connected component is a group of connected symbols.
Output 3: A list of each connected component label and their sizes. 
Output 4: A list of each connected component and their sizes for components that are greater than size of 3.

asn2Script contains a text file demonstrating the program on a command line console. 

main.java:
Does the console print functions to display outputs

uandf.java:
Implements union-find algorithm to help find sets of connected components

Node.java:
A class that implements the node objects used in union find. 

findConnectedComponents.java:
Contains all the logic for using an input ASCII image file and finding the connected components using union find. 

