package com.agoda.hotel.api.services;

import com.agoda.hotel.model.Hotel;
import com.agoda.hotel.model.Ordering;
import com.agoda.hotel.services.IHotelServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by sgupt13 on 24/12/16.
 */


@RestController
public class HotelController {

    @Autowired
    private IHotelServices hotelServices;

    @RequestMapping("/city/{cityId}")
    public List<Hotel> getHotelsByCity(@PathVariable(value = "cityId") String cityId, @RequestParam(value = "order", defaultValue = "NONE") String ordering) {
        ordering = ordering.toUpperCase();
        Ordering order = null;
        try {
            order = Ordering.valueOf(ordering);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            order = Ordering.NONE;
        }
        return hotelServices.getAllHotelsForGivenCity(cityId, order);
    }
}
