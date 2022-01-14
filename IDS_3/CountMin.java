package IDS_3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class CountMin {
    private static final String NEWLINE = "\n";

    public static void insert(int w, int[][] filter, int[] hash, int[] inp) {
        long key = inp[0];
        long value = inp[1];
        for (int i = 0; i < hash.length; i++) {
            int index = (int) Math.abs((key ^ hash[i]) % w);
            filter[i][index] += value;
        }
    }

    public static int getMin(int w, int[][] filter, int[] hash, int[] inp) {
        long key = inp[0];
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < hash.length; i++) {
            int index = (int) Math.abs((key ^ hash[i]) % w);
            if (min > filter[i][index]) {
                min = filter[i][index];
            }
        }
        return min;
    }

    public static void execute(int k, int w, int[][] inp) {
        int[][] filter = new int[k][w];
        int[] hash = Driver.getRandomArray(k);
        for (int[] i : inp) {
            CountMin.insert(w, filter, hash, i);
        }
        List<long[]> result = new ArrayList<>();
        int error = 0;
        for (int[] i : inp) {
            int min = CountMin.getMin(w, filter, hash, i);
            error += Math.abs(min - i[1]);
            result.add(new long[]{min, i[0], i[1]});
        }
        result.sort((a, b) -> (int) (b[0] - a[0]));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("CountMinOutput.txt"))) {
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
