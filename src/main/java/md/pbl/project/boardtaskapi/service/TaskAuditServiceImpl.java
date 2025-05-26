package md.pbl.project.boardtaskapi.service;

import md.pbl.project.boardtaskapi.model.ObjectMapper;
import md.pbl.project.boardtaskapi.model.task.Task;
import md.pbl.project.boardtaskapi.model.task.TaskAudit;
import md.pbl.project.boardtaskapi.model.task.TaskAuditDto;
import md.pbl.project.boardtaskapi.model.task.TaskStatus;
import md.pbl.project.boardtaskapi.repository.TaskAuditRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskAuditServiceImpl implements TaskAuditService {
    private final TaskAuditRepository auditRepo;
    private final ObjectMapper mapper;

    public TaskAuditServiceImpl(TaskAuditRepository auditRepo, ObjectMapper mapper) {
        this.auditRepo = auditRepo;
        this.mapper = mapper;
    }

    @Override
    public void record(Task task, Long userId, TaskStatus oldStatus, TaskStatus newStatus, String comment) {
        TaskAudit audit = new TaskAudit();
        audit.setTask(task);
        audit.setChangedByUserId(userId);
        audit.setOldStatus(oldStatus);
        audit.setNewStatus(newStatus);
        audit.setChangeComment(comment);
        auditRepo.save(audit);
    }

    @Override
    public List<TaskAuditDto> getAuditForTask(Long taskId) {
        return auditRepo.findByTaskIdOrderByChangedAtDesc(taskId).stream()
                .map(mapper::toTaskAuditDto)
                .collect(Collectors.toList());
    }
}
