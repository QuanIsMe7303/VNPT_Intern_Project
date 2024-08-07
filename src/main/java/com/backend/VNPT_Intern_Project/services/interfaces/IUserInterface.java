package com.backend.VNPT_Intern_Project.services.interfaces;

import com.backend.VNPT_Intern_Project.dtos.user.request.CreateUserDTORequest;
import com.backend.VNPT_Intern_Project.dtos.user.request.UpdateUserDTORequest;
import com.backend.VNPT_Intern_Project.dtos.user.response.UserDTOResponse;

import java.util.List;

public interface IUserInterface {
    UserDTOResponse getUserById(String uuidUser);
    List<UserDTOResponse> getAllUsers();
    UserDTOResponse getMyInfo();
    UserDTOResponse createUser(CreateUserDTORequest userDTORequest);
    UserDTOResponse updateUser(String uuidUser, UpdateUserDTORequest request);
    UserDTOResponse deleteUser(String uuidUser);
}
