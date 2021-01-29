package com.carfax.android.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
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
 * In MainActivityTest class I'm just checking if listings recycler view is displayed or not.
 */

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun testMainUI() {
        val mainActivityScenario = ActivityScenario.launch(MainActivity::class.java)

        Assert.assertNotNull(R.id.recycler_view_listings)

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_listings))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_listings))
            .check(ViewAssertions.matches(CustomMatchers.withItemCount(25)))

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_listings))
            .check(ViewAssertions.matches(
                CustomMatchers.atPosition(0,
                    ViewMatchers.hasDescendant(ViewMatchers.withText("Lodi, NJ"))
                    )
                )
            )

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_listings))
            .perform(RecyclerViewActions.scrollToPosition<ListingsAdapter.ViewHolder>(1))
            .check(ViewAssertions.matches(CustomMatchers.atPosition(1,
                ViewMatchers.hasDescendant(ViewMatchers.withText("2018 Fiat 124 Spider Lusso"))
            )))

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_listings))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ListingsAdapter.ViewHolder>(
                    0,
                    ViewActions.click()
                )
            )
    }
}