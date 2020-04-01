package gl_1;

import glm.mat._3.Mat3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class GL_2 {
    private static Mat3 firstMat = new Mat3();
    private static AtomicInteger x = new AtomicInteger();
    private static AtomicInteger y = new AtomicInteger();
    private static AtomicInteger z = new AtomicInteger();
    private static AtomicInteger res = new AtomicInteger();

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("values.txt"));
        double cost;
        int chunk = 4; // chunk size to divide
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] values = line.split(",");

            for (int i = 0; i < values.length; i += chunk) {
                String arrayV[] = Arrays.copyOfRange(values, i, Math.min(values.length, i + chunk));
                System.out.println(Arrays.toString(arrayV));

                x.getAndAdd(Integer.parseInt(arrayV[0].replace(" ","")));
                y.getAndAdd(Integer.parseInt(arrayV[1].replace(" ","")));
                z.getAndAdd(Integer.parseInt(arrayV[2].replace(" ","")));
                res.getAndAdd(Integer.parseInt(arrayV[3].replace(" ","")));
            }

        }
    }
}
