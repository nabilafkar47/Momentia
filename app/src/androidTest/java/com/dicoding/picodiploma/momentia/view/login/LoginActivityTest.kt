package com.dicoding.picodiploma.momentia.view.login

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.dicoding.picodiploma.momentia.R
import com.dicoding.picodiploma.momentia.utils.ProgressBarIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginActivityTest {
    private lateinit var idlingResource: ProgressBarIdlingResource

    @Before
    fun setUp() {
        val scenario = ActivityScenario.launch(LoginActivity::class.java)
        scenario.onActivity { activity ->
            idlingResource = ProgressBarIdlingResource(activity.findViewById(R.id.pb_login))
            IdlingRegistry.getInstance().register(idlingResource)
        }
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun testLoginLogout() {
        onView(withId(R.id.ed_login_email)).perform(typeText("tan123@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.ed_login_password)).perform(typeText("tan12345"), closeSoftKeyboard())

        onView(withId(R.id.button_login)).perform(click())

        onView(withText(R.string.login)).inRoot(isDialog()).check(matches(isDisplayed()))
        onView(withText(R.string.dialog_positive_button)).perform(click())

        onView(withId(R.id.main)).check(matches(isDisplayed()))

        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext())

        onView(withText(R.string.logout)).perform(click())

        onView(withText(R.string.logout)).inRoot(isDialog()).check(matches(isDisplayed()))
        onView(withText(R.string.dialog_positive_button)).perform(click())

        onView(withId(R.id.welcome)).check(matches(isDisplayed()))
    }
}