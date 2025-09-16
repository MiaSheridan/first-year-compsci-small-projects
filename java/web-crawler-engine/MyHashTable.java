package finalproject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


public class MyHashTable<K,V> implements Iterable<MyPair<K,V>>{
	// num of entries to the table
	private int size;
	// num of buckets 
	private int capacity = 16;
	// load factor needed to check for rehashing 
	private static final double MAX_LOAD_FACTOR = 0.75;
	// ArrayList of buckets. Each bucket is a LinkedList of HashPair
	private ArrayList<LinkedList<MyPair<K,V>>> buckets; 


	// constructors
	public MyHashTable() {
		// ADD YOUR CODE BELOW THIS
		this.size=0;
		this.capacity=16;
		this.buckets = new ArrayList<>(capacity);

		for (int i=0; i < capacity; i++){
			buckets.add(new LinkedList<MyPair<K,V>>());

		}

		//ADD YOUR CODE ABOVE THIS
	}

	public MyHashTable(int initialCapacity) {
		// ADD YOUR CODE BELOW THIS
		this.size = 0;
		this.capacity = initialCapacity;
		for (int i = 0; i < capacity; i++){
			buckets.add(new LinkedList<MyPair<K, V>>());
		}


		//ADD YOUR CODE ABOVE THIS
	}
	
