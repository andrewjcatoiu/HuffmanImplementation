import java.io.*;
import java.util.*;

// 100011100101001011111111011011100000100101101000011110110111010011001000101101001011111010100000001111101110010110111110111101011001100001001000010101001111000100111111101110011110111110111001001100000100101000010100000100000001010000011011110101100000111101011001101010011111001101101001100100110111100000011110100101101010010011011001111111101101100101010010111110000000100001000001001100000100110001110010011111000111011111001011111001001110010010111111111011110111110100110010001011010010111111111010110111100100000001011100110111000101100001001111010011111100001100110101100010100111110111100100000101101110111001001101101011010000001000001011011100101001111100100000111001011011010110011010011110100111000010100110001001001010001111011100111100110110111110101101011111011100010000001111110001110000010000101100011101011010111110010110100010010011001011111001001110010010111000010100110001000001010000001100011101111101110001011000010011110100111000010010111011111010110101101111111001100001001001011011011010100100110010011000010010000101010011111001111010011111100011100110011010001110001001110000101001101110000111011111111100000101110011110111111111010011001000101101001011111000000010110101111111100101101111111011001111001010011011111011100000111110000000101100101001010001011001111001010111111000111111111001010011111010110000011111011111010010100111111000010000001011010110011110110111010011001000101101001011111111101111001111101010111001111000000000111000111110001000111010110101111100110100101111001011100001110001110011110111001100110111010101001010110100001011111001010111111100111101111101010000000010011111001000001000010111001111000101110001111100001000111100101000010011011100011111100011111000100100001011010010110111010000001001101111001001111010110011010100001110010011111000000010110000100011101011100101111001101001111010011100001100100101011110101110000000100000111001101001101111101001111101110011100100111110101101010001011010110000011111011100001000001011011100000100110110111110100011111001011111001001110010010111001111011010011001011000011011
// Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
public class HuffTest {
    public static void main(String[] args) {
        try {
            // Create file objects based on command line arguments
            File inputFile = new File(args[0]);
            File outputFile = new File(args[1]);

            // Create scanners to read input and output files
            Scanner inputScanner = new Scanner(inputFile);
            Scanner outputScanner = new Scanner(outputFile);

            // Initialize strings to store content of input and output files
            String iString = "";
            String oString = "";

            // Store content of input file
            while (inputScanner.hasNextLine()) {
                iString += inputScanner.nextLine();
            }

            // Store content of output file
            while (outputScanner.hasNextLine()) {
                oString += outputScanner.nextLine();
            }

            int x = 0;
            int y = 0;
            boolean start = true;

            // Initialized variable that keeps track of how many characters are skipped in the input
            int skip = 0;

            // Loop through the input and output strings and compare the characters at each index
            while (start) {

                // Check that there are still characters to compare in both the input and output strings
                if ((iString.length() > x) && (oString.length() > y)) {

                    // Check if the current character in the input string is a valid ASCII character
                    if (iString.charAt(x) <= 254 && iString.charAt(x) >= 7 || iString.charAt(x) == 4) {
                        
                        // If the characters at the current index do not match, print a failure message and exit the loop
                        if (iString.charAt(x) != oString.charAt(y)) {
                            System.out.println("FAIL input " + iString.charAt(x) + " @ " + x + " output " + oString.charAt(y) + " @ " + y);
                            start = false;
                        } else {
                            // If the characters at the current index match, increment both indexes
                            x++;
                            y++;
                        }
                    // If the current character in the input string is not a valid ASCII character
                    } else {
                        x++;
                        skip += 1;
                    }
                // If there are no more characters to compare
                } else {
                    start = false;
                }
            }

            // Checks if output is empty
            if (oString.length() == 0) {
                System.out.println("FAIL input " + iString.charAt(x) + " @ " + x + " output null @ 0");
            }

            // Checks if the input length is greater than the output length
            if (iString.length() - skip > oString.length()) {
                System.out.println("FAIL input " + iString.charAt(x) + " @ " + x + " output " + oString.charAt(y - (iString.length() - oString.length())) + " @ " + (y - (iString.length() - oString.length())));
            } else {
                // Passes the files if they have the same length and match with the same exact characters
                if (iString.length() <= x && oString.length() <= y) {
                    System.out.println("PASS");
                }
            }

            inputScanner.close();
            outputScanner.close();

        // Catches exceptions
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: File not found");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("ERROR: Array index out of bounds");
        } catch (StringIndexOutOfBoundsException e) {
            System.err.println("ERROR: String index out of bounds");
        }
    }
}