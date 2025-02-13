package com.tzuchaedahy.domain;

public class Client {
    private String cpf;
    private String name;
    private String address;
    private String phone;

    public Client(String cpf, String name, String address, String phone) {
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF inválido");
        }

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Nome inválido");
        }

        if (address == null || address.isEmpty()) {
            throw new IllegalArgumentException("Endereço inválido");
        }

        if (phone == null || phone.isEmpty()) {
            throw new IllegalArgumentException("Telefone inválido");
        }

        this.cpf = cpf;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
