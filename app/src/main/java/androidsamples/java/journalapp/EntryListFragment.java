package androidsamples.java.journalapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A fragment representing a list of Items.
 */
public class EntryListFragment extends Fragment {

  private JournalViewModel mJournalViewModel;

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
    mJournalViewModel = new ViewModelProvider(this).get(JournalViewModel.class);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
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
    Fragment fragment = new EntryDetailsFragment();
    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
  }

  interface Callbacks{
    void onEntrySelected(UUID uuid);
  }
  private Callbacks mCallbacks = null;

  private class JournalEntryListAdapter extends RecyclerView.Adapter<JournalEntryListAdapter.EntryViewHolder> {
    private final LayoutInflater mInflater;
    private List<JournalEntry> mEntries;
    private JournalEntry mEntry;

    public JournalEntryListAdapter(Context context){
      mInflater = LayoutInflater.from(context);
    }

    @NonNull @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
      View itemView = mInflater.inflate(R.layout.fragment_entry, parent, false);
      return new EntryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position){
      if(mEntries != null){
        JournalEntry current = mEntries.get(position);
        holder.mTitle.setText(current.getTitle());
        holder.mDate.setText(current.getDate().toString());
        holder.mStart.setText(current.getStart_time().toString());
        holder.mEnd.setText(current.getEnd_time().toString());
      }
    }

    private class EntryViewHolder extends RecyclerView.ViewHolder {
      private final TextView mTitle, mDate, mStart, mEnd;

      public EntryViewHolder(@NonNull View itemView){
        super(itemView);
        mTitle = itemView.findViewById(R.id.txt_item_title);
        mDate = itemView.findViewById(R.id.txt_item_date);
        mStart = itemView.findViewById(R.id.txt_item_start_time);
        mEnd = itemView.findViewById(R.id.txt_item_end_time);
        itemView.setOnClickListener(this::launchJournalEntryFragment);
      }

      private void launchJournalEntryFragment(View v){
        mCallbacks.onEntrySelected(mEntry.getMUid());
      }
    }

    @Override
    public int getItemCount(){
      return (mEntries == null) ? 0 : mEntries.size();
    }

    public void setEntries(List<JournalEntry> entries){
      mEntries = entries;
      notifyDataSetChanged();
    }
  }

}