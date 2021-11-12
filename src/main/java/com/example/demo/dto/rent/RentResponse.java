package com.example.demo.dto.rent;

import com.example.demo.dto.movie.MovieDetailsResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentResponse {

    private String id;
    private Long startTimestamp;
    private Long endTimestamp;
    private MovieDetailsResponse movie;
    private Float charge;
    private boolean closed;

}
