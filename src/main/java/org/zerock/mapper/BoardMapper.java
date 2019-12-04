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
}
