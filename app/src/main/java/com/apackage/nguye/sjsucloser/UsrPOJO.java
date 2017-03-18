package com.apackage.nguye.sjsucloser;

/**
 * Created by Jack on 02/24/17.
 * Plain Old Java Object (POJO) used to store a usr
 */


public class UsrPOJO {

    private String id;
    private String firstName;
    private String lastName;
    private String account;
    private String password;

    /*Blank default constructor essential for Firebase*/
    public UsrPOJO() {

    }

    public UsrPOJO(String id, String firstName, String lastName, String account, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.account = account;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
