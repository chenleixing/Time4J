package net.time4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static net.time4j.CalendarUnit.DAYS;
import static net.time4j.CalendarUnit.WEEKS;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


@RunWith(JUnit4.class)
public class StdDayArithmeticTest {

    @Test
    public void plusWeeks() {
        assertThat(
            PlainDate.of(2013, 12, 31).plus(3, WEEKS),
            is(PlainDate.of(2014, 1, 21)));
    }

    @Test
    public void minusWeeks() {
        assertThat(
            PlainDate.of(2014, 1, 21).minus(3, WEEKS),
            is(PlainDate.of(2013, 12, 31)));
    }

    @Test
    public void plusZero() {
        PlainDate date = PlainDate.of(2014, 5, 24);
        assertThat(
            date.plus(0, DAYS) == date,
            is(true));
    }

    @Test
    public void minusZero() {
        PlainDate date = PlainDate.of(2014, 5, 24);
        assertThat(
            date.minus(0, DAYS) == date,
            is(true));
    }

    @Test
    public void plusDays() {
        assertThat(
            PlainDate.of(2013, 12, 31).plus(3, DAYS),
            is(PlainDate.of(2014, 1, 3)));
        assertThat(
            PlainDate.of(2014, 10, 31).plus(370, DAYS),
            is(PlainDate.of(2015, 11, 5)));
        assertThat(
            PlainDate.of(2011, 10, 31).plus(370, DAYS),
            is(PlainDate.of(2012, 11, 4)));
    }

    @Test
    public void minusDays() {
        assertThat(
            PlainDate.of(2014, 1, 3).minus(3, DAYS),
            is(PlainDate.of(2013, 12, 31)));
        assertThat(
            PlainDate.of(2015, 11, 5).minus(370, DAYS),
            is(PlainDate.of(2014, 10, 31)));
        assertThat(
            PlainDate.of(2012, 11, 4).minus(370, DAYS),
            is(PlainDate.of(2011, 10, 31)));
    }

}