package com.example.appbaothuc;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented ChallengeTrackerListener, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under ChallengeTrackerListener.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.appbaothuc", appContext.getPackageName());
    }
}
