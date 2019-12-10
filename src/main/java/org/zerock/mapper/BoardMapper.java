package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper {

	//게시판 객체 모두 가져온다.
	public List<BoardVO> getList();
	
	//게시판 객체 페이징해서 가져온다.
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	//게시판 객체 총 개수를 가져온다.
	public int getTotalCount(Criteria cri);
	
	//게시판 객체 디테일 정보를 가져온다.
	public BoardVO read(Long bno);
	
	//게시판 객체를 추가한다.
	public int insert(BoardVO boardVO);
	
	//게시판 객체를 수정한다.
	public int update(BoardVO boardVO);
	
	//게시판 객체가 존재하는 지 확인한다.
	public int isExistBoard(Long bno);
	
	//게시판 객체를 삭제한다.
	public int remove(Long bno);
}
