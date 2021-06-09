import api.Matrix;
import api.Method;
import formats.PlainMatrix;
import formats.ProfileMatrix;
import generation.Generator;
import generation.GilbertGenerator;
import generation.MainGenerator;
import methods.Gauss;
import methods.LUMethod;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Tester {

    private static PlainMatrix loadMatrix(BufferedReader reader) throws IOException {
        int n = Integer.parseInt(reader.readLine());
        PlainMatrix read = new PlainMatrix(n, n);
        for (int i = 0; i < n; i++) {
            String[] input = reader.readLine().split(" ");
            for (int j = 0; j < n; j++) {
                read.set(i, j, Double.parseDouble(input[j]));
            }
        }
        return read;
    }

    private static void check(int test, String method, String gen, double[] x) {
        for (int i = 0; i < x.length; i++) {
            if (Math.abs(x[i] - (i + 1)) > 0.000001) {
                throw new AssertionError("Wrong answer on test " + test + ", solved with method " + method + " on matrix, generated by " + gen + ": " + Arrays.toString(x));
            }
        }
    }

    public static void runTest(int i, Generator generator, Method method, boolean profile) {
        generator.generate();
        try (BufferedReader reader = Files.newBufferedReader(Path.of("matrix.out"))) {
            Matrix A = loadMatrix(reader);
            if (profile) {
                A = new ProfileMatrix(((PlainMatrix) A).getData());
            }
            double[] b = Arrays.stream(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
            double[] solution = method.solve(A, b);
            check(i, method.getClass().getSimpleName(), generator.getClass().getSimpleName(), solution);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Generator main = new MainGenerator();
        Generator gilbert = new GilbertGenerator();
        Method lu = new LUMethod();
        Method gauss = new Gauss();

        final int TESTS = 10;
        int counter = 0;
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("tests.log")))) {
            for (int i = 0; i < TESTS; i++) {
                try {
                    try {
                        runTest(i, main, lu, true);
                    } catch (IllegalStateException e) {
                        throw new AssertionError("Test " + i + ": error while evaluating LU method on common-generated matrix: " + e.getMessage());
                    }
                    try {
                        runTest(i, gilbert, lu, true);
                    } catch (IllegalStateException e) {
                        throw new AssertionError("Test " + i + ": error while evaluating LU method on Gilbert matrix: " + e.getMessage());
                    }
                    runTest(i, main, gauss, false);
                    runTest(i, gilbert, gauss, false);
                    counter++;
                    System.out.println("Test " + i + " passed");
                } catch (AssertionError e) {
                    writer.write(e.getMessage());
                    writer.newLine();
                } catch (RuntimeException e) {
                    writer.write("Fatal error on test " + i + ":");
                    writer.write(e.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("--------------------------------------");
        System.out.println("Tests: " + counter + " passed, " + (TESTS - counter) + " failed");
    }
}
