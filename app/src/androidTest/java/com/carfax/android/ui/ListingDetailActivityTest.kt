package com.carfax.android.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.carfax.android.R
import com.carfax.android.util.CustomMatchers
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * In ListingActivityTest class I'm just checking if listing detail recycler view is displayed or not.
 */

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ListingDetailActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun testMainUI() {
        val detailActivityScenario = ActivityScenario.launch(ListingDetailActivity::class.java)
        Assert.assertNotNull(R.id.recycler_view_listing_detail)
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_listing_detail))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_listing_detail))
            .check(ViewAssertions.matches(CustomMatchers.withItemCount(9)))
    }

}