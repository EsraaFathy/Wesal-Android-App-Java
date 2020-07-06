package com.example.wesal;

public class GetIdModel {
    String name;
    String phone;
    String mail;
    String message;

    public GetIdModel(String name, String phone, String mail, String message) {
        this.name = name;
        this.phone = phone;
        this.mail = mail;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getMail() {
        return mail;
    }

    public String getMessage() {
        return message;
    }
}

