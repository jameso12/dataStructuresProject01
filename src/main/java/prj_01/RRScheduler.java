//James O Rodriguez Feliciano james.rodriguez5@upr.edu
package prj_01;
/**
 * This class will start the main process and processes. 
 * */
public class RRScheduler {
	/**
	 * Makes(initializes Threads and RoundRobinCLL) and runs the project.
	 * @param args CLI arguments that will be used to change default behavior for program.
	 * */
    public static void main(String[] args){
    	// default values
        int termination_limit = 100;
        int no_threads = 5;
        int project_step = 2;
        //parsing through and processing arguments
        for (int i=0; i<args.length; i++) {
        	// sets termination limit, if present in as argument
            if (args[i].equals("-t") || args[i].equals("--termination")) {
                termination_limit = Integer.valueOf(args[++i]);
            }
            // sets amount of threads to be made, if present as argument
            else if (args[i].equals("-p") || args[i].equals("--processes")) {
                no_threads = Integer.valueOf(args[++i]);
            }
            // sets what part of the project will be executed, if present as argument
            else if (args[i].equals("-s") || args[i].equals("--prjstep")) {
                project_step = Integer.valueOf(args[++i]);
                if (project_step!=1 && project_step!=2) {
                    System.out.println("Project Step value is 1 or 2 (" + project_step + " given).");
                    System.exit(1);
                }
            }
        }

        System.out.println("Starting Program...");

        // sets roundRobine null, so that Threads don't break if step 2 was not chosen
        RoundRobinCLL roundRobine = null;
        // if step 2 was chosen, then roundRobine now points to a RoundRobinCLL object
        if (project_step==2) {
            roundRobine =  new RoundRobinCLL(12, termination_limit);
        }
        // initializes a thread that contains a reference to roundRobine
        ThreadRunnable rrRunnable = new ThreadRunnable(roundRobine);
        // initializes the Threads that will later on iterate over the roundRobine
        Threads threads = new Threads(no_threads, rrRunnable);
        // starts( start invokes a threads run() method) all of the threads.
        for (int i=0; i<threads.threads.size(); i++) {
            threads.threads.get(i).start();
        }
        // if the roundaRobin is not null(step 2 was chosen), then call findFilledSlot()
        // findFilledSlot is the "main" method or the main process as it was referred to in
        // the instructions
        if (roundRobine!=null) roundRobine.findFilledSlot() ;
        // when the main method is finished, display "Main Finished ... Bye Bye" on console
        System.out.println("Main Finished ... Bye Bye");
        // after main reaches its termination limit the threads must stop too
        // for that to happen stopLoop property must be changed to true
        if (roundRobine!=null) roundRobine.stopLoop = true;

    }
}
