package com.backend.VNPT_Intern_Project.services.interfaces;

import com.backend.VNPT_Intern_Project.dtos.user.request.CreateUserDTORequest;
import com.backend.VNPT_Intern_Project.dtos.user.request.UpdateAddressRequest;
import com.backend.VNPT_Intern_Project.dtos.user.request.UpdateUserDTORequest;
import com.backend.VNPT_Intern_Project.dtos.user.request.AddNewAddressRequest;
import com.backend.VNPT_Intern_Project.dtos.user.response.UserAddressDTOResponse;
import com.backend.VNPT_Intern_Project.dtos.user.response.UserDTOResponse;

import java.util.List;

public interface IUserInterface {
    UserDTOResponse getUserById(String uuidUser);
    List<UserDTOResponse> getAllUsers();
    UserDTOResponse getMyInfo();
    UserDTOResponse createUser(CreateUserDTORequest userDTORequest);
    UserDTOResponse updateUser(String uuidUser, UpdateUserDTORequest request);
    UserDTOResponse deleteUser(String uuidUser);

    List<UserAddressDTOResponse> getAddressesByUserUuid(String uuidUser);
    UserAddressDTOResponse addAddress(String uuidUser, AddNewAddressRequest request);
    UserAddressDTOResponse updateAddress(String uuidAddress, UpdateAddressRequest request);
    UserAddressDTOResponse deleteAddress(String uuidUserAddress);
}
