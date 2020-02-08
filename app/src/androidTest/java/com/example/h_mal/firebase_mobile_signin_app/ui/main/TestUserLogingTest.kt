package com.example.h_mal.firebase_mobile_signin_app.ui.main


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.h_mal.firebase_mobile_signin_app.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class TestUserLogingTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testUserLoginTest() {
        val appCompatEditText = onView(
            allOf(
                withId(R.id.phone_number),
                childAtPosition(
                    allOf(
                        withId(R.id.lin_lay),
                        childAtPosition(
                            withId(R.id.container),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("784"), closeSoftKeyboard())

        val appCompatButton = onView(
            allOf(
                withId(R.id.login), withText("Sign in"),
                childAtPosition(
                    allOf(
                        withId(R.id.container),
                        childAtPosition(
                            withId(R.id.container),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        testToast("Invalid Phone number")

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.phone_number), withText("784"),
                childAtPosition(
                    allOf(
                        withId(R.id.lin_lay),
                        childAtPosition(
                            withId(R.id.container),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(click())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.phone_number), withText("784"),
                childAtPosition(
                    allOf(
                        withId(R.id.lin_lay),
                        childAtPosition(
                            withId(R.id.container),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(longClick())

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.phone_number), withText("784"),
                childAtPosition(
                    allOf(
                        withId(R.id.lin_lay),
                        childAtPosition(
                            withId(R.id.container),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(replaceText("447849979363"))

        val appCompatEditText5 = onView(
            allOf(
                withId(R.id.phone_number), withText("447849979363"),
                childAtPosition(
                    allOf(
                        withId(R.id.lin_lay),
                        childAtPosition(
                            withId(R.id.container),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText5.perform(closeSoftKeyboard())

        val appCompatButton2 = onView(
            allOf(
                withId(R.id.login), withText("Sign in"),
                childAtPosition(
                    allOf(
                        withId(R.id.container),
                        childAtPosition(
                            withId(R.id.container),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatButton2.perform(click())

        onView(isRoot()).perform(waitFor(2000))

        val appCompatButton3 = onView(
            allOf(
                withId(R.id.login), withText("Sign In"),
                childAtPosition(
                    allOf(
                        withId(R.id.container),
                        childAtPosition(
                            withId(R.id.container),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatButton3.perform(click())

        val appCompatEditText6 = onView(
            allOf(
                withId(R.id.verification_code),
                childAtPosition(
                    allOf(
                        withId(R.id.container),
                        childAtPosition(
                            withId(R.id.container),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText6.perform(replaceText("123456"), closeSoftKeyboard())

        val appCompatButton4 = onView(
            allOf(
                withId(R.id.login), withText("Sign In"),
                childAtPosition(
                    allOf(
                        withId(R.id.container),
                        childAtPosition(
                            withId(R.id.container),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatButton4.perform(click())

        onView(isRoot()).perform(waitFor(600));

        val overflowMenuButton = onView(
            allOf(
                withContentDescription("More options"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.action_bar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        overflowMenuButton.perform(click())

        val appCompatTextView = onView(
            allOf(
                withId(R.id.title), withText("Sign out"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatTextView.perform(click())
    }

    private fun testToast(toastText:String){
        onView(withText(toastText))
            .inRoot(withDecorView(not(`is`(mActivityTestRule.activity.window.decorView))))
            .check(matches(isDisplayed()))
    }

    fun waitFor(delay: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for " + delay + "milliseconds"
            }

            override fun perform(uiController: UiController, view: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
