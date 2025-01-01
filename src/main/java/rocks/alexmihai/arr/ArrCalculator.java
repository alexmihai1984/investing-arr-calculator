package rocks.alexmihai.arr;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class ArrCalculator {

    private final Calculator dailySearchBasedCalculator = new DailySearchBasedCalculator();

    public double compute(Map<LocalDate, BigDecimal> investedByDate, LocalDate endDate, BigDecimal endAmount) {
        return Math.pow(
                dailySearchBasedCalculator.compute(investedByDate, endDate, endAmount),
                365.25
        );
    }
}
