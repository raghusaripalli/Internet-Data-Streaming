package IDS_2;

import java.io.FileWriter;
import java.util.Random;

public class NormalBloomFilter {
    Random random = new Random(System.nanoTime());
    int numberOfHashes = 7;
    int numberOfElements = 1000;
    int numberOfBits = 10000;
    int[] bloomFilterTable;

    NormalBloomFilter() {
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
            int localCount = 0;
            for (int j = 0; j < numberOfHashes; j++) {
                int index = (arr[i] ^ hashes[j]) % numberOfBits;
                if (bloomFilterTable[index] == 1)
                    localCount++;
            }
            if (localCount == numberOfHashes) {
                count++;
            }
        }
        return count;
    }

    // implements normal bloom filter
    void execute() {
        int[] A = new int[this.numberOfElements];
        int[] B = new int[this.numberOfElements];
        int[] hashes = new int[this.numberOfHashes];

        for (int i = 0; i < numberOfElements; i++) A[i] = rInt();
        for (int i = 0; i < numberOfHashes; i++) hashes[i] = rInt();

        // Insert data into bloomFilter
        for (int i = 0; i < numberOfElements; i++) {
            for (int j = 0; j < numberOfHashes; j++) {
                int index = (A[i] ^ hashes[j]) % numberOfBits;
                bloomFilterTable[index] = 1;
            }
        }
        for (int i = 0; i < numberOfElements; i++) B[i] = rInt();
        int count1 = search(A, hashes);
        int count2 = search(B, hashes);

        try (FileWriter fileWriter = new FileWriter("NormalBloomFilterOutput.txt")) {
            fileWriter.write(String.format("Lookup count in A: %d\n", count1));
            fileWriter.write(String.format("Lookup count in B: %d", count2));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NormalBloomFilter normalBloomFilter = new NormalBloomFilter();
        normalBloomFilter.execute();
    }
}
