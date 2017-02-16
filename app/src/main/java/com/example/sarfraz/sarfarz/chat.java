package com.example.sarfraz.sarfarz;

/**
 * Created by Microsoft on 2/16/2017.
 */

public class chat {
    String name;
    String picurl;
    String id;
    String message;
    String type;

    public chat(String name, String picurl, String id, String type,String message) {
        this.name = name;
        this.picurl = picurl;
        this.id = id;
        this.type = type;
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public String getPicurl() {
        return picurl;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public chat() {

    }
}
