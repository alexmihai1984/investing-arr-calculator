package rocks.alexmihai.arr;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * Computes the daily money-weighted rate of return needed to achieve `endAmount`. It does it by solving for
 * `rateOfReturn` in the formula below. It solves by iteratively trying values closer to the right value through
 * binary search.
 * sum[0...n](cashFlow[i] / (1 + rateOfReturn)^daysHeld[i]) = endAmount
 * - cashFlow[i] - amount of money either invested (positive) or withdrawn (negative)
 * - rateOfReturn - value we are trying to find
 * - daysHeld[i] - number of days from the moment of the cash flow to the final moment
 * - endAmount - the final value of the investment
 */
public class DailySearchBasedCalculator extends Calculator {

    public static final double REQUIRED_PRECISION = 0.00000000000001;

    @Override
    public double doCompute(Map<LocalDate, BigDecimal> investedByDate, LocalDate endDate, BigDecimal endAmount) {
        double lowerBound = 0;
        double upperBound = this.determineUpperBound(investedByDate, endDate, endAmount);

        return search(investedByDate, endDate, endAmount.doubleValue(), lowerBound, upperBound);
    }

    private double search(
            Map<LocalDate, BigDecimal> investedByDate,
            LocalDate endDate,
            double endAmount,
            double lowerBound,
            double upperBound
    ) {
        double mid = (upperBound + lowerBound) / 2;

        if (upperBound - mid < REQUIRED_PRECISION) {
            return mid;
        }

        double midAmount = compute(investedByDate, endDate, mid);

        if (midAmount == endAmount) {
            return mid;
        } else if (midAmount < endAmount) {
            return search(investedByDate, endDate, endAmount, mid, upperBound);
        } else {
            return search(investedByDate, endDate, endAmount, lowerBound, mid);
        }
    }

    private double determineUpperBound(
            Map<LocalDate, BigDecimal> investedByDate,
            LocalDate endDate,
            BigDecimal endAmount
    ) {
        double finalAmount;
        double arrAttempt = 1;
        do {
            arrAttempt *= 2;
            finalAmount = compute(investedByDate, endDate, arrAttempt);
        } while (finalAmount < endAmount.doubleValue());

        return arrAttempt;
    }

    private double compute(Map<LocalDate, BigDecimal> investedByDate, LocalDate endDate, double arr) {
        double totalAmount = 0;

        for (LocalDate date : investedByDate.keySet()) {
            BigDecimal amount = investedByDate.get(date);

            long days = Math.max(1, ChronoUnit.DAYS.between(date, endDate));

            double finalAmount = amount.doubleValue() * Math.pow(arr, days);
            totalAmount += finalAmount;
        }

        return totalAmount;
    }
}
