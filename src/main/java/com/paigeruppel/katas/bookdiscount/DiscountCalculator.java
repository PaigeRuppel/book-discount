package com.paigeruppel.katas.bookdiscount;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DiscountCalculator {

    private static final BigDecimal RAW_COST_PER_BOOK = BigDecimal.valueOf(8.00);

    private static Map<Integer, BigDecimal> discountMap() {
        Map<Integer, BigDecimal> discounts = new HashMap<>();
        discounts.put(1, BigDecimal.valueOf(1.00));
        discounts.put(2, BigDecimal.valueOf(0.95));
        discounts.put(3, BigDecimal.valueOf(0.90));
        discounts.put(4, BigDecimal.valueOf(0.80));
        discounts.put(5, BigDecimal.valueOf(0.75));
        return discounts;
    }

    public BigDecimal getCost(int[] booksToPurchase) {
        boolean[] duplicates = {false, false, false, false, false};
        int[] remainingBooks = {0, 0, 0, 0, 0};

        for (int i = 0; i < booksToPurchase.length; i++) {
            if (booksToPurchase[i] > 1) {
                duplicates[i] = true;
                remainingBooks[i] = booksToPurchase[i] - 1;
                booksToPurchase[i] = 1;
            }
        }

        int numberRemainingBooks = Arrays.stream(remainingBooks).reduce(0, (x, y) -> x + y);
        int numberOfBooks = Arrays.stream(booksToPurchase).reduce(0, (x, y) -> x + y);

        BigDecimal discount = findDiscount(numberOfBooks);
        BigDecimal rawTotalPrice = RAW_COST_PER_BOOK.multiply(BigDecimal.valueOf(numberOfBooks));
        BigDecimal additionalPrice = RAW_COST_PER_BOOK.multiply(BigDecimal.valueOf(numberRemainingBooks));
        BigDecimal discountedPrice = rawTotalPrice.multiply(discount);
        return discountedPrice.setScale(2).add(additionalPrice);
    }

    private BigDecimal findDiscount(int numberOfBooks) {
        return discountMap().get(numberOfBooks);
    }
}
