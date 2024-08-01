package com.zelenenkyi.tasktracker.controller;

import com.zelenenkyi.tasktracker.dto.TaskListCreateDto;
import com.zelenenkyi.tasktracker.dto.TaskListMessageDto;
import com.zelenenkyi.tasktracker.dto.TaskMessageDto;
import com.zelenenkyi.tasktracker.mapper.TaskListCreateMapper;
import com.zelenenkyi.tasktracker.mapper.TaskListMessageMapper;
import com.zelenenkyi.tasktracker.model.TaskList;
import com.zelenenkyi.tasktracker.service.TaskListService;
import com.zelenenkyi.tasktracker.websocket.EAction;
import com.zelenenkyi.tasktracker.websocket.ETypeObject;
import com.zelenenkyi.tasktracker.websocket.MessageTemplate;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@RequestMapping("api/taskLists")
public class TaskListController {
    private final TaskListService taskListService;
    private final TaskListCreateMapper taskListCreateMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final TaskListMessageMapper taskListMessageMapper;

    //private final UserBoardServiceImpl userBoardService;
    //private final BoardMapper boardMapper;
    //private final BoardCreateMapper boardCreateMapper;

//    @GetMapping()
//    public ResponseEntity<List<BoardDto>> read(@AuthenticationPrincipal UserDetails userDetails){
//        List<BoardDto> list= boardService.getAllByUser(userDetails.getUsername()).stream().map(boardMapper::toDto).collect(Collectors.toList());
//
//        return list!=null && !list.isEmpty()
//                ? new ResponseEntity<>(list, HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//    @GetMapping("/{id}")
//    public ResponseEntity<BoardDto> read(@PathVariable(name = "id") Long id){
//        final BoardDto board= boardMapper.toDto(boardService.getById(id));
//        return board!=null
//                ? new ResponseEntity<>(board,HttpStatus.OK)
//                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//
//    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody TaskListCreateDto taskListCreateDto, @AuthenticationPrincipal UserDetails userDetails){
        TaskList taskList= taskListCreateMapper.toEntity(taskListCreateDto);
        taskList.setPosition(taskListService.getNewPosition(taskListCreateDto.boardId()));
        taskListService.create(taskList);

        TaskListMessageDto taskListMessageDto= taskListMessageMapper.toDto(taskList);
        messagingTemplate.convertAndSend("/topic/board/" + taskList.getBoard().getId(),
                new MessageTemplate<TaskListMessageDto>(
                        ETypeObject.TASK_LIST,
                        EAction.CREATE,
                        taskListMessageDto));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody TaskList taskList){
        final boolean updated= taskListService.update(taskList,id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id){
        TaskList taskList = taskListService.getById(id);
        final boolean deleted= taskListService.delete(id);
        if (deleted) {
            messagingTemplate.convertAndSend("/topic/board/" + taskList.getBoard().getId(),
                    new MessageTemplate<TaskListMessageDto>(
                            ETypeObject.TASK_LIST,
                            EAction.DELETE,
                            new TaskListMessageDto(id,null,null,null)));
        }
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
