package rocks.alexmihai.arr;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class ArrCalculator {

    private final DailyReturnRateCalculator dailyReturnRateCalculator = new DailyReturnRateCalculator();

    public double compute(Map<LocalDate, BigDecimal> investedByDate, LocalDate endDate, BigDecimal endAmount) {
        return Math.pow(
                this.dailyReturnRateCalculator.compute(investedByDate, endDate, endAmount),
                365.25
        );
    }
}
