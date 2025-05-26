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
public class TaskAuditDto {
    private Long id;
    private Long taskId;
    private Long changedByUserId;
    private TaskStatus oldStatus;
    private TaskStatus newStatus;
    private String changeComment;
    private OffsetDateTime changedAt;
}
