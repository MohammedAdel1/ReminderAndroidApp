package com.example.reminder.UI.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.reminder.Data.Event;
import com.example.reminder.R;

public class DeleteDialog extends DialogFragment {

    public interface NoticeDeleteDialogListener{
        void onDeleteDialogPositiveClick(int position);
    }

    NoticeDeleteDialogListener listener;
    int position;

    public DeleteDialog(int position){
        this.position = position;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DeleteDialog.NoticeDeleteDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(" Must implement NoticeDialogListener");
        }// end catch

    }// end onAttach ()
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onDeleteDialogPositiveClick(position);

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setIcon(R.drawable.ic_baseline_delete_24).setTitle("Delete");
        return builder.create();


    }
}
