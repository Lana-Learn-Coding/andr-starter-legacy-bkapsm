package com.example.bkapsm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;

import com.example.bkapsm.form.FormUtils;

import org.apache.commons.lang3.StringUtils;

public class SearchDialogFragment extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_search, null);
        return new AlertDialog.Builder(getActivity(), R.style.Theme_MaterialComponents_Dialog)
            .setView(view)
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    getDialog().cancel();
                }
            })
            .setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                }
            })
            .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra("fullname", StringUtils.defaultString(FormUtils.getText(view.findViewById(R.id.edit_txt_fullname))));
                    intent.putExtra("email", StringUtils.defaultString(FormUtils.getText(view.findViewById(R.id.edit_txt_email))));
                    startActivity(intent);
                }
            })
            .create();
    }
}