package com.example.demo.mapper;

import com.example.demo.dto.user.UserResponse;
import com.example.demo.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse mapToUserResponse(User user);

}