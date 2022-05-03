package rocks.alexmihai.arr;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ArrCalculatorTest {

    private final ArrCalculator arrCalculator = new ArrCalculator();

    @Test
    void testComputeGoingBroke() {
        // Given
        var investedByDate = Map.of(
                LocalDate.of(2020, 7, 24), BigDecimal.valueOf(100),
                LocalDate.of(2019, 7, 24), BigDecimal.valueOf(200),
                LocalDate.of(2018, 7, 24), BigDecimal.valueOf(150)
        );
        var endDate = LocalDate.of(2021, 7, 24);
        var endAmount = BigDecimal.valueOf(0);

        // When
        double arr = this.arrCalculator.compute(investedByDate, endDate, endAmount);

        // Then
        assertEquals(0, arr, 0.001);
    }

    @Test
    void testCompute3SimpleYearlyInvestments() {
        // Given
        var investedByDate = Map.of(
                LocalDate.of(2020, 7, 24), BigDecimal.valueOf(100),
                LocalDate.of(2019, 7, 24), BigDecimal.valueOf(200),
                LocalDate.of(2018, 7, 24), BigDecimal.valueOf(150)
        );
        var endDate = LocalDate.of(2021, 7, 24);
        var endAmount = BigDecimal.valueOf(800);

        // When
        double arr = this.arrCalculator.compute(investedByDate, endDate, endAmount);

        // Then
        assertEquals(1.3016583177435912, arr, 0.001);
    }

    @Test
    void testComputeStellarGains() {
        // Given
        var investedByDate = Map.of(
                LocalDate.of(2020, 7, 24), BigDecimal.valueOf(100),
                LocalDate.of(2019, 7, 24), BigDecimal.valueOf(200),
                LocalDate.of(2018, 7, 24), BigDecimal.valueOf(150)
        );
        var endDate = LocalDate.of(2021, 7, 24);
        var endAmount = BigDecimal.valueOf(80_000);

        // When
        double arr = this.arrCalculator.compute(investedByDate, endDate, endAmount);

        // Then
        assertEquals(7.658477544835633, arr, 0.001);
    }

    @Test
    void testComputeWithCashOutAtTheEnd() {
        // Given
        var investedByDate = Map.of(
                LocalDate.of(2020, 7, 24), BigDecimal.valueOf(100),
                LocalDate.of(2019, 7, 24), BigDecimal.valueOf(200),
                LocalDate.of(2018, 7, 24), BigDecimal.valueOf(150),
                LocalDate.of(2021, 7, 24), BigDecimal.valueOf(-100)
        );
        var endDate = LocalDate.of(2021, 7, 24);
        var endAmount = BigDecimal.valueOf(700);

        // When
        double arr = this.arrCalculator.compute(investedByDate, endDate, endAmount);

        // Then
        assertEquals(1.3016583177435912, arr, 0.001);
    }

    @Test
    void testComputeWithCashOut() {
        // Given
        var investedByDate = Map.of(
                LocalDate.of(2020, 7, 24), BigDecimal.valueOf(100),
                LocalDate.of(2019, 7, 24), BigDecimal.valueOf(200),
                LocalDate.of(2018, 7, 24), BigDecimal.valueOf(150),
                LocalDate.of(2019, 7, 25), BigDecimal.valueOf(-100)
        );
        var endDate = LocalDate.of(2021, 7, 24);
        var endAmount = BigDecimal.valueOf(700);

        // When
        double arr = this.arrCalculator.compute(investedByDate, endDate, endAmount);

        // Then
        assertEquals(1.361191382793517, arr, 0.001);
    }

    @Test
    void testComputeWithMonthlyCashOut() {
        // Given
        var investedByDate = new HashMap<LocalDate, BigDecimal>();
        investedByDate.put(LocalDate.of(2020, 1, 1), BigDecimal.valueOf(1_000_000));

        for (int month = 1; month <= 12; month++) {
            investedByDate.put(LocalDate.of(2020, month, 2), BigDecimal.valueOf(-1_000));
            investedByDate.put(LocalDate.of(2021, month, 2), BigDecimal.valueOf(-1_000));
        }

        var endDate = LocalDate.of(2022, 1, 1);
        var endAmount = BigDecimal.valueOf(1_184_800);

        // When
        double arr = this.arrCalculator.compute(investedByDate, endDate, endAmount);

        // Then
        assertEquals(1.1, arr, 0.001);
    }

    @Test
    void testComputeOnlyInitialDeposit() {
        // Given
        var investedByDate = new HashMap<LocalDate, BigDecimal>();
        investedByDate.put(LocalDate.of(2020, 1, 1), BigDecimal.valueOf(1_000_000));

        var endDate = LocalDate.of(2022, 1, 1);
        var endAmount = BigDecimal.valueOf(1_210_000);

        // When
        double arr = this.arrCalculator.compute(investedByDate, endDate, endAmount);

        // Then
        assertEquals(1.1, arr, 0.001);
    }

    @Test
    void testComputeOnSameDayAsInitialDepositValueIncrease() {
        // Given
        var investedByDate = new HashMap<LocalDate, BigDecimal>();
        investedByDate.put(LocalDate.of(2020, 1, 1), BigDecimal.valueOf(1_000_000));

        var endDate = LocalDate.of(2020, 1, 1);
        var endAmount = BigDecimal.valueOf(1_000_010);

        // When
        double arr = this.arrCalculator.compute(investedByDate, endDate, endAmount);

        // Then
        assertEquals(1.00365, arr, 0.001);
    }

    @Test
    void testComputeOnSameDayAsInitialDepositValueDecrease() {
        // Given
        var investedByDate = new HashMap<LocalDate, BigDecimal>();
        investedByDate.put(LocalDate.of(2020, 1, 1), BigDecimal.valueOf(1_000_000));

        var endDate = LocalDate.of(2020, 1, 1);
        var endAmount = BigDecimal.valueOf(999_990);

        // When
        double arr = this.arrCalculator.compute(investedByDate, endDate, endAmount);

        // Then
        assertEquals(0.99635, arr, 0.001);
    }
}