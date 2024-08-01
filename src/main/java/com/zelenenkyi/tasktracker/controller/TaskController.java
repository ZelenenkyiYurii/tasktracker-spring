package com.zelenenkyi.tasktracker.controller;

import com.zelenenkyi.tasktracker.dto.*;
import com.zelenenkyi.tasktracker.mapper.TaskCreateMapper;
import com.zelenenkyi.tasktracker.mapper.TaskMapper;
import com.zelenenkyi.tasktracker.mapper.TaskMessageMapper;
import com.zelenenkyi.tasktracker.model.*;
import com.zelenenkyi.tasktracker.service.TaskListService;
import com.zelenenkyi.tasktracker.service.impl.TaskServiceImpl;
import com.zelenenkyi.tasktracker.service.impl.UserServiceImpl;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@RequestMapping("api/tasks")
public class TaskController {
    private final TaskServiceImpl taskService;
    private final TaskMapper taskMapper;
    private final TaskCreateMapper taskCreateMapper;
    private final UserServiceImpl userService;
    private final TaskListService taskListService;
    private final SimpMessagingTemplate messagingTemplate;
    private final TaskMessageMapper taskMessageMapper;

    @GetMapping()
    public ResponseEntity<List<TaskDto>> read(@AuthenticationPrincipal UserDetails userDetails) {
        List<TaskDto> list = taskService.getAll().stream().map(taskMapper::toDto).collect(Collectors.toList());

        return list != null && !list.isEmpty()
                ? new ResponseEntity<>(list, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> read(@PathVariable(name = "id") Long id){
        final TaskDto task= taskMapper.toDto(taskService.getById(id));
        return task!=null
                ? new ResponseEntity<>(task,HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody TaskCreateDto taskDto, @AuthenticationPrincipal UserDetails userDetails){

        Task task= taskCreateMapper.toEntity(taskDto);

        TaskList taskList=taskListService.getById(taskDto.taskListId());
        task.setTaskList(taskList);
        task.setPosition(taskService.getNewPosition(taskDto.taskListId()));
        task.setCreator(userService.findUserByUsername(userDetails.getUsername()));

        taskService.create(task);

        TaskMessageDto taskMessageDto= taskMessageMapper.toDto(task);
        messagingTemplate.convertAndSend("/topic/board/" + task.getTaskList().getBoard().getId(),
                new MessageTemplate<TaskMessageDto>(
                        ETypeObject.TASK,
                        EAction.CREATE,
                        taskMessageDto));

    return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody Task board){
        final boolean updated= taskService.update(board,id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id){
        Task task=taskService.getById(id);
        final boolean deleted= taskService.delete(id);
        if (deleted) {
            messagingTemplate.convertAndSend("/topic/board/" + task.getTaskList().getBoard().getId(),
                    new MessageTemplate<TaskMessageDto>(
                            ETypeObject.TASK,
                            EAction.DELETE,
                            new TaskMessageDto(id,null,null,null,task.getTaskList().getId())));
        }

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}