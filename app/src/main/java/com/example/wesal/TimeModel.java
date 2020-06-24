package com.example.wesal;

 class TimeModel {
    private int image;
    private String title;
    private String content;
    private String time;

     TimeModel(int image, String title, String content, String time) {
        this.image = image;
        this.title = title;
        this.content = content;
        this.time = time;
    }

     int getImage() {
        return image;
    }

     String getTitle() {
        return title;
    }

     String getContent() {
        return content;
    }

     String getTime() {
        return time;
    }
}

