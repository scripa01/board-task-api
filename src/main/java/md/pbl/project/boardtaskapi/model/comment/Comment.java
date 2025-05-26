package md.pbl.project.boardtaskapi.model.comment;

import jakarta.persistence.*;
import lombok.*;
import md.pbl.project.boardtaskapi.model.task.Task;

import java.time.OffsetDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @SequenceGenerator(name = "comment_sequence", sequenceName = "comment_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence")
    Long id;
    Long userId;
    String content;
    OffsetDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;
}

