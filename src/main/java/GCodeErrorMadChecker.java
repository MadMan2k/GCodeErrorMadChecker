import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * GCodeErrorMadChecker checks a GCode file for suspicious lines based on the ASCII table or line length
 */
public class GCodeErrorMadChecker {
    private static final String START_MARKER = ";GCodeErrorMadChecker_Start";
    private static final String END_MARKER = ";GCodeErrorMadChecker_End";

    /**
     * Main method to run the GCodeErrorMadChecker.
     *
     * @param args Command line arguments. Expects the file path as an argument.
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java GCodeErrorMadChecker <filepath>");
            System.exit(1);
        }

        String filename = args[0];

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineCounter = 0;
            int badLineCounter = 0;
            List<Integer> badLineNumbers = new ArrayList<>();
            boolean checkerActivated = false;
            boolean checkerActivatedFlag = false;
            boolean checkerDisactivatedFlag = false;

            while ((line = br.readLine()) != null) {
                lineCounter++;

                if (isStartMarker(line)) {
                    checkerActivated = true;
                    checkerActivatedFlag = true;
                }

                if (isEndMarker(line)) {
                    checkerActivated = false;
                    checkerDisactivatedFlag = true;
                }

                if (checkerActivated && isSuspiciousLine(line)) {
                    badLineCounter++;
                    badLineNumbers.add(lineCounter);
                    System.out.println("Error at line " + lineCounter + "; line length " + line.length() + ": " + line);
                }
            }

            if (checkerActivatedFlag && checkerDisactivatedFlag) {
                printNoMarkersFound();
            } else {
                printResult(lineCounter, badLineCounter, badLineNumbers);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the line contains the start marker.
     *
     * @param line The line to check.
     * @return True if the line contains the start marker, otherwise false.
     */
    private static boolean isStartMarker(String line) {
        return line.startsWith(START_MARKER);
    }

    /**
     * Checks if the line contains the end marker.
     *
     * @param line The line to check.
     * @return True if the line contains the end marker, otherwise false.
     */
    private static boolean isEndMarker(String line) {
        return line.startsWith(END_MARKER);
    }

    /**
     * Checks if the line is suspicious based on length and character patterns.
     *
     * @param line The line to check.
     * @return True if the line is suspicious, otherwise false.
     */
    private static boolean isSuspiciousLine(String line) {
        return !line.isBlank() && (!line.matches("[\\x20-\\x7E]+") || line.length() > 50);
    }

    /**
     * Prints the result of the GCodeErrorMadChecker.
     *
     * @param lineCounter       Total number of lines checked.
     * @param badLineCounter    Number of suspicious lines found.
     * @param badLineNumbers    List of line numbers with suspicious content.
     */
    private static void printResult(int lineCounter, int badLineCounter, List<Integer> badLineNumbers) {
        String result = "\n"
                + "-----GCodeErrorMadChecker-----"
                + "\n\n"
                + "Lines checked: " + lineCounter
                + "\n"
                + "Suspicious lines found: " + badLineCounter
                + "\n"
                + "Suspicious lines numbers: " + badLineNumbers
                + "\n\n"
                + "-----------------------------";

        System.out.println(result);
    }

    private static void printNoMarkersFound() {
        String noMarkersFound = "\n"
                + "-----GCodeErrorMadChecker-----"
                + "\n\n"
                + "Looks like you forgot about Start and End Markers"
                + "\n"
                + "You need this markers to specify the range for checking"
                + "\n\n"
                + "Add the Start Marker"
                + "\n"
                + ";GCodeErrorMadChecker_Start"
                + "\n"
                + "to the end of your Machine Starting Gcode in your slicer"
                + "\n\n"
                + "Add the End Marker"
                + "\n"
                + ";GCodeErrorMadChecker_End"
                + "\n"
                + "to the beginning of your Machine Ending Gcode in your slicer"
                + "\n\n"
                + "Generate GCode. Now It should be ok"
                + "\n\n"
                + "-----------------------------";
        System.out.println(noMarkersFound);
    }
}
