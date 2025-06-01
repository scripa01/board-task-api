package md.pbl.project.boardtaskapi.model.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private Long assigneeId;
    private Long createdByUserId;
    private OffsetDateTime createdAt = OffsetDateTime.now();
    private OffsetDateTime updatedAt;
    private Long boardId;
}
