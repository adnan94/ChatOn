package com.example.sarfraz.sarfarz;

/**
 * Created by Adnan Ahmed on 2/10/2017.
 */

public class user {
    String name;
    String picurl;
    String email;
    String contact;
    String birthday;
    String status;

    public user(String name, String picurl, String email, String contact, String birthday, String status) {
        this.name = name;
        this.picurl = picurl;
        this.email = email;
        this.contact = contact;
        this.birthday = birthday;
        this.status = status;
    }

    public user() {
    }

    public String getName() {
        return name;
    }

    public String getPicurl() {
        return picurl;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getStatus() {
        return status;
    }
}
