package md.pbl.project.boardtaskapi.service;

import md.pbl.project.boardtaskapi.model.task.Task;
import md.pbl.project.boardtaskapi.model.task.TaskAuditDto;
import md.pbl.project.boardtaskapi.model.task.TaskStatus;

import java.util.List;

public interface TaskAuditService {
    void record(Task task, Long userId, TaskStatus oldStatus, TaskStatus newStatus, String comment);

    List<TaskAuditDto> getAuditForTask(Long taskId);
}
