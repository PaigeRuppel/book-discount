package com.paigeruppel.katas.bookdiscount;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class DiscountCalculatorTest {

    BigDecimal eightDollars = BigDecimal.valueOf(8.00).setScale(2);
    BigDecimal sixteenDollars = BigDecimal.valueOf(16.00).setScale(2);
    BigDecimal fifteenDollarsTwentyCents = BigDecimal.valueOf(15.20).setScale(2);
    BigDecimal twentyOneDollarsSixtyCents = BigDecimal.valueOf(21.60).setScale(2);
    BigDecimal twentyFiveDollarsSixtyCents = BigDecimal.valueOf(25.60).setScale(2);
    BigDecimal thirtyDollars = BigDecimal.valueOf(30.00).setScale(2);
    BigDecimal thirtyDollarsFortyCents = BigDecimal.valueOf(30.40).setScale(2);
    BigDecimal thirtyEightDollarsFortyCents = BigDecimal.valueOf(38.40).setScale(2);
    BigDecimal fiftyOneDollarsTwentyCents = BigDecimal.valueOf(51.20).setScale(2);
    BigDecimal sixtyDollars = BigDecimal.valueOf(60.00).setScale(2);
    BigDecimal oneHundredTwoDollarsFortyCents = BigDecimal.valueOf(102.40).setScale(2);

    private DiscountCalculator underTest;

    @Before
    public void setup() {
        underTest = new DiscountCalculator();
    }

    @Test
    public void shouldReturn8WhenOnlyOneBookIsPurchased() {
        int[] booksToPurchase = {1, 0, 0, 0, 0};
        assertThat(underTest.getCost(booksToPurchase), is(eightDollars));
    }

    @Test
    public void shouldApplyA5PercentDiscountToTwoDifferentBooks() {
        int[] booksToPurchase = {1, 1, 0, 0, 0};
        assertThat(underTest.getCost(booksToPurchase), is(fifteenDollarsTwentyCents));
    }

    @Test
    public void shouldApplyA10PercentDiscountToThreeDifferentBooks() {
        int[] booksToPurchase = {1, 1, 1, 0, 0};
        assertThat(underTest.getCost(booksToPurchase), is(twentyOneDollarsSixtyCents));
    }

    @Test
    public void shouldApplyA20PercentDiscountToFourDifferentBooks() {
        int[] booksToPurchase = {1, 1, 1, 1, 0};
        assertThat(underTest.getCost(booksToPurchase), is(twentyFiveDollarsSixtyCents));
    }

    @Test
    public void shouldApplyA25PercentDiscountToFiveDifferentBooks() {
        int[] booksToPurchase = {1, 1, 1, 1, 1};
        assertThat(underTest.getCost(booksToPurchase), is(thirtyDollars));
    }

    @Test
    public void shouldNotApplyAnyDiscountToTwoLikeBooks() {
        int[] booksToPurchase = {2, 0, 0, 0, 0};
        assertThat(underTest.getCost(booksToPurchase), is(sixteenDollars));
    }

    @Test
    public void shouldOnlyApplyDiscountToTwoDifferentBooksAndCharge8ForRemainingSingleBook() {
        int[] booksToPurchase = {2, 1, 0, 0, 0};
        assertThat(underTest.getCost(booksToPurchase), is(fifteenDollarsTwentyCents.add(eightDollars)));
    }

    @Test
    public void whenTwoLikeSetsArePurchasedEachShouldGetTheTwoBookDiscount() {
        int[] booksToPurchase = {2, 2, 0, 0, 0};
        assertThat(underTest.getCost(booksToPurchase), is(thirtyDollarsFortyCents));
    }

    @Test
    public void whenTwoLikeSetsArePurchasedEachShouldGetTheTwoBookDiscountAndRemainingSingleBookShouldNotGetDiscount() {
        int[] booksToPurchase = {2, 3, 0, 0, 0};
        assertThat(underTest.getCost(booksToPurchase), is(thirtyEightDollarsFortyCents));
    }

    @Test
    public void shouldMaximizeTheDiscountWhenMultipleCombinationsArePossible() {
        int[] booksToPurchase = {2, 2, 2, 1, 1};
        assertThat(underTest.getCost(booksToPurchase), is(fiftyOneDollarsTwentyCents));
    }

    @Test
    public void shouldReturnSixtyDollarsWhenTwoCompleteSetsArePurchased() {
        int[] booksToPurchase = {2, 2, 2, 2, 2};
        assertThat(underTest.getCost(booksToPurchase), is(sixtyDollars));
    }

    @Test
    public void shouldConvertTwoSetsOfFiveAndTwoSetsOfThreeToFourSetsOfFour() {
        int[] booksToPurchase = {4, 4, 4, 2, 2};
        assertThat(underTest.getCost(booksToPurchase), is(oneHundredTwoDollarsFortyCents));
    }


}
