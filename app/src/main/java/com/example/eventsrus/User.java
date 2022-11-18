package com.example.eventsrus;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity( tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    private int userid;
    @ColumnInfo(name = "forename")
    private String forename;
    @ColumnInfo(name = "surname")
    private String surname;
    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name="password")
    private String password;

    @Ignore
    public User(@NonNull String forename,String surname,String email,String password){
        this.forename=forename;
        this.surname=surname;
        this.email=email;
        this.password=password;
  ;
    }

    public User(int userid,String forename, String surname, String email, String password){
        this.forename=forename;
        this.surname=surname;
        this.email=email;
        this.password=password;
        this.userid=userid;
       ;
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

    public int getUserid() {
        return userid;
    }
}
