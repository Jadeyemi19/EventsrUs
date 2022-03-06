package com.example.eventsrus;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity( tableName = "users")
public class User {
    @PrimaryKey
    @ColumnInfo
    private int Userid;
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

    @Ignore
    public User(@NonNull String forename,String surname,String email,String password,String username){
        this.forename=forename;
        this.surname=surname;
        this.email=email;
        this.password=password;
        this.username=username;
    }

    public User( int Userid,String forename,String surname,String email,String password,String username){
        this.forename=forename;
        this.surname=surname;
        this.email=email;
        this.password=password;
        this.username=username;
    }

    public String getEmail() {
        return email;
    }

    public String getForename() {
        return forename;
    }

    public String getPassword() {
        return password;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public int getUserid() {
        return Userid;
    }
}
