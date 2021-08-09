package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.exeption.EntityNotFoundException;
import com.softserve.itacademy.exeption.NullEntityReferenceException;
import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.repository.RoleRepository;
import com.softserve.itacademy.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role create(Role role) {
        try {
            return roleRepository.save(role);
        } catch (IllegalArgumentException e) {
            throw new NullEntityReferenceException("You are trying to create an empty Role");
        }
    }

    @Override
    public Role readById(long id) {
        try {
            Optional<Role> optional = roleRepository.findById(id);
            return optional.get();
        }catch(NoSuchElementException e ){
            throw new EntityNotFoundException("Role whit id-" + id + " is not found");
        }
    }

    @Override
    public Role update(Role role) {
        try {
            Role oldRole = readById(role.getId());
            return roleRepository.save(role);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            throw new NullEntityReferenceException("You are trying to change Role on blank or Role whit id-"
                    + role.getId() + " is not found");
        }
    }

    @Override
    public void delete(long id) {
        try {
            Role role = readById(id);
            roleRepository.delete(role);
        }catch(NoSuchElementException e ){
            throw  new EntityNotFoundException("Role whit id-" + id + " is not found");
        }
    }

    @Override
    public List<Role> getAll() {
        try {
            List<Role> roles = roleRepository.findAll();
            return roles.isEmpty() ? new ArrayList<>() : roles;
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("There are no Roles in the database");
        }
    }
}
