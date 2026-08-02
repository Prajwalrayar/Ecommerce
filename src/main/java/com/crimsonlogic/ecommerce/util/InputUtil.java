package com.crimsonlogic.ecommerce.util;

import java.util.Scanner;

public class InputUtil {

    private static Scanner sc = new Scanner(System.in);

    // Private constructor to prevent object creation.
    private InputUtil() {

    }

    /**
     * Reads a String value.
     *
     * @param message Prompt message
     * @return User input
     */
    public static String readString(String message) {

        System.out.print(message);

        return sc.nextLine().trim();
    }

    // Reads an optional String.
    public static String readOptionalString(String message) {

        System.out.print(message);

        String value = sc.nextLine().trim();

        return value.isEmpty() ? null : value;
    }

    /**
     * Reads an optional Double.
     *
     * @param message Prompt message
     * @return Entered value or null if empty
     */
    public static Double readOptionalDouble(String message) {

        while (true) {

            System.out.print(message);

            String input = sc.nextLine().trim();

            if (input.isEmpty()) {

                return null;

            }

            try {

                return Double.parseDouble(input);

            }

            catch (NumberFormatException exception) {

                System.out.println("Invalid Number.");

            }

        }

    }
    /**
     * Reads an integer safely.
     *
     * Keeps asking until a valid integer is entered.
     *
     * @param message Prompt message
     * @return Integer value
     */
    public static int readInt(String message) {

        while (true) {
            try {
                System.out.print(message);
                int value = Integer.parseInt(sc.nextLine().trim());
                return value;
            } catch (NumberFormatException exception) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    // Reads a double safely.

    public static double readDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                double value = Double.parseDouble(sc.nextLine().trim());
                return value;
            } catch (NumberFormatException exception) {
                System.out.println("Invalid input. Please enter a valid decimal value.");
            }
        }

    }
    //  Reads a yes/no choice.

    public static boolean readYesOrNo(String message) {
        while (true) {
            System.out.print(message + " (Y/N): ");
            String choice = sc.nextLine().trim();
            if (choice.equalsIgnoreCase("Y")) {
                return true;
            }
            if (choice.equalsIgnoreCase("N")) {
                return false;
            }
            System.out.println("Please enter Y or N.");
        }
    }

    /**
     * Closes the Scanner.
     * Should be called only once
     * when the application exits.
     */
    public static void closeScanner() {
        sc.close();
    }
}
