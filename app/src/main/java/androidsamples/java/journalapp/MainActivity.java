package androidsamples.java.journalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class MainActivity extends AppCompatActivity{
  public static String KEY_ENTRY_ID = "2";
  private static final String TAG = "MainActivity";
  private EntryDetailsViewModel entryDetailsViewModel;
  private JournalViewModel journalViewModel;
  private NavHostFragment navHostFragment;
  private NavController navController;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Log.d(TAG, "MainActivity onCreate Called");

    entryDetailsViewModel = new ViewModelProvider(this).get(EntryDetailsViewModel.class);
    journalViewModel = new ViewModelProvider(this).get(JournalViewModel.class);

    navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
    navController = navHostFragment.getNavController();

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.entrylist_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.info_item:
        showInfo();
        return true;
      case R.id.share_item:
        shareEntry();
        return true;
      case R.id.delete_item:
        deleteEntry();
        return true;
      default:
      return super.onOptionsItemSelected(item);
    }
  }

  public void showInfo(){
    navController.navigate(R.id.action_entryListFragment_to_infoFragment);
  }

  public void shareEntry(){
    String message = String.format(Locale.ENGLISH, "Look what I have been upto: " + entryDetailsViewModel.title + " on " + entryDetailsViewModel.date1 + ", " + entryDetailsViewModel.start + " to " + entryDetailsViewModel.end);
    Intent shareIntent = new Intent(Intent.ACTION_SEND);
    shareIntent.putExtra(Intent.EXTRA_TEXT, message);
    shareIntent.setType("text/plain");

    Intent share = Intent.createChooser(shareIntent, null);
    startActivity(share);
  }

  public void deleteEntry(){
    navController.navigate(R.id.action_entryDetailsFragment_to_deleteFragment);
  }

  @Override
  protected void onStart(){
    super.onStart();
    Log.d(TAG, "onStart");
  }

  @Override
  protected void onResume(){
    super.onResume();
    Log.d(TAG, "onResume");
  }

  @Override
  protected void onPause(){
    super.onPause();
    Log.d(TAG, "onPause");
  }

  @Override
  protected void onStop(){
    super.onStop();
    Log.d(TAG, "onStop");
  }

  @Override
  protected void onDestroy(){
    super.onDestroy();
    Log.d(TAG, "onDestroy");
  }
}