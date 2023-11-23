package androidsamples.java.journalapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.Date;
import java.util.UUID;

public class EntryDetailsViewModel extends ViewModel {

    private final JournalRepository mRepository;
    private final MutableLiveData<UUID> entryIdLiveData = new MutableLiveData<>();
    private String date;
    public String start, end, title, date1;
    boolean settingStartTime;
    private Date start_time;
    String entryId;

    public EntryDetailsViewModel() { mRepository = JournalRepository.getInstance(); }

    void saveEntry(JournalEntry entry) { mRepository.insert(entry); }

    void updateEntry(JournalEntry entry) { mRepository.update(entry); }

    public LiveData<JournalEntry> getEntryLiveData() {
        return Transformations.switchMap(entryIdLiveData, mRepository::getEntry);
    }

    void loadEntry(UUID entryId) { entryIdLiveData.setValue(entryId); }

    public void setDateInVM(String date) {
        this.date = date;
    }

    public void setStart_timeInVM(Date start_time) {
        this.start_time = start_time;
    }

    public String getDateFromVM() {
        return date;
    }

    public Date getStart_timeFromVM() {
        return start_time;
    }

}
