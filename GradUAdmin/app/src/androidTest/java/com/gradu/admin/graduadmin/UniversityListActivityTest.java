package com.example.akash.graduapplication;

import android.app.Activity;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class UniversityListActivityTest {
    @Test
    public void test_UniversityListActivity() throws Exception {
        assertEquals((Activity) UniversityListActivity.create(),true);
    }
}