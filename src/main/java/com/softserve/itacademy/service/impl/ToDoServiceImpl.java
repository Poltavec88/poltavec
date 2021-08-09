package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.exeption.EntityNotFoundException;
import com.softserve.itacademy.exeption.NullEntityReferenceException;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.repository.ToDoRepository;
import com.softserve.itacademy.service.ToDoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ToDoServiceImpl implements ToDoService {

    private ToDoRepository todoRepository;

    public ToDoServiceImpl(ToDoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public ToDo create(ToDo todo) {
        try {
            return todoRepository.save(todo);
        } catch (IllegalArgumentException e) {
            throw new NullEntityReferenceException("You are trying to create an empty ToDo");
        }
    }

    @Override
    public ToDo readById(long id) {
        try {
            Optional<ToDo> optional = todoRepository.findById(id);
            return optional.get();
        }catch(NoSuchElementException e ){
            throw new EntityNotFoundException("Todo whit id-" + id + " is not found");
        }
    }

    @Override
    public ToDo update(ToDo todo) {
        try {
            ToDo oldTodo = readById(todo.getId());
            return todoRepository.save(todo);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            throw new NullEntityReferenceException("You are trying to change ToDo on blank or Todo whit id-" + todo.getId() + " is not found");
        }
    }

    @Override
    public void delete(long id) {
        try {
            ToDo todo = readById(id);
            todoRepository.delete(todo);
        }catch(NoSuchElementException e ) {
            throw new EntityNotFoundException("Todo whit id-" + id + " is not found");
        }
    }

    @Override
    public List<ToDo> getAll() {
        try {
            List<ToDo> todos = todoRepository.findAll();
            return todos.isEmpty() ? new ArrayList<>() : todos;
        }catch(NoSuchElementException e ){
            throw new EntityNotFoundException("There are no Todos in the database");
        }
    }

    @Override
    public List<ToDo> getByUserId(long userId) {
        try {
            List<ToDo> todos = todoRepository.getByUserId(userId);
            return todos.isEmpty() ? new ArrayList<>() : todos;
        }catch(NoSuchElementException e ){
            throw new EntityNotFoundException("User's id=" + userId + " not found");
        }
    }
}
