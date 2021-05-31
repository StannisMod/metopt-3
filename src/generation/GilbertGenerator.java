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

public class GilbertGenerator implements Generator {

    @Override
    public void generate() {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of("matrix_gilbert.out"), StandardCharsets.UTF_8)) {
            int n = ThreadLocalRandom.current().nextInt(10, 1000 + 1);
            double[][] matrix = new double[n][n];
            for (int i = 1; i < n + 1; i++) {
                for (int j = 1; j < n + 1; j++) {
                    double num = (double) 1 / (i + j - 1);
                    matrix[i - 1][j - 1] = num;
                    writer.write(String.valueOf(num));
                    writer.write(" ");
                }
                writer.newLine();
            }

            writer.write(Arrays.stream(getB(new PlainMatrix(matrix))).mapToObj(String::valueOf).collect(Collectors.joining(" ")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
