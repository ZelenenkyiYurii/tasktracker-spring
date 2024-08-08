package com.zelenenkyi.tasktracker.controller;

import com.zelenenkyi.tasktracker.dto.request.create.TaskListCreateDto;
import com.zelenenkyi.tasktracker.dto.request.update.TaskListUpdateDto;
import com.zelenenkyi.tasktracker.dto.websocket.TaskListMessageDto;
import com.zelenenkyi.tasktracker.dto.websocket.TaskListUpdatePositionMessage;
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
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody TaskListUpdateDto taskList){
        final boolean updated= taskListService.update(taskList,id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
    @PutMapping("/position/{id}")
    public ResponseEntity<?> updatePosition(@PathVariable(name = "id") Long id, @RequestBody  TaskListUpdateDto taskListUpdateDto){
        final boolean updated= taskListService.changePosition(id,taskListUpdateDto.position());
        if (updated) {

            Long boardIdByTaskListId = taskListService.getBoardIdByTaskListId(id);
            messagingTemplate.convertAndSend("/topic/board/" + boardIdByTaskListId,
                    new MessageTemplate<TaskListUpdatePositionMessage>(
                            ETypeObject.TASK_LIST,
                            EAction.UPDATE_POSITION,
                            new TaskListUpdatePositionMessage(
                                  taskListService.getMapIdPosition(boardIdByTaskListId)
                            )));
        }
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
