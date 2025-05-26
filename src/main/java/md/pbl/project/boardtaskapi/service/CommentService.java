package md.pbl.project.boardtaskapi.service;

import md.pbl.project.boardtaskapi.exceptions.PblCustomException;
import md.pbl.project.boardtaskapi.model.comment.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getAll(Long taskId) throws PblCustomException;
    CommentDto get(Long id) throws PblCustomException;
    CommentDto create(CommentDto dto) throws PblCustomException;
    CommentDto update(Long id, CommentDto dto) throws PblCustomException;
    void delete(Long id) throws PblCustomException;
}
