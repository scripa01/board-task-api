package md.pbl.project.boardtaskapi.service;

import md.pbl.project.boardtaskapi.constants.ErrorCode;
import md.pbl.project.boardtaskapi.exceptions.PblCustomException;
import md.pbl.project.boardtaskapi.model.ObjectMapper;
import md.pbl.project.boardtaskapi.model.board.Board;
import md.pbl.project.boardtaskapi.model.board.BoardDto;
import md.pbl.project.boardtaskapi.repository.BoardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepo;
    private final ObjectMapper mapper;

    public BoardServiceImpl(BoardRepository boardRepo, ObjectMapper mapper) {
        this.boardRepo = boardRepo;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<BoardDto> getAll(Long organizationId, Long projectId) {
        return mapper.toBoardDtoList(boardRepo.findByOrganizationIdAndProjectId(organizationId, projectId));
    }

    @Transactional(readOnly = true)
    @Override
    public BoardDto get(Long id) throws PblCustomException {
        Board board = boardRepo.findById(id)
                .orElseThrow(() -> new PblCustomException("Board does not exist", ErrorCode.BOARD_DOESNT_EXIST, HttpStatus.NOT_FOUND));
        return mapper.toDto(board);
    }

    @Override
    public BoardDto create(BoardDto dto) {
        Board entity = mapper.toEntity(dto);
        return mapper.toDto(boardRepo.save(entity));
    }

    @Override
    public BoardDto update(Long id, BoardDto dto) throws PblCustomException {
        Board entity = boardRepo.findById(id)
                .orElseThrow(() -> new PblCustomException("Board does not exist", ErrorCode.BOARD_DOESNT_EXIST, HttpStatus.NOT_FOUND));
        mapper.updateEntityFromDto(dto, entity);
        return mapper.toDto(boardRepo.save(entity));
    }

    @Override
    public void delete(Long id) throws PblCustomException {
        if (!boardRepo.existsById(id)) {
            throw new PblCustomException("Board does not exist", ErrorCode.BOARD_DOESNT_EXIST, HttpStatus.NOT_FOUND);
        }
        boardRepo.deleteById(id);
    }
}
