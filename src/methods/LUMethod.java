package methods;

import api.Matrix;
import api.Method;
import formats.LMatrix;
import formats.PlainMatrix;
import formats.UMatrix;

public class LUMethod implements Method {
    @Override
    public double[] solve(final Matrix A, final double[] b) {
        int n = A.getWidth();
        LU(A);
        System.out.println("L: ");
        System.out.println(new LMatrix(A));
        System.out.println("U: ");
        System.out.println(new UMatrix(A));

        double[] y = new double[n];
        for (int i = 0; i < n; i++) {
            double v = 0;
            for (int m = 0; m < i; m++) {
                v += A.get(i, m) * y[m];
            }
            y[i] = b[i] - v;
        }

        double[] x = new double[n];
        for (int i = 1; i < n; i++) {
            double v = 0;
            for (int m = 0; m < i; m++) {
                v += A.get(i, m) * x[m];
            }
            x[i] =(y[i] - v) / A.get(i, i);
        }
        return x;
    }

    private void LU(Matrix a) {
        int n = a.getWidth();
        for (int k = 0; k < n; k++) {
            for (int j = k; j < n; j++) {
                double v = 0;
                for (int m = 0; m < k; m++) {
                    v += a.get(k, m) * a.get(m, j);
                }
                a.set(k, j, a.get(k, j) - v);
            }
            for (int i = k + 1; i < n; i++) {
                if (a.get(k, k) != 0) {
                    double v = 0;
                    for (int m = 0; m < k; m++) {
                        v += a.get(i, m) * a.get(m, k);
                    }
                    a.set(i, k, (a.get(i, k) - v) / a.get(k, k));
                } else {
                    System.err.println("not all major minors of a square matrix are nonzero");
                }
            }
        }
    }

    Matrix mul(Matrix A, Matrix B) {
        int n = A.getWidth();
        Matrix R = new PlainMatrix(A.getWidth(), A.getHeight());
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    R.set(i, j, R.get(i, j) + A.get(i, k) * B.get(k, j));
                }
            }
        }
        return R;
    }
}
