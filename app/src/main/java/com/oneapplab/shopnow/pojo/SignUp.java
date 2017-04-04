package com.oneapplab.shopnow.pojo;

/**
 * Created by haider on 13-02-2017.
 */

public class SignUp {
    public String fullName;
    private String mobileNumber;
    private String emailID;
    private String password;
    private String address;
    private String token;

    public SignUp(){

    }




    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



}
