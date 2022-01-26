package com.sohel.themelookassignment.model;

public class UserLocation {
    String fullName,email,phone,division,district,village;

    public UserLocation(){}
    public UserLocation(String fullName, String email, String phone, String division, String district, String village) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.division = division;
        this.district = district;
        this.village = village;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }
}