	public void clear() {
		this.size = 0;
		for (int i=0; i<this.buckets.size(); i++) {
			this.buckets.get(i).clear();
		}
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public int numBuckets() {
		return this.capacity;
	}

	/**
	 * Returns the buckets variable. Useful for testing  purposes.
	 */
	public ArrayList<LinkedList< MyPair<K,V> > > getBuckets(){
		return this.buckets;
	}

	/**
	 * Given a key, return the bucket position for the key. 
	 */
	public int hashFunction(K key) {
		int hashValue = Math.abs(key.hashCode())%this.capacity;
		return hashValue;
	}

	/**
	 * Takes a key and a value as input and adds the corresponding HashPair
	 * to this HashTable. Expected average run time  O(1)
	 */
	public V put(K key, V value) {
		//  ADD YOUR CODE BELOW HERE
		//find correct bucket index
		int i = hashFunction(key);

		//get correct bucket
		LinkedList<MyPair<K,V>> bucketo = buckets.get(i);

		//search inside that bucket
		for (MyPair<K,V> pair : bucketo){
			if(pair.getKey().equals(key)){
				V old = pair.getValue();
				pair.setValue(value);
				return old;
			}
		}

		//return null;
		//if the key isnt found, add a new pair
		bucketo.add(new MyPair<>(key, value));
		size++;
		//check load factor
		if((double) size/capacity > MAX_LOAD_FACTOR){
			rehash(); // not implemented yet
		}
		return null;
		//  ADD YOUR CODE ABOVE HERE
	}


	/**
	 * Get the value corresponding to key. Expected average runtime O(1)
	 */
	public V get(K key) {
		//ADD YOUR CODE BELOW HERE
		int index = hashFunction(key);

		LinkedList<MyPair<K,V>> bucketo = buckets.get(index);
		
		for (MyPair<K,V> pair : bucketo){
			if (pair.getKey().equals(key)){
				return pair.getValue();//found itt
			}
		}

		return null;//if key not foynd

		//ADD YOUR CODE ABOVE HERE
	}
	

	/**
	 * Remove the HashPair corresponding to key . Expected average runtime O(1) 
	 */
	public V remove(K key) {
		//ADD YOUR CODE BELOW HERE
		int index = hashFunction(key);
		LinkedList<MyPair<K,V>> bucketo = buckets.get(index);

		//use iteratorrororor
		Iterator<MyPair<K,V>> iteratora = bucketo.iterator();
		while (iteratora.hasNext()) {
			MyPair<K,V> pair = iteratora.next();
			if (pair.getKey().equals(key)) {
				iteratora.remove();//iterator remove function
				size--;
				return pair.getValue();
			}
		}

		return null;

		//ADD YOUR CODE ABOVE HERE
	}


	/** 
	 * Method to double the size of the hashtable if load factor increases
	 * beyond MAX_LOAD_FACTOR.
	 * Made public for ease of testing.
	 * Expected average runtime is O(m), where m is the number of buckets
	 */
	public void rehash() {
		//ADD YOUR CODE BELOW HERE
		//so this kinda like double capacity
		ArrayList<LinkedList<MyPair<K,V>>> oldBucketos = this.buckets;
		//int oldCapacity = this.capacity;

		//double capacity and create new empty bucketos
		this.capacity *= 2;
		this.buckets = new ArrayList<>(this.capacity);
		for (int i = 0; i < this.capacity; i++) {
			this.buckets.add(new LinkedList<>());
		}
		//reset size
		this.size=0;
		//reinsert all elements from old buckets to new buckets
		for (LinkedList<MyPair<K,V>> bucket : oldBucketos) {
			for (MyPair<K,V> pair : bucket) {
				//find new bucket index using new capacity
				int newIndex = hashFunction(pair.getKey());
				//add to new bucket
				this.buckets.get(newIndex).add(pair);
				this.size++; 
			}
		}

		//ADD YOUR CODE ABOVE HERE
	}


	/**
	 * Return a list of all the keys present in this hashtable.
	 * Expected average runtime is O(m), where m is the number of buckets
	 */

	public ArrayList<K> keySet() {
		//ADD YOUR CODE BELOW HERE
		ArrayList<K> keys = new ArrayList<>();
		//return null;
		for (LinkedList<MyPair<K,V>> bucket : this.buckets) {
			for (MyPair<K,V> pair : bucket) {
				keys.add(pair.getKey());
			}
		}
	
		return keys;

		//ADD YOUR CODE ABOVE HERE
	}

	/**
	 * Returns an ArrayList of unique values present in this hashtable.
	 * Expected average runtime is O(m) where m is the number of buckets
	 */
	public ArrayList<V> values() {
		//ADD CODE BELOW HERE
		ArrayList<V> result = new ArrayList<>();

		//create temp hash table to track seen values
    	MyHashTable<V, Boolean> seenValues = new MyHashTable<>();
		for (LinkedList<MyPair<K,V>> bucket : buckets) {
			for (MyPair<K,V> pair : bucket) {
				V value = pair.getValue();
				if (seenValues.get(value) == null) {
					result.add(value);
					seenValues.put(value, true);
				}
			}
		}
		return result;

		///return null;

		//ADD CODE ABOVE HERE
	}


	/**
	 * Returns an ArrayList of all the key-value pairs present in this hashtable.
	 * Expected average runtime is O(m) where m is the number of buckets
	 */
	public ArrayList<MyPair<K, V>> entrySet() {
		//ADD CODE BELOW HERE
		ArrayList<MyPair<K,V>> allPairs = new ArrayList<>();

    	for (LinkedList<MyPair<K,V>> bucket : this.buckets) {
        	for (MyPair<K,V> pair : bucket) {
            	allPairs.add(pair);
        	}
    	}

    	return allPairs;

		//return null;

		//ADD CODE ABOVE HERE
	}

	
	
	@Override
	public MyHashIterator iterator() {
		return new MyHashIterator();
	}   

	private class MyHashIterator implements Iterator<MyPair<K,V>> {
		private LinkedList<MyPair<K,V>> entries;

		private MyHashIterator() {
			//ADD YOUR CODE BELOW HERE
			entries = new LinkedList<>();

			//looping through each bucket,add all pairs to entries
			for (LinkedList<MyPair<K,V>> bucket : buckets) {
				entries.addAll(bucket);
			}
			//ADD YOUR CODE ABOVE HERE
		}

		@Override
		public boolean hasNext() {
			//ADD YOUR CODE BELOW HERE

			return !entries.isEmpty();

			//ADD YOUR CODE ABOVE HERE
		}

		@Override
		public MyPair<K,V> next() {
			//ADD YOUR CODE BELOW HERE
			return entries.removeFirst();
			//return null;

			//ADD YOUR CODE ABOVE HERE
		}

	}
/*
	public static void main(String[] args) {
        MyHashTable<String, Integer> table = new MyHashTable<>();

        // Test put()
        System.out.println("Adding entries");
        table.put("apple", 1);
        table.put("banana", 2);
        table.put("cherry", 3);
        table.put("date", 4);
        table.put("banana", 5); // overwrite banana

        // Test get()
        System.out.println("Testing get():");
        System.out.println("apple " + table.get("apple"));    // expect 1
        System.out.println("banana " + table.get("banana"));  // expect 5 (overwritten)
        System.out.println("cherry " + table.get("cherry"));  // expect 3
        System.out.println("date " + table.get("date"));      // expect 4
        System.out.println("pear  " + table.get("pear"));      // expect null

        // Test size()
        System.out.println("\nCurrent size: " + table.size());   // expect 4

        // Test remove()
        System.out.println("\nRemoving 'cherry'");
        Integer removedValue = table.remove("cherry");
        System.out.println("Removed value: " + removedValue);    // expect 3
        System.out.println("New size: " + table.size());         // expect 3
        System.out.println("cherry" + table.get("cherry"));  // expect null

        // Test keySet()
        System.out.println("\nKeys in the table:");
        for (String key : table.keySet()) {
            System.out.println(key);
        }

        // Test values()
        System.out.println("\nValues in the table:");
        for (Integer value : table.values()) {
            System.out.println(value);
        }

        // Test entrySet()
        System.out.println("\nEntries in the table:");
        for (MyPair<String, Integer> pair : table.entrySet()) {
            System.out.println(pair.getKey() + " " + pair.getValue());
        }

        // Test iterator() with for-each loop
        System.out.println("\nTesting iterator:");
        for (MyPair<String, Integer> pair : table) {
            System.out.println(pair.getKey() + " " + pair.getValue());
        }

        // Force a rehash by adding many more entries
        System.out.println("\nForcing rehash");
        for (int i = 0; i < 20; i++) {
            table.put("key" + i, i);
        }
        System.out.println("Size after adding 20 more elements: " + table.size());
        System.out.println("Capacity after rehash: " + table.numBuckets());
    }
	*/
	
	
}
