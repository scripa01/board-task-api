package md.pbl.project.boardtaskapi.repository;

import md.pbl.project.boardtaskapi.model.task.TaskAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskAuditRepository extends JpaRepository<TaskAudit, Long> {
    List<TaskAudit> findByTaskIdOrderByChangedAtDesc(Long taskId);
}
