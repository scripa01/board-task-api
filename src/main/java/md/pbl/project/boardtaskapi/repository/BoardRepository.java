package md.pbl.project.boardtaskapi.repository;

import md.pbl.project.boardtaskapi.model.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByOrganizationIdAndProjectId(Long organizationId, Long projectId);
}
