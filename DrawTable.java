/* This is an object that knows how to draw a table. 
Its primary use is that you can hand it any table and it will draw it.
You can modify the size, starting point, and how many edges it draws.
There are three different draw methods that the user can draw:

         -draw(Table table)
            -gives the user all options for draw
            -draws the edges one at a time
            -If the table has more than 100 edges and asks for a gif,
               the drawing may not finish in the gif time limit
               
         -drawFast(Table table)
            -gives the user all options for draw
            -draws multiple edges at once
            -Guarenteed to finish drawing any table within the gif time limit
            
         -drawMass(Table table)
            -does not allow the user to set any options
            -useful if the user wants to draw many tables in a sequence
            
The options for drawing a table are detailed below:
   
   -Advanced Options:
      -size: draws a square picture with dimensions 2*size px
         -[default: 500]
         
      -start: draws all the lines eminating from points between start and mod - 1
         -[default: 0]
         
      -count-by: draws all the lines eminating from points p such that p = 'start' (mod 'count-by')
         -[default: 1]
         
      Example: If the user wants to draw lines only eminating from odd points, they would set
            start = 1 and count-by = 2

   -Special Presentation:
      -dots: draws each point as a dot instead of a continuous circle
         -[default: n]
         
      -rainbow: draws each line in a different color to create a rainbow
         -[default: n]
         
      -directed lines: highlights the beginning of each line to see what point it is eminating from
         -[default: n]
         
      -night vision: draws the table with a black background and white lines
         -[default: n]
         
      -labels: labels each point around the circle with its number
         -[default: n]
         
      -gif: draws the table as a gif
         -[default: n]
         -if user selects to draw a gif, they will be asked how many milliseconds of rest.
            -[average: 10]
*/


import java.util.*;
import java.awt.*;

public class DrawTable {
   private int size;
   private int start;
   private int count;
   private Scanner console; 

   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   //CONSTRUCTOR +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   
   public DrawTable() {
      console = new Scanner(System.in);
   }
   
   private void initialize() {
      size = 500;
      start = 0;
      count = 1;
   }
   
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   //User-Interactive Draw Methods +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   
   public void drawFast(Table table) {
       initialize();
       boolean[] b = clarify();
       draw(table, b, true);
    }
    
   public void drawMass(Table table) {
       initialize();
       boolean[] b = new boolean[6];
       draw(table, b, false);
   }
   
   
   public void draw(Table table) {
      initialize();
      boolean[] b = clarify();
      draw(table, b, false);  
   }
   
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   //Gathering User Info about the picture +++++++++++++++++++++++++++++++++++++++++++++++++++++++
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   
   private boolean[] clarify() {
      boolean[] b = new boolean[6];
      boolean answer = yesTo("Do you want options?");
      if (answer) {
         giveOptions(b);
      }
      return b;
   }
   
   private void giveOptions(boolean[] b) {
      if (yesTo("\tWould you like advanced options?")) {
         advancedOptions();
      }
      if (yesTo("\tWould you like special presentation?")) {
         specialPresentation(b);
      }
   }
   
   private void advancedOptions() {
      System.out.print("\t\tWhat size would you like the picture to be? ");
      size = console.nextInt();
      System.out.print("\t\tWhat is the starting point? ");
      start = console.nextInt();
      System.out.print("\t\tWhat is the count-by? ");
      count = console.nextInt();
      console.nextLine();
   }
   
   private void specialPresentation(boolean[] b) {
      b[0] = yesTo("\t\tDo you want dots?");
      b[1] = yesTo("\t\tDo you want rainbow?");
      b[2] = yesTo("\t\tDo you want directed lines?");
      b[3] = yesTo("\t\tDo you want night vision?");
      b[4] = yesTo("\t\tDo you want labels?");
      b[5] = yesTo("\t\tDo you want a gif?");
      if (b[1] == true && b[2] == true) {
         System.out.println("You cannot have rainbow and directed lines. Please reset your preferences.");
         specialPresentation(b);
      }
   }
   
   
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   //Setting Up the Draw +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
   private void draw(Table table, boolean[] b, boolean fast) {
      int[][] c = calculateCoordinates(table.mod());
      int rest;
      if (b[5] == true) {
         System.out.print("\t\tHow many milliseconds of rest in GIF? ");
         rest = console.nextInt();
      } else {
         rest = 0;
      }
      createPicture(table, b, c, rest, fast);
   } 
   
