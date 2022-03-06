package com.example.eventsrus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder > {
    private Context context;
    private final LayoutInflater eInflater;
    private List<Event> allEvents;
    private List<Event> filteredEvent;




   public EventListAdapter(Context context) {
        eInflater=LayoutInflater.from(context);
        this.context=context;

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
        private TextView itemTime;
        private int itemid;




        private EventViewHolder(View itemView) {
            super(itemView);
             this.itemEventName = itemView.findViewById(R.id.eventName);
             this.imgEvent= itemView.findViewById(R.id.imgEvent);
             this.itemPlace=itemView.findViewById(R.id.eventPlace);
             this.itemDate=itemView.findViewById(R.id.eventDate);
             this.City=itemView.findViewById(R.id.eventCity);
             this.itemTime=itemView.findViewById(R.id.eventTime);




        }
    }
    void setEvents(List<Event>events){
        allEvents=events;
        notifyDataSetChanged();
    }




    @Override
    public void onBindViewHolder( EventViewHolder holder, int position) {
        if (allEvents != null) {
            Event current = allEvents.get(position);
            String imgPath=current.getImgDesc();
            holder.itemTime.setText(current.getTime());
            String eventDescription= current.getDescription();
            holder.City.setText(current.getCity());
            holder.itemEventName.setText(current.getName());
            holder.itemDescription= current.getDescription();
            holder.itemAddress= current.getAddress();
            holder.itemPostcode= current.getPostCode();
            holder.itemid=current.getId();
            SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.ENGLISH);
            try {
                Date myDate = sdf.parse(current.getDate());
                sdf.applyPattern("EEE, d MMM yy");
                String eventDate=sdf.format(myDate);
                holder.itemDate.setText(eventDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int drawableResourceId = context.getResources().getIdentifier(imgPath, "drawable", context.getPackageName());
            holder.imgEvent.setImageResource(drawableResourceId);

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




