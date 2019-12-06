package org.zerock.service;

import java.util.List;

import org.springframework.ui.Model;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardService {

	public List<BoardVO> getList();
	
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	public int getTotalCount(Criteria cri);
	
	public BoardVO read(Long bno);
	
	public boolean boardValidation(BoardVO boardVO, Model model, String type);
	
	public int register(BoardVO boardVO);
	
	public int modify(BoardVO boardVO);
	
}
