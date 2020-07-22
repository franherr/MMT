import java.util.*;

import java.util.*;

public class FixingGraph {
   public static void main(String[] args) {
/// /      GraphChange g = new GraphChange(4);
//       boolean conn = g.connectedGraphQ();
//       System.out.println(conn);
//       Matrix ind = g.incidenceMatrix();
//       System.out.println(ind.toString());
      //Set<TreeSet<Integer>> mOfG = g.matchingComplex();
      //System.out.println(mOfG.toString());
      HashSet<TreeSet<Integer>> test = test();
      System.out.println(test.toString());
      
      //look up TreeSet (sets of sets) online
      //implement Comparable interface on the new TreeSet class
      //example class header: public class TreeSet implements Comparable {
   }

   public static HashSet<TreeSet<Integer>> test() {
      HashSet<TreeSet<Integer>> big = new HashSet<TreeSet<Integer>> ();
      TreeSet<Integer> small = new TreeSet<Integer>();
      small.add(2);
      small.add(4);
      big.add(small);
      return big;
   }
}