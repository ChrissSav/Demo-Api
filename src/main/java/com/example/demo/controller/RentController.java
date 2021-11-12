package com.example.demo.controller;


import com.example.demo.dto.BaseResponse;
import com.example.demo.dto.rent.PayRentRequest;
import com.example.demo.dto.rent.RegisterRentRequest;
import com.example.demo.dto.rent.RentResponse;
import com.example.demo.model.Rent;
import com.example.demo.service.RentService;
import com.example.demo.util.Util;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/rents")
public class RentController {

    private final RentService rentService;


    @GetMapping("")
    public BaseResponse<List<RentResponse>> getAllRents(@RequestParam(value = "closed", required = false) Boolean closed) {
        return Util.generateSuccessResponse(rentService.getAllRents(closed));

    }

    @GetMapping("/{rent_id}")
    public BaseResponse<RentResponse> getRentById(@Valid @NotEmpty @PathVariable("rent_id") String rentId) {
        return Util.generateSuccessResponse(rentService.getRentById(rentId));

    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<String> rentMovie(@Valid @RequestBody RegisterRentRequest registerRent) {
        rentService.rentMovie(registerRent.getMovieId());
        return Util.generateSuccessResponse("success");

    }

    @PutMapping("")
    public BaseResponse<RentResponse> returnTheMovie(@Valid @RequestBody PayRentRequest payRentRequest) {

        return Util.generateSuccessResponse(rentService.payRent(payRentRequest.getRentId()));

    }

}
