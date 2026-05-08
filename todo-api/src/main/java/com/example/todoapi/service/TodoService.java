package com.example.todoapi.service;

import com.example.todoapi.dto.TodoCreateRequest;
import com.example.todoapi.dto.TodoResponse;
import com.example.todoapi.dto.TodoUpdateRequest;

import java.util.List;

public interface TodoService {

    List<TodoResponse> findAll(Boolean completed);

    TodoResponse findById(Long id);

    TodoResponse create(TodoCreateRequest request);

    TodoResponse update(Long id, TodoUpdateRequest request);

    void delete(Long id);

    TodoResponse toggleCompleted(Long id);
}
