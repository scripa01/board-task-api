package md.pbl.project.boardtaskapi.model.task;

import jakarta.persistence.*;
import lombok.*;
import md.pbl.project.boardtaskapi.model.board.Board;
import md.pbl.project.boardtaskapi.model.comment.Comment;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task {
    @Id
    @SequenceGenerator(name = "task_sequence", sequenceName = "task_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_sequence")
    private Long id;
    private String title;
    private String description;
    private Long assigneeId;
    private Long createdByUserId;
    private TaskStatus status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = createdAt;
        if (status == null) {
            status = TaskStatus.OPEN;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
