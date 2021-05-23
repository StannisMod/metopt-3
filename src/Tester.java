import api.Matrix;
import api.Method;
import formats.PlainMatrix;
import methods.Gauss;
import methods.LUMethod;

import java.util.Arrays;

public class Tester {

    public static void main(String[] args) {
        Matrix matrix = new PlainMatrix(3, 3);
        matrix.set(0, 0, -7);
        matrix.set(0, 1, 10);
        matrix.set(0, 2, 0);
        matrix.set(1, 0, -3);
        matrix.set(1, 1, 6);
        matrix.set(1, 2, 2);
        matrix.set(2, 0, 5);
        matrix.set(2, 1, -1);
        matrix.set(2, 2, 5);
        Method Gauss = new Gauss();
        double[] res = Gauss.solve(matrix, new double[]{1.0, 2.0, 3.0});
        System.out.println(Arrays.toString(res));
    }
}
