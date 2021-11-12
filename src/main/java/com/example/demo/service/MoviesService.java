package com.example.demo.service;


import com.example.demo.dto.movie.MovieBaseResponse;
import com.example.demo.dto.movie.MovieDetailsResponse;
import com.example.demo.exception.ConflictException;
import com.example.demo.mapper.MovieMapper;
import com.example.demo.model.Movie;
import com.example.demo.repository.MoviesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class MoviesService {

    private final MoviesRepository moviesRepository;
    private final MovieMapper movieMapper;

    public List<MovieBaseResponse> getAllMovies(String title, List<String> categories, Integer startTimestamp, Integer endTimestamp) {
        if (categories == null)
            categories = new ArrayList<>();

        List<Movie> movies = moviesRepository.searchMovie(title, startTimestamp, endTimestamp);
        List<String> finalCategories1 = categories;
        return movies
                .stream()
                .filter(movie -> {
                    List<String> first = movie.getCategories().stream().map(category -> category.getName().toLowerCase()).collect(toList());
                    List<String> second = finalCategories1.stream().map(String::toLowerCase).collect(toList());
                    return !Collections.disjoint(first, second);
                })
                .map(movieMapper::mapToMovieBaseResponse)
                .collect(toList());

    }

    public MovieDetailsResponse getMovieById(String moveId) {
        Movie movie = moviesRepository.findById(moveId)
                .orElseThrow(() -> new ConflictException("The movie id is not valid"));
        return movieMapper.mapToMovieDetailsResponse(movie);

    }

    public Movie getMovieByID(String moveId) {
        return moviesRepository.findById(moveId)
                .orElseThrow(() -> new ConflictException("The movie id is not valid"));
    }

}










