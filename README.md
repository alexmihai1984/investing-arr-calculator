# About
Computes the annual return rate of an investment portfolio given all the invested 
amounts by date and the final amount of the portfolio.

# Requirements
JDK 14+

# Usage
```java
var investedByDate = Map.of(
    LocalDate.of(2020, 7, 24), BigDecimal.valueOf(100),
    LocalDate.of(2019, 7, 24), BigDecimal.valueOf(200),
    LocalDate.of(2018, 7, 24), BigDecimal.valueOf(150)
);
var endDate = LocalDate.of(2021, 7, 24);
var endAmount = BigDecimal.valueOf(800);

var arrCalculator = new ArrCalculator();
double arr = arrCalculator.compute(investedByDate, endDate, endAmount);
```
