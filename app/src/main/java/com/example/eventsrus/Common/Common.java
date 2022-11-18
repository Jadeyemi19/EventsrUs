package com.example.eventsrus.Common;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    public static String APP_ID="11ec8447e294cd13a4571885e1f1187f";
    public static Location current_location=null;
    public static String convertUnixToDate(int dt){
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:EEE MM yyyy");
        String formatted=sdf.format(date);
        return formatted;
    }

    public static String convertUnixToHour(long dt) {
        Date date=new Date(dt*1000L);
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        String formatted=sdf.format(date);
        return formatted;
    }
    }

