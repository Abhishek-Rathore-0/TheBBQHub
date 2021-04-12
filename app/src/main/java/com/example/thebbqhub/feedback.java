package com.example.thebbqhub;


public class feedback {
    public Float Food,service,Dining;
    public String comment,Name,Email;

    feedback(){}
    feedback(Float Food,Float service,Float Dining,String comment,String Name,String Email){
        this.Food = Food;
        this.service = service;
        this.Dining = Dining;
        this.comment=comment;
        this.Name=Name;
        this.Email=Email;
    }
}

