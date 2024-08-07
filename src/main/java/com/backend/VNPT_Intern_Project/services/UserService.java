package com.backend.VNPT_Intern_Project.services;

import com.backend.VNPT_Intern_Project.dtos.role.RoleDTOResponse;
import com.backend.VNPT_Intern_Project.dtos.user.request.CreateUserDTORequest;
import com.backend.VNPT_Intern_Project.dtos.user.request.UpdateUserDTORequest;
import com.backend.VNPT_Intern_Project.dtos.user.request.UserAddressDTORequest;
import com.backend.VNPT_Intern_Project.dtos.user.response.UserAddressDTOResponse;
import com.backend.VNPT_Intern_Project.dtos.user.response.UserDTOResponse;
import com.backend.VNPT_Intern_Project.entities.*;
import com.backend.VNPT_Intern_Project.exception.ConflictException;
import com.backend.VNPT_Intern_Project.exception.ResourceNotFoundException;
import com.backend.VNPT_Intern_Project.repositories.PermissionRepository;
import com.backend.VNPT_Intern_Project.repositories.RoleRepository;
import com.backend.VNPT_Intern_Project.repositories.UserAddressRepository;
import com.backend.VNPT_Intern_Project.repositories.UserRepository;
import com.backend.VNPT_Intern_Project.services.interfaces.IUserInterface;
import com.backend.VNPT_Intern_Project.utils.RoleConstants;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService implements IUserInterface {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAddressRepository userAddressRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostAuthorize("returnObject.getEmail() == authentication.name")
    @Override
    public UserDTOResponse getUserById(String uuidUser) {
        User user = userRepository.findById(uuidUser)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + uuidUser));

        Set<Role> roleSet = roleRepository.findRolesByUserUuid(uuidUser);
        user.setRoleSet(roleSet);

        return convertToDTO(user);
    }

    @Override
    @PreAuthorize("hasAuthority('GET_ALL_USERS')")
    public List<UserDTOResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> {
                    Set<Role> roleSet = roleRepository.findRolesByUserUuid(user.getUuidUser());
                    user.setRoleSet(roleSet);
                    return convertToDTO(user);
                })
                .collect(Collectors.toList());
    }

    // Lay ra thong tin cua nguoi dung dang dang nhap
    @Override
    public UserDTOResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();


        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

        Set<Role> roleSet = roleRepository.findRolesByEmail(email);
        user.setRoleSet(roleSet);

        return convertToDTO(user);
    }

    @Transactional
    @Override
    public UserDTOResponse createUser(CreateUserDTORequest userDTORequest) {
        userRepository.findByEmail(userDTORequest.getEmail())
                .ifPresent(user -> {
                    throw new ConflictException("User with email " + userDTORequest.getEmail() + " already exists");
                });

        User user = new User();

        user.setEmail(Objects.requireNonNull(userDTORequest.getEmail(), "Email cannot be null"));
        user.setPassword(Objects.requireNonNull(passwordEncoder.encode(userDTORequest.getPassword()), "Password cannot be null"));
        user.setMobile(userDTORequest.getPhone());
        user.setFirstName(userDTORequest.getFirstName());
        user.setMiddleName(userDTORequest.getMiddleName());
        user.setLastName(userDTORequest.getLastName());
        user.setDescription(userDTORequest.getDescription());

        // Gán role mặc định cho người dùng là USER
        Role userRole = roleRepository.findByName(RoleConstants.USER.toString())
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(RoleConstants.USER.toString());
                    return roleRepository.save(newRole);
                });

        user.setRoleSet(new HashSet<>(Collections.singletonList(userRole)));

        userRepository.save(user);

        return convertToDTO(user);
    }

    @Override
    @Transactional
    @PostAuthorize("returnObject.getEmail() == authentication.name")
    public UserDTOResponse updateUser(String uuidUser, UpdateUserDTORequest request) {
        User user = userRepository.findById(uuidUser)
                .orElseThrow(() -> new ResourceNotFoundException("User is not exist"));

        if (request.getPhone() != null) user.setMobile(request.getPhone());
        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getMiddleName() != null) user.setMiddleName(request.getMiddleName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getDescription() != null) user.setDescription(request.getDescription());
        if (request.getAvatar() != null) user.setAvatar(request.getAvatar());

        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

