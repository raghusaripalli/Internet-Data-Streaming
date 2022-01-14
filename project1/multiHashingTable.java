
public class multiHashingTable {
	static int numberOfEntries;
	static int numberOfFlows;
	static int numberOfHashes;
	static int finalCount = 0;

	public static void main(String[] args) {
		numberOfEntries = 1000;
		numberOfFlows = 1000;
		numberOfHashes = 3;

		buildHashtable(numberOfEntries, numberOfFlows, numberOfHashes);

	}

	// insert in Hash table
	private static void insert(int flowID, int[] k, int numberOfFlows, int[] hashTableArray) {
		int index;
		for (int i = 0; i < k.length; i++) {
			index = (flowID ^ k[i]) % numberOfFlows;
			if (hashTableArray[index] == 0) {
				hashTableArray[index] = flowID;
				finalCount++;
				break;
			}
		}

	}

	private static void buildHashtable(int numberOfEntries, int numberOfFlows, int numberOfHashes) {

		// array for k hash functions
		int[] k = new int[numberOfHashes];

		// initializing k with random integers
		int i = 0;
		while (i < numberOfHashes) {
			k[i] = (int) (Math.random() * Math.pow(10, 8));
			i++;
		}

		// array to store Hash table entries
		int[] hashTableArray = new int[numberOfEntries];
		for (int l = 0; l < numberOfEntries; l++) {
			int flowID = (int) (Math.random() * 10000);

			// insert an element in the Hash table

			insert(flowID, k, numberOfFlows, hashTableArray);

		}
		System.out.println("No. of flows: " + finalCount);
		for (int j = 0; j < hashTableArray.length; j++) {
			System.out.println(hashTableArray[j] + ", ");
		}

	}

}
