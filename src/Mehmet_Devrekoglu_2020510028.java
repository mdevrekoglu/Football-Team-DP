import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Mehmet_Devrekoglu_2020510028
 */
public class Mehmet_Devrekoglu_2020510028 {

    public static int dp(int n, int p, int c, int[] playerSalaries, int[] playerDemands) {


        int[] remaningPlayer = new int[n + 1];
        //double[] cost = new double[n + 1];
        int min = Integer.MAX_VALUE;
        double[][] arr;

        for (int i = 1; i < remaningPlayer.length; i++) {
            int difference = p - playerDemands[i];
            
            if(difference < 0 && remaningPlayer[i - 1] == 0){
                remaningPlayer[i] = 0;
            }else if(difference < 0 && remaningPlayer[i - 1] != 0){
                if(difference * -1 > remaningPlayer[i - 1]){
                    remaningPlayer[i] = 0;
                }else{
                    remaningPlayer[i] = remaningPlayer[i - 1] + difference;
                }
            }else if(difference == 0){
                remaningPlayer[i] = remaningPlayer[i - 1];
            }else if(difference > 0){
                remaningPlayer[i] = remaningPlayer[i - 1] + difference;
            }

            System.out.printf("(%d) Remaning players by years: (%d)\n", i, (remaningPlayer[i]));
        }
        System.out.println();

        
        for (int i = 1; i <= n; i++) {
            int difference = p - playerDemands[i];

            if(difference < min){
                min = difference;
            }

            System.out.printf("(%d) difference between produced and demands: (%d)\n", i, (difference));
        }
        min *= -1;
        System.out.println();
        

        // n-> number of years wanted to be planned
        // p-> number of players you raise in a year
        // c-> cost of a coach for a year
        // System.out.printf("Max difference: %d\n", max);
        arr = new double[min + 2][n + 1];
        for(int i = 1; i <= min + 1; i++){
            int[] remaningPlayerCopy = remaningPlayer.clone();
            for (int j = n; j >= 1; j--) {
                
                // Difference between produced and demands
                int difference = p - playerDemands[j];

                // if difference is positive, it means that you have more players than you need
                if(difference >= 0){
                    arr[i][j] = arr[i][(j != n) ? j + 1 : j];
                }
                // if difference is negative, it means that you have less players than you need
                else{ 
                    
                    // There are three limits for the difference
                    // It can take maximum i-1 players from the previous year
                    // It can take maximum the difference between produced and demands
                    // It can take maximum the remaning players from the previous year
                    double cost = 0;
                    int limit = Math.min(Math.min(i - 1, difference * -1), remaningPlayerCopy[j - 1]);
                    int index = findIndex(remaningPlayerCopy, j - 1);
                    // System.out.println("Limit: " + limit);

                    // If next year taken players, you must take what is left from the previous year
                    if(remaningPlayerCopy[(j != n) ? j + 1 : j] < 0){
                        
                        for (int k = index; k < j; k++) {
                            if(remaningPlayerCopy[k] +  remaningPlayerCopy[(j != n) ? j + 1 : j] >= difference * -1){
                                cost += (double) playerSalaries[remaningPlayerCopy[k]] * (double) (difference * -1) / (double) remaningPlayerCopy[k];
                            }else if(remaningPlayerCopy[k] + remaningPlayerCopy[(j != n) ? j + 1 : j] > 0
                                 && remaningPlayerCopy[k] + remaningPlayerCopy[(j != n) ? j + 1 : j] < difference * -1){
                                cost += (double) playerSalaries[remaningPlayerCopy[k]] * (double) (remaningPlayerCopy[k] + remaningPlayerCopy[(j != n) ? j + 1 : j]) / (double) remaningPlayerCopy[k];
                            }
                        }

                        arr[i][j] = arr[i][(j != n) ? j + 1 : j] + cost;
                        remaningPlayerCopy[j] = difference + remaningPlayerCopy[(j != n) ? j + 1 : j];
                        continue;
                    }
                    
                    
                    int num = -1;
                    // If the limit is 0,
                    // it means that you can't take any player from the previous year
                    if(limit == i - 1){
                        num = remaningPlayerCopy[j - 1] - limit;
                    }
                    // If the limit is difference * -1, it means that
                    // you can maximum take diffrence * -1 players from the previous year
                    else if(limit == difference * -1){
                        num = remaningPlayerCopy[j - 1] - limit;
                    }
                    // If the limit is remaning players from the previous year,
                    // it means that you can maximum take remaning players from the previous year
                    else if(limit == remaningPlayerCopy[j - 1]){
                        num = remaningPlayerCopy[j - 1] - limit;
                    }

                    for(int k = index; k < j; k++){
                        if(remaningPlayerCopy[k] <= num){
                            remaningPlayerCopy[k] = 0;
                        }else{
                            remaningPlayerCopy[k] -= num;
                        }
                    }

                    // cost += ((difference * -1) - remaningPlayerCopy[j - 1]) * c + arr[i][(j != n) ? j + 1 : j];
                    cost += ((difference * -1) - remaningPlayerCopy[j - 1]) * c;

                    for(int k = index; k < j; k++){
                        if(remaningPlayerCopy[k] <= remaningPlayerCopy[j - 1]){
                            cost += (double) playerSalaries[remaningPlayerCopy[k]];
                        }else{
                            cost += (double) playerSalaries[remaningPlayerCopy[k]] * (double) remaningPlayerCopy[j - 1] / (double) remaningPlayerCopy[k];
                        }        
                    }

                    double val = arr[i - 1][j] - arr[i - 1][(j != n) ? j + 1 : 0];

                    // If the limit is 0 cost has to be calculated value
                    if(i == 1){
                        arr[i][j] = cost + arr[i][(j != n) ? j + 1 : j];
                    }else if(cost <= val){ // cost < arr[i - 1][j] && remaningPlayerCopy[j - 1] != 0
                        arr[i][j] = cost + arr[i][(j != n) ? j + 1 : j];
                        
                        // !!!!!!!!!!!!
                        if(remaningPlayerCopy[j - 1] != 0){
                            remaningPlayerCopy[j] = remaningPlayerCopy[j - 1] * -1;
                        }
                                              
                    }else{

                        // if previous calculated value is smaller than the calculated value
                        // it means that you have to take the previous value
                        arr[i][j] = val + arr[i][(j != n) ? j + 1 : j];

                        // Remove the extra players from the previous year
                        for(int k = index; k < j; k++){
                            if(remaningPlayerCopy[k] <= remaningPlayerCopy[j - 1]){
                                remaningPlayerCopy[k] = 0;
                            }else{
                                remaningPlayerCopy[k] -= remaningPlayerCopy[j - 1];
                            }
                        }
                    }
                }
            }
        }


        //printInt1Arr(cost);
        printInt2Arr(arr);
        return (int)arr[min + 1][1];
    }

