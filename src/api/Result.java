package api;

public class Result {

    public final double[] solution;
    public final int actions;

    public Result(double[] solution, int actions) {
        this.solution = solution;
        this.actions = actions;
    }

    public double getFault() {
        double sum = 0;
        for (int i = 0; i < solution.length; i++) {
            sum += Math.pow(solution[i] - i - 1, 2);
        }
        return Math.sqrt(sum);
    }
}
