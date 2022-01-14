
public class DLeft {
	static int numberOfEntries;
	static int numberOfFlows;
	static int numberOfSegments;
	static int totalCount;

	public static void main(String[] args) {
		numberOfEntries = 1000;
		numberOfFlows = 1000;
		//each segment has 250 table entries
		numberOfSegments = 4;

		generateHashTable(numberOfEntries, numberOfFlows, numberOfSegments);
	}

	// insert element in hash table
	public static void insert(int flowID, int[] k, int flows, int[] hashTableArray) {
		int index;
		for (int i = 0; i < k.length; i++) {
			index = ((flowID ^ k[i]) % flows / 4) + (flows / 4 * i);
			if (hashTableArray[index] == 0) {
				hashTableArray[index] = flowID;
				totalCount++;
				break;
			}
		}
	}

	private static void generateHashTable(int numberOfEntries, int flows, int numberOfSegments) {
		// for k hash functions
		int[] k = new int[numberOfSegments];
		// initialize k with random integers
		for (int i = 0; i < numberOfSegments; i++) {
			k[i] = (int) (Math.random() * Math.pow(10, 9));
		}
		// array to store hash table entries
		int[] hashTableArray = new int[numberOfEntries];
		for (int i = 0; i < numberOfEntries; i++) {
			int flowID = (int) (Math.random() * 10000);
			insert(flowID, k, flows, hashTableArray);
		}
		System.out.println("No. of flows: " + totalCount);
		for (int i : hashTableArray) {
			System.out.print(i + ", ");
		}
	}

}
