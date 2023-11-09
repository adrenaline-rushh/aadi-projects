//package androidsamples.java.journalapp;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//import java.util.Map;
//
//public class JournalEntryListAdapter extends RecyclerView.Adapter<JournalEntryListAdapter.EntryViewHolder> {
//    private final LayoutInflater mInflater;
//    private List<JournalEntry> mEntries;
//
//    public JournalEntryListAdapter(Context context){
//        mInflater = LayoutInflater.from(context);
//    }
//
//    @NonNull @Override
//    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
//        View itemView = mInflater.inflate(R.layout.fragment_entry, parent, false);
//        return new EntryViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position){
//        if(mEntries != null){
//            JournalEntry current = mEntries.get(position);
//            holder.mTitle.setText(current.getTitle());
//            holder.mDate.setText(current.getDate().toString());
//            holder.mStart.setText(current.getStart_time().toString());
//            holder.mEnd.setText(current.getEnd_time().toString());
//        }
//    }
//
//    class EntryViewHolder extends RecyclerView.ViewHolder {
//        private final TextView mTitle, mDate, mStart, mEnd;
//
//        public EntryViewHolder(@NonNull View itemView){
//            super(itemView);
//            mTitle = itemView.findViewById(R.id.txt_item_title);
//            mDate = itemView.findViewById(R.id.txt_item_date);
//            mStart = itemView.findViewById(R.id.txt_item_start_time);
//            mEnd = itemView.findViewById(R.id.txt_item_end_time);
//        }
//    }
//
//    @Override
//    public int getItemCount(){
//        return (mEntries == null) ? 0 : mEntries.size();
//    }
//
//    public void setEntries(List<JournalEntry> entries){
//        mEntries = entries;
//        notifyDataSetChanged();
//    }
//}
