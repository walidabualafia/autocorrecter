import java.io.InputStream;
import java.util.*;

public class AutocorrectDemo {

    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter filename of text to read: ");
        String filename = scan.nextLine();
        System.out.print("Enter size of hash table: ");
        int size = Integer.parseInt(scan.nextLine());

        HashtableMap<String, Integer> wordFreqs = processFile(filename, size);
        System.out.println("Entries in table: " + wordFreqs.size());
        wordFreqs.printTable();

        // Uncomment this to test the autocorrecter:
        Autocorrecter autocorrecter = new Autocorrecter(wordFreqs);
        testAutocorrect(autocorrecter);

        // Initialize autocorrecter window.
        AutocorrectFrame frame = new AutocorrectFrame(autocorrecter);
        frame.setVisible(true);
    }

    /**
     * Tester function.
     * @param autocorrecter
     */
    public static void testAutocorrect(Autocorrecter autocorrecter)
    {
        Scanner scan = new Scanner(System.in);
        String word;
        while (true) {
            System.out.print("\nEnter a word, or 'stop' to end: ");
            word = scan.nextLine();
            if (word.equals("stop"))
                break;
            System.out.println("Autocomplete: " + autocorrecter.getBestAutocomplete(word));
            System.out.println("Autocorrect (order might be different):");
            System.out.println("  " + autocorrecter.getBestAutocorrect(word));
            System.out.println("Best suggestions (order should match):");
            System.out.println("  " + autocorrecter.getBestSuggestions(word));
        }
    }

    /**
     * Read the file specified to add proper items to the word frequencies.
     */
    private static HashtableMap<String, Integer> processFile(String filename, int size)
    {
        HashtableMap<String, Integer> wordFreqs = new HashtableMap<>(size);
        InputStream is = AutocorrectDemo.class.getResourceAsStream(filename);
        if (is == null) {
            System.err.println("Bad filename: " + filename);
            System.exit(1);
        }
        Scanner scan = new Scanner(is);

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] words = line.split(" ");

            // Iterate over the words in the text file.
            for (String word : words) {
                // If word has appeared before (is already a key in wordFreqs).
                if (wordFreqs.containsKey(word)) {
                    wordFreqs.put(word, wordFreqs.get(word) + 1);
                // If word has not appeared before (is not a key in wordFreqs).
                } else
                {
                    wordFreqs.put(word, 1); // Put the new word in wordFreqs.
                }
            }
        }
        scan.close();
        return wordFreqs;
    }
}
