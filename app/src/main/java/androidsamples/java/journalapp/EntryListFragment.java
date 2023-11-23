package androidsamples.java.journalapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

/**
 * A fragment representing a list of Items.
 */
public class EntryListFragment extends Fragment {

  private JournalViewModel mJournalViewModel;
  private static final String TAG = "EntryListFragment";

  @NonNull
  public static EntryListFragment newInstance() {
    EntryListFragment fragment = new EntryListFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "EntryList onCreate Called");
    mJournalViewModel = new ViewModelProvider(this).get(JournalViewModel.class);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    Log.d(TAG, "EntryList onCreateView Called");
    View view = inflater.inflate(R.layout.fragment_entry_list, container, false);

    view.findViewById(R.id.btn_add_entry).setOnClickListener(this::addNewEntry);

    RecyclerView entriesList = view.findViewById(R.id.recyclerView);
    entriesList.setLayoutManager(new LinearLayoutManager(getActivity()));
    JournalEntryListAdapter adapter = new JournalEntryListAdapter(getActivity());
    entriesList.setAdapter(adapter);

    mJournalViewModel.getAllEntries().observe(requireActivity(), adapter::setEntries);

    return view;
  }

  public void addNewEntry(View view){
    Navigation.findNavController(view).navigate(R.id.addEntryAction);
  }

  interface Callbacks{
    void onEntrySelected(UUID uuid);
  }
  private final Callbacks mCallbacks = null;


  private class JournalEntryListAdapter extends RecyclerView.Adapter<JournalEntryListAdapter.EntryViewHolder> {
    private final LayoutInflater mInflater;
    private List<JournalEntry> mEntries;
    private JournalEntry mEntry;

    public JournalEntryListAdapter(Context context){
      mInflater = LayoutInflater.from(context);
    }

    public void setEntries(List<JournalEntry> entries){
      mEntries = entries;
      notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
      return (mEntries == null) ? 0 : mEntries.size();
    }

    @NonNull @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
      View itemView = mInflater.inflate(R.layout.fragment_entry, parent, false);
      return new EntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position){
      if(mEntries != null){
        SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd, yyyy", Locale.ENGLISH);

        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

        JournalEntry current = mEntries.get(position);
        holder.mTitle.setText(current.getTitle());
        Date date = current.getDate();
        Date start = current.getStart_time();
        Date end = current.getEnd_time();
        holder.mDate.setText(formatter.format(date));
        holder.mStart.setText(formatterTime.format(start));
        holder.mEnd.setText(formatterTime.format(end));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            mEntry = mEntries.get(holder.getBindingAdapterPosition());
            launchJournalEntryFragment(v);
          }
        });
      }
    }

    private void launchJournalEntryFragment(View v){
//        mCallbacks.onEntrySelected(mEntry.getMUid());

      EntryListFragmentDirections.AddEntryAction action = EntryListFragmentDirections.addEntryAction();
      action.setEntryId(mEntry.getMUid().toString());
      int year = mEntry.getDate().getYear() + 1900;
      int month = mEntry.getDate().getMonth();
      int day = mEntry.getDate().getDate();
      action.setSelectedTitle(mEntry.getTitle());
      action.setSelectedDate(day);
      action.setSelectedYear(year);
      action.setSelectedMonth(month);

      int startHour = mEntry.getStart_time().getHours();
      int startMinute = mEntry.getStart_time().getMinutes();
      int endHour = mEntry.getEnd_time().getHours();
      int endMinute = mEntry.getEnd_time().getMinutes();

      action.setSelectedStartTimeHour(startHour);
      action.setSelectedStartTimeMinute(startMinute);
      action.setSelectedEndTimeHour(endHour);
      action.setSelectedEndTimeMinute(endMinute);

      Navigation.findNavController(v).navigate(action);

    }

    private class EntryViewHolder extends RecyclerView.ViewHolder {
      private final TextView mTitle, mDate, mStart, mEnd;

      public EntryViewHolder(@NonNull View itemView){
        super(itemView);
        mTitle = itemView.findViewById(R.id.txt_item_title);
        mDate = itemView.findViewById(R.id.txt_item_date);
        mStart = itemView.findViewById(R.id.txt_item_start_time);
        mEnd = itemView.findViewById(R.id.txt_item_end_time);
      }
    }

  }


  @Override
  public void onDestroy() {
    super.onDestroy();

    Log.d(TAG, "onDestroy");
  }


}