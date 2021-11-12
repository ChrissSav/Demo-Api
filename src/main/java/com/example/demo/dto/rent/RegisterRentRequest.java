package com.example.demo.dto.rent;

import com.example.demo.validation.StringLength;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRentRequest {

    @StringLength(min = 4)
    private String movieId;

}
