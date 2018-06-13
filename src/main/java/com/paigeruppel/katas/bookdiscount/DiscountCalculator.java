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
        discounts.put(0, BigDecimal.valueOf(1.00));
        discounts.put(1, BigDecimal.valueOf(1.00));
        discounts.put(2, BigDecimal.valueOf(0.95));
        discounts.put(3, BigDecimal.valueOf(0.90));
        discounts.put(4, BigDecimal.valueOf(0.80));
        discounts.put(5, BigDecimal.valueOf(0.75));
        return discounts;
    }

    public BigDecimal getCost(int[] booksToPurchase) {
        boolean duplicates;
        BigDecimal totalDiscountedPrice = BigDecimal.valueOf(0);

        do {
            int distinctBooks = 0;
            duplicates = false;
            for (int i = 0; i < booksToPurchase.length; i++) {
                if (booksToPurchase[i] == 1) {
                    distinctBooks++;
                    booksToPurchase[i]--;
                } else if (booksToPurchase[i] > 1) {
                    booksToPurchase[i]--;
                    duplicates = true;
                    distinctBooks++;
                }
            }

            BigDecimal rawTotalPrice = calculateRawPrice(distinctBooks);
            BigDecimal discount = findDiscount(distinctBooks);
            BigDecimal discountedPrice = calculateDiscountedPrice(discount, rawTotalPrice);

            totalDiscountedPrice = totalDiscountedPrice.add(discountedPrice);

        } while (duplicates);

        return totalDiscountedPrice;
    }

    private BigDecimal calculateRawPrice(int numberOfBooks) {
        return RAW_COST_PER_BOOK.multiply(BigDecimal.valueOf(numberOfBooks));
    }

    private BigDecimal calculateDiscountedPrice(BigDecimal discount, BigDecimal rawTotalPrice) {
        return rawTotalPrice.multiply(discount).setScale(2);
    }

    private BigDecimal findDiscount(int numberOfBooks) {
        return discountMap().get(numberOfBooks);
    }
}
