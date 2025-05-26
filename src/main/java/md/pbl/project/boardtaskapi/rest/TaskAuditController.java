package md.pbl.project.boardtaskapi.rest;

import md.pbl.project.boardtaskapi.model.task.TaskAuditDto;
import md.pbl.project.boardtaskapi.service.TaskAuditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orgs/{orgId}/projects/{projId}/boards/{boardId}/tasks/{taskId}/audit")
public class TaskAuditController {

    private final TaskAuditService auditService;

    public TaskAuditController(TaskAuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    public ResponseEntity<List<TaskAuditDto>> getAudit(
            @PathVariable Long orgId,
            @PathVariable Long projId,
            @PathVariable Long boardId,
            @PathVariable Long taskId,
            @RequestHeader("X-User-Id") Long userId
    ) {
        return ResponseEntity.ok(auditService.getAuditForTask(taskId));
    }
}
