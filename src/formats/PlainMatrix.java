package formats;

import api.Matrix;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PlainMatrix implements Matrix {

    private final double[][] data;

    public PlainMatrix(int i, int j) {
        this(new double[i][j]);
    }

    public PlainMatrix(double[][] data) {
        this.data = data;
    }

    public double[][] getData() {
        return data;
    }

    public void swap(int i, int j) {
        List<double[]> list = Arrays.asList(data);
        Collections.swap(list, i, j);
        list.toArray(data);
    }

    @Override
    public int getWidth() {
        return data[0].length;
    }

    @Override
    public int getHeight() {
        return data.length;
    }

    @Override
    public double get(final int i, final int j) {
        return data[i][j];
    }

    @Override
    public void set(final int i, final int j, final double v) {
        data[i][j] = v;
    }

    @Override
    public String toString() {
        return Arrays.stream(data).map(m -> Arrays.stream(m).mapToObj(String::valueOf).collect(Collectors.joining("\t"))).collect(Collectors.joining(System.lineSeparator()));
    }
}
