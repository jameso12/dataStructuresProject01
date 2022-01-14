//James O Rodriguez Feliciano james.rodriguez5@upr.edu
package prj_01;
/**
 * Thread implementation(Threads are made by implementing Runnable interface).
 * */
public class ThreadRunnable implements Runnable {

    private boolean doStop = false;
    private RoundRobinCLL rr = null; // reference to round robin

    /* Constructors */
    public ThreadRunnable() { // default constructor
    }
    /**
     * Constructs thread with reference to CLL. 
     * @param rr reference to round robin
     * */
    public ThreadRunnable(RoundRobinCLL rr) {
        this.rr = rr;
    }

    @Override
    public void run() {
        System.out.println("Running Thread... This is Thread " + Thread.currentThread().getName());
        if (rr==null) { // no round robin, no work to be to be done
            return;
        }
        while  (!rr.stopLoop) {
            // keep doing what this thread should do.
            rr.findEmptySlot();
        }
        // display message when thread "dies"( ends its job )
        System.out.println("Thread " + Thread.currentThread().getName() + " Finished ... Bye Bye");
    }
}
