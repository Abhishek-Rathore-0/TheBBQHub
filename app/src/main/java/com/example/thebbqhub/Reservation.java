package com.example.thebbqhub;

import java.util.Date;

public class Reservation {
    public String no_of_guest;
    public String date;
    public String Name,Email,Mobile_no,Time_slot;
    Reservation(){}
    Reservation(String Name,String Email,String Mobile_no,String no_of_guest,String Time_slot,String date){
        this.Name=Name;
        this.Email=Email;
        this.Mobile_no=Mobile_no;
        this.no_of_guest=no_of_guest;
        this.Time_slot=Time_slot;
        this.date=date;
    }
}
