package com.crimsonlogic.ecommerce.util;

import java.util.List;

/**
 * Utility class for displaying tables.
 */
public final class TableUtil {

    private TableUtil() {

    }

    /**
     * Displays a table.
     *
     * @param title Table Title
     * @param headers Table Headers
     * @param rows Table Rows
     * @param emptyMessage Empty Message
     * @return true if table displayed
     */
    public static boolean displayTable(
            String title,
            String[] headers,
            List<String[]> rows,
            String emptyMessage) {

        if (rows.isEmpty()) {

            DisplayUtil.printWarning(
                    emptyMessage);

            return false;

        }

        DisplayUtil.printTable(

                title,

                headers,

                rows

        );

        return true;

    }

}