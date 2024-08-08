package com.zelenenkyi.tasktracker.service.impl;

import com.zelenenkyi.tasktracker.dto.request.update.BoardUpdateDto;
import com.zelenenkyi.tasktracker.model.Board;
import com.zelenenkyi.tasktracker.model.User;
import com.zelenenkyi.tasktracker.repository.BoardRepository;
import com.zelenenkyi.tasktracker.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    @Override
    public List<Board> getAll() {
        return boardRepository.findAll();
    }

    @Override
    public Board getById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void create(Board item) {
        boardRepository.save(item);
    }

    @Override
    public boolean update(BoardUpdateDto item, Long id) {
        Optional<Board> boardOptional=boardRepository.findById(id);
        if(boardOptional.isPresent()){
            Board board=boardOptional.get();
            board.setTitle(item.title());
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        boardRepository.deleteById(id);
        return !boardRepository.existsById(id);
    }

    @Override
    public List<Board> getAllByUser(User user) {
        return boardRepository.findByUserBoardRoles_User(user);

    }

    @Override
    public List<Board> getAllByUser(String username) {

        return boardRepository.findByUserBoardRoles_User_Username(username);
    }



}
