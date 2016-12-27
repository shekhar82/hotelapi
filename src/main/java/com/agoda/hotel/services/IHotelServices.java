package com.agoda.hotel.services;

import com.agoda.hotel.model.Hotel;
import com.agoda.hotel.model.Ordering;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
 * Interface that defined in methods to perform actions
 * on hotel DB
 * Created by sgupt13 on 24/12/16.
 */
public interface IHotelServices {

    /**
     * This method would return List of hotels
     * that are defined in given city
     *
     * @param cityId
     * @param ordering whether to show result in asc, desc or none
     * @return List<Hotel> back to caller
     */
    public List<Hotel> getAllHotelsForGivenCity(String cityId, Ordering ordering);
}
