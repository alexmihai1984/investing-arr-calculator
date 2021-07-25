package rocks.alexmihai.arr;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class DailyReturnRateCalculator {

    public static final double REQUIRED_PRECISION = 0.00000000000001;

    public double compute(Map<LocalDate, BigDecimal> investedByDate, LocalDate endDate, BigDecimal endAmount) {
        this.assertDates(investedByDate.keySet(), endDate);

        this.assertAmounts(investedByDate.values());
        this.assertAmount(endAmount);

        double lowerBound = 0;
        double upperBound = this.determineUpperBound(investedByDate, endDate, endAmount);

        return this.search(investedByDate, endDate, endAmount.doubleValue(), lowerBound, upperBound);
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

        double midAmount = this.compute(investedByDate, endDate, mid);

        if (midAmount == endAmount) {
            return mid;
        } else if (midAmount < endAmount) {
            return this.search(investedByDate, endDate, endAmount, mid, upperBound);
        } else {
            return this.search(investedByDate, endDate, endAmount, lowerBound, mid);
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
            finalAmount = this.compute(investedByDate, endDate, arrAttempt);
        } while (finalAmount < endAmount.doubleValue());

        return arrAttempt;
    }

    private double compute(Map<LocalDate, BigDecimal> investedByDate, LocalDate endDate, double arr) {
        double totalAmount = 0;

        for (LocalDate date : investedByDate.keySet()) {
            BigDecimal amount = investedByDate.get(date);

            long days = ChronoUnit.DAYS.between(date, endDate);

            double finalAmount = amount.doubleValue() * Math.pow(arr, days);
            totalAmount += finalAmount;
        }

        return totalAmount;
    }

    private void assertDates(Set<LocalDate> dates, LocalDate endDate) {
        dates.forEach(d -> {
            if (d.isAfter(endDate)) {
                throw new IllegalArgumentException("Date '" + d + "' is after end date '" + endDate + "'");
            }
        });
    }

    private void assertAmounts(Collection<BigDecimal> amounts) {
        amounts.forEach(this::assertAmount);
    }

    private void assertAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid amount '" + amount + "'");
        }
    }
}
