package com.example.reminder.UI.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.reminder.Data.Event;
import com.example.reminder.R;


public class EditTitleDialogFragment extends DialogFragment {

    public interface NoticeTitleDialogListener{

         void onDialogPositiveClick(DialogFragment dialog , EditText editedName);
         void onDialogNegativeClick(DialogFragment dialog);
    }// end NoticeDialogListener interface

    NoticeTitleDialogListener listener;
    Event event;
    EditText eventNameEdit;


    public EditTitleDialogFragment(Event event){
        this.event = event;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (NoticeTitleDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(" Must implement NoticeDialogListener");
        }// end catch

    }// end onAttach ()



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.title_edit_dialog, null));



        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onDialogPositiveClick(EditTitleDialogFragment.this , eventNameEdit);

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onDialogNegativeClick(EditTitleDialogFragment.this);

            }
        });

        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                eventNameEdit = dialog.findViewById(R.id.eventNameInput2);
                eventNameEdit.setText(event.getTitle());



                Button posBtn = ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                posBtn.setBackgroundResource(R.drawable.custom_dialog_buttons);
                posBtn.setTextColor(Color.parseColor("#ffffff"));
                posBtn.setTextSize(20);
                posBtn.setWidth(getResources().getDimensionPixelSize(R.dimen.dialog_button_width));
                posBtn.setHeight(getResources().getDimensionPixelSize(R.dimen.dialog_button_height));


                Button negBtn = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negBtn.setBackgroundResource(R.drawable.custom_dialog_buttons);
                negBtn.setTextColor(Color.parseColor("#ffffff"));
                negBtn.setTextSize(18);
                negBtn.setWidth(getResources().getDimensionPixelSize(R.dimen.dialog_button_width));
                negBtn.setHeight(getResources().getDimensionPixelSize(R.dimen.dialog_button_height));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                params.setMargins(0,0,20,0);
                negBtn.setLayoutParams(params);


            }
        });

        return dialog;

    }// end onCreateDialog ()


}// end Fragment
