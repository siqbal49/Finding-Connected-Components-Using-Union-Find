
// main function for the assignment. This is what runs to give the final outputs
// Shahryar Iqbal
// 250873209

public class main {
	public static void main(String [] args) {
		
        findConnectedComponents image = new findConnectedComponents(71, args[0]);
        
        System.out.println("Output 1 \n ------------------- \n");
        image.printInputImage();
        System.out.println("\n");
        
        System.out.println("Output 2 \n ------------------- \n");
        image.uniqueCharImage();
        System.out.println("\n");
        
        System.out.println("Output 3 \n ------------------- \n");
        image.componentList();
        System.out.println("\n");
        
        System.out.println("Output 4 \n ------------------- \n");
        image.componentListGreaterThanFour();
        System.out.println("\n");
    }
}
