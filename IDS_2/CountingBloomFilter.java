package IDS_2;

import java.io.FileWriter;
import java.util.Random;

public class CountingBloomFilter {
    Random random = new Random(System.nanoTime());
    int numberOfHashes = 7;
    int numberOfElements = 1000;
    int numberOfBits = 10000;
    int[] bloomFilterTable;

    CountingBloomFilter() {
        bloomFilterTable = new int[numberOfBits];
    }

    // generates a very LARGE random number
    int rInt() {
        return random.nextInt(Integer.MAX_VALUE);
    }

    // Searches for given array elements in bloomFilter
    int search(int[] arr, int[] hashes) {
        int count = 0;
        for (int i = 0; i < numberOfElements; i++) {
            int minCount = Integer.MAX_VALUE;
            int localCount = 0;
            for (int j = 0; j < numberOfHashes; j++) {
                int index = (arr[i] ^ hashes[j]) % numberOfBits;
                if (bloomFilterTable[index] > 0)
                    localCount++;
                minCount = Math.min(minCount, bloomFilterTable[index]);
            }
            if (localCount == numberOfHashes) {
                count+= minCount;
            }
        }
        return count;
    }

    void addAll(int[] arr, int[] hashes, int elementCount) {
        for (int i = 0; i < elementCount; i++) {
            for (int j = 0; j < numberOfHashes; j++) {
                int index = (arr[i] ^ hashes[j]) % numberOfBits;
                bloomFilterTable[index] += 1;
            }
        }
    }

    void removeFirst500(int[] arr, int[] hashes) {
        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < numberOfHashes; j++) {
                int index = (arr[i] ^ hashes[j]) % numberOfBits;
                if (bloomFilterTable[index] > 0)
                    bloomFilterTable[index] -= 1;
            }
        }
    }

    // implements normal bloom filter
    void execute() {
        int[] A = new int[this.numberOfElements];
        int[] newA = new int[500];
        int[] hashes = new int[this.numberOfHashes];

        for (int i = 0; i < numberOfElements; i++) A[i] = rInt();
        for (int i = 0; i < numberOfHashes; i++) hashes[i] = rInt();
        for (int i = 0; i < 500; i++) newA[i] = rInt();

        // Insert A into bloomFilter
        addAll(A, hashes, numberOfElements);
        // remove first 500 elements
        removeFirst500(A, hashes);
        // Insert newA into bloomFilter
        addAll(newA, hashes, 500);

        // search for elements in A in bloomFilter
        int count = search(A, hashes);

        try (FileWriter fileWriter = new FileWriter("CountingBloomFilterOutput.txt")) {
            fileWriter.write(String.format("Lookup count in A: %d\n", count));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CountingBloomFilter countingBloomFilter = new CountingBloomFilter();
        countingBloomFilter.execute();
    }
}

