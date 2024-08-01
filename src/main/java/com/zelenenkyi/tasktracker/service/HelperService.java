package com.zelenenkyi.tasktracker.service;

import java.util.List;

public interface HelperService<T>{
    List<T> getAll();

    T getById(Long id);

    void create(T item);

    boolean update(T item, Long id);

    boolean delete(Long id);
}
