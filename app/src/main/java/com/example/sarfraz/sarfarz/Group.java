package com.example.sarfraz.sarfarz;

/**
 * Created by Sarfraz on 2/14/2017.
 */

public class Group  {
String admin;
    String name;
    String picurl;


    public Group(String admin, String name, String picurl) {
        this.admin = admin;
        this.name = name;
        this.picurl = picurl;
    }

    public Group() {
    }

    public String getAdmin() {
        return admin;
    }

    public String getName() {
        return name;
    }

    public String getPicurl() {
        return picurl;
    }
}

