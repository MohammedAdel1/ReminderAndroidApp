package com.example.reminder.UI.Fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.reminder.Data.Event;

public class TimeChangeFragment extends DialogFragment {

    Event event;

    public TimeChangeFragment (Event event){

        this.event = event;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        String time = event.getTime();
        String hourS = time.substring(0,2);
        String minuteS = time.substring(3,5);

        int hour = Integer.parseInt(hourS);
        int minute = Integer.parseInt(minuteS);

        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity() , hour,minute, DateFormat.is24HourFormat(getActivity()));
    }// end onCreateDialog ()

}
