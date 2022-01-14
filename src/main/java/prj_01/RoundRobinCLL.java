//James O Rodriguez Feliciano james.rodriguez5@upr.edu
package prj_01;
import java.util.concurrent.ThreadLocalRandom;


class Node {
    public int id;
    public Node next;
    public Node previous;
    public Boolean proccessed_flag;

    public Node (int id) {
        this.id = id;
        proccessed_flag = true;
    }
}

interface RoundRobinCLLInterface {
    abstract void findEmptySlot();
    abstract void findFilledSlot();
}

public class RoundRobinCLL implements RoundRobinCLLInterface {
    private int num_nodes = 5;
    public Node head = null;
    public Node tail = null;
    public Boolean stopLoop = false;
    private int termination_limit;
    /**
     * Makes thread object wait a certain amount of time.
     * */
    private void holdon() {
        try{
            Thread.currentThread().sleep(ThreadLocalRandom.current().nextInt(500, 3000));
        }
        catch(Exception e){
            System.out.println("Something went wrong.");
        }
    }
    
    /**
     * Converts CLL, aka round robin, to a string.
     * 
     * @return String representation of round robin
     * */
    @Override
    public String toString () {
        String s = new String(""+ Thread.currentThread().getName() + " ");
        Node node = head;
        s+= "(Node-1: " + node.proccessed_flag + ")";
        s+= " ==> ";

        for (int i=1; i<num_nodes; i++) {
            node = node.next;
            s+= "(Node-"+(i+1)+": "+node.proccessed_flag + ")";
            if (i<num_nodes-1)
                s+= " ==> ";
        }
        return s;
    }
    /**
     * Holds node for a certain amount of time and changes processed flag.
     * */
    private synchronized void holdRR(Node node, Boolean set_slot) {
        System.out.println("Thread " + Thread.currentThread().getName() + " Holding Resources");
        node.proccessed_flag = set_slot ;
        System.out.println("Thread " + Thread.currentThread().getName() + " Releasing Resources");
        if (set_slot) holdon();
    }

    /**
     * Method threads will use to find a node to changed its processed flag.
     * */
    public void findEmptySlot() { // this what the threads will call
        holdon();
        /* PUT YOUR CODE HERE TO FIND AN EMPTY SLOT */
        Node currentNode = head; // iteration node
        for(int i = 0; i<num_nodes; i++) 
        {
        	if(currentNode.proccessed_flag)
        	{
        		holdRR(currentNode, false);
        		break; // since threads always start from head
        	}
        	currentNode = currentNode.next; // iterate
        }
        
        /* STARTING FROM THE FIRST NODE IN THE LINKED LIST */
        /*** IMPORTANT:: USE THE holdRR() METHODE TO ACCESS THE LINKED LIST ***/
        /*** TO AVOID RACE CONDITION ***/
    }
    /**
     * Iterates over RR and if it finds a node that is not_processed
     * it changes it to processed.
     *  */
    public void findFilledSlot() {
        /* PUT YOUR CODE HERE TO FIND THE FILLED SLOTS */
        /* FOR THE MAIN PROCESS                        */
    	Node currentNode = head;
        /*** IMPORTANT:: USE THE holdRR() METHODE TO ACCESS THE LINKED LIST ***/
        int count = 0 ;
        while (!stopLoop) {
            /* PUT YOUR CODE HERE TO FIND THE FILLED SLOTS */
        	holdon();
        	if(!currentNode.proccessed_flag)
        	{
        		holdRR(currentNode, true);
        	}
        	currentNode = currentNode.next; // iterate to next node
        	++count;
            if (count>termination_limit) break;
            System.out.println("Main Move No.: " + count%num_nodes + "\t" + toString());
        }
    }
    
    /**
     * Creates the CLL that will act as the Round Robin.
     * Assumes list is at least 2 nodes in length.
     * */
    private void fillRoundRubin () { // verify that list will be at least 2 nodes in length
    	head = new Node(0);
    	tail = new Node(num_nodes-1);
    	head.next = tail;
    	head.previous = tail;
    	tail.next = head;
    	tail.previous = head;
    	Node n;
    	for(int i = 1; i<num_nodes-1; i++) // wont execute if num_nodes is 2 or less
    	{
    		n = new Node(i);
    		n.previous = tail.previous;
    		n.next = tail;
    		tail.previous.next = n;
    		tail.previous = n;
    	}

    }

    /**
     * Constructor of round robin, specifying number of nodes and termination limit.
     * 
     * @param num_nodes number of nodes that round robin will have
     * @param termination_limit how many times main process will iterate
     * */
    public RoundRobinCLL(int num_nodes, int termination_limit) {
        this.num_nodes = num_nodes;
        this.termination_limit = termination_limit;
        fillRoundRubin();
    }
    /**
     * Constructor for round robin, specifying number of nodes.
     * 
     * @param num_nodes*/
    public RoundRobinCLL(int num_nodes) {
        this.num_nodes = num_nodes;
        fillRoundRubin();
    }

    /**
     * Default construct for the round robin.
     * */
    public RoundRobinCLL() {
        fillRoundRubin();
    }

}
