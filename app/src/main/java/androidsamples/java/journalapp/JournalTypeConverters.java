package androidsamples.java.journalapp;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

import java.util.Date;
import java.util.UUID;

public class JournalTypeConverters {
    @TypeConverter
    public UUID toUUID(@NonNull String uuid){
        return UUID.fromString(uuid);
    }

    @TypeConverter
    public String fromUUID(@NonNull UUID uuid){
        return uuid.toString();
    }

    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static long fromDate(Date date){
        return date == null ? 0 :date.getTime();
    }
}
