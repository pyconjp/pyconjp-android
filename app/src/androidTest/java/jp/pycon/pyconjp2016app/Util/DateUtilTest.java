package jp.pycon.pyconjp2016app.Util;


import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by rhoboro on 7/25/16.
 */
@RunWith(Enclosed.class)
public class DateUtilTest {

    @RunWith(JUnit4.class)
    public static class testToTimeFormattedString {

        @Test
        public void 正しい書式() {
            String expected = "13:00";
            String actual = DateUtil.toTimeFormattedString("13:00:00");
            assertThat(expected, is(actual));
        }

    }

    @RunWith(JUnit4.class)
    public static class testToStartToEndFormattedString {
        @Test
        public void 正しい書式() {
            String expected = "2016/09/21 13:00 - 13:30";
            String actual = DateUtil.toStartToEndFormattedString("2016-09-21", "13:00:00", "13:30:00");
            assertThat(expected, is(actual));
        }
    }
}