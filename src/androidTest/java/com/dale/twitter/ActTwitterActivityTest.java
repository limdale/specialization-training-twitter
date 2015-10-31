package com.dale.twitter;

import android.test.ActivityInstrumentationTestCase2;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.dale.twitter.ActTwitterActivityTest \
 * com.dale.twitter.tests/android.test.InstrumentationTestRunner
 */
public class ActTwitterActivityTest extends ActivityInstrumentationTestCase2<ActTwitterActivity> {

    public ActTwitterActivityTest() {
        super("com.dale.twitter", ActTwitterActivity.class);
    }

}
