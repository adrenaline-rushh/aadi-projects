package androidsamples.java.journalapp;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.espresso.accessibility.AccessibilityChecks;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnHolderItem;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import android.view.View;

/**
 * Instrumented tests for the {@link EntryDetailsFragment}.
 */
@RunWith(AndroidJUnit4.class)
public class EntryDetailsFragmentTest {

  private static Integer ITEM_IN_TEST = 0;

  @BeforeClass
  public static void enableAccessibilityChecks() {
    AccessibilityChecks.enable();
  }

  @Rule
  public ActivityScenarioRule<MainActivity> activityScenario
          = new ActivityScenarioRule<>(MainActivity.class);

  @Test
  public void testNavigationToEntryListFragment() {
    // Create a TestNavHostController
    TestNavHostController navController = new TestNavHostController(
        ApplicationProvider.getApplicationContext());

    FragmentScenario<EntryListFragment> entryDetailsFragmentFragmentScenario
        = FragmentScenario.launchInContainer(EntryListFragment.class, null, R.style.Theme_JournalApp, (FragmentFactory) null);

    entryDetailsFragmentFragmentScenario.onFragment(fragment -> {
      // Set the graph on the TestNavHostController
      navController.setGraph(R.navigation.nav_graph);

      // Make the NavController available via the findNavController() APIs
      Navigation.setViewNavController(fragment.requireView(), navController);
    });

    // Verify that performing a click changes the NavController's state
    onView(ViewMatchers.withId(R.id.btn_add_entry)).perform(ViewActions.click());
    assertThat(Objects.requireNonNull(navController.getCurrentDestination()).getId(), is(R.id.entryDetailsFragment));

  }

  @Test
  public void test_ifRecyclerViewShowsProperlyAfterAppLaunch(){
    onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));
  }

  @Test
  public void testForCreatingANewTask(){
    onView(withId(R.id.btn_add_entry)).perform(click());
    onView(withId(R.id.edit_title)).perform(clearText(), typeText("test1"), closeSoftKeyboard());
    onView(withId(R.id.btn_entry_date)).perform(click());
    onView(withText("OK")).perform(click());
    onView(withId(R.id.btn_start_time)).perform(click());
    onView(withText("OK")).perform(click());

    onView(withId(R.id.btn_end_time)).perform(click());
    onView(withText("OK")).perform(click());

    onView(withId(R.id.btn_save)).perform(click());

    onView(withText("test1")).perform(click());

    onView(withId(R.id.edit_title)).check(matches(withText("test1")));

    onView(withId(R.id.delete_item)).perform(click());
    onView(withText("YES")).perform(click());

//    onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(ITEM_IN_TEST, click()));

  }

  @Test
  public void testForCheckingOnBackPressed(){
    onView(withId(R.id.btn_add_entry)).perform(click());
    onView(withId(R.id.edit_title)).perform(clearText(), typeText("test1"), closeSoftKeyboard());
    onView(withId(R.id.btn_entry_date)).perform(click());
    onView(withText("OK")).perform(click());
    onView(withId(R.id.btn_start_time)).perform(click());
    onView(withText("OK")).perform(click());

    onView(withId(R.id.btn_end_time)).perform(click());
    onView(withText("OK")).perform(click());

    onView(withId(R.id.btn_save)).perform(click());

    onView(withText("test1")).perform(click());

    onView(isRoot()).perform(ViewActions.pressBack());

    onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));

    onView(withText("test1")).perform(click());
    onView(withId(R.id.delete_item)).perform(click());
    onView(withText("YES")).perform(click());

  }

  @Test
  public void test_adding_and_deleting_a_new_entry(){
    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.DAY_OF_WEEK, 2);
    cal.set(Calendar.MONTH, 5);
    cal.set(Calendar.YEAR, 2022);
    cal.set(Calendar.HOUR_OF_DAY, 13);
    cal.set(Calendar.MINUTE, 30);

    String date = new SimpleDateFormat("E, MMM dd, yyyy", Locale.ENGLISH).format(cal.getTime());
    onView(withId(R.id.btn_add_entry)).perform(click());

    String startTime = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(cal.getTime());

    onView(withId(R.id.edit_title)).perform(clearText(), typeText("New Entry"), closeSoftKeyboard());
    onView(withId(R.id.btn_entry_date)).perform(click());
    onView(withText("OK")).perform(click());
    onView(withId(R.id.btn_start_time)).perform(click());
//    onView(withText("AM")).perform(click());
//    onView(withText("6")).perform(click());
    onView(withText("OK")).perform(click());

    cal.set(13, 15);
    String endTime = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(cal.getTime());

    onView(withId(R.id.btn_end_time)).perform(click());
//    onView(withText("PM")).perform(click());
//    onView(withText("12")).perform(click());
//    onView(withText("30")).perform(click());
    onView(withText("OK")).perform(click());

    onView(withId(R.id.btn_save)).perform(click());

    onView(withText("New Entry")).perform(click());
    onView(withId(R.id.delete_item)).perform(click());
    onView(withText("YES")).perform(click());

  }

  @Test
  public void test_info_button(){
    onView(withId(R.id.info_item)).perform(click());
    onView(withText("OKAY")).perform(click());
  }
}