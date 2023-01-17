import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;

/**
 * Car Data Analysis: Project 3 
 *
 * Gateway Programming: Java
 * Johns Hopkins University
 * Fall 2022
 * Larue Linder 
 */
public class CarDataAnalysis {

   // menu options
   static final int BRAND_QUERY = 1;
   static final int TWO_HIGHEST_PRICES_QUERY = 2;
   static final int RANGE_QUERY = 3;
   static final int BEST_VALUE_QUERY = 4;
   static final int QUIT = 5;

   // column index constants for car data file
   static final int BRAND = 2;
   static final int YEAR = 4;
   static final int MILEAGE = 6;
   static final int PRICE = 1;

   /**
    * Counts the number of lines in a given plain-text file.
    * @param filename The file whose lines are to be counted.
    * @return the number of lines in the file.
    * @throws FileNotFoundException
    */
   public static int countFileLines(String filename)
                                    throws FileNotFoundException {

      int cnt = 0; 
      FileInputStream fileInStream = new FileInputStream(filename);
      
      Scanner inFS = new Scanner(fileInStream);
      while (inFS.hasNextLine()) {
         cnt++;
         inFS.nextLine();
      }
      
      return cnt;
   }

   /**
    * Print the program menu to the console.
    */
   public static void printMenu() {

      System.out.printf("[%d]: Average price of brand.\n", BRAND_QUERY);
      System.out.printf("[%d]: Two highest prices.\n",
             TWO_HIGHEST_PRICES_QUERY);
      System.out.printf("[%d]: Average price in year and mileage range.\n",
             RANGE_QUERY);
      System.out.printf("[%d]: Best value.\n", BEST_VALUE_QUERY);
      System.out.printf("[%d]: Quit.\n", QUIT);
      System.out.print("Please select an option: ");

   }
   
   /** 
   *populates an array with the wanted column from the csv file.
   *@param filename the file that holds the data being populated.
   *@param column
   * @param array the array that is holding the column 
   *@return the filled array
   *@throws FileNotFoundException
   */ 
 
   public static String[] fillArray(String filename, int column, 
                                     String[] array) 
                                     throws FileNotFoundException {
      
      FileInputStream fileInStream = new FileInputStream(filename);
      Scanner fileScanner = new Scanner(fileInStream);
      fileScanner.nextLine(); // do not start on first line 
      
      String str = fileScanner.nextLine(); 
      
      for (int i = 0; i < 2498; i++) {
         String[] splitLine = str.split(",");
         array[i] = splitLine[column];
         str = fileScanner.nextLine();
         
      }
      return array;
    
   }
   
   /**
   * converts an array of strings to ints.
   * @param array that is being converted. 
   * @return the array filled with ints. 
   */
   public static int[] convertToInt(String[] array) {
      
      int len = array.length;
      int[] intArray = new int[len];
      
      for (int i = 0; i < len - 1; i++) {
         intArray[i] = Integer.parseInt(array[i]);
      }
      
      return intArray;
   }
   /**
   * converts an array of strings to doubles.
   * @param array that is being converted.
   * @return the array filled with dobules. 
   */
  
   public static double[] convertToDouble(String[] array) {
      
      int len = array.length;
      double[] doubleArray = new double[len];
      
      for (int i = 0; i < len - 1; i++) {
         doubleArray[i] = Double.parseDouble(array[i]);
      }
      
      return doubleArray;
   }
   
   /**
   *checks if input is valid.
   * @param str 
   * @param brands
   *@return bool 
   */
   
   public static boolean isValidGuess(String str, String[] brands) {
    
      boolean bool = false; 
    
      for (int i = 0; i < brands.length; i++) {
    
         if (str.equals(brands[i])) {
            bool = true;
         }
      } 
      return bool;
   }
   
   
   /**
   * outputs the number of entries in the loaded data whose brand matches. 
   * And their average price. 
   * Saves all matching rows to a csv file named according to the given input
   * @param brand that holds the brands 
   *@param year that holds the years
   * @param mileage that holds mileage 
   *@param price that holds price
   * @throws FileNotFoundException
   */
 
