package com.dslplatform.examples;

import java.util.List;

public class AsciiTable {
    public static String make(final List<List<String>> table) {
        final int[] maxLengths = new int[table.get(0).size()];
        int maxMax = 0;
        for (final List<String> row : table) {
            for (int index = 0; index < row.size(); index++) {
                maxLengths[index] = Math.max(maxLengths[index], row.get(index).length());
                maxMax = Math.max(maxMax, maxLengths[index]);
            }
        }

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxMax + 2; i++) sb.append('-');
        final String dashes = sb.toString();
        final String whitespaces = dashes.replace('-', ' ');

        final String nl = System.getProperty("line.separator");
        sb.setLength(0);
        for (final int maxLength : maxLengths) {
            sb.append('+').append(dashes.substring(0, maxLength + 2));
        }
        sb.append('+').append(nl);

        final String border = sb.toString();
        final String separator = border.replace('-', '=');

        boolean first = true;
        for (final List<String> row : table) {
            sb.append("| ");
            for (int index = 0; index < row.size(); index++) {
                final int maxLength = maxLengths[index];
                final String cell = row.get(index);
                final int padding = maxLength - cell.length();

                sb.append(whitespaces.substring(0, padding / 2))
                        .append(cell)
                        .append(whitespaces.substring(padding / 2, padding))
                        .append(" | ");
            }
            sb.setLength(sb.length() - 1);
            sb.append(nl).append(first ? separator : border);
            first = false;
        }

        return sb.toString();
    }
}
