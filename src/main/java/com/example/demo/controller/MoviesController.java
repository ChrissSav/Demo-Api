package com.example.demo.controller;


import com.example.demo.dto.BaseResponse;
import com.example.demo.dto.movie.MovieBaseResponse;
import com.example.demo.dto.movie.MovieDetailsResponse;
import com.example.demo.service.MoviesService;
import com.example.demo.util.Util;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/movies")
public class MoviesController {

    private final MoviesService moviesService;

    @GetMapping("")
    public BaseResponse<List<MovieBaseResponse>> getAllMovies(
            @RequestParam(name = "title", required = false , defaultValue = "") String title, @RequestParam(name = "category", required = false ) List<String> categories,
            @RequestParam(name = "min_date", required = false) Integer startTimestamp, @RequestParam(name = "max_date", required = false ) Integer endTimestamp
    ) {
        return Util.generateSuccessResponse(moviesService.getAllMovies(title,categories,startTimestamp,endTimestamp));

    }

    @GetMapping("/{movie_id}")
    public BaseResponse<MovieDetailsResponse> getMovieById(@Valid @NotEmpty @PathVariable("movie_id") String movieId) {
        return Util.generateSuccessResponse(moviesService.getMovieById(movieId));
    }
}
