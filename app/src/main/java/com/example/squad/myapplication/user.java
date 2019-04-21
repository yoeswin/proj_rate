package com.example.squad.myapplication;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class user {
    public String Name;
    public String Comment;

    public user() {
        // Default constructor required for calls to DataSnapshot.getValue(Like.class)
    }

    public user(String Name, String Comment) {
        this.Name = Name;
        this.Comment = Comment;
    }


}