//    public UserDTOResponse updateRole(String uuidUser, List<String> roles) {
//        User user = userRepository.findById(uuidUser)
//                .orElseThrow(() -> new ResourceNotFoundException("User is not exist"));
//
//        var roles = roleRepository.findAllByName(roles);
//
//    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('DELETE_USER')")
    public UserDTOResponse deleteUser(String uuidUser) {
        User user = userRepository.findById(uuidUser)
                .orElseThrow(() -> new ResourceNotFoundException("User is not exist"));

        userRepository.delete(user);
        return convertToDTO(user);
    }

    @Transactional
    public UserAddressDTOResponse addAddress(String uuidUser, UserAddressDTORequest request) {
        User user = userRepository.findById(uuidUser)
                .orElseThrow(() -> new ResourceNotFoundException("User is not exist"));

        UserAddress address = new UserAddress();
        address.setStreet(request.getStreet());
        address.setDistrict(request.getDistrict());
        address.setCity(request.getCity());
        address.setPostalCode(request.getPostalCode());
        address.setMobile(request.getMobile());
        address.setUser(user);

        userAddressRepository.save(address);
        return convertAddressToDTOResponse(address);
    }

    private UserAddressDTOResponse convertAddressToDTOResponse(UserAddress userAddress) {
        UserAddressDTOResponse addressDTO = new UserAddressDTOResponse();

        addressDTO.setUuidUser(userAddress.getUser().getUuidUser());
        addressDTO.setUuidAddress(userAddress.getUuidAddress());
        addressDTO.setStreet(userAddress.getStreet());
        addressDTO.setDistrict(userAddress.getDistrict());
        addressDTO.setCity(userAddress.getCity());
        addressDTO.setPostalCode(userAddress.getPostalCode());
        addressDTO.setMobile(userAddress.getMobile());

        return addressDTO;
    }

    @Transactional
    private UserDTOResponse convertToDTO(User user) {
        UserDTOResponse userDTO = new UserDTOResponse();
        userDTO.setUuidUser(user.getUuidUser());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setMiddleName(user.getMiddleName());
        userDTO.setLastName(user.getLastName());
        userDTO.setMobile(user.getMobile());
        userDTO.setDescription(user.getDescription());
        userDTO.setRegisterDate(user.getRegisterDate());
        userDTO.setLastLogin(user.getLastLogin());
        userDTO.setActivate(user.getActivate());

        if (user.getAddressList() != null) {
            List<UserDTOResponse.UserAddressResponse> addressList = user.getAddressList().stream()
                    .map(address -> new UserDTOResponse.UserAddressResponse(
                            address.getUuidAddress(),
                            address.getMobile(),
                            address.getStreet(),
                            address.getDistrict(),
                            address.getCity(),
                            address.getPostalCode()
                    ))
                    .collect(Collectors.toList());
            userDTO.setAddressList(addressList);
        }

        if (user.getRoleSet() != null && !user.getRoleSet().isEmpty()) {
            userDTO.setRoles(user.getRoleSet().stream()
                    .map(role -> {
                        RoleDTOResponse roleDTOResponse = new RoleDTOResponse();
                        roleDTOResponse.setUuidRole(role.getUuidRole());
                        roleDTOResponse.setName(role.getName());

                        Set<Permission> permissionSet = permissionRepository.findPermissionsByRoleName(role.getName());
                        roleDTOResponse.setPermissionSet(permissionSet
                                .stream()
                                .map(Permission::getName)
                                .collect(Collectors.toSet()));
                        return roleDTOResponse;
                    })
                    .collect(Collectors.toSet()));
        }

        return userDTO;
    }

}

