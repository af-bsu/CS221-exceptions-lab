import java.util.Scanner;

public class FormatChecker {
    private String delimiter = "\\s+";

    // Utility methods go here
    public boolean checkFileExists(String filename) throws FileNotFoundException {
        try {
            File testFile = new File(filename);
        } catch (FileNotFoundException e) {
            e.toString();
            return false;
        }

        return true;
    }

    public boolean checkTable(File file) {
        int rows, cols;
        Scanner linescan = new Scanner(file);
        linescan.close();
    }

    public static void main(String[] args) {
        // Don't waste my program's time.
        if (args.length < 1) {
            System.out.println("No arguments (valid file names) provided.");
            System.out.println("Usage: $ java FormatChecker file1 [file2 file3 ... fileN]");
            System.exit(1);
        }

        int fileIndex = 0;
        while (fileIndex < args.length) {
            try {
                File file = new File(args[fileIndex]);
            } catch (FileNotFoundException e) {
                e.toString();
                System.out.println("INVALID\n");
            }
        }
    }
}