package com.example.eventsrus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder > {
    private final LayoutInflater eInflater;
    private List<Event> allEvents;
    private List<Event> filteredEvent;

    EventListAdapter(Context context, ArrayList<Event>allEvents) {
        eInflater=LayoutInflater.from(context);
        this.allEvents=allEvents;
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        private TextView itemEventName;
        private TextView itemPlace;
        private TextView City;
        private String itemDescription;
        private TextView itemDate;
        private ImageView imgEvent;
        private String itemAddress;
        private String itemPostcode;
        private String imgDesc;


        private EventViewHolder(View itemView) {
            super(itemView);
            itemEventName = itemView.findViewById(R.id.eventName);
             imgEvent= itemView.findViewById(R.id.imgEvent);
             itemPlace=itemView.findViewById(R.id.eventPlace);
             itemDate=itemView.findViewById(R.id.eventDate);
             City=itemView.findViewById(R.id.eventCity);



        }
    }
    @Override
    public void onBindViewHolder( EventViewHolder holder, int position) {
        if (allEvents != null) {
            Event current = allEvents.get(position);
            String imgPath=current.getImgDesc();
            String eventDescription= current.getDescription();
            holder.City.setText(current.getCity());
            holder.itemEventName.setText(current.getName());
            //holder.imgEvent.setImageResource(R.drawable.imgPath);
            holder.itemDescription= current.getDescription();
            holder.itemAddress= current.getAddress();
            holder.itemPostcode= current.getPostCode();
            holder.itemDate.setText(current.getDate());
        } else {
            // Covers the case of data not being ready yet.
            holder.itemEventName.setText("No events available");
        }
    }



    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = eInflater.inflate(R.layout.card_layout, parent, false);
        return new EventViewHolder(itemView);
    }



    @Override
    public int getItemCount() {
        if (allEvents != null)
            return allEvents.size();
        else return 0;
    }


    }




