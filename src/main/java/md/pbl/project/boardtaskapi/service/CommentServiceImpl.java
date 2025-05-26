package md.pbl.project.boardtaskapi.service;

import md.pbl.project.boardtaskapi.constants.ErrorCode;
import md.pbl.project.boardtaskapi.exceptions.PblCustomException;
import md.pbl.project.boardtaskapi.model.ObjectMapper;
import md.pbl.project.boardtaskapi.model.comment.Comment;
import md.pbl.project.boardtaskapi.model.comment.CommentDto;
import md.pbl.project.boardtaskapi.model.task.Task;
import md.pbl.project.boardtaskapi.repository.CommentRepository;
import md.pbl.project.boardtaskapi.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepo;
    private final TaskRepository taskRepo;
    private final ObjectMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepo, TaskRepository taskRepo, ObjectMapper mapper) {
        this.commentRepo = commentRepo;
        this.taskRepo = taskRepo;
        this.mapper = mapper;
    }

    @Override
    public List<CommentDto> getAll(Long taskId) throws PblCustomException {
        if (!taskRepo.existsById(taskId)) {
            throw new PblCustomException("Task does not exist", ErrorCode.TASK_DOESNT_EXIST, HttpStatus.NOT_FOUND);
        }
        return mapper.toCommentDtoList(commentRepo.findByTaskId(taskId));
    }

    @Override
    public CommentDto get(Long id) throws PblCustomException {
        Comment comment = commentRepo.findById(id)
                .orElseThrow(() -> new PblCustomException("Comment does not exist", ErrorCode.COMMENT_DOESNT_EXIST, HttpStatus.NOT_FOUND));
        return mapper.toDto(comment);
    }

    @Override
    public CommentDto create(CommentDto dto) throws PblCustomException {
        Task task = taskRepo.findById(dto.getTaskId())
                .orElseThrow(() -> new PblCustomException("Task does not exist", ErrorCode.TASK_DOESNT_EXIST, HttpStatus.NOT_FOUND));
        Comment entity = mapper.toEntity(dto);
        entity.setTask(task);
        return mapper.toDto(commentRepo.save(entity));
    }

    @Override
    public CommentDto update(Long id, CommentDto dto) throws PblCustomException {
        Comment entity = commentRepo.findById(id)
                .orElseThrow(() -> new PblCustomException("Comment does not exist", ErrorCode.COMMENT_DOESNT_EXIST, HttpStatus.NOT_FOUND));
        mapper.updateEntityFromDto(dto, entity);
        return mapper.toDto(commentRepo.save(entity));
    }

    @Override
    public void delete(Long id) throws PblCustomException {
        if (!commentRepo.existsById(id)) {
            throw new PblCustomException("Comment does not exist", ErrorCode.COMMENT_DOESNT_EXIST, HttpStatus.NOT_FOUND);
        }
        commentRepo.deleteById(id);
    }
}
