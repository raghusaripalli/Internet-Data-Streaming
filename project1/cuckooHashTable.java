
public class cuckooHashTable {
	static int numberOfEntries;
	static int numberOfFlows;
	static int numberOfHashes;
	static int numberOfSteps;
	static int finalCount = 0;

	public static void main(String[] args) {
		numberOfEntries = 1000;
		numberOfFlows = 1000;
		numberOfHashes = 3;
		numberOfSteps = 2;

		generateCuckooHashTable(numberOfEntries, numberOfFlows, numberOfHashes, numberOfSteps);
	}

	// insert element in hash table
	private static boolean insert(int flowID, int[] k, int flows, int[] hashTableArray, int steps) {
		int index;
		for (int i = 0; i < k.length; i++) {
			index = (flowID ^ k[i]) % flows;
			if (hashTableArray[index] == 0) {
				hashTableArray[index] = flowID;
				finalCount++;
				return true;
			}
		}
		for (int i = 0; i < k.length; i++) {
			index = (flowID ^ k[i]) % flows;
			if (move(flowID, steps, k, flows, hashTableArray, index) == true) {
				hashTableArray[index] = flowID;
				finalCount++;
				return true;
			}
		}
		return false;
	}

	private static void generateCuckooHashTable(int numberOfEntries, int numberOfFlows, int numberOfHashes,
			int numberOfSteps) {
		// for k hash functions
		int[] k = new int[numberOfHashes];
		// initialize k with random integers
		for (int i = 0; i < numberOfHashes; i++) {
			k[i] = (int) (Math.random() * Math.pow(10, 8));
		}
		// array to store hash table entries
		int[] hashTableArray = new int[numberOfEntries];
		for (int i = 0; i < numberOfEntries; i++) {
			int flowID = (int) (Math.random() * 10000);
			insert(flowID, k, numberOfFlows, hashTableArray, numberOfSteps);
		}
		System.out.println("No. of flows: " + finalCount);
		// printing all the hash table entries
		for (int i : hashTableArray) {
			System.out.print(i + ", ");
		}
	}

	// move already existing element to another index if possible
	private static boolean move(int flowID, int steps, int[] k, int flows, int[] arr, int index) {
		if (steps == 0)
			return false;
		int flowId1 = arr[index];
		int ind;
		for (int i = 0; i < k.length; i++) {
			ind = (flowId1 ^ k[i]) % flows;
			if (ind != index && arr[ind] == 0) {
				arr[ind] = flowId1;
				return true;
			}
		}
		for (int i = 0; i < k.length; i++) {
			ind = (flowId1 ^ k[i]) % flows;
			if (ind != index && move(flowId1, steps - 1, k, flows, arr, ind) == true) {
				arr[ind] = flowId1;
				return true;
			}
		}
		return false;
	}

}
