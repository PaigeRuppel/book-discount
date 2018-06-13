package com.paigeruppel.katas.bookdiscount;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DiscountCalculatorTest {

    BigDecimal eightDollars = BigDecimal.valueOf(8.00).setScale(2);
    BigDecimal fifteenDollarsTwentyCents = BigDecimal.valueOf(15.20).setScale(2);

    @Test
    public void shouldReturn8WhenOnlyOneBookIsPurchased() {
        DiscountCalculator underTest = new DiscountCalculator();
        int[] booksToPurchase = {1, 0, 0, 0, 0};
        assertThat(underTest.getCost(booksToPurchase), is(eightDollars));
    }

    @Test
    public void shouldApplyA5PercentDiscountToTwoDifferentBooks() {
        DiscountCalculator underTest = new DiscountCalculator();
        int[] booksToPurchase = {1, 1, 0, 0, 0};
        assertThat(underTest.getCost(booksToPurchase), is(fifteenDollarsTwentyCents));
    }

}
