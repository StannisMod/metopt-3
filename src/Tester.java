import api.Matrix;
import api.Method;
import formats.PlainMatrix;
import formats.ProfileMatrix;
import generation.Generator;
import generation.GilbertGenerator;
import generation.MainGenerator;
import methods.Gauss;
import methods.LUMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    private static void check(int test, double[] x) {
        for (int i = 0; i < x.length; i++) {
            if (Math.abs(x[i] - (i + 1)) > 0.000001) {
                throw new AssertionError("Wrong answer on test " + test + ": " + Arrays.toString(x));
            }
        }
    }

    public static void runTest(int i, Generator generator, Method method) {
        generator.generate();
        try (BufferedReader reader = Files.newBufferedReader(Path.of("matrix.out"))) {
            Matrix A = loadMatrix(reader);
            double[] b = Arrays.stream(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
            double[] solution = method.solve(A, b);
            check(i, solution);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Generator main = new MainGenerator();
        Generator gilbert = new GilbertGenerator();
        Method lu = new LUMethod();
        Method gauss = new Gauss();
        for (int i = 0; i < 10; i++) {
            runTest(i, gilbert, lu);
            runTest(i, main, gauss);
        }
    }
}
