package rocks.alexmihai.arr;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class ArrCalculator {

    private final double AVERAGE_DAYS_IN_YEAR = 365.25;

    private final DailyReturnRateCalculator dailyReturnRateCalculator = new DailyReturnRateCalculator();

    public double compute(Map<LocalDate, BigDecimal> investedByDate, LocalDate endDate, BigDecimal endAmount) {
        return Math.pow(
                this.dailyReturnRateCalculator.compute(investedByDate, endDate, endAmount),
                AVERAGE_DAYS_IN_YEAR
        );
    }
}
