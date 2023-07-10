package com.example.bijli;

public class User {

    public String consumerNo;
    public String userName;
    public String userPassword;
    public String phoneNo;
    public String email;
    public String address;

    public User() {             //same name of constructor as class

    }

    public User(String address, String userName, String password, String mobileNo, String email, String consumerNo) {
        this.userName = userName;
        this.userPassword = password;
        this.phoneNo = mobileNo;
        this.email = email;
        this.consumerNo = consumerNo;
        this.address = address;
    }

}
