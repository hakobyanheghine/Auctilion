package com.crossover.auctilion;

import com.crossover.auctilion.utils.Utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilsUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals("20 seconds is 20s", "20s", Utils.convertTimeInSecondsToString(20));
        assertEquals("80 seconds is 1m 20s", "1m 20s", Utils.convertTimeInSecondsToString(80));
    }
}