   public static void averagePriceOfBrand(String[] brand, int[] year, 
                                          double[] mileage, int[] price) 
                                          throws FileNotFoundException {
      
      Scanner scnr = new Scanner(System.in);
      
      System.out.println("Please enter a car brand: ");  
      String b = scnr.next();
      System.out.println("please enter an outputfile name: ");
      String filename = scnr.next();
      
      String checker = b.toLowerCase();
      
      //method that checks for valid input if true run rest if not print menu 
      boolean bool = isValidGuess(checker, brand);
      
      if (bool) {
         FileOutputStream fileOutStream = new FileOutputStream(filename);
         PrintWriter outFs = new PrintWriter(fileOutStream);
      
         int cnt = 0; 
         int totPrice = 0;
      
         for (int i = 0; i < brand.length - 1; i++) {
         
            if (brand[i].equalsIgnoreCase(b)) {
               cnt++;
               totPrice += price[i];
            
               outFs.print(i + "," + " " + brand[i] + "," + " " + year[i] + "," 
                  + " " + mileage[i] + "," + " " + price[i]);
            
               outFs.println();
            
            }
         }
         double avgPrice = (double) totPrice / cnt;
      
         System.out.print("There are " + cnt + " matching entries for brand " 
            + b + " with an average price of $");
         System.out.printf("%.2f", avgPrice);
         System.out.print(".");
         System.out.println();
      
      } 
      else {
         System.out.println("There are no matching entries for brand " 
            + b + ".");
     
         Scanner keyboard = new Scanner(System.in);
     
         // while the user doesn't choose to quit...
         int option = 0;
         while (option != QUIT) {

            // display the menu and get an option
            printMenu();
            option = keyboard.nextInt();

         
            switch (option) {
               case BRAND_QUERY:
                  averagePriceOfBrand(brand, year, mileage, price); 
                  break;
               case TWO_HIGHEST_PRICES_QUERY:
                  twoHighestPrices(price);
                  break;
               case RANGE_QUERY:
                  avgPriceInYearAndMileage(year, mileage, price);
                  break;
               case BEST_VALUE_QUERY:
                  bestValue(brand, year, mileage, price);
                  break;
               case QUIT:
                  System.out.println("Thank you for using the program!");
                  break;
               default:
                  System.out.println("Invalid option.");

            }

            // leave empty line for next printing of menu
            System.out.println();

         }
      
      }
      
   }
   
   /**
   * outputs the two highest prices out of the dataset. 
   * @param priceArray that holds all the prices 
   */
  
   public static void twoHighestPrices(int[] priceArray) {
      
      int maxOne = 0; 
      int maxTwo = 0; 
      
      for (int i = 0; i < priceArray.length; i++) {
         if (maxOne < priceArray[i]) {
            maxTwo = maxOne;
            maxOne = priceArray[i];
         } else if (maxTwo < priceArray[i]) {
            maxTwo = priceArray[i];
         }
      }
      System.out.println("The two highest prices are " + "$" + maxOne + ".00" 
         + " and " + "$" + maxTwo + ".00" + ".");
      
   }
   
   /**
   * outputs how many entries in the loaded data are within the range inputted. 
   * and their average price. 
   * @param year that holds the years 
   *@param mileage that holds mileage 
   * @param price that holds price
   */
 
   public static void avgPriceInYearAndMileage(int[] year, 
                                               double[] mileage, int[] price) {
      
      Scanner scnr = new Scanner(System.in);
      
      System.out.println("Please enter the year lower bound: ");
      int yearLower = scnr.nextInt();
      
      System.out.println("Please enter the year upper bound: ");
      int yearUpper = scnr.nextInt();
      
      System.out.println("Please enter the mileage lower bound: ");
      int mileageLower = scnr.nextInt();
      
      System.out.println("Please enter the mileage upper bound: ");
      int mileageUpper = scnr.nextInt();
      
      int cnt = 0; 
      int totPrice = 0;
      
      for (int i = 0; i < year.length; i++) {
         if ((yearLower <= year[i] && year[i] <= yearUpper) && 
             (mileageLower <= mileage[i] && mileage[i] <= mileageUpper)) {
            cnt++;
            totPrice += price[i];
         }
      }
      
      double avgPrice = (double) (totPrice) / cnt; 
      System.out.print("There are " + cnt + 
         " matching entries for year range [" 
         + yearLower + ", " + yearUpper + "] and mileage range [" 
         + mileageLower + ", " + mileageUpper 
         + "] with an average price of $");
      
      System.out.printf("%.2f", avgPrice);
      System.out.print(".");
      System.out.println();
   }
   
