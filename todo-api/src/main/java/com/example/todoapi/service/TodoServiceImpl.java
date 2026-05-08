package com.example.todoapi.service;

import com.example.todoapi.dto.TodoCreateRequest;
import com.example.todoapi.dto.TodoResponse;
import com.example.todoapi.dto.TodoUpdateRequest;
import com.example.todoapi.entity.Todo;
import com.example.todoapi.exception.TodoNotFoundException;
import com.example.todoapi.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<TodoResponse> findAll(Boolean completed) {
        List<Todo> todos;
        if (completed != null) {
            todos = todoRepository.findByCompleted(completed);
        } else {
            todos = todoRepository.findAll();
        }
        return todos.stream()
                .map(TodoResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public TodoResponse findById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        return TodoResponse.from(todo);
    }

    @Override
    @Transactional
    public TodoResponse create(TodoCreateRequest request) {
        Todo todo = Todo.create(request.getTitle(), request.getDescription());
        Todo saved = todoRepository.save(todo);
        return TodoResponse.from(saved);
    }

    @Override
    @Transactional
    public TodoResponse update(Long id, TodoUpdateRequest request) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        todo.update(request.getTitle(), request.getDescription(), request.getCompleted());
        return TodoResponse.from(todo);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        todoRepository.delete(todo);
    }

    @Override
    @Transactional
    public TodoResponse toggleCompleted(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        todo.toggleCompleted();
        return TodoResponse.from(todo);
    }
}
