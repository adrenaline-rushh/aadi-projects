package androidsamples.java.journalapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryDetailsFragment # newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryDetailsFragment extends Fragment {
  private JournalEntry entry;
  private EntryDetailsViewModel mEntryDetailsViewModel;
  private static final String TAG = "EntryDetailsFragment";
  private EditText mTitle;
  private Button mDate, mStart, mEnd;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    mEntryDetailsViewModel = new ViewModelProvider(requireActivity()).get(EntryDetailsViewModel.class);
    assert getArguments() != null;
    UUID entryId = (UUID) getArguments().getSerializable(MainActivity.KEY_ENTRY_ID);
    Log.d(TAG, "Loading entry: " + entryId);

    mEntryDetailsViewModel.loadEntry(entryId);
  }


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_entry_details, container, false);
    mTitle = view.findViewById(R.id.edit_title);
    mDate = view.findViewById(R.id.btn_entry_date);
    mStart = view.findViewById(R.id.btn_start_time);
    mEnd = view.findViewById(R.id.btn_end_time);

    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mEntryDetailsViewModel.getEntryLiveData().observe(requireActivity(), entry -> {
      this.entry = entry;
      updateUI();
    });
  }

  private void saveEntry(View view) throws ParseException {
    entry.setTitle(mTitle.getText().toString());
    SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd, yyyy", Locale.ENGLISH);
    entry.setDate(formatter.parse(mDate.getText().toString()));

    SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    entry.setStart_time((formatterTime.parse(mStart.getText().toString())));
    entry.setStart_time((formatterTime.parse(mEnd.getText().toString())));
  }

  public void updateUI(){

  }
}