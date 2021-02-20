package com.example.reminder.Data;


import android.content.Context;
import android.media.Image;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.reminder.R;

import java.util.List;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder>{



    List<Event> eventList;
    Context context;

    public EventAdapter (Context context, List<Event> eventList){
        this.context = context;
        this.eventList = eventList;
    }// end Constructor ()

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_layout, parent,false);
        return new MyViewHolder(view, listener) ;
    }// end onCreateViewHolder ()


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
            Event event = eventList.get(position);
            holder.title.setText(event.getTitle());
            holder.date.setText(event.getDate());
            holder.time.setText(event.getTime());
            holder.budget.setText(String.valueOf(event.getBudget()));
    }// end onBindViewHolder()

    @Override
    public int getItemCount() {
        return eventList.size();
    }// end getItemCount ()


    private OnItemClickListener listener;
    public interface OnItemClickListener {

        void onItemTitleClick (int position);
        void onItemDateClick(int position);
        void onItemTimeClick (int position);
        void  onItemBudgetClick (int position);
        void onItemDeleteClick (int position);

    }

    public void setOnItemClickListener (OnItemClickListener listener){
        this.listener = listener;
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder  {

        TextView title , date , time , budget;
        ConstraintLayout eventLayout;
        ImageView deleteIcon;

        public MyViewHolder(View itemView , final OnItemClickListener listener ) {
            super(itemView);

            title = itemView.findViewById(R.id.event_title);
            date = itemView.findViewById(R.id.event_date);
            time = itemView.findViewById(R.id.event_time);
            budget = itemView.findViewById(R.id.event_budget);
            eventLayout = itemView.findViewById(R.id.eventLayout);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);



            deleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position  = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemDeleteClick(position);
                        }
                    }
                }
            });


            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemTitleClick(position);
                        }
                    }
                }
            });
            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemDateClick(position);
                        }
                    }
                }
            });

            time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemTimeClick(position);
                        }
                    }

                }
            });
            budget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemBudgetClick(position);
                        }
                    }
                }
            });
        }// end Constructor ()



    }// end MyViewHolder Class

}// end EventAdapter Class
