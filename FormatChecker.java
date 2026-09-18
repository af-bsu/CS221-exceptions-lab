import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 */
public class FormatChecker {
    /**
     * 
     */
    public static boolean checkFile(String filename) throws FileNotFoundException {
        int rows, cols, foundRows, foundCols;
        double[][] table;

        File file = new File(filename);
        Scanner linescan, fieldscan;
        try {
            linescan = new Scanner(file);
        } catch (FileNotFoundException exception) {
            exception.toString();
            return false;
        }

        

        return true;
    }

    // Driver class ...
    public static void main(String[] args) throws FileNotFoundException {
        // Don't waste my program's time.
        if (args.length < 1) {  
            System.out.println("Usage: $ java FormatChecker file1 [file2 ... fileN]");
            System.exit(1);
        }

        int fileIndex = 0;
        boolean isValidFile;
        while (fileIndex < args.length) {
            String filename = args[fileIndex];
            isValidFile = checkFile(filename);
            if (isValidFile) {
                System.out.println("VALID\n");
            } else {
                System.out.println("INVALID\n");
            }
        }
    }
}