package md.pbl.project.boardtaskapi.model;

import md.pbl.project.boardtaskapi.model.board.Board;
import md.pbl.project.boardtaskapi.model.board.BoardDto;
import md.pbl.project.boardtaskapi.model.comment.Comment;
import md.pbl.project.boardtaskapi.model.comment.CommentDto;
import md.pbl.project.boardtaskapi.model.task.Task;
import md.pbl.project.boardtaskapi.model.task.TaskAudit;
import md.pbl.project.boardtaskapi.model.task.TaskAuditDto;
import md.pbl.project.boardtaskapi.model.task.TaskDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@Component
public interface ObjectMapper {
    // Board
    BoardDto toDto(Board board);

    Board toEntity(BoardDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(BoardDto dto, @MappingTarget Board entity);

    // Task
    @Mapping(source = "task.board.id", target = "boardId")
    TaskDto toDto(Task task);

    @Mapping(source = "dto.boardId", target = "board", qualifiedByName = "mapBoardIdToBoard")
    Task toEntity(TaskDto dto);

    @Mapping(source = "dto.boardId", target = "board", qualifiedByName = "mapBoardIdToBoard")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(TaskDto dto, @MappingTarget Task entity);

    // Comment
    @Mapping(source = "comment.task.id", target = "taskId")
    CommentDto toDto(Comment comment);

    @Mapping(target = "task", source = "taskId", qualifiedByName = "mapTaskIdToTask")
    Comment toEntity(CommentDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "task", source = "taskId", qualifiedByName = "mapTaskIdToTask")
    void updateEntityFromDto(CommentDto dto, @MappingTarget Comment entity);

    // Collections
    List<BoardDto> toBoardDtoList(List<Board> boards);

    List<TaskDto> toTaskDtoList(List<Task> tasks);

    List<CommentDto> toCommentDtoList(List<Comment> comments);

    @Mapping(source = "audit.task.id", target = "taskId")
    TaskAuditDto toTaskAuditDto(TaskAudit audit);

    TaskAudit toTaskAudit(TaskAuditDto dto);

    @Named("mapTaskIdToTask")
    default Task mapTaskIdToTask(Long taskId) {
        if (taskId == null) return null;
        return Task.builder().id(taskId).build();
    }

    @Named("mapBoardIdToBoard")
    default Board mapBoardIdToBoard(Long boardId) {
        if (boardId == null) return null;
        Board b = new Board();
        b.setId(boardId);
        return b;
    }
}
