import java.util.*;
import java.io.*;

public class Encode {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Invalid number of arguments (accepts 2)");
            return;
        }

        String codebookFile = "codebook.txt";
        String input = args[0];
        String output = args[1];

        // Read codebook file and store mappings in HashMap
        HashMap<Integer, String> codebook = readBook(codebookFile);

        try {
            // Open input file for reading
            File fInput = new File(input);
            Scanner scanner = new Scanner(fInput);

            // Open output file for writing
            FileWriter writer = new FileWriter(output);

            while (scanner.hasNext()) { // Read each line from input file and convert line to character array
                String line = scanner.nextLine();
                char[] chars = line.toCharArray();

                for (char x : chars) { // Iterate over each character in line and write compressed representation to output file
                    int charVal = (int) x;
                    String compressed = codebook.get(charVal);
                    writer.write(compressed);
                }
            }

            // Append end-of-transmission Huffman code to output file
            int eot = '\u0004';
            String eotCompressed = codebook.get(eot);
            writer.write(eotCompressed);

            // Close input and output files
            scanner.close();
            writer.close();

        } catch (FileNotFoundException e) {
            System.out.println("Input file not found: " + input);
        } catch (IOException e) {
            System.out.println("Error writing output file: " + output);
        }

        
    }
    
    // Read codebook file and return HashMap with character mappings
    private static HashMap<Integer, String> readBook(String codebookFile) {
        HashMap<Integer, String> book = new HashMap<>();

        try {
            // Open codebook file for reading
            File file = new File(codebookFile);
            Scanner scanner = new Scanner(file);

            // Read each line from codebook file
            while (scanner.hasNext()) { // Split each line into character value and compressed representation
                String line = scanner.nextLine();
                String[] parts = line.split(":");
                int charVal = Integer.parseInt(parts[0]);
                String compressed = parts[1];
                book.put(charVal, compressed); // Store mapping in HashMap
            }

            scanner.close(); // Close codebook file
        } catch (FileNotFoundException e) {
            System.out.println("Codebook file not found: " + codebookFile);
        }
        
        return book; // Returns populated HashMap of codebook
    }
}