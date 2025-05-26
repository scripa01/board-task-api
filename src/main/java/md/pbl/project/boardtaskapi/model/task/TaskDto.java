package md.pbl.project.boardtaskapi.model.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import md.pbl.project.boardtaskapi.model.comment.CommentDto;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private String status;
    private Long assigneeId;
    private Long createdByUserId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private List<CommentDto> comments;
    private Long boardId;
}
