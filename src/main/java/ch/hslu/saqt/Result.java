package ch.hslu.saqt;

import java.util.StringJoiner;

/**
 * A result.
 */
public class Result {
    public String term;

    public int trials;

    public boolean passed;

    public Result(String term, int trials, boolean passed) {
        this.term = term;
        this.trials = trials;
        this.passed = passed;
    }

    @Override
    public String toString() {
        String outcome;

        if (passed) {
            outcome = "PASSED";
        } else {
            outcome = "FAILED";
        }

        StringJoiner stringJoiner = new StringJoiner(";");
        stringJoiner.add(term).add("" + trials).add(outcome);
        return stringJoiner.toString();
    }
}
