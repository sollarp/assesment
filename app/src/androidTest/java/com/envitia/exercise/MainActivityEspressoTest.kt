package com.envitia.exercise

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityEspressoTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    private lateinit var decorView: View

    @Before
    fun setUp() {
        activityRule.scenario.onActivity { activity ->
            decorView = activity.window.decorView
        }
    }

    @Test
    fun testAddingTextToList() {
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        val testText = "This is a test text"

        onView(withId(R.id.my_text_field)).perform(
            typeText(testText),
            closeSoftKeyboard()
        )

        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.recycler_view))
            .check(matches(hasDescendant(withText("[$currentTime] $testText"))))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testBlankText() {
        onView(withId(R.id.button)).perform(click())
        onView(withText(R.string.no_text))
            .inRoot(withDecorView(Matchers.not(decorView)))
            .check(matches(isDisplayed()))
    }
}
