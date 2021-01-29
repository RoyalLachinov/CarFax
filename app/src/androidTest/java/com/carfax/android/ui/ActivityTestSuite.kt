package com.carfax.android.ui

import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * In this class we run test case in app level
 */

@RunWith(Suite::class)
@Suite.SuiteClasses(
    MainActivityTest::class,
    ListingDetailActivityTest::class
)
class ActivityTestSuite