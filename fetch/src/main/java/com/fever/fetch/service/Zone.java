package com.fever.fetch.service;

import jakarta.xml.bind.annotation.XmlAttribute;

public class Zone {

    private String id;
    private Double price;

    private String name;


    public String getId() {
        return id;
    }
    @XmlAttribute(name = "zone_id")
    public void setId(String id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    @XmlAttribute(name = "price")
    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    @XmlAttribute(name = "name")
    public void setName(String name) {
        this.name = name;
    }

}
