package com.nvm.imapp.model;


public class Contact {

    private String fullName;
    private String phone;
    private String email;
    private String uid;
    private String status;
    private String password;




    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Contact() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Contact(String fullName, String phone, String email, String uid, String password) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.uid = uid;
        this.password = password;
    }

    public Contact(String fullName, String phone, String email, String uid) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.uid = uid;
        this.status="online";
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }


    @Override
    public String toString() {
        return "Contact{" +
                "fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", uid='" + uid + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
