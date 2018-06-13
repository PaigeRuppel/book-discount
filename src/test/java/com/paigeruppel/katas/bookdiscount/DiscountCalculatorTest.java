package com.paigeruppel.katas.bookdiscount;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DiscountCalculatorTest {

    @Test
    public void shouldReturn8WhenOnlyOneBookIsPurchased() {
        DiscountCalculator underTest = new DiscountCalculator();
        int[] booksToPurchase = {1, 0, 0, 0, 0};
        assertThat(underTest.getCost(booksToPurchase), is(BigDecimal.valueOf(8.00)));
    }

}
