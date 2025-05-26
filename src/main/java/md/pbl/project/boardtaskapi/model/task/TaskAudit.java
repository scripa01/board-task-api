package md.pbl.project.boardtaskapi.model.task;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "task_audit")
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class TaskAudit {
    @Id
    @SequenceGenerator(name = "task_sequence", sequenceName = "task_sequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_sequence")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    private Long changedByUserId;
    private TaskStatus oldStatus;
    private TaskStatus newStatus;
    private String changeComment;
    private OffsetDateTime changedAt;

    @PrePersist
    protected void onAudit() {
        changedAt = OffsetDateTime.now();
    }
}
