package com.example.demo.service;


import com.example.demo.dto.rent.RentResponse;
import com.example.demo.exception.ConflictException;
import com.example.demo.mapper.RentMapper;
import com.example.demo.model.Movie;
import com.example.demo.model.Rent;
import com.example.demo.repository.RentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class RentService {

    private final AuthService authService;
    private final RentRepository rentRepository;
    private final MoviesService moviesService;
    private final RentMapper rentMapper;


    public List<RentResponse> getAllRents(Boolean closed) {

        List<Rent> rents;
        if (closed == null)
            rents = rentRepository.findRentByUserId(authService.getCurrentUser().getId());
        else {
            rents = rentRepository.findRentByClosedAndUserId(closed, authService.getCurrentUser().getId());
        }

        return rents.stream()
                .map(rentMapper::mapToRentResponse)
                .collect(toList());
    }

    public RentResponse getRentById(String id) {

        Rent rent = rentRepository.findById(id)
                .orElseThrow(() -> new ConflictException("The rent id is not valid"));
        return rentMapper.mapToRentResponse(rent);

    }


    public RentResponse payRent(String id) {
        Rent rent = rentRepository.findRentByIdAndClosedAndUserId(id, false, authService.getCurrentUser().getId())
                .orElseThrow(() -> new ConflictException("The rent payment is not valid"));
        rent.setCharge(charge(rent));
        rent.setClosed(true);
        rent.setEndTimestamp(Instant.now().getEpochSecond());
        return rentMapper.mapToRentResponse(rentRepository.save(rent));
    }

    public void rentMovie(String movieId) {
        if (rentRepository.findRentByMovieIdAndClosed(movieId, false, authService.getCurrentUser().getId()).isPresent())
            throw new ConflictException("Already rent this movie");
        Movie movie = moviesService.getMovieByID(movieId);
        Rent rent = new Rent();
        rent.setUser(authService.getCurrentUser());
        rent.setStartTimestamp(Instant.now().getEpochSecond());
        rent.setMovie(movie);
        rent.setClosed(false);
        rentRepository.save(rent);
    }


    public static float charge(Rent rent) {
        final int DAY_IN_SECONDS = 86400;

        int days = (int) (Instant.now().getEpochSecond() - rent.getStartTimestamp()) / DAY_IN_SECONDS;
        if (days == 0)
            return 1;
        if (days <= 3)
            return days;
        else
            return (float) ((days - 3) * 0.5 + 3);
    }
}
