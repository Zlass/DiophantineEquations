/**
 *  @Name Answer.java
 *  @author Zach Glassner
 *  This class is a reduction variable for solving Diophantine equations.
 *  The two subclasses provide reductions for finding the highest and lowest
 *    solutions with respect to x, y, then z.
 */
import edu.rit.io.InStream;
import edu.rit.io.OutStream;
import edu.rit.pj2.Tuple;
import edu.rit.pj2.Vbl;
import java.io.IOException;

public class Answer implements Vbl {
 public long x;
 public long y;
 public long z;

 /**
  *  Initializes an answer with x, y, and z set to 0.
  */
 public Answer() {}

 /**
  *  Initializes an answer with x, y, and z set to the given parameters.
  *  @param  long  x the x value of the answer.
  *  @param  long  y the y value of the answer.
  *  @param  long  z the z value of the answer.
  */
 public Answer(long x, long y, long z) {
  this.x = x;
  this.y = y;
  this.z = z;
 }

 /**
  *  Returns the x value of the shared variable.
  *  @return long the x value.
  */
 public long x() {
  return this.x;
 }

 /**
  *  Returns the y value of the shared variable.
  *  @return long the y value.
  */
 public long y() {
  return this.y;
 }

 /**
  *  Returns the z value of the shared variable.
  *  @return long the z value.
  */
 public long z() {
  return this.z;
 }

 /**
  *  Reduces the given shared variable into this variable.
  *  @param  Vbl shared variable
  */
 public void reduce(Vbl vbl) {}

 /**
  * Set this answer to the given shared variable. This variable must
  *  be set to a deep copy of the given variable.
  * @param  vbl  Shared variable.
  */
 public void set(Vbl vbl) {
  this.x = ((Answer) vbl).x();
  this.y = ((Answer) vbl).y();
  this.z = ((Answer) vbl).z();
 }

 /**
  * Checks if a given set of x, y, and z satisfy our given Diophantine equation
  * @param  long  x the x value to test
  * @param  long  y the y value to test
  * @param  long  z the z value to test
  * @return boolean  if the equation is a solution
  */
 public static boolean isDioEqn(long x, long y, long z, int n, long c) {
  return pow(x, n) + pow(y, n) == pow(z, n) + c;
 }

/**
 *  Raises a base to an exponent. ie. base^exponent
 *  @param  long  base the number to raise to a power.
 *  @param  int   exponent the power to raise the base to.
 *  @return long  base^exponent
 */
 private static long pow(long base, int exponent){
   if (exponent == 0){
      return 1;
   }
   long calc = base;
   for (int e = 1; e < exponent; e++) {
     calc *= base;
   }
   return calc;
 }

 /**
  * Prints out a Diophantine equation for a given Answer
  * @param Answer  a the answer to print out.
  */
 public void printAnswer(int n, long c) {
   System.out.println(this.x + "^" + n + " + " + this.y + "^" + n + " = " + this.z + "^" + n + " + " + c);
 }

 public Answer clone(){
  try {
    return (Answer) super.clone();
  } catch (CloneNotSupportedException e) {
   throw new RuntimeException();
  }
 }

 /**
  * Class Answer.High provides reduction variable to find the highest answer
  *  with respect to x, then y.
  */
 public static class High extends Answer {

  /**
   * Construct a new answer variable with x, y, and z set to 0.
   */
  public High() {
   super();
  }

  /**
   *  Initializes an answer with x, y, and z set to the given parameters.
   *  @param  long  x the x value of the answer.
   *  @param  long  y the y value of the answer.
   *  @param  long  z the z value of the answer.
   */
  public High(long x, long y, long z) {
   super(x, y, z);
  }

  /**
   *  Reduces the given shared variable into this variable. To get the highest
   *  solution with respect to x, then y.
   *  @param  Vbl shared variable
   */
  public void reduce(Vbl vbl) {
   long x = ((Answer) vbl).x();
   long y = ((Answer) vbl).y();
   long z = ((Answer) vbl).z();
   if (x > this.x || (x == this.x && y > this.y)
   || (x == this.x && y == this.y && z > this.z)) {
    this.x = x;
    this.y = y;
    this.z = z;
   }
  }
 }

 /**
  * Class Answer.Low provides reduction variable to find the lowest answer
  *  with respect to x, then y.
  */
 public static class Low extends Answer {

  /**
   * Construct a new answer variable with x, y, and z set to 0.
   */
  public Low() {
   super();
  }

  /**
   *  Initializes an answer with x, y, and z set to the given parameters.
   *  @param  long  x the x value of the answer.
   *  @param  long  y the y value of the answer.
   *  @param  long  z the z value of the answer.
   */
  public Low(long x, long y, long z) {
   super(x, y, z);
  }

  /**
   *  Reduces the given shared variable into this variable. To get the lowest
   *  solution with respect to x, then y.
   *  @param  Vbl shared variable
   */
  public void reduce(Vbl vbl) {
   long x = ((Answer) vbl).x();
   long y = ((Answer) vbl).y();
   long z = ((Answer) vbl).z();
   if (x < this.x || (x == this.x && y < this.y)
   || (x == this.x && y == this.y && z < this.z)) {
    this.x = x;
    this.y = y;
    this.z = z;
   }
  }
 }

}
