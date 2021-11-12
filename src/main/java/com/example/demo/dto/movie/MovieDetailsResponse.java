package com.example.demo.dto.movie;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDetailsResponse extends MovieBaseResponse{
    private String description;
    private ArrayList<String> categories;
    private Integer date;
}
