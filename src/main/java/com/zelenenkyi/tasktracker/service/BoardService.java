package com.zelenenkyi.tasktracker.service;

import com.zelenenkyi.tasktracker.dto.request.update.BoardUpdateDto;
import com.zelenenkyi.tasktracker.model.Board;
import com.zelenenkyi.tasktracker.model.User;


import java.util.HashMap;
import java.util.List;


public interface BoardService {
    List<Board> getAll();

    Board getById(Long id);

    void create(Board item);

    boolean update(BoardUpdateDto item, Long id);

    boolean delete(Long id);
    public List<Board> getAllByUser(User user);
    public List<Board> getAllByUser(String username);

}