   /**
   * outputs the "best (highest) value" entry's year, brand, miles, and price.
   * From among those exceeding the given thresholds.
   * @param brand holding brands 
   *@param year holding years 
   * @param mileage holding mileage 
   *@param price holding price
   */
   public static void bestValue(String[] brand, int[] year,
                                double[] mileage, int[] price) {
      
      Scanner scnr = new Scanner(System.in); 
      
      System.out.println("Please enter lower mileage threshold: ");
      int mileageLower = scnr.nextInt();
      
      System.out.println("Please enter lower price threshold: ");
      int priceLower = scnr.nextInt();
      
      //formula year - (mileage/ 13500) - (price/ 1900)
      double bestVal = year[0] - (mileage[0] / 13500.0) - (price[0] / 1900.0);
      double checkVal;
      int holder = 0;
      
      for (int i = 0; i < brand.length; i++) {
         checkVal = year[i] - (double) (mileage[i] / 13500.0) 
            - (double) (price[i] / 1900.0);
         
         if ((mileage[i] > mileageLower && price[i] > priceLower) 
            && (checkVal > bestVal)) {
            bestVal = checkVal;
            holder = i;
         }
      
      }
      
      System.out.print("The best-value entry with more than " + mileageLower 
         + ".0 miles and a price of higher than $" +
         priceLower + " is a " + year[holder] + " " 
         + brand[holder] + " with " + mileage[holder] + " miles" +
         " for a price of $" + price[holder]);
      System.out.print(".");
      System.out.println();
      
   }

   /**
    * Drive the Car Data Analysis program.
    * @param args This program does not take commandline arguments.
    * @throws FileNotFoundException
    */
   public static void main(String[] args) throws FileNotFoundException {

      // output purpose
      System.out.println("Welcome to the car dataset analysis program.");

      // get input filename (e.g. "USA_cars_datasets.csv")
      System.out.print("Please enter input csv filename: ");
      Scanner keyboard = new Scanner(System.in);
      String filename = keyboard.nextLine();
      
      // count the number of rows in the file (ignore headers line)
      int rowCount = countFileLines(filename) - 1;
      System.out.println("File has " + rowCount + " entries.");
      System.out.println();
     
      String[] brand = new String[rowCount]; // column 2
      int[] year = new int[rowCount]; // clumn 4
      double[] mileage = new double[rowCount]; //column 6
      int[] price = new int[rowCount]; /// column 1
      
      String[] tmpArray = new String[rowCount];
      String[] tmpArrayTwo = new String[rowCount];
      String[] tmpArrayThree = new String[rowCount];
      String[] tmpArrayFour = new String[rowCount];
      String[] tmpArrayFive = new String[rowCount];
      
     
        
      brand = fillArray(filename, BRAND, tmpArray); 
      
      tmpArrayTwo = fillArray(filename, YEAR, tmpArrayTwo);
     
      year = convertToInt(tmpArrayTwo); 
    
     
      tmpArrayThree = fillArray(filename, PRICE, tmpArrayThree);
      price = convertToInt(tmpArrayThree); 
     
      tmpArrayFour = fillArray(filename, MILEAGE, tmpArrayFour);
      mileage = convertToDouble(tmpArrayFour); 

      // while the user doesn't choose to quit...
      int option = 0;
      while (option != QUIT) {

         // display the menu and get an option
         printMenu();
         option = keyboard.nextInt();

         
         switch (option) {
            case BRAND_QUERY:
               averagePriceOfBrand(brand, year, mileage, price); 
               break;
            case TWO_HIGHEST_PRICES_QUERY:
               twoHighestPrices(price);
               break;
            case RANGE_QUERY:
               avgPriceInYearAndMileage(year, mileage, price);
               break;
            case BEST_VALUE_QUERY:
               bestValue(brand, year, mileage, price);
               break;
            case QUIT:
               System.out.println("Thank you for using the program!");
               break;
            default:
               System.out.println("Invalid option.");

         }

         // leave empty line for next printing of menu
         System.out.println();

      }

   }

}
