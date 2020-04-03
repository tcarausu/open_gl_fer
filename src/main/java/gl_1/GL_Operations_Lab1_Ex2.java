package gl_1;

import glm.mat._3.Mat3;
import glm.vec._3.Vec3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static gl_1.GFG.*;

public class GL_Operations_Lab1_Ex2 {
    private static Mat3 firstMat = new Mat3();
    private static AtomicInteger x = new AtomicInteger();
    private static AtomicInteger y = new AtomicInteger();
    private static AtomicInteger z = new AtomicInteger();
    private static AtomicInteger res = new AtomicInteger();

    private static float[][] matrix = {

    };

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("values.txt"));
        double cost;
        int chunk = 4; // chunk size to divide
        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] values = line.split(",");

            retrieveMatrix(chunk, values);
            // Order of Matrix(n)
            int n = 3, flag = 0;

            // Performing Matrix transformation
            flag = PerformOperation(matrix, n);

            if (flag == 1)
                flag = CheckConsistency(matrix, n, flag);

            // Printing Final Matrix
            System.out.println("\nFinal Augmented Matrix is : ");
            PrintMatrix(matrix, n);
            System.out.println("");

            // Printing Solutions(if exist)
            PrintResult(matrix, n, flag);
        }
    }

    private static void retrieveMatrix(int chunk, String[] values) {
        System.out.println("Original Augmented Matrix: ");

        for (int i = 0; i < values.length; i += chunk) {
            String arrayV[] = Arrays.copyOfRange(values, i, Math.min(values.length, i + chunk));
            System.out.println(Arrays.toString(arrayV));

            x.getAndAdd(Integer.parseInt(arrayV[0].replace(" ", "")));
            y.getAndAdd(Integer.parseInt(arrayV[1].replace(" ", "")));
            z.getAndAdd(Integer.parseInt(arrayV[2].replace(" ", "")));
            res.getAndAdd(Integer.parseInt(arrayV[3].replace(" ", "")));

            float[][] row = {
                    {x.get(), y.get(), z.get(), res.get()}
            };
            valueReset();

            if (matrix.length < 4) {
                matrix = append(matrix, row);
            }
        }
    }

    public static float[][] append(float[][] a, float[][] b) {
        float[][] result = new float[a.length + b.length][];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    private static void valueReset() {
        x.set(0);
        y.set(0);
        z.set(0);
        res.set(0);
    }

    public static Mat3 calcRRowEche(Mat3 incomplete, Vec3 results) {
        int lead = 0;
//        int  rowCount = the number of rows in M
//        columnCount := the number of columns in M
//        for( 0 <= r < rowCount){
//                if(  columnCount ≤ lead){
//                break;
//
//            }
//        }
//        stop
//        end if
//        i = r
//        while M[i, lead] = 0 do
//            i = i + 1
//        if rowCount = i then
//                i = r
//        lead = lead + 1
//        if columnCount = lead then
//                stop
//        end if
//        end if
//        end while
//        Swap rows i and r
//        If M[r, lead] is not 0 divide row r by M[r, lead]
//        for 0 ≤ i < rowCount do
//            if i ≠ r do
//            Subtract M[i, lead] multiplied by row r from row i
//        end if
//        end for
//        lead = lead + 1
//        end for
        return new Mat3();
    }
}
