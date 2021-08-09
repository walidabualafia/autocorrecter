import javax.swing.*;
import java.util.*;

public class Autocorrecter {

    // Holds frequencies of every possible word.
    private final HashtableMap<String, Integer> wordFreqs;

    /**
     * Create a new Autocorrecter, based on the supplied map of word frequencies.
     */
    public Autocorrecter(HashtableMap<String, Integer> wordFrequencies)
    {
        wordFreqs = wordFrequencies;
    }

    /**
     * Returns the most frequent word in the wordFreqs map that has inputString as a prefix.
     * If no string in the wordFreq map starts with this string, returns null.
     */
    public String getBestAutocomplete(String inputString)
    {
        // Initialize local variables holding: maximum frequency, most frequent word.
        int maxFreq = 0;
        String mostFreq = "";
        // Loop over words/keys in wordFreqs.
        for (String wordLinear : wordFreqs.keySet())
        {
            // If the inputString is a prefix of a word, and the frequency of that word is greater than the max.
            if (wordLinear.startsWith(inputString) && wordFreqs.get(wordLinear) > maxFreq)
            {
                maxFreq = wordFreqs.get(wordLinear);
                mostFreq = wordLinear;
            }
        }
        // If a most frequent word is found, return it.
        if (!mostFreq.equals(""))
            return mostFreq;
        // Otherwise, return null.
        return null;
    }

    /**
     * Return the set of possible words that are *both* an edit distance of 1 away from the inputString,
     * *and* are contained in wordFreq.
     */
    public Set<String> getBestAutocorrect(String inputString)
    {
        Set<String> edits = editDistance1(inputString);
        edits.retainAll(wordFreqs.keySet());
        return edits;
    }

    /**
     * Return the "best suggestions" for an inputString, based on both the most likely autocompletion,
     * and the set of possible autocorrections.  The suggestions are in decreasing sorted order of frequency.
     */
    public List<String> getBestSuggestions(String inputString)
    {
        // Initialize local variables.
        // String holding the best autocomplete word.
        String bestAutocomplete = getBestAutocomplete(inputString);
        // Set of Strings holding the best autocorrect words.
        Set<String> bestAutocorrect = getBestAutocorrect(inputString);
        // ArrayList of Strings holding the best suggestion words.
        ArrayList<String> bestSuggestions = new ArrayList<String>(bestAutocorrect);

        // Add bestAutocomplete to the result if bestAutocomplete is not null and is not already in the result.
        if (bestAutocomplete != null && !bestSuggestions.contains(bestAutocomplete))
            bestSuggestions.add(bestAutocomplete);

        // Create an instance of the comparator and sort bestSuggestions.
        Comparator<String> wordByFreqCompare = new WordByFrequencyComparator();
        Mergesort.mergesort(bestSuggestions, wordByFreqCompare);

        return bestSuggestions;
    }

    /**
     * Returns the set of possible strings that have an edit distance of 1 from string s.
     */
    private static Set<String> editDistance1(String s)
    {
        HashSet<String[]> splits = new HashSet<>();
        for (int x = 0; x < s.length() + 1; x++)
        {
            splits.add(new String[] { s.substring(0, x), s.substring(x) });
        }

        HashSet<String> edits = new HashSet<>();

        // deletions
        for (String[] splitString : splits)
        {
            String L = splitString[0], R = splitString[1];

            // deletion
            if (!R.equals(""))
                edits.add(L + R.substring(1));

            // transposition
            if (R.length() > 1)
                edits.add(L + R.charAt(1) + R.charAt(0)+ R.substring(2));

            String alphabet = "abcdefghijklmnopqrstuvwxyz";

            // replace
            if (!R.equals(""))
            {
                for (char ch : alphabet.toCharArray())
                    edits.add(L + ch + R.substring(1));
            }

            // insert
            for (char ch : alphabet.toCharArray())
                edits.add(L + ch + R);
        }

        return edits;
    }

    /**
     * This comparator subclass compares two strings according to their frequency in the wordFreq map.
     */
    class WordByFrequencyComparator implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            // Initialize local variables holding frequencies of input strings.
            int s1Freq = wordFreqs.get(s1);
            int s2Freq = wordFreqs.get(s2);

            // If frequencies are not equal, check which is smaller and which is bigger.
            if (s1Freq != s2Freq)
            {
                if (s1Freq < s2Freq)
                    return 1;
                else
                    return -1;
            }
            // If frequencies are equal, compare strings alphabetically.
            return s1.compareTo(s2);
        }
    }
}
