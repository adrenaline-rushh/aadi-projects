package androidsamples.java.journalapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
//  private static final String DATE_KEY = "dateKey";
  private Calendar c = Calendar.getInstance();
  private final static String TAG = "DateFragment";
  private EntryDetailsViewModel entryDetailsViewModel;
//  onDialogCloseListener

  @NonNull
  public static DatePickerFragment newInstance(Date date) {
    // TODO implement the method
    DatePickerFragment datePicker = new DatePickerFragment();
    Bundle args = new Bundle();
    datePicker.setArguments(args);

    return datePicker;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // TODO implement the method

    Log.d(TAG, "DatePicker Created");

    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    entryDetailsViewModel = new ViewModelProvider(requireActivity()).get(EntryDetailsViewModel.class);

    Log.d(TAG, "First Date : "+ new SimpleDateFormat("E, MMM dd, yyyy", Locale.ENGLISH).format(c.getTime()));

    return new DatePickerDialog(requireContext(), this, year, month, day);
  }

  public void onDateSet(@NonNull DatePicker view, int year, int month, int day){

    Log.d(TAG, "onDateSetCalled");

    SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd, yyyy", Locale.ENGLISH);

    c.set(year, month, day);
    String finalDate = formatter.format(c.getTime());

    entryDetailsViewModel.setDateInVM(finalDate);

    c.set(year, month, day);
    String formattedDate = formatter.format(c.getTime());
    if(year <= 1950) Toast.makeText(requireContext(), "Incorrect year", Toast.LENGTH_SHORT).show();
    else ((Button)getActivity().findViewById(R.id.btn_entry_date)).setText(formattedDate);

    Log.d(TAG, "Set Date : " + entryDetailsViewModel.getDateFromVM());
  }

  @Override
  public void onDismiss(@NonNull DialogInterface dialog) {
    super.onDismiss(dialog);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();

    Log.d(TAG, "Dialog Destroyed");
  }
}
