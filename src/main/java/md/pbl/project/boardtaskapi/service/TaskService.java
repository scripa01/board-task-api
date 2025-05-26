package md.pbl.project.boardtaskapi.service;

import md.pbl.project.boardtaskapi.exceptions.PblCustomException;
import md.pbl.project.boardtaskapi.model.task.TaskDto;
import md.pbl.project.boardtaskapi.model.task.TaskStatus;

import java.util.List;

public interface TaskService {
    List<TaskDto> getAll(Long boardId) throws PblCustomException;

    TaskDto get(Long id) throws PblCustomException;

    TaskDto create(TaskDto dto) throws PblCustomException;

    TaskDto update(Long id, TaskDto dto) throws PblCustomException;

    void delete(Long id) throws PblCustomException;

    TaskDto workflowStatus(Long id, TaskStatus newStatus, Long userId) throws PblCustomException;

}
