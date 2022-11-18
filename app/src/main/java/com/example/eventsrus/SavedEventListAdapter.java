package com.example.eventsrus;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SavedEventListAdapter extends RecyclerView.Adapter<SavedEventListAdapter.EventViewHolder> {

    private Context context;
    private LayoutInflater eInflater;
    private List<Event> allSavedEvents;
    private List<Event> alleventsfull;
    private RecyclerViewInterface recyclerViewinterface;

    public SavedEventListAdapter(Context context, RecyclerViewInterface recyclerViewInterface) {
        eInflater = LayoutInflater.from(context);
        this.context = context;
        this.recyclerViewinterface = recyclerViewInterface;

    }

    void setEvents(List<Event> events) {
        allSavedEvents = events;
        alleventsfull= new ArrayList<>(events);
        notifyDataSetChanged();
    }

    public Event getEventAt(int position){
        return allSavedEvents.get(position);
    }





    public static class EventViewHolder extends RecyclerView.ViewHolder {
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

        public EventViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            this.itemEventName = itemView.findViewById(R.id.eventName);
            this.imgEvent = itemView.findViewById(R.id.imgEvent);
            this.itemPlace = itemView.findViewById(R.id.eventPlace);
            this.itemDate = itemView.findViewById(R.id.eventDate);
            this.City = itemView.findViewById(R.id.eventCity);
            this.itemTime = itemView.findViewById(R.id.eventTime);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Navigation.findNavController(v).navigate(R.id.action_discoveryFragment_to_expandedEventCard);
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onitemClick(pos);
                        }

                    }
                }

            });


        }

    }


    @Override
    public void onBindViewHolder(@NonNull SavedEventListAdapter.EventViewHolder holder, int position) {
        Log.d("size of allSavedEvents","the size is"+allSavedEvents.size());
        if(!allSavedEvents.isEmpty()) {
            Event current = allSavedEvents.get(position);
             if (current==null){
                 Log.d("what is going on","iiiiii");
             }else{
            Log.d("Position", "the position is" + position);
                 Log.d("name", "the name is" + current.getName());
            String imgPath = current.getImgDesc();
            holder.itemTime.setText(current.getTime());
            String eventDescription = current.getDescription();
            holder.City.setText(current.getCity());
            holder.itemEventName.setText(current.getName());
            holder.itemDescription = current.getDescription();
            holder.itemAddress = current.getAddress();
            holder.itemPostcode = current.getPostCode();
            holder.itemid = current.getId();
            holder.itemPlace.setText(current.getPlace());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.ENGLISH);
            try {
                Date myDate = sdf.parse(current.getDate());
                sdf.applyPattern("EEE, d MMM yy");
                String eventDate = sdf.format(myDate);
                holder.itemDate.setText(eventDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
           int drawableResourceId = context.getResources().getIdentifier(imgPath, "drawable", context.getPackageName());
           holder.imgEvent.setImageResource(drawableResourceId);


        }
        }
}







    @NonNull
    @Override
    public SavedEventListAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = eInflater.inflate(R.layout.card_layout, parent, false);
        return new SavedEventListAdapter.EventViewHolder(itemView, recyclerViewinterface);
    }


    @Override
    public int getItemCount() {
        if (allSavedEvents != null)
            return allSavedEvents.size();
        else return 0;
    }


}


