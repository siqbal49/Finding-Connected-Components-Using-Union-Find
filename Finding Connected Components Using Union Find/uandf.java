import java.util.ArrayList;
/*
 * Shahryar Iqbal
 * 250873209
*/
public class uandf<T> {
	private boolean finalize = false;
    public ArrayList<Node> rep;

    // constructs a union-find data type with n elements
    public uandf(int n) {
           this.rep = new ArrayList<Node>(n);
        }

    // creates a new set whose only member (and thus representative) is i.
    public void make_set(T i) {
        // check to see if the data structure has been finalized 
    	// if not, then create the new set
        if (this.finalize == false) {
            Node<T> newNode = new Node<T>(i);
            newNode.parent = newNode;
            rep.add(newNode);
        } else {
            System.out.println("Error: Union find data stucture has been finalized");
        }
    }

    // unites the dynamic sets that contains i and j, respectively, into a new set that is the union of these two sets.
    public void union_sets(T i, T j) {
    	// check to see if the data structure has been finalized 
    	// if not, then create the new set
        if (this.finalize == false) {
            
            Node<T> firstRoot = find_set(i);
            Node<T> secondRoot = find_set(j);

            // if the root for both are the same, then do nothing 
            if (firstRoot.value.equals(secondRoot.value)) {
            	return;
            }
            // assign ranks 
            if (firstRoot.rank > secondRoot.rank) {
                secondRoot.parent = firstRoot;
            } else if (secondRoot.rank > firstRoot.rank) {
                firstRoot.parent = secondRoot;
            } else {
                // if both ranks are equal
                firstRoot.parent = secondRoot;
                secondRoot.rank = secondRoot.rank + 1;
            }
        } else {
            System.out.println("Error: The union find data structure has been finalized");
        }
    }

    // returns the representative of the set containing i
    public Node<T> find_set(T i) {
        Node<T> result = null;
        for (int j = 0; j < rep.size(); j++) {
            if (rep.get(j).value.equals(i)) {
            	result = findSetHelper(rep.get(j));		// see findSetHelper method below
            }
        }
        if (result == null) {
            System.out.println("The input set was not found");
        }
        return result;
    }

    // Recursive helper method that returns the given node root.
    private Node<T> findSetHelper(Node<T> node) {
        if (!node.parent.value.equals(node.value)) {
            return findSetHelper(node.parent);
        }
        return node;
    }
    /*
     * returns the total number of current sets and finalizes the current sets
     * make_set and union_sets will have no effect after this operation
     * resets the representatives of the sets so that integers from 1 to final_sets
     * will be used as representatives
     */
    public int final_sets() {
        // Push all the root sets into final_sets()
        int result = 0;	// return for the total number of current sets 
        for (int i = 0; i < rep.size(); i++) {
            if (rep.get(i).parent.value.equals(rep.get(i).value)) {
                rep.add(0,rep.get(i));
                rep.remove(i+1);
                result++;
            }
        }
        // finalize the current sets
        this.finalize = true;
        return result;
    }
}
