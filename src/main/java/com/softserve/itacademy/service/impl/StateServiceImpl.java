package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.exeption.EntityNotFoundException;
import com.softserve.itacademy.exeption.NullEntityReferenceException;
import com.softserve.itacademy.model.State;
import com.softserve.itacademy.repository.StateRepository;
import com.softserve.itacademy.service.StateService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StateServiceImpl implements StateService {
    private StateRepository stateRepository;

    public StateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public State create(State state) {
        try {
            return stateRepository.save(state);
        } catch (IllegalArgumentException e) {
            throw new NullEntityReferenceException("You are trying to crete an empty State");
        }
    }

    @Override
    public State readById(long id) {
        try {
            Optional<State> optional = stateRepository.findById(id);
            return optional.get();
        }catch(NoSuchElementException e ){
            throw new EntityNotFoundException("State whit id-" + id + " is not found");
        }
    }

    @Override
    public State update(State state) {
        try {
            State oldState = readById(state.getId());
            return stateRepository.save(state);
        } catch (IllegalArgumentException e) {
            throw new NullEntityReferenceException("You are trying to change State on blank or State whit id-"
                    + state.getId() + " is not found");
        }
    }

    @Override
    public void delete(long id) {
        try {
            State state = readById(id);
            stateRepository.delete(state);
        }catch(NoSuchElementException e ){
            throw new EntityNotFoundException("State whit id-" + id + " is not found");
        }
    }

    @Override
    public State getByName(String name) {
        try {
            Optional<State> optional = Optional.ofNullable(stateRepository.getByName(name));
            return optional.get();
        }catch(NoSuchElementException e ){
            throw new EntityNotFoundException("State whit name-" + name + " is not found");
        }
    }

    @Override
    public List<State> getAll() {
        try {
            List<State> states = stateRepository.getAll();
            return states.isEmpty() ? new ArrayList<>() : states;
        }catch(NoSuchElementException e ){
            throw new EntityNotFoundException("There are no States in the database");
        }
    }
}
