/*
 * Shahryar Iqbal
 * 250873209
 * Node class for the nodes of the union field data structure
 */
public class Node<T> {
	public Node<T> parent;  
    public Node<T> child;
    public T value;
    public int rank; 

    public Node(T v) {
        this.value = v;
        this.rank = 0;
    }
}
