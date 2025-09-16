

import java.util.ArrayList;

public class Sorting {

	/*
	 * This method takes as input an MyHashTable with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable<V>> ArrayList<K> slowSort (MyHashTable<K, V> results) {
        ArrayList<K> sortedUrls = new ArrayList<K>();
        sortedUrls.addAll(results.keySet());	//Start with unsorted list of urls

        int N = sortedUrls.size();
        for(int i=0; i<N-1; i++){
			for(int j=0; j<N-i-1; j++){
				if(results.get(sortedUrls.get(j)).compareTo(results.get(sortedUrls.get(j+1))) < 0){
					K temp = sortedUrls.get(j);
					sortedUrls.set(j, sortedUrls.get(j+1));
					sortedUrls.set(j+1, temp);					
				}
			}
        }
        return sortedUrls;                    
    }
    
    
	/*
	 * This method takes as input an MyHashTable with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */    
    public static <K, V extends Comparable<V>> ArrayList<K> fastSort(MyHashTable<K, V> results) {
		//ADD YOUR CODE BELOW HERE
		ArrayList<K> KEYS = new ArrayList<>(results.keySet());
		return mergeSort(KEYS, results);


		//return null;

		//ADD YOUR CODE ABOVE HERE
    }

	private static <K, V extends Comparable<V>> ArrayList<K> mergeSort(ArrayList<K> keys, MyHashTable<K, V> results) {
        if (keys.size() <= 1) {
            return keys;
        } else {
            int middle = (keys.size() - 1) / 2;
            ArrayList<K> left = new ArrayList<>(keys.subList(0, middle + 1));
            ArrayList<K> right = new ArrayList<>(keys.subList(middle + 1, keys.size()));

            left = mergeSort(left, results);
            right = mergeSort(right, results);

            return merge(left, right, results);
        }
	}

	private static <K, V extends Comparable<V>> ArrayList<K> merge(ArrayList<K> list1, ArrayList<K> list2, MyHashTable<K, V> results) {
        ArrayList<K> new_list = new ArrayList<>();

        while (!list1.isEmpty() && !list2.isEmpty()) {
            V val1 = results.get(list1.get(0));
            V val2 = results.get(list2.get(0));

            // Descending order: larger value first
            if (val1.compareTo(val2) >= 0) {
                new_list.add(list1.remove(0));
            } else {
                new_list.add(list2.remove(0));
            }
        }

        while (!list1.isEmpty()) {
            new_list.add(list1.remove(0));
        }

        while (!list2.isEmpty()) {
            new_list.add(list2.remove(0));
        }

        return new_list;
    }
	/* 
	public static void main(String[] args) {
        // Create a small MyHashTable<String, Integer> for testing
        MyHashTable<String, Integer> testTable = new MyHashTable<>();

        // Insert some entries
        testTable.put("apple", 5);
        testTable.put("banana", 3);
        testTable.put("cherry", 8);
        testTable.put("date", 2);
        testTable.put("fig", 7);
        testTable.put("grape", 8); // Same value as cherry

        // Run slowSort
        ArrayList<String> slowSorted = Sorting.slowSort(testTable);
        System.out.println("SlowSort Result:");
        for (String key : slowSorted) {
            System.out.println(key + "" + testTable.get(key));
        }

        System.out.println("\n--\n");

        // Run fastSort
        ArrayList<String> fastSorted = Sorting.fastSort(testTable);
        System.out.println("FastSort Result:");
        for (String key : fastSorted) {
            System.out.println(key + " " + testTable.get(key));
        }

        System.out.println("\n---\n");

        // Compare both sorted results
        boolean same = slowSorted.equals(fastSorted);
        if (same) {
            System.out.println(" fastSort and slowSort produce the SAME output!");
        } else {
            System.out.println("fastSort and slowSort produce DIFFERENT outputs!");
        }
    }
		*/
       
    
   
}