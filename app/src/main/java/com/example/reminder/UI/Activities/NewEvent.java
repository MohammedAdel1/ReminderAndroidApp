package com.example.reminder.UI.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.IpSecManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import com.example.reminder.Data.Event;
import com.example.reminder.Data.EventDatabase;
import com.example.reminder.R;
import com.example.reminder.UI.Fragments.DatePickerFragment;
import com.example.reminder.UI.Fragments.TimePickerFragment;

import java.io.Serializable;
import java.util.Calendar;


public class NewEvent extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener , DatePickerDialog.OnDateSetListener {

    EditText name , budget;
    TextView date , time;
    Button  doneBtn;

    EventDatabase db;

    String[] spinnerTimes = {"one day" , "three days" , "five days"};
    Spinner spinner;

    int numDaysSelected ;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_event);

        name =  findViewById(R.id.eventNameInput);
        budget = findViewById(R.id.eventBudgetInput);
        date = findViewById(R.id.eventDateInput);
        time = findViewById(R.id.eventTimeInput);
        doneBtn = findViewById(R.id.doneBtn);

        spinner = findViewById(R.id.spinner);
        handleSpinner();

        numDaysSelected = 1;



        db = new EventDatabase(this);

    }// end onCreate ()

    public void handleSpinner(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        numDaysSelected = 1;
                        break;
                    case 1:
                        numDaysSelected = 3;
                        break;
                    case 2:
                        numDaysSelected = 5;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Nothing selected", Toast.LENGTH_SHORT).show();

            }
        });

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.spinner_item, spinnerTimes );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    public void onBackBtn (View view){
        super.onBackPressed();
    }// end onBackBtn ()


    public void onDoneClick (View view) {

        if (name.getText().toString().length() != 0 && date.getText().toString().length() != 0 &&
                time.getText().toString().length() != 0 && budget.getText().toString().length() != 0) {

        Event event = new Event();
        event.setTitle(name.getText().toString());
        event.setDate(date.getText().toString());
        event.setTime(time.getText().toString());
        String budg = budget.getText().toString();
        event.setBudget(Float.parseFloat(budg));
        boolean addition = db.addEvent(event);
        if (addition) {
            Intent back = new Intent(NewEvent.this, MainActivity.class);
            back.putExtra("EventName",event.getTitle());
            back.putExtra("EventDate", event.getDate());
            back.putExtra("EventTime", event.getTime());
            back.putExtra("EventBudget", String.valueOf(event.getBudget()));
            back.putExtra("Interval" , numDaysSelected);
            startActivity(back);
        }// end if
        else {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }

    }else {
            Toast.makeText(getApplicationContext(), "Fill all the above fields" , Toast.LENGTH_LONG).show();
        }

            }// end onDoneClick()

    public void showTimePicker (View view){
        DialogFragment fragment = new TimePickerFragment();
        fragment.show(getSupportFragmentManager(), "TimePicker");
    }// end showTimePicker()

    public void showDatePicker (View view){
        DialogFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "DatePicker");
    }// end showDatePicker()

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

        String hourPicked = String.valueOf(i);
        if (hourPicked.length() == 1){
            hourPicked = "0"+String.valueOf(i);
        }else{hourPicked = String.valueOf(i);}
        String minutePicked = String.valueOf(i1);
        if (minutePicked.length() == 1){
            minutePicked = "0"+String.valueOf(i1);
        }else {minutePicked = String.valueOf(i1);}
        String timePicked = hourPicked + ":"+ minutePicked ;
        time.setText(timePicked);


    }// end onTimeSet()

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        String yearPicked = String.valueOf(i);
         String monthPicked = String.valueOf(i1+1);
         if (monthPicked.length() == 1){
             monthPicked = "0"+String.valueOf(i1+1);
         }else {monthPicked = String.valueOf(i1+1);}
        String dayPicked = String.valueOf(i2);
         if (dayPicked.length() == 1){
             dayPicked = "0"+String.valueOf(i2);
         }else {dayPicked = String.valueOf(i2);}
        String datePicked = dayPicked + " "+ monthPicked + " "+ yearPicked;
        date.setText(datePicked);

    }// end onDateSet ()


}// end Activity
