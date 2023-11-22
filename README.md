# GCodeErrorMadChecker

GCodeErrorMadChecker is a small Java CLI application that checks a GCode file for suspicious lines based on the ASCII table or line length.

## Requires

Java 17

## Usage

To use GCodeErrorMadChecker first you need compile `GCodeErrorMadChecker.java` by running the following command in your terminal or command prompt on your computer (Windows/Mac/Linux):

```bash
javac GCodeErrorMadChecker.java
```

Now, you can run the compiled GCodeErrorMadChecker.class:

```bash
java GCodeErrorMadChecker <filepath>
```

## Command Line Options
```<filepath>```: Path to the GCode file.

## Features
Checks for suspicious lines based on the ASCII table and line length.
Allows customization of markers to specify the range for checking.
Customization
You can customize the markers used for the start and end of the checking range by modifying the constants in the code:

```bash
private static final String START_MARKER = ";GCodeErrorMadChecker_Start";
private static final String END_MARKER = ";GCodeErrorMadChecker_End";
```

## Start and End Markers
The application uses markers to specify the range for checking.

Add the Start Marker 
```
;GCodeErrorMadChecker_Start
```
to the end of your Machine Starting Gcode in your slicer.

Add the End Marker
```
;GCodeErrorMadChecker_End
```
to the beginning of your Machine Ending Gcode in your slicer.

**GCodeErrorMadChecker will not check your code without Start and End Markers**

## Customization (optional)
You can customize the markers used for the start and end of the checking range by modifying the constants in the code:

```
private static final String START_MARKER = ";GCodeErrorMadChecker_Start";
private static final String END_MARKER = ";GCodeErrorMadChecker_End";
```

## Result
The application prints the result, including:

* Total lines checked
* Number of suspicious lines found
* List of line numbers with suspicious content

License
This project is licensed under the MIT License - see the [LICENSE](https://github.com/MadMan2k/GCodeErrorMadChecker/blob/main/LICENSE) file for details.