import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 */
public class FormatChecker {
    /**
     * 
     */
    public static boolean checkFile(String filename) throws FileNotFoundException, InputMismatchException {
        String currentLine;
        Scanner linescan, fieldscan;
        int declaredRows, declaredCols;
        double[][] table;

        // Ensure file exists
        File file = new File(filename);
        try {
            linescan = new Scanner(file);
        } catch (FileNotFoundException exception) {
            System.out.println(exception.toString());
            return false;
        }

        // Cannot have more or less than two declared dimensions
        currentLine = linescan.nextLine(); // I hate this form of scanning but it's my best solution
        String[] headerElements = currentLine.trim().split("\\s+"); // Whitespace delimiter from warmup project
        if (headerElements.length != 2) {
            System.out.println("Declaration header in wrong format");
            return false;
        }

        // Read declared rows and columns
        fieldscan = new Scanner(currentLine);
        fieldscan.useDelimiter("\\s+");
        try {
            declaredRows = fieldscan.nextInt();
            declaredCols = fieldscan.nextInt();
            table = new double[declaredRows][declaredCols];
        } catch (InputMismatchException exception) {
            System.out.println(exception.toString() + ": Declaration header has unexpected values");
            return false;
        }

        // Write table data to array
        int arrayRow = 0;
        int arrayCol;
        try {
            while (linescan.hasNextLine()) {
                currentLine = linescan.nextLine();
                
                // Keep reading even with trailing or ending newlines (created due to valid3.dat sample)
                if (currentLine.trim().isEmpty()) {
                    continue;
                }
                if (arrayRow >= declaredRows) {
                    System.out.println("More rows than declared");
                    return false;
                }

                arrayCol = 0;
                fieldscan = new Scanner(currentLine);
                while (fieldscan.hasNext()) {
                    if (arrayCol >= declaredCols) {
                        System.out.println("More columns than declared");
                        return false;
                    }

                    table[arrayRow][arrayCol] = fieldscan.nextDouble();
                    arrayCol++;
                }
                if (arrayCol != declaredCols) {
                    System.out.println("Fewer columns than declared");
                    return false;
                }
                arrayRow++;
            }
        } catch (InputMismatchException exception) {
            System.out.println(exception.toString());
            return false;
        }

        if (arrayRow != declaredRows) {
            System.out.println("Fewer rows than declared");
            return false;
        }

        return true;
    }

    // Driver class ...
    public static void main(String[] args) throws FileNotFoundException, InputMismatchException {
        // Don't waste my program's time.
        if (args.length < 1) {  
            System.out.println("Usage: $ java FormatChecker file1 [file2 ... fileN]");
            System.exit(1);
        }

        int fileIndex = 0;
        boolean isValidFile;
        String filename;
        while (fileIndex < args.length) {
            filename = args[fileIndex];

            System.out.println(filename);
            isValidFile = checkFile(filename);
            if (isValidFile) {
                System.out.println("VALID\n");
            } else {
                System.out.println("INVALID\n");
            }
            fileIndex++;
        }
    }
}