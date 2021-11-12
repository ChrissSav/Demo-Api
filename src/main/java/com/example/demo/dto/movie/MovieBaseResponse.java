package com.example.demo.dto.movie;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieBaseResponse {
    private String id;
    private String title;
    private String picture;
}
