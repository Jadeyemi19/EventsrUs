package com.example.eventsrus;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName ="event_table")

public class Event {
   @PrimaryKey(autoGenerate = true)
   @ColumnInfo(name="id")
   private int id;
   @ColumnInfo(name="name")
    private String name;
   @ColumnInfo(name = "place")
   private String place;
   @ColumnInfo(name="city")
   private String city;
    @ColumnInfo(name="address")
    private String address;
    @ColumnInfo(name="postcode")
    private String postCode;
    @ColumnInfo(name="imgDesc")
    private String imgDesc;
    @ColumnInfo(name="date")
    private String date;
    @ColumnInfo(name="time")
    private String time;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "type")
    private String type;

    @Ignore
    public Event ( String name, String address,String postCode,String imgDesc, String date,String time,String city,String description,String place,String type){
    this.name=name;
    this.address=address;
    this.postCode=postCode;
    this.imgDesc=imgDesc;
    this.date=date;
    this.time=time;
    this.city=city;
    this.description=description;
    this.place=place;
    this.type=type;


    }



    public Event (int id, String name, String address,String postCode,String imgDesc, String date,String time,String city,String description,String place,String type){
        this.name=name;
        this.address=address;
        this.postCode=postCode;
        this.imgDesc=imgDesc;
        this.date=date;
        this.time=time;
        this.city=city;
        this.description=description;
        this.place=place;
        this.type=type;
        this.id=id;


    }

    public String getName(){
        return this.name;
    }
    public String getCity(){
        return this.city;
    }
    public String getAddress(){
        return this.address;
    }
    public String getPostCode(){
        return this.postCode;
    }
    public String getImgDesc(){
        return this.imgDesc;
    }
    public String getDate(){
        return this.date;
    }
    public String getTime(){
        return this.time;
    }
    public String getDescription(){
        return this.description;
    }
    public String getPlace(){
        return this.place;
    }
    public int getId(){return this.id;}

    public String getType() {
        return type;
    }
}


