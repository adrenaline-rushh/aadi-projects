package androidsamples.java.journalapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryDetailsFragment # newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryDetailsFragment extends Fragment {

  private JournalEntry mEntry;
  private EntryDetailsViewModel entryDetailsViewModel;
  private static final String TAG = "EntryDetailsFragment";
  private EditText mTitle;
  private Button mDate, mStart, mEnd;
  private String entryId;

  @NonNull
  public static EntryDetailsFragment newInstance() {
    EntryDetailsFragment fragment = new EntryDetailsFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "EntryDetails onCreate Called");
    setHasOptionsMenu(true);
    entryDetailsViewModel = new ViewModelProvider(requireActivity()).get(EntryDetailsViewModel.class);

//    assert getArguments() != null;
    UUID entryId = (UUID) getArguments().getSerializable(MainActivity.KEY_ENTRY_ID);

    if(getArguments() != null){
      entryDetailsViewModel.loadEntry(entryId);
    }

    Log.d(TAG, "Loading entry: " + entryId);
  }


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    Log.d(TAG, "EntryDetails onCreateView called");

    return inflater.inflate(R.layout.fragment_entry_details, container, false);
  }


  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mTitle = view.findViewById(R.id.edit_title);
    mDate = view.findViewById(R.id.btn_entry_date);
    mStart = view.findViewById(R.id.btn_start_time);
    mEnd = view.findViewById(R.id.btn_end_time);

    if(savedInstanceState == null){
      mDate.setText(R.string.date);
      mStart.setText(R.string.start_time);
      mEnd.setText(R.string.end_time);
    }

    mDate.setOnClickListener(this::setDate);
    mStart.setOnClickListener(this::setStartTime);
    mEnd.setOnClickListener(this::setEndTime);
    view.findViewById(R.id.btn_save).setOnClickListener(this::saveEntry);

    entryId = EntryDetailsFragmentArgs.fromBundle(getArguments()).getEntryId();
    entryDetailsViewModel.entryId = entryId;

    if(!entryId.isEmpty()) {

      Calendar cal = Calendar.getInstance();

      int date = EntryDetailsFragmentArgs.fromBundle(getArguments()).getSelectedDate();
      int month = EntryDetailsFragmentArgs.fromBundle(getArguments()).getSelectedMonth();
      int year = EntryDetailsFragmentArgs.fromBundle(getArguments()).getSelectedYear();

      SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd, yyyy", Locale.ENGLISH);
      cal.set(year, month, date);
      String formattedDate = formatter.format(cal.getTime());
      mDate.setText(formattedDate);

      String title = EntryDetailsFragmentArgs.fromBundle(getArguments()).getSelectedTitle();

      mTitle.setText(title);

      int startHour = EntryDetailsFragmentArgs.fromBundle(getArguments()).getSelectedStartTimeHour();
      int startMin = EntryDetailsFragmentArgs.fromBundle(getArguments()).getSelectedStartTimeMinute();
      int endHour = EntryDetailsFragmentArgs.fromBundle(getArguments()).getSelectedEndTimeHour();
      int endMin = EntryDetailsFragmentArgs.fromBundle(getArguments()).getSelectedEndTimeMinute();

      mStart.setText(String.format(Locale.ENGLISH, "%02d:%02d", startHour, startMin));
      mEnd.setText(String.format(Locale.ENGLISH, "%02d:%02d", endHour, endMin));

      entryDetailsViewModel.title = mTitle.getText().toString();
      entryDetailsViewModel.date1 = mDate.getText().toString();
      entryDetailsViewModel.start = mStart.getText().toString();
      entryDetailsViewModel.end = mEnd.getText().toString();

    }

  }

  public void setDate(View view){
    Navigation.findNavController(view).navigate(R.id.datePickerAction);

  }

  public void setStartTime(View view){
    entryDetailsViewModel.settingStartTime = true;
    Navigation.findNavController(view).navigate(R.id.timePickerAction);
  }

  public void setEndTime(View view){
    entryDetailsViewModel.settingStartTime = false;
    Navigation.findNavController(view).navigate(R.id.timePickerAction);

  }

  private void saveEntry(View view) {
    SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd, yyyy", Locale.ENGLISH);

    SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

    try{
      Date entryDate = formatter.parse(mDate.getText().toString());

      Date entryStartTime = (formatterTime.parse(mStart.getText().toString()));
      Date entryEndTime = (formatterTime.parse(mEnd.getText().toString()));

      if(entryStartTime.after(entryEndTime)) {
        Toast.makeText(requireContext(), "IncorrectEnd time set", Toast.LENGTH_SHORT).show();
        return;
      }

      JournalEntry newEntry = new JournalEntry(mTitle.getText().toString(), entryDate, entryStartTime, entryEndTime);


      if(entryId.isEmpty()){
        entryDetailsViewModel.saveEntry(newEntry);
      }
      else {
        newEntry.setMUid(UUID.fromString(entryId));
        entryDetailsViewModel.updateEntry(newEntry);
      }
      requireActivity().onBackPressed();
    }catch (ParseException e){
      Log.d("ERROR", "saveEntry: " + e.getMessage());
    }
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    menu.clear();
    inflater.inflate(R.menu.entrydetails_menu, menu);
  }

  //  private void updateEntry(View view) {
//      entryDetailsViewModel.updateEntry(mEntry);
//      requireActivity().onBackPressed();
//
//  }

  public void updateUI(){

  }

}