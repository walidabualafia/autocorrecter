import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * A Map from keys to values that is implemented with a hash table.  Chaining is used to resolve collisions.
 * The user may set the starting size of the hash table, which never changes.
 */
public class HashtableMap<K, V> {

    // The ArrayList that stores the table itself.  Because we are using chaining, each entry in the ArrayList
    // is a linked list, containing key-value pairs.
    private final ArrayList<LinkedList<KVPair<K, V>>> table;

    /**
     * Creates a new hash table of size initialSize.
     */
    HashtableMap(int initialSize) {
        // Initialize table to have a pre-defined size.
        table = new ArrayList<LinkedList<KVPair<K, V>>>(initialSize);

        // Go through table and add an empty linked list to each slot.
        for (int x = 0; x < initialSize; x++)
        {
            table.add(new LinkedList<KVPair<K, V>>());
        }
    }

    /**
     * Adds a new key-value pair into the hash table.  If there is already an entry in
     * the table for this key, then it overwrites it with the new value.
     *
     * Since we are using chaining, add items to the front of the linked
     * list, once you have found the correct index in the table in which
     * to insert.
     */
    public void put(K newKey, V newValue) {
        // This is the correct index to insert the key-value pair.
        int hashIndex = Math.abs(newKey.hashCode() % table.size());

        // Initializing the KV Pair
        KVPair<K, V> kv_pair = new KVPair<K, V>();
        kv_pair.key = newKey;
        kv_pair.value = newValue;

        // Iterate over the linked list at the hashIndex.
        for (int i = 0; i < table.get(hashIndex).size(); i++)
        {
            // If the key at an index of the linked list matches the searchKey.
            if (table.get(hashIndex).get(i).key.equals(newKey))
            {
                // Remove the KV pair at that index
                table.get(hashIndex).set(i, kv_pair);
                return; // Return to avoid chaining.
            }
        }
        // Chain the new KV Pair to the beginning of the linked list.
        table.get(hashIndex).addFirst(kv_pair);
    }


    /**
     * Gets a value from this hash table, based on its key.  If the key doesn't already exist in the table,
     * this method returns null.
     */
    public V get(K searchKey) {
        // This is the correct index to look for the key-value pair.
        int hashIndex = Math.abs(searchKey.hashCode() % table.size());

        // If the table is empty at the hashIndex
        if (table.get(hashIndex) == null)
            return null;
        else
            // Iterate over the linked list at the hashIndex.
            for (int i = 0; i < table.get(hashIndex).size(); i++)
            {
                // If the key at an index of the linked list matches the searchKey.
                if (table.get(hashIndex).get(i).key.equals(searchKey))
                    return table.get(hashIndex).get(i).value;   // Return the corresponding value for the hashIndex.
            }
        return null;
    }

    /**
     * Tests if this key exists in the hash table, and returns true if it does, and false if it doesn't.
     */
    public boolean containsKey(K searchKey) {
        // This is the correct index to look for the key-value pair.
        int hashIndex = Math.abs(searchKey.hashCode() % table.size());

        // If the table is empty at the hashIndex
        if (table.get(hashIndex) == null)
            return false;
        else
            // Iterate over the linked list at the hashIndex.
            for (int i = 0; i < table.get(hashIndex).size(); i++)
            {
                // If the key at an index of the linked list matches the searchKey.
                if (table.get(hashIndex).get(i).key.equals(searchKey))
                    return true;
            }
        return false;
    }

    /**
     * Prints the hash table.  If the table has more than 100 slots, only prints the top 100 (indices 0-99).
     *
     * Prints the contents of each index in the table on a single line.  Includes the index number, the
     * number of entries at that index (hint--use .size() on the linked list), and each individual entry
     * in the format "key: value" or something similar.
     *
     * Example:
     * Index 0 (2): key1: value1, key2: value2
     * Index 1 (3): key3: value3, etc, etc
     */
    public void printTable() {
        // If the table size is greater than 100, only print the first 100 elements.
        if (table.size() > 99)
        {
            // Iterate over the first 100 elements of hashTable table.
            for (int i = 0; i < 100; i++) {
                System.out.print("Index " + i + " (" + table.get(i).size() + "): [");
                // Iterate over the linked list at the current index.
                for (int j = 0; j < table.get(i).size(); j++)
                {
                    // If the current index is at the last element.
                    if (j == table.get(i).size() - 1)
                        System.out.print(table.get(i).get(j).toString());
                    // If the current index is not at the last element.
                    else
                        System.out.print(table.get(i).get(j).toString() + ", ");
                }
                System.out.println("]");
            }
        }
        // If the table size is less than 100, print all elements.
        else
        {
            // Iterate over hashTable table.
            for (int i = 0; i < table.size(); i++) {
                System.out.print("Index " + i + " (" + table.get(i).size() + "): [");
                // Iterate over the linked list at the current index.
                for (int j = 0; j < table.get(i).size(); j++)
                {
                    // If the current index is at the last element.
                    if (j == table.get(i).size() - 1)
                        System.out.print(table.get(i).get(j).toString());
                    // If the current index is not at the last element.
                    else
                        System.out.print(table.get(i).get(j).toString() + ", ");
                }
                System.out.println("]");
            }
        }
    }

    /**
     * Returns the total number of key-value pairs stored in the hash table.
     * Note, this is not the same thing as the number of slots in the table.
     */
    public int size() {
        int count = 0;  // Accumulator to hold the size of elements in table.

        // Loop over all the indices of table.
        for (LinkedList<KVPair<K, V>> kvPairs : table) {
            // Loop over all the indices of the linked list at each index of table.
            for (KVPair<K, V> kvPair : kvPairs) {
                count++;    // Increment count.
            }
        }
        return count;
    }

    /**
     * Turns the hash table into a Set of all the keys in the table (values are ignored).
     * Does this by creating an empty HashSet<K> (built-in Java class),
     * looping through the hash table, looping over each linked list in each slot of the table,
     * and adding each key into the hash set.
     */
    Set<K> keySet() {
        // Initialize an empty HashSet of type K.
        HashSet<K> set = new HashSet<K>();

        // Loop over all the indices of table.
        for (LinkedList<KVPair<K, V>> kvPairs : table) {
            // Loop over all the indices of the linked list at each index of table.
            for (KVPair<K, V> kvPair : kvPairs) {
                set.add(kvPair.key);    // Add each key to the new set.
            }
        }
        return set;
    }


    /**
     * Private KVPair helper class. KVPair is nested inside HashtableMap as it serves
     * no purpose outside of this class.
     */
    private static class KVPair<K, V> {
        public K key = null;
        public V value = null;

        /**
         * Helper method to print KVPair keys and values as Strings.
         */
        public String toString() {
            return "{key=" + key + ", value=" + value + "}";
        }

    }

}
