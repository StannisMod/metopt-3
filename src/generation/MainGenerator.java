package generation;

import formats.PlainMatrix;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class MainGenerator implements Generator {
    @Override
    public void generate() {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of("matrix.out"), StandardCharsets.UTF_8)) {
            int n = ThreadLocalRandom.current().nextInt(10, 1000 + 1);
            writer.write(String.valueOf(n));
            writer.newLine();
            double[][] matrix = new double[n][n];
            // k = 0, 1, 2,...
            int k = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        matrix[i][j] = ThreadLocalRandom.current().nextInt(-4, 1);
                    }
                }
            }
            for (int i = 0; i < n; i++) {
                if (i == 0) {
                    double aii = 0;
                    for (int z = 1; z < n; z++) {
                        aii += matrix[i][z];
                    }
                    aii *= -1;
                    aii += Math.pow(10, -k);
                    matrix[i][i] = aii;
                } else {
                    double aii = 0;
                    for (int z = 0; z < n; z++) {
                        if (i != z) {
                            aii += matrix[i][z];
                        }
                    }
                    aii *= -1;
                    matrix[i][i] = aii;
                }
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    writer.write(matrix[i][j] + " ");
                }
                writer.newLine();
            }

            writer.write(Arrays.stream(getB(new PlainMatrix(matrix))).mapToObj(String::valueOf).collect(Collectors.joining(" ")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
