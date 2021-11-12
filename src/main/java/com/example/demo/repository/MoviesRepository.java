package com.example.demo.repository;

import com.example.demo.model.Movie;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MoviesRepository extends JpaRepository<Movie, String> {


    @Query("Select m from Movie m where m.title like %:title% and  (:timestampMin is null or m.date >= :timestampMin)  and (:timestampMax is null or m.date <= :timestampMax) ")
    List<Movie> searchMovie(@Param("title") String title, @Param("timestampMin") Integer startDate, @Param("timestampMax") Integer endDate);
}
