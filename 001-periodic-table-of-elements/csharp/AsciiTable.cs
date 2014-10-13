using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace PeriodicTable
{
    class AsciiTable
    {
        private const char crossBorder  = '+';
        private const char rowBorder    = '-';
        private const char columnBorder = '|';
        private const char headerBorder = '=';


        public static String Make(IList<IList<String>> table)
        {
            int colCount = table[0].Count;
            int[] maxColLengths = new int[table[0].Count];
            int totalMax = 0;
            foreach (IList<String> row in table)
            {
                for (int column = 0; column < row.Count; column++)
                {
                    int cellLength = row[column].Length;
                    maxColLengths[column] = Math.Max(maxColLengths[column], cellLength);
                    totalMax = Math.Max(totalMax, cellLength);
                }
            }

            StringBuilder sb = new StringBuilder();
            addSeparator(sb, false, maxColLengths);
            bool isFirst = true;
            foreach (IList<String> row in table)
            {
                addRow(sb, maxColLengths, row);
                addSeparator(sb, isFirst, maxColLengths);
                isFirst = false;
            }
            return sb.ToString();
        }

        private static void addSeparator(StringBuilder sb, bool isHeader, IList<int> maxLengths)
        {
            char border = isHeader ? headerBorder : rowBorder;
            sb.Append(crossBorder);
            foreach (int length in maxLengths)
            {
                sb.Append(border, length + 2);
                sb.Append(crossBorder);
            }
            sb.Append(Environment.NewLine);
        }

        private static void addRow(StringBuilder sb, IList<int> maxLengths, IList<String> row)
        {
            sb.Append(columnBorder);
            for (int column = 0; column < maxLengths.Count; column++)
            {
                String cell = row[column];
                int ws = maxLengths[column] - row[column].Length + 2;

                sb.Append(' ', ws / 2);
                sb.Append(cell);
                sb.Append(' ', ws / 2 + ws % 2);
                sb.Append(columnBorder);
            }
            sb.Append(Environment.NewLine);
        }

    }
}
