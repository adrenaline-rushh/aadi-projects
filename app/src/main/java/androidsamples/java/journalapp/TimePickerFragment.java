package androidsamples.java.journalapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

  private Calendar c = Calendar.getInstance();
  private EntryDetailsViewModel entryDetailsViewModel;

  @NonNull
  public static TimePickerFragment newInstance(Date time) {
    // TODO implement the method
    return null;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO implement the method
    entryDetailsViewModel = new ViewModelProvider(requireActivity()).get(EntryDetailsViewModel.class);
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    // TODO implement the method
//    final Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);

    return new TimePickerDialog(requireContext(), this, hour, minute, false);
  }

  @Override
  public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    c.set(Calendar.HOUR_OF_DAY, hourOfDay);
    c.set(Calendar.MINUTE, minute);

    String finalTime = formatterTime.format(c.getTime());

    if(entryDetailsViewModel.settingStartTime) {
      entryDetailsViewModel.setStart_timeInVM(c.getTime());
      entryDetailsViewModel.start = finalTime;
      ((Button)getActivity().findViewById(R.id.btn_start_time)).setText(finalTime);
    }
    else {
        entryDetailsViewModel.end = finalTime;
        ((Button) getActivity().findViewById(R.id.btn_end_time)).setText(finalTime);
    }
  }
}
