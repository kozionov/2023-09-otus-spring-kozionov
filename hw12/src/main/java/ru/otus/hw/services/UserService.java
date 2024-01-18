package ru.otus.hw.services;

import ru.otus.hw.dto.UserRegisterDto;
import ru.otus.hw.models.User;

public interface UserService {
    User save(UserRegisterDto userRegDto);
}