   private int[][] calculateCoordinates(int mod) {
      int[][] c = new int[mod][2];
      double th;
      for (int i = 0; i < mod; i++) {
         th = (2*Math.PI/mod)*i;
         c[i][0] = (int) round(size + (size-100)*Math.sin(th));
         c[i][1] = (int) round(size - (size-100)*Math.cos(th));
      }
      return c;
   }
     
   
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   //TIME TO DRAW! +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
   //Calls methods to create the various parts of the picture
   private void createPicture(Table table, boolean[] b, int[][] c, int rest, boolean fast) {
      DrawingPanel p = new DrawingPanel(2*size, 2*size);
      Graphics g = p.getGraphics();
      Color[] theme = new Color[3];
      if (b[3]) {
         theme[0] = Color.BLACK;
         theme[1] = Color.WHITE;
         theme[2] = Color.YELLOW;
      } else {
         theme[0] = Color.WHITE;
         theme[1] = Color.BLACK;
         theme[2] = Color.RED;  
      }
      p.setBackground(theme[0]);
      g.setColor(theme[1]);
      if (b[0] == true) {
         drawVertices(g, p, c);
      } else {
         g.drawOval(100, 100, 2*(size-100), 2*(size-100));
      }
      if (b[4] == true) {
         drawLabels(g, p, c);
      }
      drawEdges(table, g, p, c, b, theme, rest, fast);
   }
   
   
   //Draws dots for vertices
   private void drawVertices(Graphics g, DrawingPanel p, int[][] c) {
      for (int i = 0; i < c.length; i++) {
         g.fillOval(c[i][0], c[i][1], 6, 6);
      
      }
   }

   //Draws a label for each point
   private void drawLabels(Graphics g, DrawingPanel p, int[][] c) {
      double dis = 1.05;
      int space = 0;
      while (space < c.length) {
         if (space == 0) {
            g.drawString("" + space, (int)(dis*(c[space][0] - size)) + size, (int)(dis*(c[space][1] - size)) + size);
         } else {
            g.drawString("" + (space), (int)(dis*(c[space][0] - size)) + size, (int)(dis*(c[space][1] - size)) + size);
         }
         if (c.length > 200) {
            space += 10;
         } else {
            space += 1;
         }
      }
   }
   /* Here is a summary of the drawEdges method:
         initializes all values (sec, step, color, conn)
         If rainbow is true, it sets initial color to red, resets sec
         If fast is true, it resets step
         
   
   */
   
   
   //Draws the edges of the table according to the User's preference
   private void drawEdges(Table table, Graphics g, DrawingPanel p, int[][] c, boolean[] b, Color[] theme, int rest, boolean fast) {
      int sec = 0;
      int step = 0;
      int[] color = new int[3];
      ArrayList<Integer> conn;
      if (b[1] == true) {
         color[0] = 255;
         Color RED = new Color(color[0], 0, 0);
         g.setColor(RED);
         sec = table.mod() / 5;
      }
      if (fast) {
         step = (table.mod() / 100);
         if ((table.mod() % 100) > 0) {
            step++;
         }
      }
      for (int i = start; i < table.mod(); i = i + count) {
         conn = (ArrayList<Integer>) table.connections(i);
         for (int j : conn) {
            if (b[2]) {
               int x = (int) (c[j][0] - 0.2*(c[j][0] - c[i][0]));
               int y = (int) (c[j][1] - 0.2*(c[j][1] - c[i][1]));
               g.setColor(theme[1]);
               g.drawLine(c[i][0], c[i][1], x, y);
               g.setColor(theme[2]);
               g.drawLine(x, y, c[j][0], c[j][1]);
            } else {
               g.drawLine(c[i][0], c[i][1], c[j][0], c[j][1]);
            }
         }
         if (fast) {
            if ((i - start) != 0 && (i - start) % step == 0) {
                p.sleep(rest);
             }
         } else if (b[3] == true) {
             p.sleep(rest);
         }
         if (b[1] == true) {
             g.setColor(changeColor(color, sec, i));
         }
      }
   }
  
   //changes the color if the graph is rainbow
   private Color changeColor(int[] color, int sec, int i) {
       if ( i < 2*sec) {
          color[1] += 255/(2*sec);
       } else if (i < 3*sec) {
          color[0] -= 255/(sec);
          color[1] = 255;
       } else if (i < 4*sec) {
          color[0] = 0;
          color[1] -= 255/sec;
          color[2] += 255/sec;
       } else {
          color[1] = 0;
          color[0] += 255/sec;
          color[2] = 255;
       }
       Color NEW = new Color(color[0], color[1], color[2]); 
       return NEW;

   }
   
   

   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   // HELPER METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
   
   //Turns a user's y/n into a boolean for the given prompt
   public boolean yesTo(String prompt) {
      System.out.print(prompt + " (y/n)? ");
      String response = console.nextLine().trim().toLowerCase();
      while (!response.equals("y") && !response.equals("n")) {
         System.out.println("Please answer y or n.");
         System.out.print(prompt + " (y/n)? ");
         response = console.nextLine().trim().toLowerCase();
      }
      return response.equals("y");
    }
    
   //rounds double to nearest int
   private static double round(double n) {
     return Math.round(n * 10.0) / 10.0;
   }  

}
