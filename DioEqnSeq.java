/**
 *  @Name DioEqnSeq.java
 *  @author Zach Glassner
 *  This class uses a sequential approach to find all the answers to a given
 *    Diophantine equation.
 */
import edu.rit.pj2.LongLoop;
import edu.rit.pj2.Task;
import edu.rit.pj2.vbl.LongVbl;

public class DioEqnSeq extends Task {

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

  long numSolutions = 0;
  Answer low = new Answer.Low(ub, ub, ub);
  Answer high = new Answer.High(lb, lb, lb);

  for (long x = lb; x <= ub; x++) {
   for (long y = x; y <= ub; y++) {
    for (long z = lb; z <= ub; z++) {
     if (Answer.isDioEqn(x, y, z, n, c)) {
       Answer temp = new Answer(x, y, z);
      ++numSolutions;
      low.reduce(temp);
      high.reduce(temp);
     }

    }

   }

  }
  System.out.println(numSolutions);
  if (numSolutions == 1) {
    high.printAnswer(n, c);
  } else if (numSolutions > 1) {
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
