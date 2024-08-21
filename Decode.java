import java.util.*;
import java.io.*;

public class Decode {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Invalid number of arguments (accepts 2)");
            return;
        }

        String codebookFile = "codebook.txt";
        String zeroesAndOnesInput = args[0];
        String decodedTextOutput = args[1];

        // Read codebook file and store mappings in HashMap
        HashMap<String, Character> codebook = readBook(codebookFile);

        try {
            // Open input file for reading
            File fInput = new File(zeroesAndOnesInput);
            Scanner scanner = new Scanner(fInput);

            // Open output file for writing
            FileWriter writer = new FileWriter(decodedTextOutput);

            Node root = new Node(); // Create root of Huffman tree

            for (String symbol : codebook.keySet()) { // Build tree using codebook mappings
                char character = codebook.get(symbol);
                Node current = root;

                for (char bit : symbol.toCharArray()) { // Choose direction based on bit of symbol
                    if (bit == '0') { // If 0, Node child goes to the left
                        if (current.left == null) {
                            current.left = new Node();
                        }
                        current = current.left;
                    } else if (bit == '1') { // If 1, Node child goes to the right
                        if (current.right == null) {
                            current.right = new Node();
                        }
                        current = current.right;
                    }
                }

                // Set character at child node
                current.character = character;
            }
            
            Node current = root;

            while (scanner.hasNext()) { // Read input file bit by bit and decode Huffman text
                String bit = scanner.nextLine();
                for (char c : bit.toCharArray()) {

                    // Choose direction based on bit value
                    if (c == '0') {
                        current = current.left;
                    } else if (c == '1') {
                        current = current.right;
                    }

                    // If leaf node is reached, write decoded character to output file
                    if (current.isLeaf()) {
                        char decoded = current.character;
                        if (decoded == '\u0004') {
                            break;
                        }
                        writer.write(decoded);

                        // Reset to root for decoding next symbol
                        current = root;
                    }
                }
            }

            // Close input and output files
            scanner.close();
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found: " + zeroesAndOnesInput);
        } catch (IOException e) {
            System.out.println("Error writing output file: " + decodedTextOutput);
        }
    }

    private static HashMap<String, Character> readBook(String codebookFile) { // Read codebook file and return HashMap with symbol/character mappings
        HashMap<String, Character> book = new HashMap<>();

        try {
            // Open codebook file for reading
            File file = new File(codebookFile);
            Scanner scanner = new Scanner(file);

            // Read each line from codebook file
            while (scanner.hasNext()) { // Split each line into compressed symbol and character value; store mapping in the HashMap
                String line = scanner.nextLine();
                String[] parts = line.split(":");
                int charVal = Integer.parseInt(parts[0]);
                String compressed = parts[1];
                book.put(compressed, (char) charVal);
            }

            // Close codebook file
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Codebook file not found: " + codebookFile);
        }

        // Return populated codebook HashMap
        return book;
    }

    // Class representing a node in Huffman tree
    private static class Node {
        private char character;
        private Node left;
        private Node right;
    
        // Check if current node has no left and right child
        public boolean isLeaf() {
            return left == null && right == null;
        }
    }
}