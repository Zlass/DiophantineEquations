/**
 *  @Name DioEqnSmp.java
 *  @author Zach Glassner
 *  This class uses a parallel approach to find all the answers to a given
 *    Diophantine equation.
 */
import edu.rit.pj2.LongLoop;
import edu.rit.pj2.Task;
import edu.rit.pj2.vbl.LongVbl;

public class DioEqnSmp extends Task {

 // Private Data Members
 private static int n;
 private static long c;
 private static long lb;
 private static long ub;

 /**
  * Uses a brute force search to find solutions to a given Diophantine equation
  *  based on the four parameters given.
  * @param String[] args arguments give via the command line
  */
 public void main(String[] args) {
  if (args.length == 4) {
   try {
    n = Integer.parseInt(args[0]);
   } catch (NumberFormatException e) {
    usage();
    System.out.println("Error: <n> = "+args[0]+"\t<n> is the exponent. It must be an integer ≥ 2.");
    terminate(1);
   }
   if (n < 2) {
    usage();
    terminate(1);
   }
   try {
    c = Long.parseLong(args[1]);
   } catch (NumberFormatException e) {
    usage();
    System.out.println("Error: <c> = " + args[1] + "\t<c> is the constant; it must be a long integer.");
    terminate(1);
   }
   try {
    lb = Long.parseLong(args[2]);
   } catch (NumberFormatException e) {
    usage();
    System.out.println("Error: <lb> = " + args[2] + "\t<lb> is the lower bound for x, y, and z; it must be a long integer.");
    terminate(1);
   }
   try {
    ub = Long.parseLong(args[3]);
   } catch (NumberFormatException e) {
    usage();
    System.out.println("Error: <ub> = " + args[3] + "\t<ub> is the upper bound for x, y, and z; it must be a long integer.");
    terminate(1);
   }

  } else {
   usage();
   terminate(1);
  }

  LongVbl numSolutions = new LongVbl.Sum(0);
  // LongVbl threadTime1 = new LongVbl.Sum(0);
  // LongVbl threadTime2 = new LongVbl.Sum(0);
  // LongVbl threadTime3 = new LongVbl.Sum(0);
  // LongVbl threadTime4 = new LongVbl.Sum(0);

  Answer high = new Answer.High(lb, lb, lb);
  Answer low = new Answer.Low(ub, ub, ub);
  parallelFor(lb, ub).schedule(dynamic).exec(new LongLoop() {
   LongVbl locNumSolutions;
  //  LongVbl locThreadTime1;
  //  LongVbl locThreadTime2;
  //  LongVbl locThreadTime3;
  //  LongVbl locThreadTime4;
   Answer locHigh;
   Answer locLow;
   Answer temp;
   long time;
   public void start() {
    locNumSolutions = threadLocal(numSolutions);
    locHigh = threadLocal(high);
    locLow = threadLocal(low);
    // locThreadTime1 = threadLocal(threadTime1);
    // locThreadTime2 = threadLocal(threadTime2);
    // locThreadTime3 = threadLocal(threadTime3);
    // locThreadTime4 = threadLocal(threadTime4);
   }
   public void run(long x) {
    time = System.currentTimeMillis();
    for (long y = x; y <= ub; y++) {
     for (long z = lb; z <= ub; z++) {
      if (Answer.isDioEqn(x, y, z, n, c)) {
        temp = new Answer(x, y, z);
       ++locNumSolutions.item;
       locLow.reduce(temp);
       locHigh.reduce(temp);
      }
     }
    }
    // time = System.currentTimeMillis() - time;
    // // System.out.println(x+": " + time);
    //
    // if (rank() == 0) {
    //  locThreadTime1.item += time;
    // } else if (rank() == 1) {
    //  locThreadTime2.item += time;
    // } else if (rank() == 2) {
    //  locThreadTime3.item += time;
    // } else if (rank() == 3) {
    //  locThreadTime4.item += time;
    // }
   }
  });

  // System.out.println("Thread 1: " + threadTime1);
  // System.out.println("Thread 2: " + threadTime2);
  // System.out.println("Thread 3: " + threadTime3);
  // System.out.println("Thread 4: " + threadTime4);
  System.out.println(numSolutions.item);
  if (numSolutions.item == 1) {
   high.printAnswer(n, c);
  } else if (numSolutions.item > 1) {
   low.printAnswer(n, c);
   high.printAnswer(n, c);
  }
 }

 /**
  *  Prints the correct usage of the this class
  */
 public static void usage() {
  System.out.println("Usage: java pj2 cores=<K> DioEqnSmp <n> <c> <lb> <ub>");
 }


}
