/*This is a type of "Table", a Modular Multiplication Table. 
The primary existence of a modular multiplication table is as a
circle of dots with lines connecting each each i to a*i mod m

Here is a brief summary of methods for this program:
   Constructors:
      - MMT()
      - MMT(mod, mult, add)
      - MMT(mod, mult)
      -read(Scanner input)
         -takes in a scanner linked up to data that gives
                   mod    mult   add
   Information:
      -mod()
      -mult()
      -add()
      
   Printing into a file:
      -print(Printstream Output)
         - prints out mod, mult, add
      -printMatrix(Printstream Output)
         -prints out underlying matrix */

import java.util.*;
import java.io.*;

public class MMT implements Table {
   private Matrix matrix;
   private int mod;
   private int mult;
   private int add;
      
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   //Constructors for MMT ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   
   //Constructs an empty modular multiplication table
   public MMT() {
      matrix = new Matrix(0, 0);
   }
   
   //Constructs a MMT with the given mod, mult, and add
   public MMT(int mod, int mult, int add) {
      this.mod = mod;
      this.mult = mult;
      this.add = add;
      matrix = new Matrix(mod, mod);
      addEdges();
   }
   
   //Constructs a MMT given only a mod and mult
   public MMT(int mod, int mult) {
      this(mod, mult, 0);
   }
   
   //Change this MMT to the MMT given by the data in the Scanner
   public void read(Scanner input) {
      mod = input.nextInt();
      mult = input.nextInt();
      add = input.nextInt();
      matrix = new Matrix(mod, mod);
      addEdges();
   }
   
   //adds the edges of the MMT 
   private void addEdges() {
      int conn;
      for (int i = 0; i < mod; i++) {
         conn = (((i*mult) % mod) + add) % mod;
         //conn = (mod - (i*(mult) % mod)) % mod; --without an add value
         matrix.inc(i, conn); 
      }
   }
   
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   //Information about the Modular Multiplication Table ++++++++++++++++++++++++++++++++++++++++++
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   
   public int mult() {
      return mult;
   }
   
   public int mod() {
      return mod;
   }
   
   public int add() {
      return add;
   }
   
   //returns True/False if the two points are connected or not
   public boolean connected(int i, int j) {
      if (matrix.get(i, j) > 0) {
         return true;
      }
      return false;
   }
   
   //returns a List containing all points that i is connected to
   public List<Integer> connections(int i) {
      List<Integer> conn = new ArrayList<>();
      for (int j = 0; j < matrix.cols(); j++) {
         if (connected(i, j)) {
            conn.add(j);
         }
      }
      return conn;
   }
   
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   // Printing into file to be read by "DrawMMT" +++++++++++++++++++++++++++++++++++++++++++++++++
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   
   public void print(PrintStream output) {
      output.println(mod);
      output.println(mult);
      output.println(add);
   }
   
   public void printMatrix(PrintStream output) {
      output.println(matrix.toString());
   }

}
