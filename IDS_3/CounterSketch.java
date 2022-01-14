package IDS_3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CounterSketch {
    private static final String NEWLINE = "\n";

    public static void insert(int w, int[][] filter, int[] hash, int[] inp) {
        long key = inp[0];
        long value = inp[1];
        long bit;
        for (int i = 0; i < hash.length; i++) {
            bit = ((key ^ hash[i]) >> 31) & 1;
            int index = (int) Math.abs((key ^ hash[i]) % w);
            if (bit == 0L) {
                filter[i][index] -= value;
            } else {
                filter[i][index] += value;
            }
        }
    }

    public static int getMedian(int w, int[][] filter, int[] hash, int[] inp) {
        long key = inp[0];
        long bit;
        int[] res = new int[hash.length];
        for (int i = 0; i < hash.length; i++) {
            bit = ((key ^ hash[i]) >> 31) & 1;
            int index = (int) Math.abs((key ^ hash[i]) % w);
            res[i] = filter[i][index];
            if (bit == 0L) {
                res[i] = -res[i];
            }
        }
        Arrays.sort(res);
        return res[1];
    }

    public static void execute(int k, int w, int[][] inp) {
        int[][] filter = new int[k][w];
        int[] hash = Driver.getRandomArray(k);
        for (int[] i : inp) {
            CounterSketch.insert(w, filter, hash, i);
        }
        List<long[]> result = new ArrayList<>();
        int error = 0;
        for (int[] i : inp) {
            int median = CounterSketch.getMedian(w, filter, hash, i);
            error += Math.abs((median - i[1]));
            result.add(new long[]{median, i[0], i[1]});
        }
        result.sort((a, b) -> (int) (b[0] - a[0]));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("CounterSketch.txt"))) {
            bw.write("Error: " + (error / inp.length));
            bw.write(NEWLINE);
            bw.write("Actual, FlowId, Expected" + NEWLINE);
            for (long[] a : result) {
                bw.write(a[0] + "," + a[1] + "," + a[2] + NEWLINE);
            }
            bw.write(NEWLINE);
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
