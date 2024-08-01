package com.zelenenkyi.tasktracker.service;

import com.zelenenkyi.tasktracker.model.Board;
import com.zelenenkyi.tasktracker.model.User;


import java.util.List;


public interface BoardService extends HelperService<Board>{
    public List<Board> getAllByUser(User user);
    public List<Board> getAllByUser(String username);
}
