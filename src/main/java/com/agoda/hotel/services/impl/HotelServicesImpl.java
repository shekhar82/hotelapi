package com.agoda.hotel.services.impl;

import com.agoda.hotel.db.repository.HotelDBRepository;
import com.agoda.hotel.model.Hotel;
import com.agoda.hotel.model.Ordering;
import com.agoda.hotel.services.IHotelServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by sgupt13 on 24/12/16.
 */

@Service("hotelService")
public class HotelServicesImpl implements IHotelServices {

    @Autowired
    private HotelDBRepository hotelDBRepository;

    @Override
    public List<Hotel> getAllHotelsForGivenCity(String cityId, Ordering ordering) {
        List<Hotel> hotels = hotelDBRepository.getHotelsByCityId(cityId);

        switch (ordering)
        {
            case ASC:
                Collections.sort(hotels, (h1, h2) ->  h1.getPrice() > h2.getPrice()?1:-1);
                break;
            case DESC:
                Collections.sort(hotels, (h1, h2) ->  h1.getPrice() > h2.getPrice()?-1:1);
                break;
        }

        return hotels;
    }
}
