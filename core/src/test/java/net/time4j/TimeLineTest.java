package net.time4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(JUnit4.class)
public class TimeLineTest {

    @Test
    public void forwardDate() {
        PlainDate date = PlainDate.of(2012, Month.FEBRUARY, 28);
        assertThat(
            PlainDate.axis().stepForward(date),
            is(PlainDate.of(2012, Month.FEBRUARY, 29)));
    }

    @Test
    public void backwardsDate() {
        PlainDate date = PlainDate.of(2012, Month.MARCH, 1);
        assertThat(
            PlainDate.axis().stepBackwards(date),
            is(PlainDate.of(2012, Month.FEBRUARY, 29)));
    }

    @Test
    public void forwardTime() {
        PlainTime time = PlainTime.of(23, 45);
        assertThat(
            PlainTime.axis().stepForward(time),
            is(PlainTime.of(23, 45, 0, 1)));
    }

    @Test
    public void backwardsTime() {
        PlainTime time = PlainTime.of(23, 45);
        assertThat(
            PlainTime.axis().stepBackwards(time),
            is(PlainTime.of(23, 44, 59, 999999999)));
    }

    @Test
    public void forwardTimestamp() {
        PlainTimestamp tsp = PlainTimestamp.of(2014, 3, 1, 0, 0);
        assertThat(
            PlainTimestamp.axis().stepForward(tsp),
            is(
                PlainTimestamp.of(2014, 3, 1, 0, 0)
                .with(PlainTime.NANO_OF_SECOND, 1)));
    }

    @Test
    public void backwardsTimestamp() {
        PlainTimestamp tsp = PlainTimestamp.of(2014, 3, 1, 0, 0);
        assertThat(
            PlainTimestamp.axis().stepBackwards(tsp),
            is(
                PlainTimestamp.of(2014, 2, 28, 23, 59, 59)
                .with(PlainTime.NANO_OF_SECOND, 999999999)));
    }

    @Test
    public void forwardMoment() {
        Moment time =
            PlainTimestamp.of(2012, 6, 30, 23, 59, 59)
                .with(PlainTime.NANO_OF_SECOND, 999999999)
                .atUTC();
        assertThat(
            Moment.axis().stepForward(time),
            is(
                PlainTimestamp.of(2012, 6, 30, 23, 59, 59)
                    .atUTC()
                    .with(PlainTime.SECOND_OF_MINUTE, 60)));
    }

    @Test
    public void backwardsMoment() {
        Moment time = PlainTimestamp.of(2012, 7, 1, 0, 0).atUTC();
        assertThat(
            Moment.axis().stepBackwards(time),
            is(
                PlainTimestamp.of(2012, 6, 30, 23, 59, 59)
                    .with(PlainTime.NANO_OF_SECOND, 999999999)
                    .atUTC()
                    .with(PlainTime.SECOND_OF_MINUTE, 60)));
    }

}