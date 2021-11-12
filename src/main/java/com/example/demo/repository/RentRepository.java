package com.example.demo.repository;

import com.example.demo.model.Rent;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mapping.callback.ReactiveEntityCallbacks;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentRepository extends JpaRepository<Rent, String> {


    @Query("Select r from Rent r where r.movie.id = :movieId and r.closed = :closed  and r.user.id = :userId")
    Optional<Rent> findRentByMovieIdAndClosed(@Param("movieId") String movieId, @Param("closed") boolean closed, @Param("userId") String userId);

    List<Rent> findRentByUserId(String userId);

    List<Rent> findRentByClosedAndUserId(boolean closed, String userId);

    Optional<Rent> findRentByIdAndClosedAndUserId(String rentId, boolean closed, String userId);

}

//closed