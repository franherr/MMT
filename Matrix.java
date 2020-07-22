import java.util.*;

public class Matrix {
   private int row;
   private int col;
   private ArrayList<ArrayList<Integer>> matrix;
   
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   //Constructors ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   public Matrix() {
   this(0, 0);
   }
   
   public Matrix(int x, int y) {
      row = x;
      col = y;
      initializeMatrix();
   }
   
   public Matrix(ArrayList<ArrayList<Integer>> mat) {
      row = mat.size(); 
      col = mat.get(0).size();
      if (testInput(mat)) {
         throw new IllegalArgumentException();
      }
      for (ArrayList<Integer> r : mat) {
         matrix.add(r);
      }
   }
   
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   //Gathering information about the Matrix ++++++++++++++++++++++++++++++++++++++++++++++++++++++
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   public int rows() {
      return matrix.size();
   }
   
   public int cols() {
      return matrix.get(0).size();
   }
   
   /* returns the sum of the values
   public int sum() {
   
   } */
   
   public int get(int i, int j) {
      return matrix.get(i).get(j);
   }
   
   public ArrayList<Integer> getRow(int i) {
      return matrix.get(i);
   }
   
   public ArrayList<Integer> getCol(int j) {
      ArrayList<Integer> col = new ArrayList<>();
      for (int a = 0; a < rows(); a++) {
         col.add(get(a, j));
      }
      return col;
   }
   
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   //changing values in the matrix +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   
   //post: adds x to value at (i, j)
   public void add(int i, int j, int x) {
      matrix.get(i).set(j, get(i, j) + x);
   }
   
   //post: adds 1 to value at (i, j)
   public void inc(int i, int j) {
      matrix.get(i).set(j, get(i, j) + 1);
   }
   
   //post: subtracts x from value at (i, j)
   public void subtract(int i, int j, int x) {
      matrix.get(i).set(j, get(i, j) - x);
   }
   
   //post: subtracts 1 from the value at (i, j)
   public void dec(int i, int j) {
      matrix.get(i).set(j, get(i, j) - 1);
   }

   //post: sets value at (i, j) to the given x
   public void setValue(int i, int j, int x) {
      matrix.get(i).set(j, x);
   }
   
    //post: sets the row at the given index to the given ArrayList
    public void rowInput(int i, ArrayList<Integer> r) {
       check(r, col);
       matrix.set(i, r);
    }
      
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   //performing row operations on the matrix +++++++++++++++++++++++++++++++++++++++++++++++++++++
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   
   //post: adds row b to row a
   public void addRows(int a, int b) {
      for (int k = 0; k < cols(); k++) {
         add(a, k, matrix.get(b).get(k));
      }
   }
     
   //post: adds column b to column a
   public void addColumns(int a, int b) { 
      for (int k = 0; k < rows(); k++) {
         add(k, a, matrix.get(k).get(b));
      }
   }

   //post: removes row i from the matrix
   public void deleteRow(int i) {
      matrix.remove(i);
      row--;
   }
   
   //post: removes column i from the matrix
   public void deleteCol(int i) {
      for (int k = 0; k < rows(); k++) {
         matrix.get(k).remove(i);
      }
      col--;
   }
   
   //Add a row to the matrix at index i
   //===================================
   
   //adds a row of zeros to the matrix at the end
   public void newRow() {
      newRow(row, blankList(col));
   }
   
   //adds a row of zeros at the given index
   public void newRow(int i) {
      newRow(i, blankList(col));
   }
   
   //adds the given row to the end of the matrix
   public void newRow(ArrayList<Integer> r) {
      newRow(row, r);
   }
   
   //adds the given row to the matrix at the given index
   public void newRow(int i, ArrayList<Integer> r) {
      check(r, col);
      matrix.add(i, r);
      row++;
   }
   
   
   //Add a column to the Matrix at the given index
   //=============================================
   
   //adds a Column of zeros to the end of the matrix
   public void newColumn() {
      newColumn(col, blankList(row));
   }
   
   //adds a column of zeros to the matrix at index i
   public void newColumn(int i) {
      newColumn(i, blankList(row));
   }
   
   //adds the given column to the end of the matrix
   public void newColumn(ArrayList<Integer> c) {
      newColumn(col, c);
   }
   
   //adds the given column to the matrix at the given index 
   public void newColumn(int i, ArrayList<Integer> c) {
      check(c, row);
      for (int r = 0; r < row; r++) {
         matrix.get(r).add(i, c.get(r));
      }
      col++;
   }
   
   //*********************************************************************************************
   //toString printing method ********************************************************************
   //*********************************************************************************************
   public String toString() {
      ArrayList<String> printMat = new ArrayList<>();
      for (int i = 0; i < rows(); i++) {
         printMat.add(matrix.get(i).toString());
      }
      return twoDimString(printMat);
   }
   
   private String twoDimString(ArrayList<String> list) {
      String print = list.get(0);
      for (int i = 1; i < list.size(); i++) {
         print = print + "\n" + list.get(i);
      }
      return print;
   }
   
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   //Helper Methods ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   
   //creates a List of zeros
   private ArrayList<Integer> blankList(int n) {
      ArrayList<Integer> list = new ArrayList<>();
      for (int k = 0; k < n; k++) {
         list.add(0);
      }
      return list;
   }
   
   //throws an IllegalArgumentException if the list is not of the given size
   private void check(ArrayList<Integer> list, int s) {
      if (list.size() != s) {
         throw new IllegalArgumentException();
      }
   }
   
   //initializes matrix to a row by col matrix of zeros
   private void initializeMatrix() {
      matrix = new ArrayList<ArrayList<Integer>>(); 
      for (int r = 0; r < row; r++) {
         matrix.add(blankList(col));  
      }
   }
   
   //tests whether the given ArrayList can be a valid Matrix   
   private boolean testInput(ArrayList<ArrayList<Integer>> mat) {
      int rowSize = mat.get(0).size();
      for (ArrayList<Integer> r : mat) {
         if(r.size() != rowSize) {
            return true;
         }
      }
      return false;
   }
}