package com.backend.VNPT_Intern_Project.services;

import com.backend.VNPT_Intern_Project.dtos.role.RoleDTOResponse;
import com.backend.VNPT_Intern_Project.dtos.user.request.*;
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
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @PostAuthorize("returnObject.getEmail() == authentication.name")
    @Cacheable(value = "users", key = "#uuidUser")
    public UserDTOResponse getUserById(String uuidUser) {
        User user = userRepository.findById(uuidUser)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + uuidUser));

        Set<Role> roleSet = roleRepository.findRolesByUserUuid(uuidUser);
        user.setRoleSet(roleSet);

        return convertToDTO(user);
    }

    @Override
    @PreAuthorize("hasAuthority('GET_ALL_USERS')")
    @Cacheable(value = "users")
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

        Set<Role> roleSet = roleRepository.findRolesByUserUuid(user.getUuidUser());

        UserDTOResponse userDTOResponse = convertToDTO(user);
        Set<RoleDTOResponse> roleDTOResponseList = new HashSet<>();

        roleSet.forEach(role -> {
            RoleDTOResponse roleDTOResponse = new RoleDTOResponse();
            roleDTOResponse.setUuidRole(role.getUuidRole());
            roleDTOResponse.setName(role.getName());

            Set<Permission> permissionSet = permissionRepository.findPermissionsByRoleName(role.getName());
            roleDTOResponse.setPermissionSet(permissionSet
                    .stream()
                    .map(Permission::getName)
                    .collect(Collectors.toSet()));
            roleDTOResponseList.add(roleDTOResponse);
        });

        userDTOResponse.setRoles(roleDTOResponseList);
        return userDTOResponse;
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
    @PreAuthorize("@userService.isValidUser(authentication, #uuidUser)")
    @CacheEvict(value = "users", key = "#uuidUser")
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

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTOResponse updateRole(String uuidUser, List<String> roleNames) {
        for (String roleName : roleNames) {
            if (!RoleConstants.isValidRole(roleName)) {
                throw new IllegalArgumentException("Invalid role name: " + roleName);
            }
        }

        User user = userRepository.findById(uuidUser)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

        var roles = roleRepository.findAllByName(roleNames);
        user.setRoleSet(new HashSet<>(roles));

        userRepository.save(user);

        return convertToDTO(user);
    }

    @Transactional
    @PreAuthorize("@userService.isValidUser(authentication, #uuidUser)")
    public void changePassword(String uuidUser, ChangePasswordRequest request) {
        User user = userRepository.findById(uuidUser)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('DELETE_USER')")
    @CacheEvict(value = "users", key = "#uuidUser")
    public UserDTOResponse deleteUser(String uuidUser) {
        User user = userRepository.findById(uuidUser)
                .orElseThrow(() -> new ResourceNotFoundException("User is not exist"));

        String deleteFromUserRole = "DELETE FROM user_role WHERE uuid_user = :uuidUser";
        entityManager.createNativeQuery(deleteFromUserRole)
                .setParameter("uuidUser", uuidUser)
                .executeUpdate();

        userRepository.delete(user);
        return convertToDTO(user);
    }

    // address

    @Override
    @PreAuthorize("@userService.isValidUser(authentication, #uuidUser)")
    public List<UserAddressDTOResponse> getAddressesByUserUuid(String uuidUser) {
        User user = userRepository.findById(uuidUser)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

        List<UserAddress> addresses = userAddressRepository.findByUserUuidUser(uuidUser);

        return addresses.stream()
                .map(this::convertAddressToDTOResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    @PreAuthorize("@userService.isValidUser(authentication, #uuidUser)")
    public UserAddressDTOResponse addAddress(String uuidUser, AddNewAddressRequest request) {
        User user = userRepository.findById(uuidUser)
                .orElseThrow(() -> new ResourceNotFoundException("User is not exist"));

        UserAddress address = new UserAddress();
        address.setStreet(request.getStreet());
        address.setDistrict(request.getDistrict());
        address.setCity(request.getCity());
        if (request.getPostalCode() != null) address.setPostalCode(request.getPostalCode());
        if (request.getMobile() != null) address.setMobile(request.getMobile());
        address.setUser(user);

        userAddressRepository.save(address);
        return convertAddressToDTOResponse(address);
    }

    @Override
    @Transactional
    @PreAuthorize("@userService.isValidUser(authentication, #uuidUser)")
    public UserAddressDTOResponse deleteAddress(String uuidUserAddress) {
        UserAddress userAddress = userAddressRepository.findById(uuidUserAddress)
                .orElseThrow(() -> new ResourceNotFoundException("Address is not exist"));

        userAddressRepository.delete(userAddress);

        return convertAddressToDTOResponse(userAddress);
    }

    @Override
    @Transactional
    @PreAuthorize("@userService.isValidToModifyAddress(authentication, #uuidUserAddress)")
    public UserAddressDTOResponse updateAddress(String uuidAddress, UpdateAddressRequest request) {
        UserAddress userAddress = userAddressRepository.findById(uuidAddress)
                .orElseThrow(() -> new ResourceNotFoundException("Address is not exist"));

        userAddress.setStreet(request.getStreet());
        userAddress.setDistrict(request.getDistrict());
        userAddress.setCity(request.getCity());
        if (request.getPostalCode() != null) userAddress.setPostalCode(request.getPostalCode());
        if (request.getMobile() != null) userAddress.setMobile(request.getMobile());

        userAddressRepository.save(userAddress);

        return convertAddressToDTOResponse(userAddress);
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

    // Authentication
    public boolean isValidToModifyAddress(Authentication authentication, String uuidUserAddress) {
        UserAddress userAddress = userAddressRepository.findById(uuidUserAddress)
                .orElseThrow(() -> new ResourceNotFoundException("Address is not exist"));

        return userAddress.getUser().getEmail().equals(authentication.getName());
    }

    public boolean isValidUser(Authentication authentication, String uuidUser) {
        User user = userRepository.findById(uuidUser)
                .orElseThrow(() -> new ResourceNotFoundException("User is not exist"));

        return user.getEmail().equals(authentication.getName());
    }

}

