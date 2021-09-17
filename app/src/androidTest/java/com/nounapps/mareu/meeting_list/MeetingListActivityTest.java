package com.nounapps.mareu.meeting_list;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;



import static com.nounapps.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;
import android.widget.TimePicker;


import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.nounapps.mareu.R;
import com.nounapps.mareu.ui.meeting_list.MeetingListActivity;
import com.nounapps.mareu.utils.DeleteViewAction;


import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MeetingListActivityTest {

    //number of meeting created (load with DummyMeetingGenerator)
    private static int ITEMS_COUNT = 3;

    private MeetingListActivity mActivity;

    @Rule
    public ActivityTestRule<MeetingListActivity> mActivityRule =
            new ActivityTestRule(MeetingListActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void meetingListActivityTest_shouldNotBeEmpty() {
        // Check list is not empty
        onView(ViewMatchers.withId(R.id.rv_meeting))
                .check(matches(hasMinimumChildCount(1)));

    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void meetingListActivityTest_MeetingRemoveAction_shouldRemoveItem() {
        // Given : We are 3 elements and we remove the element at position 2
        onView(ViewMatchers.withId(R.id.rv_meeting)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.rv_meeting))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 2
        onView(ViewMatchers.withId(R.id.rv_meeting)).check(withItemCount(ITEMS_COUNT - 1));
    }

    /**
     * We can create meeting
     */
    @Test
    public void meetingListActivityTest_meeting_shouldBeAdd() {
        //click on Add button
        ViewInteraction floatingActionButton = onView(withId(R.id.add_meeting));
        floatingActionButton.perform(click());

        //We can view the AddMeetingActivity and enter Object
        ViewInteraction textInputEditText = onView(withId(R.id.tfObjectInput));
        textInputEditText.perform(replaceText("TestAddMeeting"), closeSoftKeyboard());

        //We can input location
        onView(withId(R.id.sMeetingRoom)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Koopa"))).perform(click());

        //We can input the date
        onView(withId(R.id.tvSelectedDate)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2021, 12, 7));
        onView(withId(android.R.id.button1)).perform(click());

        //We can input the time
        onView(withId(R.id.tvSelectedHourStart)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(14, 0));
        onView(withId(android.R.id.button1)).perform(click());

        //We can choose teh duration
        onView(withId(R.id.sDurationMeeting)).perform(click());
        onData(allOf(is(4))).perform(click());

        //We can input participants mail
        ViewInteraction textInputEditText2 = onView(withId(R.id.tfParticipantsInput));
        textInputEditText2.perform(replaceText("test@live.fr"), closeSoftKeyboard());

        //We can add participants mail
        onView(withId(R.id.svAddActivity)).perform(swipeUp());
        ViewInteraction materialButton6 = onView(withId(R.id.mbAddMailButton));
        materialButton6.perform(click());


        //We can add participants mail
        onView(withId(R.id.svAddActivity)).perform(swipeUp());
        ViewInteraction materialButton7 = onView(withId(R.id.mbCreate));
        materialButton7.perform(click());

        //Check the number of meeting is 3
        onView(ViewMatchers.withId(R.id.rv_meeting)).check(withItemCount(ITEMS_COUNT));
    }


    /**
     * We can filter meeting by location
     */
    @Test
    public void meetingListActivityTest_meetingLocation_shouldBeFiltered() {
        //We click on hamburger menu
        onView(withId(R.id.filter)).perform(click());
        //We select the location filter option
        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.title), withText("Location Filter")));
        materialTextView.perform(click());
        //We input text of location
        ViewInteraction searchAutoComplete = onView(
                allOf(withId(R.id.search_src_text)));
        searchAutoComplete.perform(replaceText("Toad"), closeSoftKeyboard());
        //We can view the filtered meeting;
        onView(ViewMatchers.withId(R.id.rv_meeting)).check(withItemCount(1));
        //We check the location filter is the same of location from item
        ViewInteraction textView = onView(
                allOf(withId(R.id.tv_meeting)));
        textView.check(matches(withText("Réunion C - 10:00 -> 4 hour(s) - Toad")));
    }

    /**
     * We can filter meeting by date
     */
    @Test
    public void meetingListActivityTest_meeting_shouldBeFilteredByDate() {
        //for information the date of item 3 is selected to be used in filter
        //Meeting(id=3, object="Réunion C", location="Toad",new Date(1659960000000L), participant="bloo@ffd.com")
        //Date(1659960000000L)= year: 2022, monthOfYear: 8, dayOfMonth: 8
        //We click on hamburger menu
        onView(withId(R.id.filter)).perform(click());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.title), withText("Date Filter")));
        materialTextView.perform(click());
        //We select the date filter option
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions
                .setDate(2021, 11, 26));
        onView(withId(android.R.id.button1)).perform(click());
        //We can view the filtered meeting;
        onView(ViewMatchers.withId(R.id.rv_meeting)).check(withItemCount(1));
        //We check the location filter is the same of location from item
        ViewInteraction textView = onView(
                allOf(withId(R.id.tv_meeting)));
        textView.check(matches(withText("Réunion C - 10:00 -> 4 hour(s) - Toad")));
    }

    /**
     * We can reset filter
     */
    @Test
    public void meetingListActivityTest_MeetingFilter_shouldBeReset() {

        //We click on hamburger menu
        onView(withId(R.id.filter)).perform(click());
        //We select the location filter option
        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.title), withText("Location Filter")));
        materialTextView.perform(click());
        //We input text of location
        ViewInteraction searchAutoComplete = onView(
                allOf(withId(R.id.search_src_text)));
        searchAutoComplete.perform(replaceText("Toad"), closeSoftKeyboard());
        //We can view the filtered meeting;
        onView(ViewMatchers.withId(R.id.rv_meeting)).check(withItemCount(1));
        //We check the location filter is the same of location from item
        ViewInteraction textView = onView(
                allOf(withId(R.id.tv_meeting)));
        textView.check(matches(withText("Réunion C - 10:00 -> 4 hour(s) - Toad")));

        //We click on hamburger menu
        onView(withId(R.id.filter)).perform(click());
        //We selected Reset Date Filter Option
        ViewInteraction materialTextView2 = onView(
                allOf(withId(R.id.title), withText("Reset Filter")));
        materialTextView2.perform(click());
        //We check the reset of list
        onView(ViewMatchers.withId(R.id.rv_meeting)).check(withItemCount(ITEMS_COUNT));

        //We click on hamburger menu
        onView(withId(R.id.filter)).perform(click());
        ViewInteraction materialTextView3 = onView(
                allOf(withId(R.id.title), withText("Date Filter")));
        materialTextView3.perform(click());
        //We select the date filter option
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions
                .setDate(2021, 11, 26));
        onView(withId(android.R.id.button1)).perform(click());
        //We can view the filtered meeting;
        onView(ViewMatchers.withId(R.id.rv_meeting)).check(withItemCount(1));
        //We check the location filter is the same of location from item
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.tv_meeting)));
        textView2.check(matches(withText("Réunion C - 10:00 -> 4 hour(s) - Toad")));

        //We click on hamburger menu
        onView(withId(R.id.filter)).perform(click());
        //We selected Reset Date Filter Option
        ViewInteraction materialTextView4 = onView(
                allOf(withId(R.id.title), withText("Reset Filter")));
        materialTextView4.perform(click());
        //We check the reset of list
        onView(ViewMatchers.withId(R.id.rv_meeting)).check(withItemCount(ITEMS_COUNT));
    }


    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}