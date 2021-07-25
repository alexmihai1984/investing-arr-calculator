package rocks.alexmihai.arr;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        assertEquals(0, arr, 0.000001);
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
        assertEquals(1.3016583177435912, arr, 0.000001);
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
        assertEquals(7.658477544835633, arr, 0.000001);
    }
}