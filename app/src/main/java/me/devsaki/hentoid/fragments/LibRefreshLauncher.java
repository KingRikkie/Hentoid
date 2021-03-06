package me.devsaki.hentoid.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import java.util.Objects;

import me.devsaki.hentoid.R;
import me.devsaki.hentoid.activities.ImportActivity;
import me.devsaki.hentoid.util.ConstsImport;

/**
 * Created by Robb on 11/2018
 * Launcher dialog for the library refresh feature
 */
public class LibRefreshLauncher extends DialogFragment {

    public static void invoke(FragmentManager fragmentManager) {
        LibRefreshLauncher fragment = new LibRefreshLauncher();
        fragment.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.ImportDialogTheme);
        fragment.show(fragmentManager, null);
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setTitle(R.string.app_name)
                .setMessage(R.string.cleanup_folders)
                .setPositiveButton(R.string.action_refresh_cleanup,
                        (dialog1, which) -> launchRefreshImport(true))
                .setNegativeButton(R.string.action_refresh,
                        (dialog12, which) -> launchRefreshImport(false))
                .create();
    }

    private void launchRefreshImport(boolean shouldCleanup) {
        Intent refresh = new Intent(requireContext(), ImportActivity.class);
        refresh.setAction("android.intent.action.APPLICATION_PREFERENCES"); // Is only a constant since API 24 -> using the string
        refresh.putExtra("refresh", true);
        refresh.putExtra("cleanup", shouldCleanup);
        startActivityForResult(refresh, ConstsImport.RQST_IMPORT_RESULTS);
    }
}