    public static int findIndex(int arr[], int start){

        for (int i = start; i >= 1; i--) {
            if(arr[i] == 0){
                return i + 1;
            }
        }
        return 1;
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

    public static void printInt2Arr(double[][] arr){
        
        System.out.println();
        System.out.print("\t");
        for (int i = 0; i < arr[0].length; i++) {
            System.out.printf("%d.\t", i);
        }
        System.out.println();

        for(int i = 0; i < arr.length; i++){
            System.out.printf("%d.\t", i);
            for(int j = 0; j < arr[i].length; j++){
                System.out.printf("%.2f\t", arr[i][j]);
            }
            System.out.println();
        }
    }

    public static void printInt1Arr(double[] arr){
            
        System.out.println();
        System.out.print("\t");
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("%d.\t", i);
        }
        System.out.println();

        System.out.print("\t");
        for(int i = 0; i < arr.length; i++){
            System.out.printf("%.2f\t", arr[i]);
        }
        System.out.println();
    }
    public static void main(String[] args) {

        System.out.println("\nHello World!\n");

        int[] playerSalaries = readAndAssign("players_salary.txt");
        int[] playerDemands = readAndAssign("yearly_player_demand.txt");

        int n = 50; // n-> number of years wanted to be planned
        int p = 5; // p-> number of players you raise in a year
        int c = 10; // c-> cost of a coach for a year

        int minimumCost = dp(n, p, c, playerSalaries, playerDemands);
        System.out.println("\nMinimum cost: " + minimumCost);

        System.out.println("Number of years wanted to be planned: " + n);
        System.out.println("Number of players you raise in a year: " + p);
        System.out.println("Cost of a coach for a year: " + c);
    }
}