package com.sohel.themelookassignment.model;

public class User {
    public static  final String SIMPLE_USER="User";
    public  static  final String SELLER_USER="Seller";
    private String userId,name,email,password,phone,shopName,userType;
    private  int balance=0;
    private UserLocation location;

    public User(){}
    public User(String userId, String name, String email, String password, String phone, int balance,String userType,UserLocation location) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.balance = balance;
        this.userType=userType;
        this.location=location;
    }

    public User(String userId, String name, String email, String password, String phone, int balance,String userType, String shopName,UserLocation location) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.shopName = shopName;
        this.balance = balance;
        this.userType=userType;
        this.location=location;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public UserLocation getLocation() {
        return location;
    }

    public void setLocation(UserLocation location) {
        this.location = location;
    }
}
