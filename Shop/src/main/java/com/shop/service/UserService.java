package com.shop.service;

import com.shop.entity.User;
import com.shop.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(long id)
    {
        return userRepository.findById(id);
    }

    public User saveUser(User user)
    {
        return userRepository.save(user);
    }

    public void deleteUser(User user)
    {
        userRepository.delete(user);
    }
}