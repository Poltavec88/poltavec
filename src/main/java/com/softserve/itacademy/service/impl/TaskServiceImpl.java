package com.softserve.itacademy.service.impl;

import com.softserve.itacademy.exeption.EntityNotFoundException;
import com.softserve.itacademy.exeption.NullEntityReferenceException;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.repository.TaskRepository;
import com.softserve.itacademy.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task create(Task user) {
        try {
            return taskRepository.save(user);
        } catch (IllegalArgumentException e) {
            throw new NullEntityReferenceException("You are trying to create an empty Task");
        }
    }

    @Override
    public Task readById(long id) {
        try {
            Optional<Task> optional = taskRepository.findById(id);
            return optional.get();
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Task with id=" + id + " is not found");
        }
    }

    @Override
    public Task update(Task task) {
        try {
            Task oldTask = readById(task.getId());
            return taskRepository.save(task);
        } catch (IllegalArgumentException | NoSuchElementException  e) {
            throw new NullEntityReferenceException("You are trying to change Task to blank or Task with id=" + task.getId() + " not found");
        }
    }

    @Override
    public void delete(long id) {
        try {
            Task task = readById(id);
            taskRepository.delete(task);
        }catch(NoSuchElementException e ){
            throw new EntityNotFoundException("Task with id=" + id + " is not found");

        }
    }

    @Override
    public List<Task> getAll() {
        try {
            List<Task> tasks = taskRepository.findAll();
            return tasks.isEmpty() ? new ArrayList<>() : tasks;
        }catch(NoSuchElementException e ){
            throw new EntityNotFoundException("There are no Tasks in the database");
        }
    }

    @Override
    public List<Task> getByTodoId(long todoId) {
        try {
            List<Task> tasks = taskRepository.getByTodoId(todoId);
            return tasks.isEmpty() ? new ArrayList<>() : tasks;
        }catch (NoSuchElementException e ){
            throw new EntityNotFoundException("Todo whit id-" + todoId + " is not found");
        }
    }
}
