package com.sohel.themelookassignment.model;

public class Order {

    public static final String ORDER_STEP_1="pending";
    public static final String ORDER_STEP_2="accepted";
    public static final String ORDER_STEP_3="delivered";
    public static final String ORDER_STEP_4="complete";

    String orderId,userId,shopId,productId,orderNo;
    double totalAmount;
    int totalProduct;
    String userName,userPhone;
    String date;
    String state=ORDER_STEP_1;

    public Order(){}

    public Order(String orderId, String userId, String shopId, String productId, String orderNo, double totalAmount, int totalProduct, String userName, String userPhone, String date,String state) {
        this.orderId = orderId;
        this.userId = userId;
        this.shopId = shopId;
        this.productId = productId;
        this.orderNo = orderNo;
        this.totalAmount = totalAmount;
        this.totalProduct = totalProduct;
        this.userName = userName;
        this.userPhone = userPhone;
        this.date = date;
        this.state=state;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(int totalProduct) {
        this.totalProduct = totalProduct;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
