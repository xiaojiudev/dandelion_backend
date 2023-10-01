package com.dandelion.backend.services;

import com.dandelion.backend.payloads.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO user);

    UserDTO updateUser(Long userId, UserDTO user);

    UserDTO getUserById(Long userId);

    List<UserDTO> getAllUsers();

    void deleteUser(Long userId);

//    @Autowired
//    private UserRepo userRepo;
//    @Autowired
//    private EncryptionService encryptionService;
//    @Autowired
//    private JWTService jwtService;
//    @Autowired
//    private RoleRepo roleRepo;
//
//    public User registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {
//
//        String emailReg = registrationBody.getEmail();
//        String phoneReg = registrationBody.getPhone();
//        String fullNameReg = registrationBody.getFullName();
//        String passwordReg = encryptionService.encryptPassword(registrationBody.getPassword());
//
//        if (userRepo.findByEmailIgnoreCase(emailReg).isPresent() || userRepo.findByPhoneIgnoreCase(phoneReg).isPresent()) {
//            throw new UserAlreadyExistsException();
//        }
//
//        Optional<Role> userRole = roleRepo.findByRoleName(RoleBase.CUSTOMER);
//
//        System.out.println(userRole);
//
//        User user = User.builder()
//                .email(emailReg)
//                .phone(phoneReg)
//                .roles(List.of(userRole.get()))
//                .fullName(fullNameReg)
//                .password(passwordReg)
//                .build();
//
//
//        //        TODO: Encrypt Passwords!!
//
//        return userRepo.save(user);
//    }
//
//    public String loginUser(LoginBody loginBody) {
//        Optional<User> opUser = userRepo.findByEmailIgnoreCase(loginBody.getEmail());
//
//        if (opUser.isPresent()) {
//            User user = opUser.get();
//            if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
//                //TODO: Login success then create JWT token
//                return jwtService.generateJWT(user);
//            }
//        }
//
//        return null;
//    }
//
//    public List<UserDTO> getAllUsers() throws UserNotFoundException {
//        List<User> users = userRepo.findAll();
//
//        if (users.size() == 0) {
//            throw new UserNotFoundException("No record exists");
//        }
//
//        List<UserDTO> userDTOs = users.stream().map(UserDTO::new).collect(Collectors.toList());
//
//        return userDTOs;
//    }
//
//    // TODO: Method to get a customer by id
//    public UserDetailDTO getUserDetails(Long userId) throws UserNotFoundException {
//
//        Optional<User> opUser = userRepo.findById(userId);
//
//        if (opUser.isEmpty()) {
//            throw new UserNotFoundException();
//        }
//
//        UserDetailDTO userDetailDTO = new UserDetailDTO(opUser.get());
//
//        return userDetailDTO;
//    }
}
