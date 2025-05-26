package md.pbl.project.boardtaskapi.rest;

import md.pbl.project.boardtaskapi.exceptions.PblCustomException;
import md.pbl.project.boardtaskapi.model.task.TaskDto;
import md.pbl.project.boardtaskapi.model.task.TaskStatus;
import md.pbl.project.boardtaskapi.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orgs/{orgId}/projects/{projId}/boards/{boardId}/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> list(
            @PathVariable Long orgId,
            @PathVariable Long projId,
            @PathVariable Long boardId,
            @RequestHeader("X-User-Id") Long userId
    ) throws PblCustomException {
        return ResponseEntity.ok(service.getAll(boardId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> get(
            @PathVariable Long orgId,
            @PathVariable Long projId,
            @PathVariable Long boardId,
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId
    ) throws PblCustomException {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping
    public ResponseEntity<TaskDto> create(
            @PathVariable Long orgId,
            @PathVariable Long projId,
            @PathVariable Long boardId,
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody TaskDto dto
    ) throws PblCustomException {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> update(
            @PathVariable Long orgId,
            @PathVariable Long projId,
            @PathVariable Long boardId,
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody TaskDto dto
    ) throws PblCustomException {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long orgId,
            @PathVariable Long projId,
            @PathVariable Long boardId,
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId
    ) throws PblCustomException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskDto> changeStatus(
            @PathVariable Long orgId,
            @PathVariable Long projId,
            @PathVariable Long boardId,
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam TaskStatus newStatus
    ) throws PblCustomException {
        return ResponseEntity.ok(service.workflowStatus(id, newStatus, userId));
    }
}
