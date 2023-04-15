import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Trial {

    public static int dp(int n, int p, int c, int[] playerSalaries, int[] playerDemands) {

        int[][] arr;
        int keepedMan = 0;
        int totalCost = 0;
        int cost;

        // n-> number of years wanted to be planned
        // p-> number of players you raise in a year
        // c-> cost of a coach for a year

        for (int i = n; i > 0; i--) {
            System.out.printf("(%d) Difference between produced and demands: (%d)\n", i, (p - playerDemands[i]));
        }

        for (int i = n; i > 0; i--) {
            int differance = p - playerDemands[i];
            cost = 0;
            //System.out.printf("(%d) Difference between produced and demands: (%d)\n", i, (differance));

            // Find the nearest point that difference is positive
            // return (a > b) ? a : b;
            int j = i - 1;
            while (j > 0 && differance < 0) {
                int dif = p - playerDemands[j];

                if (dif > 0) {

                    int q = j + 1;
                    while(q != i){
                        dif += p - playerDemands[q];                     
                        q++;
                    }

                    if(dif > 0){
                        cost = playerSalaries[dif] * (i - j);
                        if (cost < c * dif) {
                            totalCost += cost;
                            playerDemands[j] += dif;
                            differance += dif;

                            // keep these mans
                            // Print information
                        }
                        else{
                            break;
                        }
                    }
                }

                j--;
            }

            if (differance < 0) {
                totalCost += c * Math.abs(differance);
                // print information
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

        System.out.println("\nHello World!");

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