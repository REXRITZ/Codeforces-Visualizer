package com.ritesh.codeforcesportal.utils;

import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void checkFormattedStartTime() {
        Assert.assertEquals("22 January 2022 20:05:00", Utils.formatContestStartTime(1642862100));
    }

}