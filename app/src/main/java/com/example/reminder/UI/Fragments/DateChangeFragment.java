package com.example.reminder.UI.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.reminder.Data.Event;

public class DateChangeFragment extends DialogFragment {

    Event event;

    public DateChangeFragment(Event event){

        this.event = event;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

       String date =  event.getDate();
       String dayS = date.substring(0,2);
       String monthS = date.substring(3,5);
        String yearS = date.substring(6,10);

        int year = Integer.parseInt(yearS);
        int month = Integer.parseInt(monthS)-1;
        int day = Integer.parseInt(dayS);

        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
    }
}
