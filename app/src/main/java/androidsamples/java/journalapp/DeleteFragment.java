package androidsamples.java.journalapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Date;
import java.util.UUID;

public class DeleteFragment extends DialogFragment {
    private JournalViewModel journalViewModel;
    private EntryDetailsViewModel entryDetailsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        journalViewModel = new ViewModelProvider(requireActivity()).get(JournalViewModel.class);
        entryDetailsViewModel = new ViewModelProvider(requireActivity()).get(EntryDetailsViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.dltConfirm))
                .setNegativeButton(getString(R.string.no), (dialog, which) -> {
                    requireActivity().onBackPressed();
                })
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                    JournalEntry entryToDelete = new JournalEntry("", new Date(), new Date(), new Date());
                    entryToDelete.setMUid(UUID.fromString(entryDetailsViewModel.entryId));

                    journalViewModel.delete(entryToDelete);
                    requireActivity().onBackPressed();
                    requireActivity().onBackPressed();
                }).create();
    }
}
