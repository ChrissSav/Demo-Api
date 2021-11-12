package com.example.demo.mapper;

import com.example.demo.dto.movie.MovieBaseResponse;
import com.example.demo.dto.movie.MovieDetailsResponse;
import com.example.demo.dto.user.UserResponse;
import com.example.demo.model.Movie;
import com.example.demo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Mapper(componentModel = "spring")
public abstract class MovieMapper {

    public abstract  MovieBaseResponse mapToMovieBaseResponse(Movie movie);

    @Mapping(target = "categories", expression = "java(getCategories(movie))")
    public abstract MovieDetailsResponse mapToMovieDetailsResponse(Movie movie);

    ArrayList<String> getCategories(Movie movie){
        ArrayList<String> categories = new ArrayList<>();
        if (movie == null)
            return categories;
        movie.getCategories().forEach((category -> categories.add(category.getName())));
        return categories;
    }
}