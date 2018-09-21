import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author Alice Lew
 * @version 1.0
 */
public class StringSearching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the failure table before implementing this method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for pattern
     * @return list of integers representing the first index a match occurs or
     * an empty list if the text is of length 0
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null.");
        } else if (pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern cannot be empty.");
        } else if (text == null) {
            throw new IllegalArgumentException("Text cannot be null.");
        }
        List<Integer> returnList = new ArrayList<>();
        int m = pattern.length();
        int n = text.length();
        if (m > n) {
            return returnList;
        }
        int[] failureTable = buildFailureTable(pattern);

        int patternPointer = 0;
        int textPointer = 0;
        while (n - textPointer >= m - patternPointer) {
            if (pattern.charAt(patternPointer) == text.charAt(textPointer)) {
                if (patternPointer == (m - 1)) {
                    returnList.add(textPointer - m + 1);
                    patternPointer = failureTable[patternPointer] - 1;
                }
                patternPointer++;
                textPointer++;
            } else if (patternPointer > 0) {
                patternPointer = failureTable[patternPointer - 1];
            } else {
                textPointer++;
            }
        }
        return returnList;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input text.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @throws IllegalArgumentException if the pattern is null
     * @param pattern a {@code CharSequence} you are building failure table for
     * @return integer array of size text.length that you are building a failure
     * table for
     */
    public static int[] buildFailureTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null.");
        }
        int[] returnTable = new int[pattern.length()];
        returnTable[0] = 0;
        int i = 0;
        int j = 1;
        while (j < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(j)) {
                returnTable[j] = i + 1;
                i++;
                j++;
            } else if (i > 0) {
                i = returnTable[i - 1];
            } else {
                returnTable[j++] = i;
            }
        }
        return returnTable;
    }

    /**
     * Boyer Moore algorithm that relies on last table. Works better with large
     * alphabets.
     *
     * Make sure to implement the last table before implementing this method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for the pattern
     * @return list of integers representing the first index a match occurs or
     * an empty list if the text is of length 0
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
            CharSequence text) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null.");
        } else if (pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern cannot be empty.");
        } else if (text == null) {
            throw new IllegalArgumentException("Text cannot be null.");
        }
        List<Integer> returnList = new ArrayList<>();
        int m = pattern.length();
        int n = text.length();
        if (m > n) {
            return returnList;
        }
        Map<Character, Integer> returnTable = buildLastTable(pattern);

        int patternPointer = m - 1;
        int textPointer = m - 1;
        while (textPointer < n) {
            char textChar = text.charAt(textPointer);
            if (pattern.charAt(patternPointer) == textChar) {
                if (patternPointer == 0) {
                    returnList.add(textPointer);
                    patternPointer = m;
                    textPointer += m + 1;
                }
                patternPointer--;
                textPointer--;
            } else {
                int lastOccurance = -1;
                if (returnTable.containsKey(textChar)) {
                    lastOccurance = returnTable.get(textChar);
                }
                textPointer += m - Math.min(patternPointer, 1 + lastOccurance);
                patternPointer = m - 1;
            }
        }
        return returnList;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x
     * and you will have to check for that in your BoyerMoore
     *
     * Ex. octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @throws IllegalArgumentException if the pattern is null
     * @param pattern a {@code CharSequence} you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     *         to their last occurrence in the pattern
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null.");
        }
        Map<Character, Integer> returnTable = new HashMap<>();
        for (int j = 0; j < pattern.length(); j++) {
            char key = pattern.charAt(j);
            returnTable.put(key, j);
        }
        return returnTable;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 337;

    /**
     * Runs Rabin-Karp algorithm. Generate initial hash, and compare it with
     * hash from substring of text same length as pattern. If the two
     * hashes match compare their individual characters, else update hash
     * and continue.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text is null
     * @param pattern a string you're searching for in a body of text
     * @param text the body of text where you search for pattern
     * @return list of integers representing the first index a match occurs or
     * an empty list if the text is of length 0
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
            CharSequence text) {
        if (pattern == null) {
            throw new IllegalArgumentException("Pattern cannot be null.");
        } else if (pattern.length() == 0) {
            throw new IllegalArgumentException("Pattern cannot be empty.");
        } else if (text == null) {
            throw new IllegalArgumentException("Text cannot be null.");
        }
        List<Integer> returnList = new ArrayList<>();
        int m = pattern.length();
        int n = text.length();
        if (m > n) {
            return returnList;
        }

        int patternHash = generateHash(pattern, m);
        int textHash = generateHash(text, m);

        for (int i = 0; (i + m) <= n; i++) {
            if (patternHash == textHash) {
                boolean flag = true;
                for (int p = 0; p < m && flag; p++) {
                    if (pattern.charAt(p) != text.charAt(p + i)) {
                        flag = false;
                    }
                }
                if (flag) {
                    returnList.add(i);
                }
            }
            if (i + m < n) {
                textHash = updateHash(textHash, m, text.charAt(i),
                    text.charAt(i + m));
            }
        }
        return returnList;
    }

    /**
     * Hash function used for Rabin-Karp. The formula for hashing a string is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i), where c is the integer
     * value of the current character, and i is the index of the character
     *
     * For example: Hashing "bunn" as a substring of "bunny" with base 337 hash
     * = b * 337 ^ 3 + u * 337 ^ 2 + n * 337 ^ 1 + n * 337 ^ 0 = 98 * 337 ^ 3 +
     * 117 * 337 ^ 2 + 110 * 337 ^ 1 + 110 * 337 ^ 0 = 3764054547
     *
     * However, note that that will roll over to -530912749, because the largest
     * number that can be represented by an int is 2147483647.
     *
     * Do NOT use {@code Math.pow()} in this method.
     *
     * @throws IllegalArgumentException if current is null
     * @throws IllegalArgumentException if length is negative or greater
     *     than the length of current
     * @param current substring you are generating hash function for
     * @param length the length of the string you want to generate the hash for,
     * starting from index 0. For example, if length is 4 but current's length
     * is 6, then you include indices 0-3 in your hash (and pretend the actual
     * length is 4)
     * @return hash of the substring
     */
    public static int generateHash(CharSequence current, int length) {
        if (current == null) {
            throw new IllegalArgumentException("Substring cannot be null.");
        } else if (length < 0) {
            throw new IllegalArgumentException("Length cannot be negative.");
        } else if (length > current.length()) {
            throw new IllegalArgumentException("Length cannot be greater"
                + " than the substring's length.");
        }
        int hash = 0;
        for (int i = 0; i < length; i++) {
            hash += (current.charAt(i) * pow(BASE, length - 1 - i));
        }
        return hash;
    }

    /**
     * Updates a hash in constant time to avoid constantly recalculating
     * entire hash. To update the hash:
     *
     *  remove the oldChar times BASE raised to the length - 1, multiply by
     *  BASE, and add the newChar.
     *
     * For example: Shifting from "bunn" to "unny" in "bunny" with base 337
     * hash("unny") = (hash("bunn") - b * 337 ^ 3) * 337 + y * 337 ^ 0 =
     * (3764054547 - 98 * 337 ^ 3) * 337 + 121 * 337 ^ 0 = 4490441882
     *
     * However, the number will roll over to 195474586.
     *
     * The computation of BASE raised to length - 1 may require O(log n) time,
     * but the method should otherwise run in O(1).
     *
     * Do NOT use {@code Math.pow()} in this method.
     *
     * @throws IllegalArgumentException if length is negative
     * @param oldHash hash generated by generateHash
     * @param length length of pattern/substring of text
     * @param oldChar character we want to remove from hashed substring
     * @param newChar character we want to add to hashed substring
     * @return updated hash of this substring
     */
    public static int updateHash(int oldHash, int length, char oldChar,
            char newChar) {
        if (length < 0) {
            throw new IllegalArgumentException("Length cannot be negative.");
        }
        int hash = oldHash;
        hash -= (oldChar * pow(BASE, length - 1));
        hash *= BASE;
        hash += newChar;
        return hash;
    }

    /**
     * Calculate the result of a number raised to a power. Use this method in
     * your rapin karp instead of {@code Math.pow()}.
     *
     * DO NOT MODIFY THIS METHOD.
     *
     * @throws IllegalArgumentException if both {@code base} and {@code exp} are
     * 0
     * @throws IllegalArgumentException if {@code exp} is negative
     * @param base base of the number
     * @param exp power to raise the base to. Must be 0 or greater.
     * @return result of the base raised to that power
     */
    private static int pow(int base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Exponent cannot be negative.");
        } else if (base == 0 && exp == 0) {
            throw new IllegalArgumentException(
                    "Both base and exponent cannot be 0.");
        } else if (exp == 0) {
            return 1;
        } else if (exp == 1) {
            return base;
        }
        int halfPow = pow(base, exp / 2);
        if (exp % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return halfPow * pow(base, (exp / 2) + 1);
        }
    }
}
