package com.agoda.hotel.model;

import java.io.Serializable;

/**
 * POJO to store Hotel model stored in CSV structure
 * Created by sgupt13 on 24/12/16.
 */
public class Hotel implements Serializable {

    private String city;
    private int hotelId;
    private String room;
    private float price;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Hotel withCity(String city)
    {
        this.city = city;
        return this;
    }

    public Hotel withHotelId(int hotelId)
    {
        this.hotelId = hotelId;
        return this;
    }

    public Hotel withRoom(String room)
    {
        this.room = room;
        return this;
    }

    public Hotel withPrice(float price)
    {
        this.price = price;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hotel hotel = (Hotel) o;

        return hotelId == hotel.hotelId;
    }

    @Override
    public int hashCode() {
        return hotelId;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "city='" + city + '\'' +
                ", hotelId=" + hotelId +
                ", room='" + room + '\'' +
                ", price=" + price +
                '}';
    }
}
