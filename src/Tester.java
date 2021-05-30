import api.Matrix;
import api.Method;
import formats.PlainMatrix;
import formats.ProfileMatrix;
import methods.LUMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class Tester {

    private static Matrix loadMatrix(BufferedReader reader) throws IOException {
        int n = Integer.parseInt(reader.readLine());
        PlainMatrix read = new PlainMatrix(n, n);
        for (int i = 0; i < n; i++) {
            String[] input = reader.readLine().split(" ");
            for (int j = 0; j < n; j++) {
                read.set(i, j, Double.parseDouble(input[j]));
            }
        }
        return new ProfileMatrix(read.getData());
    }

    public static void main(String[] args) {
        Matrix matrix = new PlainMatrix(3, 3);
//        matrix.set(0, 0, 10);
//        matrix.set(0, 1, -7);
//        matrix.set(0, 2, 0);
//        matrix.set(1, 0, -3);
//        matrix.set(1, 1, 6);
//        matrix.set(1, 2, 2);
//        matrix.set(2, 0, 5);
//        matrix.set(2, 1, -1);
//        matrix.set(2, 2, 5);
        matrix.set(0, 0, 1);
        matrix.set(0, 1, 1);
        matrix.set(0, 2, 1);
        matrix.set(1, 0, 1);
        matrix.set(1, 1, 2);
        matrix.set(1, 2, 3);
        matrix.set(2, 0, 1);
        matrix.set(2, 1, 2);
        matrix.set(2, 2, 2);
        Method lu = new LUMethod();
        double[] res = lu.solve(matrix, new double[]{1, 1, 1});
        System.out.println(Arrays.toString(res));
    }
}
