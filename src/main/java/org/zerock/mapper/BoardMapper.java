package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper {

	//게시판 객체 모두 가져온다.
	public List<BoardVO> getList();
	
	//게시판 객체 페이징해서 가져온다.
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	//게시판 객체 총 개수
	public int getTotalCount(Criteria cri);
	
	//게시판 객체 디테일 정보
	public BoardVO read(Long bno);
	
	//게시판 추가
	public int insert(BoardVO boardVO);
	
	//게시판 수정
	public int update(BoardVO boardVO);
	
	//게시판이 존재하는 지 확인
	public int isExistBoard(Long bno);
}
