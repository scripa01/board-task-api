package md.pbl.project.boardtaskapi.model.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import md.pbl.project.boardtaskapi.model.task.TaskDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {
    private Long id;
    private String name;
    private String description;
    private Long organizationId;
    private Long projectId;
    private List<TaskDto> tasks;
}
