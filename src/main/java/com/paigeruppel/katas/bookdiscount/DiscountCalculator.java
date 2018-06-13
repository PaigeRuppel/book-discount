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
        Map<Integer, Integer> distinctGroupings = buildDistinctGroupingsOfBooks(booksToPurchase);
        return maximizeDiscount(distinctGroupings);
    }

    private Map<Integer, Integer> buildDistinctGroupingsOfBooks(int[] booksToPurchase) {
        boolean duplicates;
        Map<Integer, Integer> distinctGroupings = new HashMap<>();
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
            int numberOfSets = distinctGroupings.getOrDefault(distinctBooks, 0);
            distinctGroupings.put(distinctBooks, ++numberOfSets);
        } while (duplicates);
        return distinctGroupings;
    }

    private BigDecimal maximizeDiscount(Map<Integer, Integer> distinctGroupings) {
        if (distinctGroupings.getOrDefault(3, 0) >= 1 && distinctGroupings.getOrDefault(5, 0) >= 1) {
            distinctGroupings.remove(3);
            distinctGroupings.remove(5);
            int numberOfSetsOfFour = distinctGroupings.getOrDefault(4, 0) + 2;
            distinctGroupings.put(4, numberOfSetsOfFour);
        }

        int counter = 1;
        BigDecimal totalPrice = BigDecimal.valueOf(0);

        while (counter < 6) {
            int numberSets = distinctGroupings.getOrDefault(counter, 0);

            if (numberSets > 0) {
                BigDecimal rawTotalPrice = calculateRawPrice(counter);
                BigDecimal discount = findDiscount(counter);
                BigDecimal discountedPriceForOneSet = calculateDiscountedPrice(discount, rawTotalPrice);
                BigDecimal totalDiscountedPrice = discountedPriceForOneSet.multiply(BigDecimal.valueOf(numberSets));
                totalPrice = totalPrice.add(totalDiscountedPrice);
            }
            counter++;
        }
        return totalPrice;
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
