package com.example.demo.model;


import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.FetchType.LAZY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Rent {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(fetch = LAZY)
    private User user;
    @JoinColumn(name = "movie_id", referencedColumnName = "id")
    @ManyToOne(fetch = LAZY)
    private Movie movie;
    private Long startTimestamp;
    private Long endTimestamp;
    private Float charge;
    private boolean closed;
}
