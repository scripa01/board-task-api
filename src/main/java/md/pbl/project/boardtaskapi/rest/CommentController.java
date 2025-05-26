package md.pbl.project.boardtaskapi.rest;

import md.pbl.project.boardtaskapi.exceptions.PblCustomException;
import md.pbl.project.boardtaskapi.model.comment.CommentDto;
import md.pbl.project.boardtaskapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orgs/{orgId}/projects/{projId}/boards/{boardId}/tasks/{taskId}/comments")
public class CommentController {
    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> list(
            @PathVariable Long orgId,
            @PathVariable Long projId,
            @PathVariable Long boardId,
            @PathVariable Long taskId,
            @RequestHeader("X-User-Id") Long userId
    ) throws PblCustomException {
        return ResponseEntity.ok(service.getAll(taskId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> get(
            @PathVariable Long orgId,
            @PathVariable Long projId,
            @PathVariable Long boardId,
            @PathVariable Long taskId,
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId
    ) throws PblCustomException {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping
    public ResponseEntity<CommentDto> create(
            @PathVariable Long orgId,
            @PathVariable Long projId,
            @PathVariable Long boardId,
            @PathVariable Long taskId,
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody CommentDto dto
    ) throws PblCustomException {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> update(
            @PathVariable Long orgId,
            @PathVariable Long projId,
            @PathVariable Long boardId,
            @PathVariable Long taskId,
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody CommentDto dto
    ) throws PblCustomException {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long orgId,
            @PathVariable Long projId,
            @PathVariable Long boardId,
            @PathVariable Long taskId,
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId
    ) throws PblCustomException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
