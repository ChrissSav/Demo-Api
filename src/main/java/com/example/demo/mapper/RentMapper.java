package com.example.demo.mapper;

import com.example.demo.dto.movie.MovieBaseResponse;
import com.example.demo.dto.movie.MovieDetailsResponse;
import com.example.demo.dto.rent.RentResponse;
import com.example.demo.model.Movie;
import com.example.demo.model.Rent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static com.example.demo.service.RentService.charge;

@Mapper(componentModel = "spring")
public abstract class RentMapper {

    @Autowired
    private MovieMapper movieMapper;

    public RentResponse mapToRentResponse(Rent rent) {

        RentResponse rentResponse = new RentResponse();
        rentResponse.setId(rent.getId());
        rentResponse.setClosed(rent.isClosed());
        rentResponse.setEndTimestamp(rent.getEndTimestamp());
        rentResponse.setStartTimestamp(rent.getStartTimestamp());
        rentResponse.setCharge(rent.getCharge());
        if (!rent.isClosed())
            rentResponse.setCharge(charge(rent));
        rentResponse.setMovie(movieMapper.mapToMovieDetailsResponse(rent.getMovie()));

        return rentResponse;
    }

}