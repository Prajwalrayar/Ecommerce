package com.crimsonlogic.ecommerce.util;

import java.util.List;

/**
 * Utility class for displaying
 * formatted tables.
 */
public class DisplayUtil {

    /**
     * Private Constructor.
     */
    private DisplayUtil() {

    }

    /**
     * Prints data in tabular format.
     *
     * @param title Table Title
     * @param headers Column Headers
     * @param rows Table Rows
     */
    public static void printTable(String title,
                                  String[] headers,
                                  List<String[]> rows) {

        int columns = headers.length;
        int[] columnWidths = new int[columns];

        // Calculate column widths
        for (int i = 0; i < columns; i++) {
            columnWidths[i] = headers[i].length();
        }

        for (String[] row : rows) {

            for (int i = 0; i < columns; i++) {

                String value = row[i] == null ? "N/A" : row[i];

                if (value.length() > columnWidths[i]) {
                    columnWidths[i] = value.length();
                }

            }

        }

        System.out.println();

        System.out.println(title.toUpperCase());

        String border = createBorder(columnWidths);

        System.out.println(border);

        // Header

        System.out.print("|");

        for (int i = 0; i < columns; i++) {

            System.out.printf(
                    " %-" + columnWidths[i] + "s |",
                    headers[i]);

        }

        System.out.println();

        System.out.println(border);

        // Rows

        for (String[] row : rows) {

            System.out.print("|");

            for (int i = 0; i < columns; i++) {

                String value =
                        row[i] == null ? "N/A" : row[i];

                System.out.printf(
                        " %-" + columnWidths[i] + "s |",
                        value);

            }

            System.out.println();

        }

        System.out.println(border);

        System.out.println();

    }

    /**
     * Prints a Success Message.
     *
     * @param message Success Message
     */
    public static void printSuccess(String message) {

        System.out.println();

        System.out.println("SUCCESS : " + message);

        System.out.println();

    }

    /**
     * Prints an Error Message.
     *
     * @param message Error Message
     */
    public static void printError(String message) {

        System.out.println();

        System.out.println("ERROR : " + message);

        System.out.println();

    }

    public static void printInvalidChoice() {
        System.out.println();
        System.out.println("Invalid Choice! Please try again.");
        System.out.println();

    }
    /**
     * Prints a Warning Message.
     *
     * @param message Warning Message
     */
    public static void printWarning(String message) {

        System.out.println();

        System.out.println("WARNING : " + message);

        System.out.println();

    }

    /**
     * Prints a Heading.
     *
     * @param heading Heading
     */
    public static void printHeading(String heading) {

        System.out.println();

        System.out.println(heading);

        System.out.println();

    }

    /**
     * Prints a formatted message.
     *
     * @param title Message Title
     * @param message Message Content
     */
    public static void printMessage(String message) {

        System.out.println();
        System.out.println(message);

    }

    /**
     * Creates table border.
     *
     * @param columnWidths Width of each column
     * @return Border String
     */
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