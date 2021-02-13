import java.util.*;
import java.util.Map.Entry;
import java.io.File;
import java.io.FileNotFoundException;

/*
 * Shahryar Iqbal
 * 250873209
*/

public class findConnectedComponents {

	private int size;
	private char[][] image;
	private uandf<String> newUnionFind;
	private boolean labelsDone = false;
	private HashMap<Character, Integer> componentLabels;
	private LinkedHashMap<Character, Integer> labelsOrdered;

	// Constructor that initializes attributes and also creates creates the image
	// using the given file name
	public findConnectedComponents(int n, String fileName) {
		this.size = 0;
		this.newUnionFind = new uandf<String>(n * n);
		image = new char[n][n];
		componentLabels = new HashMap<Character, Integer>();
		labelsOrdered = new LinkedHashMap<Character, Integer>();

		// store file image in the character array matrix
		File imageFile = new File(fileName);
		try {
			Scanner input = new Scanner(imageFile);
			int row = 0;
			while (input.hasNextLine()) {
				String line = input.nextLine();
				for (int column = 0; column < line.length(); column++) {
					image[row][column] = line.charAt(column);
				}
				row++;
			}
			input.close();
		}

		catch (FileNotFoundException e) {
			System.out.println("File Not found");
		}
	}

	// prints the input image without any changes
	public void printInputImage() {
		for (int row = 0; row < image.length; row++) {
			for (int column = 0; column < image[0].length; column++) {
				System.out.print(image[row][column]);
			}
			System.out.print("\n");
		}
	}

	// prints the input image with unique characters labelling the connected
	// components
	public void uniqueCharImage() {
		// Labels them depending on the component
		for (int row = 0; row < image.length; row++) {
			for (int column = 0; column < image[0].length; column++) {
				if (image[row][column] == '+') {
					String coordinate = Integer.toString(row) + "-" + Integer.toString(column);
					newUnionFind.make_set(coordinate);

					if (row == 0 && column == 0) { // cannot check left or top, so do nothing

					} else if (row == 0) { // if it is the first row, then we will not check the top. prevents null
											// pointer
						if (checkLeft(row, column) == true) {
							String leftCoordinate = Integer.toString(row) + "-" + Integer.toString(column - 1);
							newUnionFind.union_sets(coordinate, leftCoordinate);
						}
					} else if (column == 0) { // if it is the first column, then we will not be checking the left.
												// prevents null pointer
						if (checkTop(row, column) == true) {
							String topCoordinate = Integer.toString(row - 1) + "-" + Integer.toString(column);
							newUnionFind.union_sets(coordinate, topCoordinate);
						}
					} else { // handles all the normal cases that are not the first row or column
						if (checkLeft(row, column) == true && checkTop(row, column) == true) {
							String leftCoordinate = Integer.toString(row) + "-" + Integer.toString(column - 1);
							String topCoordinate = Integer.toString(row - 1) + "-" + Integer.toString(column);
							newUnionFind.union_sets(coordinate, leftCoordinate);
							newUnionFind.union_sets(leftCoordinate, topCoordinate);
						} else if (checkLeft(row, column) == true) {
							String leftCoordinate = Integer.toString(row) + "-" + Integer.toString(column - 1);
							newUnionFind.union_sets(coordinate, leftCoordinate);
						} else if (checkTop(row, column) == true) {
							String topCoordinate = Integer.toString(row - 1) + "-" + Integer.toString(column);
							newUnionFind.union_sets(coordinate, topCoordinate);
						}
					}
				}
			}
		}
		size = newUnionFind.final_sets();

		for (int row = 0; row < image.length; row++) {
			for (int column = 0; column < image[0].length; column++) {
				if (image[row][column] == '+') {
					System.out.print(uniqueCharImageHelper(row, column));
				} else {
					System.out.print(" ");
				}
			}
			System.out.print("\n");
		}
	}

	// Prints component list labels
	public void componentList() {
		if (labelsDone == false) { // if labels are not collected, then collect them
			uniqueCharImage();
		} else { // else print the component list labels
			labelsOrdered = sortLabels(); // orders the labels. see the sortLabels() method below
			for (Map.Entry<Character, Integer> label : labelsOrdered.entrySet()) {
				System.out.println("Component Label: " + label.getKey() + " size: " + label.getValue());
			}
		}
	}

	// prints components whose size is greater than or equal to 4
	public void componentListGreaterThanFour() {
		if (labelsDone == false) { // if labels are not collected, then collect them
			uniqueCharImage();
		} else { // else print the component list
			labelsOrdered = sortLabels();
			for (Map.Entry<Character, Integer> label : labelsOrdered.entrySet()) {
				if (label.getValue() >= 4) {
					System.out.println("Component Label: " + label.getKey() + " size: " + label.getValue());
				}
			}
		}
	}

	// Sorts component labels
	private LinkedHashMap<Character, Integer> sortLabels() {
		// create an array list of the component labels and sort it
		ArrayList<Map.Entry<Character, Integer>> compareList = new ArrayList<Entry<Character, Integer>>(componentLabels.entrySet());
		int n = compareList.size();
		for(int i = 0; i < n-1; i++) {
			for (int j =0; j < n-i-1; j++) {
				if(compareList.get(j).getValue() > compareList.get(j+1).getValue()) {
					Entry<Character, Integer> temp = compareList.get(j);
					compareList.set(j, compareList.get(j+1));
					compareList.set(j+1, temp);
				}
			}
		}
		
		// return the sorted list as a LinkedHashMap
		LinkedHashMap<Character, Integer> result = new LinkedHashMap<Character, Integer>();
		for (Map.Entry<Character, Integer> entry : compareList) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	// helper method for printing the unique character image
	private char uniqueCharImageHelper(int row, int column) {
		String coordinates = Integer.toString(row) + "-" + Integer.toString(column);
		char uniqueChar = ' ';
		for (int i = 0; i < size; i++) {
			if (newUnionFind.find_set(coordinates).value.equals(newUnionFind.rep.get(i).value)) {
				uniqueChar = (char) ((char) 97 + i);
				if (componentLabels.containsKey(uniqueChar)) {
					componentLabels.put(uniqueChar, componentLabels.get(uniqueChar) + 1);
				} else {
					componentLabels.put(uniqueChar, 1);
				}
			}
		}
		labelsDone = true;
		return uniqueChar;
	}

	// helper method that checks if the left coordinate is a + symbol
	private boolean checkLeft(int row, int column) {
		if (image[row][column - 1] == '+') {
			return true;
		}
		return false;
	}

	// helper method that checks if the top coordinate is a + symbol
	private boolean checkTop(int row, int column) {
		if (image[row - 1][column] == '+') {
			return true;
		}
		return false;
	}
}
