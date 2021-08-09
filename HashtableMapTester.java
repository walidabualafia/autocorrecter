public class HashtableMapTester {
    public static void main(String args[])
    {
        testIntIntMap();
        testStringStringMap();
        testLongIntMap();
        testOverwrite();
    }

    public static void testIntIntMap()
    {
        System.out.println("int -> int map:");
        HashtableMap<Integer, Integer> map = new HashtableMap<Integer, Integer>(4);

        // Test put().
        map.put(1, 2);
        map.put(3, 4);
        map.put(5, 6);
        map.put(7, 8);

        // Test get().
        System.out.println("Get 1: " + map.get(1));
        System.out.println("Get 3: " + map.get(3));
        System.out.println("Get 5: " + map.get(5));
        System.out.println("Get 7: " + map.get(7));
        System.out.println("Get 9: " + map.get(9));

        // Test containsKey().
        System.out.println("Contains 1: " + map.containsKey(1));
        System.out.println("Contains 3: " + map.containsKey(3));
        System.out.println("Contains 5: " + map.containsKey(5));
        System.out.println("Contains 7: " + map.containsKey(7));
        System.out.println("Contains 9: " + map.containsKey(9));

        // Test size().
        System.out.println("Size = " + map.size());

        // Test keySet().
        System.out.println("All keys (order might vary): " + map.keySet());

        // Test printTable().
        map.printTable();
    }

    public static void testStringStringMap()
    {
        System.out.println("\nString -> String map:");
        HashtableMap<String, String> map2 = new HashtableMap<String, String>(7);

        // Test put().
        map2.put("Elvis Presley", "elvis@graceland.com");
        map2.put("Albus Dumbledore", "dumbledore@hogwarts.edu");
        map2.put("Dorothy Gale", "dorothy@oz.org");
        map2.put("Grace Hopper", "hopper@navy.mil");  // look her up!

        // Test get().
        System.out.println("Get: " + map2.get("Elvis Presley"));
        System.out.println("Get: " + map2.get("Albus Dumbledore"));
        System.out.println("Get: " + map2.get("Dorothy Gale"));
        System.out.println("Get: " + map2.get("Grace Hopper"));

        // Test size().
        System.out.println("Size = " + map2.size());

        // Test keySet().
        System.out.println("All keys (order might vary): " + map2.keySet());

        // Test printTable().
        map2.printTable();
    }

    public static void testLongIntMap()
    {
        System.out.println("\nlong -> int map:");
        HashtableMap<Long, Integer> map3 = new HashtableMap<Long, Integer>(11);

        // Insert some powers of 2 into the map.
        for (int x = 0; x < 60; x++)
        {
            map3.put((long)Math.pow(2, x), x);
        }

        // Test size().
        System.out.println("Size = " + map3.size());

        // Test keySet().
        System.out.println("All keys (order might vary): " + map3.keySet());

        // Test printTable().
        map3.printTable();
    }

    public static void testOverwrite()
    {
        System.out.println("\nint -> int map (testing overwriting with put):");
        HashtableMap<Integer, Integer> map = new HashtableMap<Integer, Integer>(4);

        System.out.println("\nBefore overwriting: ");

        // Test put().
        map.put(1, 2);
        map.put(3, 4);
        map.put(5, 6);
        map.put(7, 8);

        // Test get().
        System.out.println("Get 1: " + map.get(1));
        System.out.println("Get 3: " + map.get(3));
        System.out.println("Get 5: " + map.get(5));
        System.out.println("Get 7: " + map.get(7));

        // Test size().
        System.out.println("Size = " + map.size());

        // Test keySet().
        System.out.println("All keys (order might vary): " + map.keySet());

        // Test printTable().
        map.printTable();

        System.out.println("\nAfter overwriting: ");
        // Test put().
        map.put(1, 4);
        map.put(3, 6);
        map.put(5, 8);
        map.put(7, 10);

        // Test get().
        System.out.println("Get 1: " + map.get(1));
        System.out.println("Get 3: " + map.get(3));
        System.out.println("Get 5: " + map.get(5));
        System.out.println("Get 7: " + map.get(7));

        // Test size().
        System.out.println("Size = " + map.size());

        // Test keySet().
        System.out.println("All keys (order might vary): " + map.keySet());

        // Test printTable().
        map.printTable();
    }
}
