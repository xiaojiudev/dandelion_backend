package com.dandelion.backend.service;

import com.dandelion.backend.entities.LocalUser;
import com.dandelion.backend.entities.Role;
import com.dandelion.backend.entities.enumType.RoleBase;
import com.dandelion.backend.exceptions.UserAlreadyExistsException;
import com.dandelion.backend.exceptions.UserNotFoundException;
import com.dandelion.backend.payloads.LoginBody;
import com.dandelion.backend.payloads.RegistrationBody;
import com.dandelion.backend.payloads.UserDTO;
import com.dandelion.backend.payloads.UserDetailDTO;
import com.dandelion.backend.repositories.LocalUserDAO;
import com.dandelion.backend.repositories.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    @Autowired
    private LocalUserDAO localUserDAO;
    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private RoleDAO roleDAO;

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {

        String emailReg = registrationBody.getEmail();
        String phoneReg = registrationBody.getPhone();
        String fullNameReg = registrationBody.getFullName();
        String passwordReg = encryptionService.encryptPassword(registrationBody.getPassword());

        if (localUserDAO.findByEmailIgnoreCase(emailReg).isPresent() || localUserDAO.findByPhoneIgnoreCase(phoneReg).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        Optional<Role> userRole = roleDAO.findByRoleName(RoleBase.CUSTOMER);

        System.out.println(userRole);

        LocalUser user = LocalUser.builder()
                .email(emailReg)
                .phone(phoneReg)
                .roles(List.of(userRole.get()))
                .fullName(fullNameReg)
                .password(passwordReg)
                .build();


        //        TODO: Encrypt Passwords!!

        return localUserDAO.save(user);
    }

    public String loginUser(LoginBody loginBody) {
        Optional<LocalUser> opUser = localUserDAO.findByEmailIgnoreCase(loginBody.getEmail());

        if (opUser.isPresent()) {
            LocalUser user = opUser.get();
            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
                //TODO: Login success then create JWT token
                return jwtService.generateJWT(user);
            }
        }

        return null;
    }

    // TODO: Method to get all customers - only seller or admin can get all customers - check validity of seller token
    public List<UserDTO> getAllUsers() throws UserNotFoundException {
        List<LocalUser> users = localUserDAO.findAll();

        if (users.size() == 0) {
            throw new UserNotFoundException("No record exists");
        }

        List<UserDTO> userDTOs = users.stream().map(UserDTO::new).collect(Collectors.toList());

        return userDTOs;
    }

    // TODO: Method to get a customer by id
    public UserDetailDTO getUserDetails(Long userId) throws UserNotFoundException {

        Optional<LocalUser> opUser = localUserDAO.findById(userId);

        if (opUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        UserDetailDTO userDetailDTO = new UserDetailDTO(opUser.get());

        return userDetailDTO;
    }
}
