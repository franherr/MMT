import java.util.*;
import java.io.*;

public interface Table {
   public int mod();
   public boolean connected(int i, int j);
   public List<Integer> connections(int i);
   public void print(PrintStream output);
   public void read(Scanner input);
}