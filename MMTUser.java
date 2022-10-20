import java.util.*;
import java.io.*;

/* This is a program to allow the user to create and draw Modular Multiplication Tables.
Once run, this program prompts the user to input information to create a desired table. 
It asks for the modulus (m), multiplier (a), and addValue (add).
The modulus determines how many equidistant points are on the circumference of the circle.
The multiplier and addValue determine how each line is drawn. For every point 'p' around the circle,
there is a line segment from 'p' to 'a*p + add (mod m)'. For standard tables, set 'add = 0'.

Once 'm', 'a', and 'add' are chosen, the user is asked how they want the table to be drawn. 
They can access advanced options and change the size, start, and count-by. 
These parameters change what is being drawn. 
They can also access special presentation. 
This option encompasses dots, labels, rainbow, directed lines, night vision, and gif. 
The program will allow the user to draw the created table until they no longer want to. Then, it will
allow the user to draw any other table that they wish. */

public class MMTUser {
   public static void main(String[] args) {
      Table m = new MMT();
      Scanner console = new Scanner(System.in);
      DrawTable d = new DrawTable();
      boolean answerD = true;
      boolean answerS = true;
      while(answerD) {
         answerS = true;
         System.out.println("Please enter the table that you wish to draw in the following format:");
         System.out.println("modulus(m) multiplier(a) addValue(t)");
         m.read(console);
         while (answerS) {
            d.drawFast(m);
            answerS = yesTo("Would you like to draw this table again?", console);
         }
         System.out.println();
         answerD = yesTo("Would you like to draw another table?", console);
      }
   }
   
   //Turns a user's y/n into a boolean for the given prompt
   public static boolean yesTo(String prompt, Scanner console) {
      System.out.print(prompt + " (y/n)? ");
      String response = console.nextLine().trim().toLowerCase();
      while (!response.equals("y") && !response.equals("n")) {
         System.out.println("Please answer y or n.");
         System.out.print(prompt + " (y/n)? ");
         response = console.nextLine().trim().toLowerCase();
      }
      return response.equals("y");
    }
}
