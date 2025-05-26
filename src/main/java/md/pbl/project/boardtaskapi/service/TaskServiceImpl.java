package md.pbl.project.boardtaskapi.service;

import md.pbl.project.boardtaskapi.constants.ErrorCode;
import md.pbl.project.boardtaskapi.exceptions.PblCustomException;
import md.pbl.project.boardtaskapi.model.ObjectMapper;
import md.pbl.project.boardtaskapi.model.board.Board;
import md.pbl.project.boardtaskapi.model.task.Task;
import md.pbl.project.boardtaskapi.model.task.TaskDto;
import md.pbl.project.boardtaskapi.model.task.TaskStatus;
import md.pbl.project.boardtaskapi.repository.BoardRepository;
import md.pbl.project.boardtaskapi.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepo;
    private final BoardRepository boardRepo;
    private final TaskAuditServiceImpl taskAuditService;
    private final ObjectMapper mapper;
    private static final Map<TaskStatus, Set<TaskStatus>> ALLOWED = Map.of(
            TaskStatus.OPEN, Set.of(TaskStatus.IN_PROGRESS, TaskStatus.DONE, TaskStatus.SCHEDULED),
            TaskStatus.IN_PROGRESS, Set.of(TaskStatus.DONE, TaskStatus.RE_OPENED),
            TaskStatus.SCHEDULED, Set.of(TaskStatus.IN_PROGRESS, TaskStatus.RE_OPENED),
            TaskStatus.DONE, Set.of(TaskStatus.RE_OPENED),
            TaskStatus.RE_OPENED, Set.of(TaskStatus.IN_PROGRESS, TaskStatus.SCHEDULED)
    );

    public TaskServiceImpl(TaskRepository taskRepo,
                           BoardRepository boardRepo,
                           ObjectMapper mapper,
                           TaskAuditServiceImpl taskAuditService) {
        this.taskRepo = taskRepo;
        this.boardRepo = boardRepo;
        this.mapper = mapper;
        this.taskAuditService = taskAuditService;
    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskDto> getAll(Long boardId) throws PblCustomException {
        if (!boardRepo.existsById(boardId)) {
            throw new PblCustomException("Board does not exist", ErrorCode.BOARD_DOESNT_EXIST, HttpStatus.NOT_FOUND);
        }
        return mapper.toTaskDtoList(taskRepo.findByBoardId(boardId));
    }

    @Transactional(readOnly = true)
    @Override
    public TaskDto get(Long id) throws PblCustomException {
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new PblCustomException("Task does not exist", ErrorCode.TASK_DOESNT_EXIST, HttpStatus.NOT_FOUND));
        return mapper.toDto(task);
    }

    @Override
    public TaskDto create(TaskDto dto) throws PblCustomException {
        Board board = boardRepo.findById(dto.getBoardId())
                .orElseThrow(() -> new PblCustomException("Board does not exist", ErrorCode.BOARD_DOESNT_EXIST, HttpStatus.NOT_FOUND));
        Task entity = mapper.toEntity(dto);
        entity.setBoard(board);
        taskAuditService.record(entity, dto.getCreatedByUserId(), TaskStatus.OPEN, TaskStatus.OPEN, "Create Task");
        return mapper.toDto(taskRepo.save(entity));
    }

    @Override
    public TaskDto update(Long id, TaskDto dto) throws PblCustomException {
        Task entity = taskRepo.findById(id)
                .orElseThrow(() -> new PblCustomException("Task does not exist", ErrorCode.TASK_DOESNT_EXIST, HttpStatus.NOT_FOUND));
        mapper.updateEntityFromDto(dto, entity);
        taskAuditService.record(entity, dto.getCreatedByUserId(), TaskStatus.OPEN, TaskStatus.OPEN, "Update Task");
        return mapper.toDto(taskRepo.save(entity));
    }

    @Override
    public void delete(Long id) throws PblCustomException {
        if (!taskRepo.existsById(id)) {
            throw new PblCustomException("Task does not exist", ErrorCode.TASK_DOESNT_EXIST, HttpStatus.NOT_FOUND);
        }
        taskRepo.deleteById(id);
    }

    @Override
    public TaskDto workflowStatus(Long id, TaskStatus newStatus, Long userId) throws PblCustomException {
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new PblCustomException("Task not found", ErrorCode.TASK_DOESNT_EXIST, HttpStatus.NOT_FOUND));
        TaskStatus current = task.getStatus();
        if (!ALLOWED.getOrDefault(current, Collections.emptySet()).contains(newStatus)) {
            throw new PblCustomException(
                    String.format("Invalid status transition from %s to %s", current, newStatus),
                    ErrorCode.PROJECT_DOESNT_EXIST,
                    HttpStatus.BAD_REQUEST);
        }
        task.setStatus(newStatus);
        task.setUpdatedAt(OffsetDateTime.now());
        taskRepo.save(task);
        taskAuditService.record(task,
                userId,
                current,
                newStatus,
                "Changed Status from " + current + " to " + newStatus);
        return mapper.toDto(task);
    }
}
