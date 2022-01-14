package IDS_4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.BitSet;
import java.util.Random;


public class Driver {

    // main method
    public static void main(String[] args) {
        int m = 500000;
        int l = 500;
        BitSet bitmap = new BitSet(m);
        Flow[] input = parseInputFile();
        if (input != null) {
            int[] hashes = generateRandomArray(l);
            recordFlows(bitmap, input, hashes, l, m);
            int[] estimates = calculateEstimates(bitmap, input, hashes, l, m);
            writeOutput(input, estimates);
        }
    }

    /**
     * record each flow and elements into the bitmap
     * calc hash for each element for the virtual bitmap
     * hash the flow id and xor with the random int at the position of hashE
     * then set the bit in physical map to true
     */
    private static void recordFlows(BitSet bitmap, Flow[] input, int[] hashes, int l, int m) {
        for (Flow flow : input) {
            for (int element : flow.getElements()) {
                int hashe = Math.abs(hash(element)) % l;
                int hashF = hash(hash(hash(flow.getFlowId()) ^ hashes[hashe])) % m;
                bitmap.set(hashF, true);
            }
        }
    }

    /**
     * this method calculates number of zeros for entire bitmap
     * & num of zeros in each virtual bitmap
     * then calculate estimate by l*log(vb)-l*log(vf)
     */
    private static int[] calculateEstimates(BitSet bitmap, Flow[] input, int[] hashes, int l, int m) {
        // cardinality gives number of 1's set in bitmap
        // therefore to get number of 0's total bits (m) - cardinality
        double Vb = (m - bitmap.cardinality()) / (m * 1.0);

        int[] estimates = new int[m];
        int idx = 0;

        //calculate estimated spread
        for (Flow flow : input) {
            int zeros = 0;
            for (int i = 0; i < l; i++) {
                int hashF = hash(hash(flow.getFlowId()) ^ hashes[i]) % m;
                if (bitmap.get(hashF)) zeros++;
            }
            double Vf = (1.0 * zeros) / l;
            double estimatedSpread = (l * Math.log(Vb)) - (l * Math.log(Vf));
            estimates[idx] = (int) Math.round(estimatedSpread);
            if (estimates[idx] < 0) estimates[idx] = 0;
            idx++;
        }
        return estimates;
    }

    /**
     * Write output to a csv file.
     */
    private static void writeOutput(Flow[] input, int[] estimates) {
        final String NEWLINE = "\n";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("output.csv"))) {
            bw.write("Actual Spread,Estimated Spread" + NEWLINE);
            for (int i = 0; i < input.length; i++) {
                bw.write(input[i].getElementCount() + "," + estimates[i] + NEWLINE);
            }
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Method to parse the input file
     * Split each line and extracts flow ids and number of elements
     *
     * @return array of Flows
     */
    private static Flow[] parseInputFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/IDS_4/project4input.txt"))) {
            int n = Integer.parseInt(br.readLine());
            Flow[] inp = new Flow[n];
            for (int i = 0; i < n; i++) {
                String[] words = br.readLine().split("\\s+");
                inp[i] = new Flow(words[0], Integer.parseInt(words[1]));
            }
            return inp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // this function is a modification of one of the hash functions provided in the below website
    // http://web.archive.org/web/20071223173210/http://www.concentric.net/~Ttwang/tech/inthash.htm
    private static int hash(int key) {
        int c2=0x27d4eb2d;
        key = (key ^ 61) ^ (key >>> 16);
        key = key + (key << 3);
        key = key ^ (key >>> 4);
        key = key * c2;
        key = key ^ (key >>> 15);
        return Math.abs(key);
    }

    private static int hash(String str) {
        int h = 7;
        for (int i = 0; i < str.length(); i++) {
            h = h * 31 + str.charAt(i);
        }
        return h;
    }

    // method that generates a random integer array
    private static int[] generateRandomArray(int n) {
        Random random = new Random(Integer.MAX_VALUE);
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt() + 1;
        }
        return arr;
    }
}
