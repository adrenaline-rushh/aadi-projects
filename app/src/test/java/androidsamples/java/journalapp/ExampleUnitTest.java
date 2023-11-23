package androidsamples.java.journalapp;

import static org.junit.Assert.assertEquals;

//import androidx.test.ext.junit.runners.AndroidJUnit4;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//@RunWith(AndroidJUnit4.class)
public class ExampleUnitTest {
  private JournalEntryDao journalEntryDao;
  private JournalRoomDatabase journalRoomDatabase;

  @Before
  public void createDB(){
    Context context = ApplicationProvider.getApplicationContext();
    journalRoomDatabase = Room.inMemoryDatabaseBuilder(context, JournalRoomDatabase.class).build();
    journalEntryDao = journalRoomDatabase.journalEntryDao();
  }

  @After
  public void closeDB() throws IOException {
    journalRoomDatabase.close();
  }


  @Test
  public void addition_isCorrect() {
    assertEquals(4, 2 + 2);
  }

//  @Test
//  public void insertEntryAndReadInList(){
//    JournalEntry entry =
//  }
}