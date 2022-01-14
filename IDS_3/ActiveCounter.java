package IDS_3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ActiveCounter {

    public static void insert(int[] counter, int presentBit, int prevCounterIndex, int exponentPart) {
        if (presentBit > prevCounterIndex) {
            insert(counter, 0, exponentPart, exponentPart);
            for (int k = prevCounterIndex; k > exponentPart - 1; k--) {
                counter[k] = (k == prevCounterIndex) ? 1 : 0;
            }
        } else {
            if (counter[presentBit] == 1) {
                counter[presentBit] = 0;
                insert(counter, presentBit + 1, prevCounterIndex, exponentPart);
            } else if (counter[presentBit] == 0) {
                counter[presentBit] = 1;
            }
        }
    }

    public static long exp2Dec(int[] counter, int exponentPart) {
        long result;
        StringBuilder sb = new StringBuilder();
        for (int i = exponentPart - 1; i >= 0; i--)
            sb.insert(exponentPart - i - 1, counter[i]);
        result = Integer.parseInt(sb.toString(), 2);
        return result;
    }

    public static void execute(int num) {
        int[] counter = new int[32];
        int res, p = 1;
        for (int i = 0; i < num; i++) {
            if (Driver.random.nextInt(p) == 0) {
                insert(counter, 16, 31, 16);
            }
            res = (int) exp2Dec(counter, 16);
            p = (int) Math.ceil(Math.pow(2, res));
        }

        // converting binary to decimal for output
        long result;
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (int i = 15; i >= 0; i--)
            sb.insert(15 - i, counter[i]);
        for (int i = counter.length - 1; i >= 16; i--)
            sb2.insert(counter.length - 1 - i, counter[i]);
        result = Integer.parseInt(sb2.toString(), 2);
        result *= Math.pow(2, Integer.parseInt(new String(sb), 2));


        try (BufferedWriter bw = new BufferedWriter(new FileWriter("ActiveCounter.txt"))) {
            bw.write("Final Value of Active Counter: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
