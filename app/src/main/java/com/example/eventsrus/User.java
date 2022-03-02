package com.example.eventsrus;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity( tableName = "users")
public class User {
    @ColumnInfo(name = "forename")
    private String forename;
    @ColumnInfo(name = "surname")
    private String surname;
    @ColumnInfo(name = "email")
    private String email;
    @ColumnInfo(name="username")
    private String username;
    @ColumnInfo(name="password")
    private String password;

    public User(@NonNull String forename,String surname,String email,String password,String username){
        this.forename=forename;
        this.surname=surname;
        this.email=email;
        this.password=password;
        this.username=username;
    }



}
