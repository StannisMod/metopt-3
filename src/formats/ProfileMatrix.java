package formats;

import api.Matrix;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProfileMatrix implements Matrix {

    double[][] data;

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public double get(final int i, final int j) {
        return 0;
    }

    @Override
    public void set(final int i, final int j, final double v) {

    }

    public void swap(int i, int j) {
        List<double[]> list = Arrays.asList(data);
        Collections.swap(list, i, j);
        data = (double[][]) list.toArray();
    }
}
