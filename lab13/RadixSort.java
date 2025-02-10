/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {

    // Constant for the ASCII character range (0-255)
    private static final int ASCII_RANGE = 256;

    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        String[] sorted = asciis.clone();
        int maxLength = 0;

        for (String str : asciis) {
            maxLength = Math.max(maxLength, str.length());
        }

        for (int index = maxLength - 1; index >= 0; index -= 1) {
            sorted = sortHelperLSD(sorted, index);
        }
        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static String[] sortHelperLSD(String[] asciis, int index) {
        int[] counts = new int[ASCII_RANGE];
        int[] start = new int[ASCII_RANGE];
        String[] sorted = new String[asciis.length];

        // Count the frequency of characters at the specified index
        for (String str : asciis) {
            int place = (index < str.length()) ? (int) str.charAt(index) : 0;
            counts[place] += 1;
        }

        // Calculate the starting index for each character
        int pos = 0;
        for (int i = 0; i < counts.length; i += 1) {
            start[i] = pos;
            pos += counts[i];
        }

        // Place the strings into the output array in the correct order
        for (String str : asciis) {
            int place = (index < str.length()) ? (int) str.charAt(index) : 0;
            sorted[start[place]] = str;
            start[place] += 1;
        }
        return sorted;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
