package com.example.reminder.UI.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.reminder.Data.Event;
import com.example.reminder.Data.EventAdapter;
import com.example.reminder.Data.EventDatabase;
import com.example.reminder.Data.ReminderReceiver;
import com.example.reminder.R;
import com.example.reminder.UI.Fragments.DateChangeFragment;
import com.example.reminder.UI.Fragments.DeleteDialog;
import com.example.reminder.UI.Fragments.EditBudgetDialogFragment;
import com.example.reminder.UI.Fragments.EditTitleDialogFragment;
import com.example.reminder.UI.Fragments.TimeChangeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity  implements  DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener
        , EditTitleDialogFragment.NoticeTitleDialogListener , EditBudgetDialogFragment.NoticeBudgetDialogListener ,
        DeleteDialog.NoticeDeleteDialogListener {

    RecyclerView recyclerView;
    TextView noEvents;

    List<Event> eventList;
    EventAdapter eventAdapter;
    EventDatabase db ;

    Event titleEditedEvent;
    Event dateEditedEvent;
    Event timeEditedEvent;
    Event budgetEditedEvent;
    Event toDeleteEvent;

    public static final String Preferences = "myPrefs";
    public static final String Name = "nameKey";
    public static final String Budget = "budgetKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        noEvents = findViewById(R.id.noEventsText);

        manageAdapter();

        Intent fromNewEvent = getIntent();
        startAlarm(fromNewEvent);


    }// end onCreate ()


    public void startAlarm (Intent intent){

        String eventName = intent.getStringExtra("EventName");
        String eventDate = intent.getStringExtra("EventDate");
        String eventTime = intent.getStringExtra("EventTime");
        String eventBudget = intent.getStringExtra("EventBudget");
        int intervalSelected = intent.getIntExtra("Interval", 1);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent receiverGo = new Intent(this, ReminderReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1,receiverGo,0);

        if (eventName != null && eventBudget != null){

            SharedPreferences preferences = getSharedPreferences(Preferences , Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Name, eventName);
            editor.putString(Budget , eventBudget);
            editor.commit();


        }

        if (eventTime != null && eventDate != null) {
            String hour = eventTime.substring(0, 2);
            String minute = eventTime.substring(3, 5);
            String day = eventDate.substring(0,2);
            String month = eventDate.substring(3,5);
            String year = eventDate.substring(6,10);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR , Integer.parseInt(year));
            calendar.set(Calendar.MONTH , Integer.parseInt(month) -1);
            calendar.set(Calendar.DATE , Integer.parseInt(day) - intervalSelected);
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
            calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
            calendar.set(Calendar.SECOND, 0);


            Objects.requireNonNull(alarmManager).setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }



    public void handleAddBtn ( View view){
                Intent newEvent = new Intent(MainActivity.this, NewEvent.class);
                startActivity(newEvent);
    }// end handleAddBtn ()

    public void manageAdapter (){

        eventList = new ArrayList<>();
        db = new EventDatabase(this);
        eventList = db.getAllEvent();
        if(eventList.size() == 0){
            noEvents.setVisibility(View.VISIBLE);
        }// end if
        else {
            eventAdapter = new EventAdapter(this, eventList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

            DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider)));
            recyclerView.addItemDecoration(divider);

            eventAdapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
                @Override
                public void onItemTitleClick(int position) {
                    titleEditedEvent = eventList.get(position);
                    DialogFragment fragment = new EditTitleDialogFragment(titleEditedEvent);
                    fragment.show(getSupportFragmentManager(), "editEvent");

                }// end onItemTitleClick ()

                @Override
                public void onItemDateClick(int position) {
                    dateEditedEvent = eventList.get(position);
                    DialogFragment fragment = new DateChangeFragment(dateEditedEvent);
                    fragment.show(getSupportFragmentManager(), "DateChange");
                }// end onItemDateClick ()

                @Override
                public void onItemTimeClick(int position) {
                    timeEditedEvent = eventList.get(position);
                    DialogFragment fragment = new TimeChangeFragment(timeEditedEvent);
                    fragment.show(getSupportFragmentManager(), "TimeChange");
                }// end onItemTimeClick ()

                @Override
                public void onItemBudgetClick(int position) {
                    budgetEditedEvent = eventList.get(position);
                    DialogFragment fragment = new EditBudgetDialogFragment(budgetEditedEvent);
                    fragment.show(getSupportFragmentManager(), "EditBudget");
                }// end onItemBudgetClick()

                @Override
                public void onItemDeleteClick(int position) {
                    toDeleteEvent = eventList.get(position);
                    DialogFragment fragment = new DeleteDialog(position);
                    fragment.show(getSupportFragmentManager(), "DeleteEvent");

                }
            });


                recyclerView.setAdapter(eventAdapter);
        }// end else

    }// end manageAdapter()

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }// end onBackPressed ()

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
        dateEditedEvent.setDate(datePicked);
        db.updateEvent(dateEditedEvent);
        eventAdapter.notifyDataSetChanged();
    }// end onDateSet()

    @Override
    public void onDialogPositiveClick(DialogFragment dialog , EditText editedName) {

        String editedEventName = editedName.getText().toString();
        titleEditedEvent.setTitle(editedEventName);
        db.updateEvent(titleEditedEvent);
        eventAdapter.notifyDataSetChanged();
    }// end onDialogPositiveClick()

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }

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
        timeEditedEvent.setTime(timePicked);
        db.updateEvent(timeEditedEvent);
        eventAdapter.notifyDataSetChanged();

    }// end onTimeSet ()

    @Override
    public void onBudgetDialogPositiveClick(DialogFragment dialogFragment, EditText editedBudget) {

        float editedBudgetF = Float.parseFloat(editedBudget.getText().toString());
        budgetEditedEvent.setBudget(editedBudgetF);
        db.updateEvent(budgetEditedEvent);
        eventAdapter.notifyDataSetChanged();
    }


    @Override
    public void onDeleteDialogPositiveClick(int position) {
        db.deleteEvent(toDeleteEvent);
        eventList.remove(position);
        eventAdapter.notifyItemRemoved(position);
        eventAdapter.notifyItemRangeChanged(position, eventList.size());
    }
}// end MainActivity