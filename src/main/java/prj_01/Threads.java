//James O Rodriguez Feliciano james.rodriguez5@upr.edu
package prj_01;
import java.util.*;
/**
 * Assigns ThreadRunnable objects(threads) to threads arraylist. 
*/
public class Threads {
    public ArrayList<Thread> threads = new ArrayList<Thread>(); // threads will be stored in this array list
    /**
     * Makes or populates threads array list.
     * Used in step 1 of the project.
     * 
     * @param noThreads number of threads to be created.
    */
    public Threads(int noThreads){
       for (int i=0; i<noThreads; i++){
           ThreadRunnable runnable = new ThreadRunnable();
           System.out.println("Creating Thread " + (i+1)); // displays message
           threads.add(new Thread(runnable, ""+i)); // creates thread
       }
    }
    /**
     * Makes or populates threads array list with threads that will interact with the round Robin.
     * Used in step 2 of the project.
     * @param noThreads number of threads to be created.
     * @param runnable  threads with referrence to round robin
    */
    public Threads(int noThreads, ThreadRunnable runnable){
        for (int i=0; i<noThreads; i++){
            System.out.println("Creating Thread " + (i+1)); // displays message
            threads.add(new Thread(runnable, ""+i)); // creates thread
        }
    }
}
