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

    private static void check(int test, String method, double[] x) {
        for (int i = 0; i < x.length; i++) {
            if (Math.abs(x[i] - (i + 1)) > 0.000001) {
                throw new AssertionError("Wrong answer on test " + test + ", solved with method " + method + ": " + Arrays.toString(x));
            }
        }
    }

    public static void runTest(int i, Generator generator, Method method) {
        generator.generate();
        try (BufferedReader reader = Files.newBufferedReader(Path.of("matrix.out"))) {
            Matrix A = loadMatrix(reader);
            double[] b = Arrays.stream(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
            double[] solution = method.solve(A, b);
            check(i, method.getClass().getName(), solution);
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
                        runTest(i, gilbert, lu);
                    } catch (IllegalStateException e) {
                        writer.write("Test " + i + ": error while evaluating LU method: " + e.getMessage());
                        writer.newLine();
                    }
                    runTest(i, main, gauss);
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
