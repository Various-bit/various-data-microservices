package org.example.authorization.service;

import org.example.authorization.dto.JWT;
import org.example.authorization.entity.User;

import java.io.IOException;

public interface UserService {
    public void registerUser(User user) throws IOException, InterruptedException;

    public JWT loginUser(User user);

    public User getCurrentUser();
}
