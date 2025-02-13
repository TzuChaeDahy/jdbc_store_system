package com.tzuchaedahy.domain;

public class Order {
    private Integer id;
    private String clientCpf;
    private String employeeCpf;
    private Double totalPrice;

    public Order(Integer id, String clientCpf, String employeeCpf, Double totalPrice) {
        this.id = id;
        this.clientCpf = clientCpf;
        this.employeeCpf = employeeCpf;
        this.totalPrice = totalPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientCpf() {
        return clientCpf;
    }

    public void setClientCpf(String clientCpf) {
        this.clientCpf = clientCpf;
    }

    public String getEmployeeCpf() {
        return employeeCpf;
    }

    public void setEmployeeCpf(String employeeCpf) {
        this.employeeCpf = employeeCpf;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

}
