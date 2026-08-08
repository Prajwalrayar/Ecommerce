package com.crimsonlogic.ecommerce.util;

import java.util.List;

/**
 * Utility class for displaying
 * formatted tables.
 */
public class DisplayUtil {

    // Private Constructor.
    private DisplayUtil() {
    }

    // Prints data in tabular format.
    public static void printTable(String title,
            String[] headers, List<String[]> rows) {

        int columns = headers.length;

        int[] columnWidths = calculateColumnWidths(headers, rows);

        System.out.println();

        System.out.println(title.toUpperCase());

        String border = createBorder(columnWidths);

        System.out.println(border);

        // Header
        printHeader(headers, columnWidths);
        System.out.println(border);
        // Rows
        for (String[] row : rows) {
            printRow(row, columnWidths);
        }

        System.out.println(border);
        System.out.println();
    }

    // Calculates column widths.
    private static int[] calculateColumnWidths(String[] headers, List<String[]> rows) {
        int columns = headers.length;

        int[] columnWidths = new int[columns];

        // Header widths
        for (int i = 0; i < columns; i++) {
            columnWidths[i] = headers[i].length();
        }

        // Row widths
        for (String[] row : rows) {

            for (int i = 0; i < columns; i++) {

                String value = row[i] == null ? "N/A" : row[i];

                String[] lines = value.split("\\R", -1);
                for (String line : lines) {

                    if (line.length() > columnWidths[i]) {

                        columnWidths[i] = line.length();

                    }
                }
            }
        }
        return columnWidths;
    }

    // Prints table header.
    private static void printHeader(String[] headers, int[] columnWidths) {

        System.out.print("|");
        for (int i = 0; i < headers.length; i++) {

            System.out.printf(" %-" + columnWidths[i] + "s |", headers[i]);
        }
        System.out.println();
    }

    // Prints a table row.
    private static void printRow(String[] row, int[] columnWidths) {

        String[][] cellLines = new String[row.length][];

        int rowHeight = 1;

        // Split every cell into lines
        for (int i = 0; i < row.length; i++) {

            String value = row[i] == null ? "N/A" : row[i];

            cellLines[i] = value.split("\\R", -1);

            if (cellLines[i].length > rowHeight) {

                rowHeight = cellLines[i].length;
            }
        }
        // Print each physical line of the row
        for (int line = 0; line < rowHeight; line++) {

            System.out.print("|");

            for (int column = 0; column < row.length; column++) {

                String value = "";

                if (line < cellLines[column].length) {
                    value = cellLines[column][line];
                }

                System.out.printf(" %-" + columnWidths[column] + "s |", value);
            }
            System.out.println();
        }
    }

    // Prints a Success Message.
    public static void printSuccess(String message) {
        System.out.println("SUCCESS : " + message);
        System.out.println();
    }

    // Prints an invalid choice message.
    public static void printInvalidChoice() {
        System.out.println("Invalid Choice! Please try again.");
        System.out.println();
    }

    // Prints a Heading.
    public static void printHeading(String heading) {
        System.out.println();
        System.out.println(heading);
        System.out.println();
    }

    // Prints a formatted message.

    public static void printMessage(String message) {
        System.out.println(message);
    }

    // Creates table border.

    private static String createBorder(int[] columnWidths) {

        StringBuilder border = new StringBuilder();

        border.append("+");

        for (int width : columnWidths) {

            border.append("-".repeat(width + 2));
            border.append("+");

        }
        return border.toString();
    }
}