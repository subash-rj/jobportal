package com.example.jobportal.service;

import com.example.jobportal.dto.UserRequestDTO;
import com.example.jobportal.model.User;

public interface UserService {

    User registerUser(UserRequestDTO userRequestDTO);

}
