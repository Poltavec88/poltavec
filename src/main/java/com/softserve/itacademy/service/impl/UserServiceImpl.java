package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.exeption.EntityNotFoundException;
import com.softserve.itacademy.exeption.NullEntityReferenceException;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.repository.UserRepository;
import com.softserve.itacademy.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        try {
            return userRepository.save(user);
        } catch (IllegalArgumentException e){
            throw new NullEntityReferenceException("You are trying to create an empty user");
        }
    }

    @Override
    public User readById(long id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else
            throw new EntityNotFoundException("User's id=" + id + " not found");
    }

    @Override
    public User update(User user) {
        try {
            User oldUser = readById(user.getId());
            return userRepository.save(user);
        } catch (IllegalArgumentException | NoSuchElementException e){
            throw new NullEntityReferenceException("You are trying to change User to blank or User's id="
                    + user.getId() + " not found");
        }
    }

    @Override
    public void delete(long id) {
        try {
            User user = readById(id);
            userRepository.delete(user);
        }catch(NoSuchElementException e ){
            throw  new EntityNotFoundException("User's id=" + id + " not found");
        }
    }

    @Override
    public List<User> getAll() {
        try {
            List<User> users = userRepository.findAll();
            return users.isEmpty() ? new ArrayList<>() : users;
        }catch(NoSuchElementException e ){
            throw new EntityNotFoundException("There are no Users in the database");
        }
    }

}
