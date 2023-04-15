import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Mehmet_Devrekoglu_2020510028
 */
public class Mehmet_Devrekoglu_2020510028 {

    public static int dp(int n, int p, int c, int[] playerSalaries, int[] playerDemands) {

        int[][] arr;
        int totalCost = 0;
        

        // n-> number of years wanted to be planned
        // p-> number of players you raise in a year
        // c-> cost of a coach for a year
        int max = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            int differance = p - playerDemands[i];
            
            if(differance > max){
                max = differance;
            }
            //System.out.printf("(%d) Difference between produced and demands: (%d)\n", i, (differance));
            System.out.println(differance);
        }
        //System.out.printf("Max differance: %d\n", max);

        arr = new int[n][max + 1];
        for (int i = 1; i <= n; i++) {
            for(int j = 0; j <= playerDemands[i]; j++){
                
               

            }
        }

        return totalCost;
    }

    public static int[] readAndAssign(String fileName) {

        BufferedReader reader; // Reading object
        String line; // Line read from the file
        int[] arr = new int[fileSize(fileName)]; // Array to be returned

        try {

            reader = new BufferedReader(new FileReader(fileName)); // Assigning the file to the reader
            reader.readLine(); // Skipping the first line because it is meaningless
            while ((line = reader.readLine()) != null) { // Reading the file line by line until it ends
                String[] parts = line.split("\t"); // Splitting the line into parts
                arr[Integer.parseInt(parts[0])] = Integer.parseInt(parts[1]); // Assigning the given indexses and values
                                                                              // to the array
            }
            reader.close(); // Closing the reader

        } catch (IOException e) { // If an error occurs while reading the file
            // TODO Auto-generated catch block
            System.out.println("Error in reading file"); // Warning message
            e.printStackTrace(); // Printing the error
            System.exit(0); // Exiting the program
        }

        return arr; // Returning the array
    }

    public static int fileSize(String fileName) {

        BufferedReader reader; // Reading object
        int lines = 0; // Number of lines in the file

        try {

            reader = new BufferedReader(new FileReader(fileName)); // Assigning the file to the reader
            while (reader.readLine() != null) { // Reading the file line by line until it ends
                lines++;
            }
            reader.close(); // Closing the reader

        } catch (IOException e) { // If an error occurs while reading the file
            // TODO Auto-generated catch block
            System.out.println("Error in reading file"); // Warning message
            e.printStackTrace(); // Printing the error
            System.exit(0); // Exiting the program
        }

        return lines; // Returning the number of lines in the file
    }

    public static void main(String[] args) {

        System.out.println("\nHello World!\n");

        int[] playerSalaries = readAndAssign("players_salary.txt");
        int[] playerDemands = readAndAssign("yearly_player_demand.txt");

        int n = 15; // n-> number of years wanted to be planned
        int p = 5; // p-> number of players you raise in a year
        int c = 10; // c-> cost of a coach for a year

        int minimumCost = dp(n, p, c, playerSalaries, playerDemands);
        System.out.println("\nMinimum cost: " + minimumCost);
        //System.out.println("Number of years wanted to be planned: " + n);
        //System.out.println("Number of players you raise in a year: " + p);
        System.out.println("Cost of a coach for a year: " + c);
    }
}