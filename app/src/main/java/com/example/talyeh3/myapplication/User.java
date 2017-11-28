package com.example.talyeh3.myapplication;

import java.util.List;

/**
 * Created by talyeh3 on 16/11/2017.
 */

public class User {
    public String key;
    public String uid;
    public String userName;
    public String email;
    public int age;
    List<String> teams;

    public User()
    {
        //empty constructor becouse firebase needs
    }

    public User(String uid, String userName, String email, int age, String key,List<String> teams) {
        this.key = key;
        this.teams=teams;
        this.uid = uid;
        this.userName = userName;
        this.email = email;
        this.age = age;

    }
}
