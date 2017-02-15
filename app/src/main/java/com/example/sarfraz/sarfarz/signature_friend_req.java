package com.example.sarfraz.sarfarz;

/**
 * Created by adnan on 2/15/2017.
 */

public class signature_friend_req {
String name;
    String id;
    String picurl;

    public signature_friend_req() {
    }

    public signature_friend_req(String name, String id, String picurl) {

        this.name = name;
        this.id = id;
        this.picurl = picurl;
    }

    public String getName() {

        return name;
    }

    public String getId() {
        return id;
    }

    public String getPicurl() {
        return picurl;
    }
}
