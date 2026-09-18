import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Given an existing file, this will check if the data inside the file
 * is formatted in a particular manner. It first checks the declared
 * rows and columns of the following data table in the first line.
 * During the reading of the table, it ensures the dimensions are correct
 * and no invalid values (such as characters or non-positive declarations)
 * exist.
 * 
 * @author Amira Freeman
 */
public class FormatChecker {
    /**
     * Verifies a given file and compares it directly to a strict expected format.
     * 
     * @param filename of which to read and validate
     * @return true if the file matches the expected format, false otherwise
     */
    public static boolean checkFile(String filename) {
        // Design note: each possible path closes the appropriate scanners instead
        // of using a large try/finally block. This makes it a bit redundant but
        // this makes every false return or exception explicit.

        String currentLine;
        Scanner linescan, fieldscan = null;
        int declaredRows, declaredCols;

        // Ensure file exists
        File file = new File(filename);
        try {
            linescan = new Scanner(file);
        } catch (FileNotFoundException exception) {
            System.out.println(exception.toString());
            return false;
        }

        // Cannot read an empty file
        if (linescan.hasNextLine()) {
            currentLine = linescan.nextLine().trim();
        } else {
            System.out.println("File is empty");
            linescan.close();
            return false;
        }

        // Cannot have more or less than two declared dimensions
        String[] header = currentLine.split("\\s+"); // Whitespace delimiter from warmup project
        if (header.length != 2) {
            System.out.println("Declaration header in wrong format");
            linescan.close();
            return false;
        }

        // Write declarations to variables
        try {
            declaredRows = Integer.parseInt(header[0]);
            declaredCols = Integer.parseInt(header[1]);
        } catch (NumberFormatException exception) {
            System.out.println(exception.toString());
            linescan.close();
            return false;
        }

        // Cannot read a non-positive array
        if (declaredRows < 1 || declaredCols < 1) {
            System.out.println("Array declarations are undersized");
            linescan.close();
            return false;
        }

        // Read table data and verify table structure and contents
        int arrayRow = 0;
        int arrayCol;
        try {
            while (linescan.hasNextLine()) {
                currentLine = linescan.nextLine();

                // Keep reading even with empty lines (created due to valid3.dat sample)
                if (currentLine.trim().isEmpty()) {
                    continue;
                }
                if (arrayRow >= declaredRows) {
                    System.out.println("More rows than declared");
                    linescan.close();
                    return false;
                }

                arrayCol = 0;
                fieldscan = new Scanner(currentLine);
                while (fieldscan.hasNext()) {
                    if (arrayCol >= declaredCols) {
                        System.out.println("More columns than declared");
                        linescan.close();
                        fieldscan.close();
                        return false;
                    }

                    fieldscan.nextDouble();
                    arrayCol++;
                }
                
                if (arrayCol != declaredCols) {
                    System.out.println("Fewer columns than declared");
                    linescan.close();
                    fieldscan.close();
                    return false;
                }

                fieldscan.close();
                arrayRow++;
            }
        } catch (InputMismatchException exception) {
            System.out.println(exception.toString());
            linescan.close();
            if (fieldscan != null) {
                fieldscan.close();
            }
            return false;
        }

        if (arrayRow != declaredRows) {
            System.out.println("Fewer rows than declared");
            linescan.close();
            return false;
        }

        linescan.close();

        return true;
    }

    /**
     * Driver method to detect empty args and validate files.
     * 
     * @param args File names of which to read and validate
     */
    public static void main(String[] args) {
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