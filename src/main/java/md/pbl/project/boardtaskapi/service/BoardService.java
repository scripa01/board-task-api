package md.pbl.project.boardtaskapi.service;

import md.pbl.project.boardtaskapi.exceptions.PblCustomException;
import md.pbl.project.boardtaskapi.model.board.BoardDto;

import java.util.List;

public interface BoardService {
    List<BoardDto> getAll(Long organizationId, Long projectId) throws PblCustomException;
    BoardDto get(Long id) throws PblCustomException;
    BoardDto create(BoardDto dto) throws PblCustomException;
    BoardDto update(Long id, BoardDto dto) throws PblCustomException;
    void delete(Long id) throws PblCustomException;
}
