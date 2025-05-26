package md.pbl.project.boardtaskapi.model.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private Long taskId;
    private Long userId;
    private String content;
    private OffsetDateTime createdAt;
}
