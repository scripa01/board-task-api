package md.pbl.project.boardtaskapi.rest;

import md.pbl.project.boardtaskapi.exceptions.PblCustomException;
import md.pbl.project.boardtaskapi.model.board.BoardDto;
import md.pbl.project.boardtaskapi.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orgs/{orgId}/projects/{projId}/boards")
public class BoardController {
    private final BoardService service;

    public BoardController(BoardService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<BoardDto>> list(
            @PathVariable Long orgId,
            @PathVariable Long projId,
            @RequestHeader("X-User-Id") Long userId
    ) throws PblCustomException {
        return ResponseEntity.ok(service.getAll(orgId, projId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDto> get(
            @PathVariable Long orgId,
            @PathVariable Long projId,
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId
    ) throws PblCustomException {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping
    public ResponseEntity<BoardDto> create(
            @PathVariable Long orgId,
            @PathVariable Long projId,
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody BoardDto dto
    ) throws PblCustomException {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardDto> update(
            @PathVariable Long orgId,
            @PathVariable Long projId,
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody BoardDto dto
    ) throws PblCustomException {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long orgId,
            @PathVariable Long projId,
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId
    ) throws PblCustomException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
