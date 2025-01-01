package rocks.alexmihai.arr;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public abstract class Calculator {

    public double compute(Map<LocalDate, BigDecimal> investedByDate, LocalDate endDate, BigDecimal endAmount) {
        assertDates(investedByDate.keySet(), endDate);

        assertAmounts(investedByDate);
        assertFinalAmount(endAmount);

        return doCompute(investedByDate, endDate, endAmount);
    }

    protected abstract double doCompute(
            Map<LocalDate, BigDecimal> investedByDate,
            LocalDate endDate,
            BigDecimal endAmount
    );

    private void assertDates(Set<LocalDate> dates, LocalDate endDate) {
        dates.forEach(d -> {
            if (d.isAfter(endDate)) {
                throw new IllegalArgumentException("Date '" + d + "' is after end date '" + endDate + "'");
            }
        });
    }

    private void assertAmounts(Map<LocalDate, BigDecimal> investedByDate) {
        LocalDate minDate = investedByDate.keySet().stream()
                .min(LocalDate::compareTo)
                .orElseThrow(() -> new IllegalArgumentException("No invested amounts provided"));

        if (investedByDate.get(minDate).compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("First invested amount should be positive");
        }
    }

    private void assertFinalAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid final amount '" + amount + "'");
        }
    }
}
