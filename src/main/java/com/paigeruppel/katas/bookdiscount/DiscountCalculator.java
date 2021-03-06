package com.paigeruppel.katas.bookdiscount;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class DiscountCalculator {

    private static final BigDecimal RAW_COST_PER_BOOK = BigDecimal.valueOf(8.00);
    private Map<Integer, Integer> distinctGroupings;

    private static Map<Integer, BigDecimal> discounts() {
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
        buildDistinctGroupingsOfBooks(booksToPurchase);
        return findMaximumDiscount();
    }

    private Map<Integer, Integer> buildDistinctGroupingsOfBooks(int[] booksToPurchase) {
        distinctGroupings = new HashMap<>();
        while (hasUngroupedBooksToPurchase()) {
            int numberDistinctBooks = 0;
            for (int i = 0; i < booksToPurchase.length; i++) {
                if (booksToPurchase[i] >= 1) {
                    numberDistinctBooks++;
                    booksToPurchase[i]--;
                }
            }
            int numberOfSetsWithSameNumberBooks = distinctGroupings.getOrDefault(numberDistinctBooks, 0);
            distinctGroupings.put(numberDistinctBooks, ++numberOfSetsWithSameNumberBooks);
        }

        return distinctGroupings;
    }

    private boolean hasUngroupedBooksToPurchase() {
        return distinctGroupings.getOrDefault(0, 0) == 0;
    }

    private BigDecimal findMaximumDiscount() {
        adjustGroupingsToMaximizeDiscount();

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

    private void adjustGroupingsToMaximizeDiscount() {
        if (hasASetOfFiveAndThree()) {
            int numberOfThrees = distinctGroupings.get(3);
            int numberOfFives = distinctGroupings.get(5);
            int numberOfSetsOfFour = distinctGroupings.getOrDefault(4, 0);
            if (numberOfFives == numberOfThrees) {
                distinctGroupings.remove(3);
                distinctGroupings.remove(5);
                numberOfSetsOfFour += numberOfFives + numberOfThrees;
            } else if (numberOfFives > numberOfThrees) {
                distinctGroupings.remove(3);
                distinctGroupings.put(5, --numberOfFives);
                numberOfSetsOfFour += numberOfThrees + numberOfThrees;
            } else if (numberOfThrees > numberOfFives) {
                distinctGroupings.remove(5);
                distinctGroupings.put(3, --numberOfThrees);
                numberOfSetsOfFour += numberOfFives + numberOfFives;
            }

            distinctGroupings.put(4, numberOfSetsOfFour);
        }
    }

    private boolean hasASetOfFiveAndThree() {
        return distinctGroupings.getOrDefault(3, 0) >= 1 && distinctGroupings.getOrDefault(5, 0) >= 1;
    }

    private BigDecimal calculateRawPrice(int numberOfBooks) {
        return RAW_COST_PER_BOOK.multiply(BigDecimal.valueOf(numberOfBooks));
    }

    private BigDecimal calculateDiscountedPrice(BigDecimal discount, BigDecimal rawTotalPrice) {
        return rawTotalPrice.multiply(discount).setScale(2);
    }

    private BigDecimal findDiscount(int numberOfBooks) {
        return discounts().get(numberOfBooks);
    }

}
