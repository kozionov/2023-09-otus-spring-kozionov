package ru.otus.hw.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.UserRegisterDto;
import ru.otus.hw.models.User;
import ru.otus.hw.repositories.UserRepository;
import ru.otus.hw.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User save(UserRegisterDto userRegDto) {
        User user = new User();
        user.setUsername(userRegDto.getPassword());
//        user.setLastName(userRegDto.getLastName());
//        user.setEmail(userRegDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegDto.getPassword()));
//        List<Role> roles=new ArrayList<Role>();
//        Role role=new Role();
//        role.setName("ROLE_USER");
//        roles.add(role);
//        user.setRoles(roles);
        return userRepo.save(user);
    }

}