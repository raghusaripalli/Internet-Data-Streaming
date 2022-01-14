package IDS_3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.Instant;
import java.util.Random;

public class Driver {
    public static final Random random = new Random(Instant.now().toEpochMilli());


    // this function is a modification of one of the hash functions provided in the below website
    // http://web.archive.org/web/20071223173210/http://www.concentric.net/~Ttwang/tech/inthash.htm
    private static int parseIPAddress(String ip) {
        int key = ip.hashCode() % Integer.MAX_VALUE;
        key = ~key + (key << 15);
        key = key ^ (key >>> 12);
        key = key + (key << 2);
        key = key ^ (key >>> 4);
        key = key * 2057;
        key = key ^ (key >>> 16);
        return key;
    }

    private static int[][] parseInputFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("project3input.txt"))) {
            int n = Integer.parseInt(br.readLine());
            int[][] inp = new int[n][2];
            for (int i = 0; i < n; i++) {
                String[] words = br.readLine().split("\\s+");
                inp[i][0] = parseIPAddress(words[0]);
                inp[i][1] = Integer.parseInt(words[1]);
            }
            return inp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int[] getRandomArray(int k) {
        int[] hash = new int[k];
        for (int i = 0; i < k; i++) {
            hash[i] = random.nextInt(Integer.MAX_VALUE);
        }
        return hash;
    }


    public static void main(String[] args) {
        // number of counter arrays
        int k = 3;
        // number of counters in each counter array
        int w = 3000;
        // read input from file
        int[][] inp = parseInputFile();

        if (inp != null) {
            CountMin.execute(k, w, inp);
            CounterSketch.execute(k, w, inp);
            ActiveCounter.execute(1000000);
        }
    }
}
