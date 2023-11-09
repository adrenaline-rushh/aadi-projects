package androidsamples.java.journalapp;

import android.text.format.Time;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

@Entity(tableName = "journal_table")
public class JournalEntry {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private UUID mUid;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "start_time")
    private Date start_time;

    @ColumnInfo(name = "end_time")
    private Date end_time;

    public JournalEntry(@NonNull String title, Date date, Date start_time, Date end_time){
        mUid = UUID.randomUUID();
        this.title = title;
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    @NonNull
    public UUID getMUid(){
        return mUid;
    }

    public String getTitle(){
        return title;
    }

    public Date getDate(){
        return date;
    }

    public Date getStart_time(){
        return start_time;
    }

    public Date getEnd_time(){
        return end_time;
    }

    public void setMUid(@NonNull UUID uuid){
        this.mUid = uuid;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public void setStart_time(Date startTime){
        this.start_time = startTime;
    }

    public void setEnd_time(Date endTime){
        this.end_time = endTime;
    }
}
