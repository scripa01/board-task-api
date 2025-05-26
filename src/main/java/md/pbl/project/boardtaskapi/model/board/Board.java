package md.pbl.project.boardtaskapi.model.board;

import jakarta.persistence.*;
import lombok.*;
import md.pbl.project.boardtaskapi.model.task.Task;

import java.util.List;

@Entity
@Table(name = "boards")
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Board {
    @Id
    @SequenceGenerator(name = "board_sequence", sequenceName = "board_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_sequence")
    private Long id;
    private String name;
    private String description;
    private Long organizationId;
    private Long projectId;
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;
}
