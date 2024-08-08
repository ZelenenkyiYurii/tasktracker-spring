package com.zelenenkyi.tasktracker.controller;

import com.zelenenkyi.tasktracker.dto.request.create.BoardCreateDto;
import com.zelenenkyi.tasktracker.dto.BoardDto;
import com.zelenenkyi.tasktracker.dto.request.update.BoardUpdateDto;
import com.zelenenkyi.tasktracker.mapper.BoardCreateMapper;
import com.zelenenkyi.tasktracker.mapper.BoardMapper;
import com.zelenenkyi.tasktracker.model.Board;
import com.zelenenkyi.tasktracker.model.ERole;
import com.zelenenkyi.tasktracker.model.UserBoardRole;
import com.zelenenkyi.tasktracker.service.BoardService;
import com.zelenenkyi.tasktracker.service.impl.UserBoardServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
@RequestMapping("api/boards")
@AllArgsConstructor

public class BoardController {

    private final BoardService boardService;
    private final UserBoardServiceImpl userBoardService;
    private final BoardMapper boardMapper;
    private final BoardCreateMapper boardCreateMapper;

    @GetMapping()
    public ResponseEntity<List<BoardDto>> read(@AuthenticationPrincipal UserDetails userDetails){
        List<BoardDto> list= boardService.getAllByUser(userDetails.getUsername()).stream().map(boardMapper::toDto).collect(Collectors.toList());

        return list!=null && !list.isEmpty()
                ? new ResponseEntity<>(list, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BoardDto> read(@PathVariable(name = "id") Long id){
        final BoardDto board= boardMapper.toDto(boardService.getById(id));
        return board!=null
                ? new ResponseEntity<>(board,HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody BoardCreateDto boardDto,@AuthenticationPrincipal UserDetails userDetails){
        Board board= boardCreateMapper.toEntity(boardDto);
        Set<UserBoardRole> userBoardRoleSet=new HashSet<>();
        UserBoardRole userBoardRole=userBoardService.create(board,userDetails.getUsername(), ERole.ROLE_USER);
        userBoardRoleSet.add(userBoardRole);
        board.setUserBoardRoles(userBoardRoleSet);
        boardService.create(board);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody BoardUpdateDto board){
        final boolean updated= boardService.update(board,id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id){
        final boolean deleted= boardService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
