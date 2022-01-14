package IDS_2;

import java.io.FileWriter;
import java.util.Random;

public class CodedBloomFilter {
    Random random = new Random(System.nanoTime());
    int numberOfHashes = 7;
    int numberOfElements = 1000;
    int numberOfBits = 30000;
    int numberOfFilters = 3;
    int numberOfSets = 7;
    int[][] bloomFilterTable;

    CodedBloomFilter() {
        bloomFilterTable = new int[numberOfFilters][numberOfBits];
    }

    // generates a very LARGE random number
    int rInt() {
        return random.nextInt(Integer.MAX_VALUE);
    }

    int search(int[][] sets, int[] hashes) {
        int count = 0;
        for (int i = 1; i < numberOfSets + 1; i++) {
            for (int k = 0; k < numberOfElements; k++) {
                int result = 0;
                int element = sets[i - 1][k];
                for (int bit = 0; bit < numberOfFilters; bit++) {
                    int flag = 0;
                    for (int j = 0; j < numberOfHashes; j++) {
                        int index = (element ^ hashes[j]) % numberOfBits;
                        if (bloomFilterTable[bit][index] == 1)
                            flag += 1;
                        if (flag == 7)
                            result |= (1 << bit);
                        if (result == i)
                            count += 1;
                    }
                }
            }
        }
        return count;
    }

    // implements normal bloom filter
    void execute() {
        int[][] sets = new int[numberOfSets][numberOfElements];
        int[] hashes = new int[this.numberOfHashes];

        for (int i = 0; i < numberOfSets; i++) {
            for (int j = 0; j < numberOfElements; j++) {
                sets[i][j] = rInt();
            }
        }
        for (int i = 0; i < numberOfHashes; i++) hashes[i] = rInt();


        // add sets to bloom filter
        for (int i = 1; i < numberOfSets + 1; i++) {
            for (int bit = 0; bit < numberOfFilters; bit++) {
                int x = (i >> bit) & 1;
                if (x == 1) {
                    for (int k = 0; k < numberOfElements; k++) {
                        int element = sets[i - 1][k];
                        for (int j = 0; j < numberOfHashes; j++) {
                            int index = (element ^ hashes[j]) % numberOfBits;
                            bloomFilterTable[bit][index] = 1;
                        }
                    }
                }
            }
        }

        // count of elements in bloomFilter
        int count = search(sets, hashes);

        try (FileWriter fileWriter = new FileWriter("CodedBloomFilterOutput.txt")) {
            fileWriter.write(String.format("Lookup count in A: %d\n", count));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CodedBloomFilter codedBloomFilter = new CodedBloomFilter();
        codedBloomFilter.execute();
    }
}
