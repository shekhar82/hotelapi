package com.agoda.hotel.db.repository;

import com.agoda.hotel.model.Hotel;
import com.csvreader.CsvReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sgupt13 on 24/12/16.
 */

@Repository("hotelDBRepository")
public class HotelDBRepository implements InitializingBean {

    private static final String CITY_COLUMN = "CITY";
    private static final String HOTEL_COLUMN = "HOTELID";
    private static final String ROOM_COLUMN = "ROOM";
    private static final String PRICE_COLUMN = "PRICE";

    private List<Hotel> hotelList = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        InputStream is = null;
        try {
            is = getClass().getClassLoader().getResourceAsStream("data/hoteldb.csv");
            if (is != null)
            {
                CsvReader hotels = new CsvReader(is, Charset.defaultCharset());
                hotels.readHeaders();

                while(hotels.readRecord())
                {
                    Hotel hotel = new Hotel();
                    hotel.withCity(hotels.get(CITY_COLUMN)).withHotelId(Integer.parseInt(hotels.get(HOTEL_COLUMN)))
                            .withRoom(hotels.get(ROOM_COLUMN)).withPrice(Float.parseFloat(hotels.get(PRICE_COLUMN)));

                    hotelList.add(hotel);
                }
            }
            else
                throw new Exception("Couldn't parse CSV file");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public List<Hotel> getHotelsByCityId(String cityId)
    {
        List<Hotel> cityHotelList = new ArrayList<>();
        for (Hotel hotel: hotelList)
        {
            if (cityId.equalsIgnoreCase(hotel.getCity()))
                cityHotelList.add(hotel);
        }

        return cityHotelList;
    }
}
